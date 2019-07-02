package com.libs.modle.manager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.libs.k;

import java.io.FileDescriptor;
import java.io.IOException;



/**
 * @ author：mo
 * @ data：2019/1/8
 * @ 功能：媒体播放器管理器
 * <p>
 * void statr()：开始或恢复播放。
 * void stop()：停止播放。
 * void pause()：暂停播放。
 * int getDuration()：获取流媒体的总播放时长，单位是毫秒。
 * int getCurrentPosition()：获取当前流媒体的播放的位置，单位是毫秒。
 * void seekTo(int msec)：设置当前MediaPlayer的播放位置，单位是毫秒。
 * void setLooping(boolean looping)：设置是否循环播放。
 * boolean isLooping()：判断是否循环播放。
 * boolean  isPlaying()：判断是否正在播放。
 * void prepare()：同步的方式装载流媒体文件。
 * void prepareAsync()：异步的方式装载流媒体文件。
 * void release ()：回收流媒体资源。
 * void setAudioStreamType(int streamtype)：设置播放流媒体类型。
 * void setWakeMode(Context context, int mode)：设置CPU唤醒的状态。
 * setNextMediaPlayer(MediaPlayer next)：设置当前流媒体播放完毕，下一个播放的MediaPlayer。
 * ///////////////////////回调///////////////////////////////
 * setOnCompletionListener(MediaPlayer.OnCompletionListener listener)：当流媒体播放完毕的时候回调。
 * setOnErrorListener(MediaPlayer.OnErrorListener listener)：当播放中发生错误的时候回调。
 * setOnPreparedListener(MediaPlayer.OnPreparedListener listener)：当装载流媒体完毕的时候回调。
 * setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener listener)：当使用seekTo()设置播放位置的时候回调。
 */
public class KMediaPlayerManager {

    /**
     * 获取媒体播放器
     *
     * @return
     */
    public static MediaPlayer getMediaPlayer() {
        return new MediaPlayer();
    }

    /**
     * 开启播放
     * 异步装载，为了避免还没有装载完成就调用start()而报错,避免装载超时引发ANR
     *
     * @param mediaPlayer
     */
    public static void start(final MediaPlayer mediaPlayer) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 通过异步的方式装载媒体资源
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕回调
                mediaPlayer.start();
            }
        });
    }

    /**
     * 停止播放
     *
     * @param mediaPlayer
     * @return
     */
    public static MediaPlayer stop(MediaPlayer mediaPlayer) {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        return mediaPlayer;
    }

    /**
     * 获取媒体播放器
     *
     * @param rawId 项目自带音频文件
     * @return
     */
    public static MediaPlayer getMediaPlayer(int rawId) {
        return MediaPlayer.create(k.app(), rawId);
    }

    /**
     * 获取媒体播放器
     *
     * @param uri
     * @return
     */
    public static MediaPlayer getMediaPlayer(Uri uri) {
//        MediaPlayer mediaPlayer = getMediaPlayer();
//        try {
//            mediaPlayer.setDataSource(k.app(),uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return mediaPlayer;
        return MediaPlayer.create(k.app(), uri);
    }

    /**
     * 获取媒体播放器
     *
     * @param path 本地路径/网路路径
     * @return
     */
    public static MediaPlayer getMediaPlayer(String path) {
        MediaPlayer mediaPlayer = getMediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    /**
     * 获取媒体播放器
     *
     * @param fd AssetFileDescriptor fileDescriptor = getAssets().openFd("rain.mp3");
     * @return
     */
    public static MediaPlayer getMediaPlayer(FileDescriptor fd) {
        MediaPlayer mediaPlayer = getMediaPlayer();
        try {
            mediaPlayer.setDataSource(fd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    /**
     * 获取媒体播放器
     *
     * @param fd     AssetFileDescriptor fileDescriptor = getAssets().openFd("rain.mp3");
     * @param offset
     * @param length
     * @return
     */
    public static MediaPlayer getMediaPlayer(FileDescriptor fd, long offset, long length) {
        MediaPlayer mediaPlayer = getMediaPlayer();
        try {
            mediaPlayer.setDataSource(fd, offset, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
}
