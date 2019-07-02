package com.libs.utils.systemUtils;

import android.os.Build;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * @ author：mo
 * @ data：2019/2/13:11:57
 * @ 功能：
 */
public class CpuUtil {
    /**
     * 得到CPU核心数
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            });
            return files.length;
        } catch (Exception e) {
            return 1;
        }
    }

    public static String getCpuAbi() {
        String cpuAbi = Build.CPU_ABI;
        if (cpuAbi == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String[] abis = Build.SUPPORTED_ABIS;
                if (abis != null) {
                    cpuAbi = abis[0];
                }
            }
        }
        return cpuAbi;
    }

    public static boolean matchAbi(@NonNull String... abis) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.SUPPORTED_ABIS != null) {
            for (String supportedAbi : Build.SUPPORTED_ABIS) {
                for (String abi : abis) {
                    if (supportedAbi.equals(abi)) {
                        return true;
                    }
                }
            }
        } else {
            for (String abi : abis) {
                if (abi != null && (abi.equals(Build.CPU_ABI) || abi.equals(Build.CPU_ABI2))) {
                    return true;
                }
            }
        }
        return false;
    }
}
