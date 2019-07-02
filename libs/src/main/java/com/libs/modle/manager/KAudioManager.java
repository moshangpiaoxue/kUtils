package com.libs.modle.manager;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.IntRange;

import com.libs.k;



/**
 * @ author：mo
 * @ data：2019/1/5
 * @ 功能：音频管理器
 */
public class KAudioManager {
    /**
     * 获取音频管理器
     */
    public static AudioManager getAudioManager() {
        return (AudioManager) k.app().getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 调整手机音量
     *
     * @param direction 方向：AudioManager.ADJUST_LOWER==调小一个单位、AudioManager.ADJUST_RAISE==调大一个单位
     * @param flags     附加参数:FLAG_PLAY_SOUND== 调整音量时播放声音、FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个(设置这个参数时只会显示音量条，不会改变音量)
     */
    public static void adjustVolume(int direction, int flags) {
        getAudioManager().adjustVolume(direction, flags);
    }

    /**
     * 调整手机音量
     *
     * @param streamType 声音类型:STREAM_ALARM：提示音/手机闹铃/警报 STREAM_MUSIC：手机音乐  STREAM_RING：电话铃声 STREAM_SYSTEAM：手机系统
     *                   STREAM_DTMF：音调 STREAM_NOTIFICATION：系统提示  STREAM_VOICE_CALL:语音电话
     * @param direction  方向：AudioManager.ADJUST_LOWER==调小一个单位、AudioManager.ADJUST_RAISE==调大一个单位
     * @param flags      附加参数:FLAG_PLAY_SOUND== 调整音量时播放声音、FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
     */
    public static void adjustStreamVolume(int streamType, int direction, int flags) {
        getAudioManager().adjustStreamVolume(streamType, direction, flags);
    }

    /**
     * 设置音量大小
     *
     * @param streamType 声音类型:STREAM_ALARM：提示音/手机闹铃/警报 STREAM_MUSIC：手机音乐 STREAM_RING：电话铃声 STREAM_SYSTEAM：手机系统
     *                   STREAM_DTMF：音调 STREAM_NOTIFICATION：系统提示   STREAM_VOICE_CALL:语音电话
     * @param index      音量大小（此值要处于要设置音量类型的大小区间里）
     * @param flags      附加参数:FLAG_PLAY_SOUND== 调整音量时播放声音、FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
     */
    public static void setStreamVolume(int streamType, @IntRange(from = 0, to = 7) int index, int flags) {
        getAudioManager().setStreamVolume(streamType, index, flags);
    }

    /**
     * 获取某个声音类型最大音量
     *
     * @param streamType 声音类型:STREAM_ALARM：提示音/手机闹铃/警报 STREAM_MUSIC：手机音乐 STREAM_RING：电话铃声 STREAM_SYSTEAM：手机系统
     *                   STREAM_DTMF：音调 STREAM_NOTIFICATION：系统提示   STREAM_VOICE_CALL:语音电话
     * @return 最大值为7, 最小值为0
     */
    public static int getStreamMaxVolume(int streamType) {
        return getAudioManager().getStreamMaxVolume(streamType);
    }

    /**
     * 获取某个声音类型当前音量
     *
     * @param streamType 声音类型:STREAM_ALARM：提示音/手机闹铃/警报 STREAM_MUSIC：手机音乐 STREAM_RING：电话铃声 STREAM_SYSTEAM：手机系统
     *                   STREAM_DTMF：音调 STREAM_NOTIFICATION：系统提示   STREAM_VOICE_CALL:语音电话
     * @return 最大值为7, 最小值为0
     */
    public static int getStreamVolume(int streamType) {
        return getAudioManager().getStreamVolume(streamType);
    }

    /**
     * 设置声音模式
     *
     * @param mode 声音模式:MODE_NORMAL(普通), MODE_RINGTONE(铃声) ,MODE_IN_CALL(打电话)，MODE_IN_COMMUNICATION(通话)
     */
    public static void setMode(int mode) {
        getAudioManager().setMode(mode);
    }

    /**
     * 获取声音模式
     *
     * @return
     */
    public static int getMode() {
        return getAudioManager().getMode();
    }

    /**
     * 设置铃声模式
     *
     * @param streamType 铃声模式: RINGER_MODE_NORMAL（普通）、 RINGER_MODE_SILENT（静音）、RINGER_MODE_VIBRATE（震动）
     */
    public static void setRingerMode(int streamType) {
        getAudioManager().setRingerMode(streamType);
    }

    /**
     * 获取铃声模式
     *
     * @return
     */
    public static int getRingerMode() {
        return getAudioManager().getRingerMode();
    }

    /**
     * 将手机某个声音类型设置为静音 也可以调用setStreamVolume（）方法
     *
     * @param streamType 声音类型:STREAM_ALARM：提示音/手机闹铃/警报 STREAM_MUSIC：手机音乐 STREAM_RING：电话铃声 STREAM_SYSTEAM：手机系统
     *                   STREAM_DTMF：音调 STREAM_NOTIFICATION：系统提示   STREAM_VOICE_CALL:语音电话
     * @param state
     */
    public static void setStreamMute(int streamType, boolean state) {
        getAudioManager().setStreamMute(streamType, state);
    }

    /**
     * 否打开扩音器
     *
     * @param on
     */
    public static void setSpeakerphoneOn(boolean on) {
        getAudioManager().setSpeakerphoneOn(on);
    }

    /**
     * 是否让麦克风静音
     *
     * @param on
     */
    public static void setMicrophoneMute(boolean on) {
        getAudioManager().setMicrophoneMute(on);
    }


    /**
     * 是否有音乐处于活跃状态
     *
     * @return
     */
    public static Boolean isMusicActive() {
        return getAudioManager().isMusicActive();
    }

    /**
     * 是否插入了耳机
     *
     * @return
     */
    public static Boolean isWiredHeadsetOn() {
        return getAudioManager().isWiredHeadsetOn();
    }



    /**
     * 调整最相关的流的音量，或者给定的回退流
     *
     * @param direction
     * @param suggestedStreamType
     * @param flags
     */
    public static void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) {
        getAudioManager().adjustSuggestedStreamVolume(direction, suggestedStreamType, flags);
    }

