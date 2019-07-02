package com.libs.modle.manager;

import android.media.MediaRecorder;

import com.libs.KApplication;
import com.libs.utils.dataUtil.StringUtil;
import com.libs.utils.dataUtil.date.DateUtil;
import com.libs.utils.fileUtil.FileUtil;
import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.systemUtils.storageUtil.SDCardUtil;

import java.io.File;
import java.io.IOException;



/**
 * @ author：mo
 * @ data：2019/1/8
 * @ 功能：录音
 */
public class KMp3Recorder {
    /**
     * 音频名
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 录音器
     */
    private MediaRecorder mediaRecorder;
    /**
     * 音频文件
     */
    private File mAudioFile;
    /**
     * 录音起止时间
     */
    private long mStartRecordTime, mStopRecordTime;


    public KMp3Recorder(String name) {
        this.name = name;

        path = SDCardUtil.getSDCardPath() + "/sounds/" +
                (StringUtil.isEmpty(name) ? DateUtil.getString("yyyy_MM_dd_HH_mm_ss") : name) + ".mp3";
    }


    public boolean doStart() {
        try {
            //创建mediaRecorder
            mediaRecorder = new MediaRecorder();
            //创建录音文件
            mAudioFile = new File(path);
            mAudioFile.getParentFile().mkdirs();
            mAudioFile.createNewFile();
            //配置Media Recorder
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioEncodingBitRate(96000);
            //设置录音文件的位置
            mediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());
            //开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
            //记录开始录音时间 用于统计时长
            mStartRecordTime = System.currentTimeMillis();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    public boolean doStop() {
        //停止录音
        try {
            mediaRecorder.stop();
            //记录停止时间
            mStopRecordTime = System.currentTimeMillis();
            //只接受超过三秒的录音
            final int second = (int) (mStopRecordTime - mStartRecordTime) / 1000;
            if (second > 3) {
                KApplication.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.i(name + "==录音成功");
                    }
                });
            } else {
                LogUtil.i(name + "==不足3秒，录音失败，删除新文件文件");
                FileUtil.deleteFile(mAudioFile);
            }
            //停止成功
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public File getmAudioFile() {
        return mAudioFile;
    }

    public long getmStartRecordTime() {
        return mStartRecordTime;
    }

    public long getmStopRecordTime() {
        return mStopRecordTime;
    }
}
