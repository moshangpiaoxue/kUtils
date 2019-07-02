package com.libs.utils.systemUtils.fingerUtil;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;

import com.libs.utils.logUtils.LogUtil;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


/**
 * @ author：mo
 * @ data：2019/5/16：11:03
 * @ 功能：指纹识别帮助类
 */
@RequiresApi(Build.VERSION_CODES.M)
public class CryptoObjectHelper {
    /**
     * 秘钥key，唯一的，绝对路径+类名
     */
    static final String KEY_NAME = "mo.klib.utils.systemUtils.fingerUtil.CryptoObjectHelper";
    /**
     * 秘钥库名
     */
    static final String KEYSTORE_NAME = "AndroidKeyStore";
    /**
     * 加密算法 (别动)
     */
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    /**
     * 模式 (别动)
     */
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    /**
     * 填充模式 (别动)
     */
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    /**
     * 转换规则 (别动)
     */
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" + BLOCK_MODE + "/" + ENCRYPTION_PADDING;
    /**
     * 秘钥
     */
    final KeyStore mKeyStore;

    /**
     * 创建keystore
     */
    public CryptoObjectHelper() throws Exception {
        mKeyStore = KeyStore.getInstance(KEYSTORE_NAME);
        mKeyStore.load(null);
        LogUtil.i("密码库" + mKeyStore.toString());
    }

    /**
     * 获取加密对象
     */
    public FingerprintManager.CryptoObject buildCryptoObject() throws Exception {
        Cipher cipher = createCipher(true);
        return new FingerprintManager.CryptoObject(cipher);
    }

    /**
     * 密码生成(递归实现)
     *
     * @param retry 是否重试
     * @return
     * @throws Exception
     */
    Cipher createCipher(boolean retry) throws Exception {
        Key key = GetKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        try {
            cipher.init(Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE, key);
        } catch (KeyPermanentlyInvalidatedException e) {
            //删除获取的码，保留生成的密码
            mKeyStore.deleteEntry(KEY_NAME);
            if (retry) {
                createCipher(false);
            } else {
                throw new Exception("Could not create the cipher for fingerprint authentication.", e);
            }
        }
        LogUtil.i("生成的密码" + cipher.toString());
        return cipher;
    }

    /**
     * 获取秘钥密码（指纹码）
     */
    Key GetKey() throws Exception {
        Key secretKey;
        if (!mKeyStore.isKeyEntry(KEY_NAME)) {
            CreateKey();
        }
        secretKey = mKeyStore.getKey(KEY_NAME, null);
        LogUtil.i("指纹码" + secretKey.toString());
        return secretKey;
    }

    /**
     * 获取秘钥生成器，用于生成秘钥
     */
    void CreateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        keyGen.generateKey();
        LogUtil.i("秘钥" + keyGen.toString());
    }
}