    /**
     * 给音频硬件设置一个varaible数量的参数值。
     *
     * @param keys
     * @return
     */
    public static String getParameters(String keys) {
        return getAudioManager().getParameters(keys);
    }

    /**
     * 是否该用户的振动设置为振动类型
     *
     * @param vibrateType
     * @return
     */
    public static int getVibrateSetting(int vibrateType) {
        return getAudioManager().getVibrateSetting(vibrateType);
    }

    /**
     * A2DP蓝牙耳机音频路由是否打开
     *
     * @return
     */
    public static Boolean isBluetoothA2dpOn() {
        return getAudioManager().isBluetoothA2dpOn();
    }
    public static void setBluetoothSco(boolean on) {
        if (on){
            getAudioManager().startBluetoothSco();
        }else {
            getAudioManager().stopBluetoothSco();
        }

    }
    /**
     * 要求使用蓝牙SCO耳机进行通讯
     * @param on
     */
    public static void setBluetoothScoOn(boolean on) {
         getAudioManager().setBluetoothScoOn(on);
    }

    /**
     * 当前平台是否支持使用SCO的关闭调用用例
     *
     * @return
     */
    public static Boolean isBluetoothScoAvailableOffCall() {
        return getAudioManager().isBluetoothScoAvailableOffCall();
    }

    /**
     * 通信是否使用蓝牙SCO
     *
     * @return
     */
    public static Boolean isBluetoothScoOn() {
        return getAudioManager().isBluetoothScoOn();
    }

    /**
     * 加载声音效果
     */
    public static void loadSoundEffects() {
        getAudioManager().loadSoundEffects();
    }

    /**
     * 播放声音效果
     * @param effectType
     * @param volume
     */
    public static void playSoundEffect(int effectType, float volume) {
        getAudioManager().playSoundEffect(effectType,volume );
    }
    /**
     * 监听音频失去焦点
     *
     * @param listener
     */
    public static void abandonAudioFocus(AudioManager.OnAudioFocusChangeListener listener) {
        getAudioManager().abandonAudioFocus(listener);
    }

    /**
     * 请求音频的焦点
     * @param listener
     * @param streamType
     * @param durationHint
     */
    public static void requestAudioFocus(AudioManager.OnAudioFocusChangeListener listener,int streamType,int durationHint) {
        getAudioManager().requestAudioFocus(listener,streamType,durationHint);
    }

    /**
     * 注册一个组件MEDIA_BUTTON意图的唯一接收机
     * @param eventReceiver
     */
    public static void registerMediaButtonEventReceiver(ComponentName eventReceiver) {
        getAudioManager().registerMediaButtonEventReceiver(eventReceiver);
    }

    /**
     * 卸载音效
     */
    public static void unloadSoundEffects() {
        getAudioManager().unloadSoundEffects();
    }
}
