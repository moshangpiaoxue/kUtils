package com.libs.view.video;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

import com.libs.utils.logUtils.LogUtil;
import com.libs.utils.task.threadPool.ThreadPoolUtil;

import java.io.IOException;



/**
 * @ User：mo
 * <>
 * @ 功能：自定义视频播放
 * <>
 * @ 入口：
 * <>
 * @ Time：2018/7/31 0031 14:37
 */

public class KVideoTextureView extends TextureView {
  /**
   * 影音播放器
   */
  private MediaPlayer mMediaPlayer;
  /**
   * 是否播放完成
   */
  private boolean isPlayFinished = false;
  /**
   * 播放器状态
   */
  private MediaState mMediaState;

  /**
   * 该接口提供给外部回调,监听播放器的状态变化
   */
  private OnStateChangeListener onStateChangeListener;
  private Boolean isCanPaly = false;
  /**
   * 全屏标记
   */
  private boolean isFullScreen = false;

  public KVideoTextureView(Context context, AttributeSet attrs) {
    super(context, attrs);
    lis();
  }

  public KVideoTextureView(Context context) {
    super(context);
    lis();
  }

  private void lis() {
    //设置监听
    setSurfaceTextureListener(new SurfaceTextureListener() {
      /**
       * View准备就绪
       */
      @Override
      public void onSurfaceTextureAvailable(SurfaceTexture surface1, int width, int height) {
        //创建了Surface渲染器
        Surface surface = new Surface(surface1);
        //创建播放器
        if (mMediaPlayer == null) {
          mMediaPlayer = new MediaPlayer();
        }
        /**
         * 预加载监听,开始预加载
         */
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          //预加载
          @Override public void onPrepared(MediaPlayer mp) {
            start();
            isCanPaly = true;
            if (onStateChangeListener != null) {
              onStateChangeListener.onBuffering(0);
            }
          }
        });
        /**
         * 添加监听-警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化,(可以监听:开始缓冲、缓冲结束、播放第一帧等等视频状态)
         */
        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
          /**
           *指示信息或警告
           * @param mp  播放器
           * @param what  信息或警告的类型
           *              MEDIA_INFO_UNKNOWN:               未指定的媒体播放器信息
           *              MEDIA_INFO_VIDEO_TRACK_LAGGING:   视频对于解码器来说太复杂了：它不能足够快地解码帧。可能只有音频在这个阶段播放得很好。
           *              MEDIA_INFO_VIDEO_RENDERING_START: 开始播放，玩家刚刚按下第一个视频帧进行渲染。
           *              MEDIA_INFO_BUFFERING_START：      开始缓存，播放器暂时暂停播放，以缓冲更多的数据。
           *              MEDIA_INFO_BUFFERING_END：        缓存完成，播放器在填充缓冲器后恢复播放。
           *              703：                             带宽可用
           *              MEDIA_INFO_BAD_INTERLEAVING：     坏交织意味着媒体被完全交错或根本不交织，例如，所有视频样本先是所有音频样本，然后是所有音频样本。视频正在播放，但很多磁盘搜索可能会发生（没看懂）
           *              MEDIA_INFO_NOT_SEEKABLE：         数据源没找到
           *              MEDIA_INFO_METADATA_UPDATE：      一组新的元数据是可用的。
           *              MEDIA_INFO_UNSUPPORTED_SUBTITLE： 媒体框架不支持字幕跟踪。
           *              MEDIA_INFO_SUBTITLE_TIMED_OUT：   阅读字幕轨迹太长
           *
           * @param extra 一个额外的代码，特定于信息。通常依赖于实现。
           * @return 如果该方法处理了信息，则false，如果它不返回false，或者根本没有OnIfLististor，将导致信息被丢弃。
           */
          @Override public boolean onInfo(MediaPlayer mp, int what, int extra) {
            //状态监听不为空 并且不处于暂停状态
            if (onStateChangeListener != null && mMediaState != MediaState.PAUSE) {
              //开始缓存
              if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                onStateChangeListener.onBuffering(0);
                //缓存完成
              } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                onStateChangeListener.onBuffering(100);
                //第一次渲染即开始播放
              } else if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                onStateChangeListener.onSeek(true, mMediaPlayer.getDuration(), 0, 0);
              }
            }
            return false;
          }
        });
        /**
         *缓冲监听
         */
        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
          /**
           * 缓冲更新
           * @param mp  播放器
           * @param percent 已缓冲或播放的内容的百分比（0～100）
           */
          @Override public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (onStateChangeListener != null) {
              //有些手机缓冲完成,不会回调OnInfolistener,为了保险起见,我们在这个地方增加了这个处理
              onStateChangeListener.onBuffering(percent);
              //缓存完成并且不处于暂停状态
              if (percent == 100 && mMediaState != MediaState.PAUSE) {
                mMediaState = MediaState.PREPARING;
                onStateChangeListener.onBuffering(100);
              }
              //如果处于播放状态
              if (mMediaState == MediaState.PLAYING) {
                if (isPlayFinished) {
                  return;
                }
                //继续播放
                onStateChangeListener.onSeek(false, mMediaPlayer.getDuration(),
                    mMediaPlayer.getCurrentPosition(),
                    (int) (mMediaPlayer.getCurrentPosition() / new Double(
                        mMediaPlayer.getDuration()) * 100));
              }
            }
          }
        });
        /**
         *播放完成
         */
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
          @Override public void onCompletion(MediaPlayer mp) {
            if (onStateChangeListener != null) {
              if (mMediaState != MediaState.PLAYING) {
                return;
              }

              onStateChangeListener.playFinish();
              isPlayFinished = true;
            }
          }
        });

        /**
         * 播放异常(意外情况:断网、网络不稳定等等......)
         */
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
          @Override public boolean onError(MediaPlayer mp, int what, int extra) {
            mMediaPlayer.reset();
            mMediaState = MediaState.INIT;
            if (onStateChangeListener != null) {
              onStateChangeListener.onError(what);
            }

            return false;
          }
        });
        mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
          @Override public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            updateTextureViewSizeCenterCrop(mp.getVideoWidth(), mp.getVideoHeight());
          }
        });
        //绑定视图
        mMediaPlayer.setSurface(surface);
        //记录状态
        mMediaState = MediaState.INIT;
        if (onStateChangeListener != null) {
          onStateChangeListener.onCreate();
        }
      }

      /**
       * view的缓冲大小变化
       */
      @Override
      public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

      }

      /**
       * view即将被销毁
       */
      @Override
      public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mMediaPlayer.pause();
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaState = MediaState.RELEASE;
        if (onStateChangeListener != null) {
          onStateChangeListener.onDestroy(surface);
        }
        return false;
      }

      /**
       * view更新了（数据刷新了，可以理解为正在播放）
       *
       */

      @Override
      public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        if (onStateChangeListener != null) {
          onStateChangeListener.onSeek(false, mMediaPlayer.getDuration(),
              mMediaPlayer.getCurrentPosition(),
              (int) (mMediaPlayer.getCurrentPosition() / new Double(mMediaPlayer.getDuration())
                  * 100));
        }
      }
    });
  }

  /**
   * 重新计算video的显示位置，裁剪后全屏显示
   */
  private void updateTextureViewSizeCenterCrop(int mVideoWidth, int mVideoHeight) {

    float sx = (float) getWidth() / (float) mVideoWidth;
    float sy = (float) getHeight() / (float) mVideoHeight;

    Matrix matrix = new Matrix();
    float maxScale = Math.max(sx, sy);

    //第1步:把视频区移动到View区,使两者中心点重合.
    matrix.preTranslate((getWidth() - mVideoWidth) / 2, (getHeight() - mVideoHeight) / 2);

    //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
    matrix.preScale(mVideoWidth / (float) getWidth(), mVideoHeight / (float) getHeight());

    //第3步,等比例放大或缩小,直到视频区的一边超过View一边, 另一边与View的另一边相等. 因为超过的部分超出了View的范围,所以是不会显示的,相当于裁剪了.
    matrix.postScale(maxScale, maxScale, getWidth() / 2, getHeight() / 2);//后两个参数坐标是以整个View的坐标系以参考的

    setTransform(matrix);
    postInvalidate();
  }

  /**
   * 横竖屏切换
   */
  public void setScreenVOrH() {
    if (getISFullScreen()) {
      setHalfScreen();
    } else {
      setFullScreen();
    }
  }

  /**
   * 切换为半屏
   */
  public void setHalfScreen() {
    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    isFullScreen = false;
    //设置播放视频控件的大小
    ViewGroup.LayoutParams textureViewLa = getLayoutParams();
    int videoH = getMeasuredHeight();
    textureViewLa.height = videoH;
    textureViewLa.width = ViewGroup.LayoutParams.MATCH_PARENT;
  }

  /**
   * 切换为全屏
   */
  public void setFullScreen() {
    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    isFullScreen = true;
    //设置控件大小
    ViewGroup.LayoutParams params = getLayoutParams();

    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
  }

  /**
   * 获取当前横竖屏状态
   */
  public Boolean getISFullScreen() {
    return isFullScreen;
  }

  /**
   * 初始化
   *
   * @param videoUrl 视频地址
   */
  public void init(String videoUrl) {
    init(videoUrl, false);
  }

  /**
   * 初始化，
   *
   * @param videoUrl 视频地址
   * @param isLooping 是否循环播放 默认否
   */
  public void init(String videoUrl, Boolean isLooping) {
    if (mMediaState == MediaState.RELEASE) {
      stop();
      return;
    }

    try {
      if (mMediaPlayer == null) {
        mMediaPlayer = new MediaPlayer();
      }
      mMediaPlayer.reset();
      //设置是否循环播放
      mMediaPlayer.setLooping(isLooping);
      //设置播放地址
      mMediaPlayer.setDataSource(videoUrl);
      //开始预加载
      mMediaPlayer.prepareAsync();
      if (onStateChangeListener != null) {
        onStateChangeListener.onPrepare();
      }
      mMediaState = MediaState.PREPARING;
    } catch (IOException e) {
      mMediaPlayer.reset();
      mMediaState = MediaState.INIT;
    }
  }

  /**
   * 停止
   */
  public void stop() {

    ThreadPoolUtil.runTaskInThread(new Runnable() {
      @Override public void run() {
        try {

          if (mMediaState == MediaState.INIT) {
            return;
          }
          if (mMediaState == MediaState.PREPARING) {
            mMediaPlayer.reset();
            mMediaState = MediaState.INIT;
          }
          if (mMediaState == MediaState.PAUSE) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaState = MediaState.INIT;
          }
          if (mMediaState == MediaState.PLAYING) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaState = MediaState.INIT;
          }
        } catch (Exception e) {
          e.printStackTrace();
          if (null != mMediaPlayer) {
            mMediaPlayer.reset();
            mMediaState = MediaState.INIT;
          }
        } finally {
          Message.obtain(new Handler(
          ) {
            @Override public void handleMessage(Message msg) {
              super.handleMessage(msg);
              switch (msg.what) {
                case 0:
                  if (onStateChangeListener != null) {
                    onStateChangeListener.onError(msg.what);
                  }
                  break;
                default:
                  break;
              }
            }
          }, 0).sendToTarget();
        }
      }
    });
  }

  /**
   * 启动播放(开始播放)
   */
  public void start() {
    if (isCanPaly) {
      isPlayFinished = false;
      mMediaPlayer.start();
      //纪录播放器的状态
      mMediaState = MediaState.PLAYING;
    } else {
      new Handler().postDelayed(new Runnable() {
        @Override public void run() {
          start();
        }
      }, 200);
    }
  }

  /**
   * 暂停
   */
  public void pause() {
    mMediaPlayer.pause();
    mMediaState = MediaState.PAUSE;
    if (onStateChangeListener != null) {
      onStateChangeListener.onPause();
    }
  }

  /**
   * 调整到指定位置
   *
   * @param progress 进度（0-100）
   */
  public void setSeekTo(int progress) {
    mMediaPlayer.seekTo((int) (progress / 100.f * mMediaPlayer.getDuration()));
    LogUtil.i("调整进度为===" + (int) (progress / 100.f * mMediaPlayer.getDuration()) + "\n "
        + "现在获取当前的进度==" + mMediaPlayer.getCurrentPosition());
  }

  /**
   * 设置状态变化监听
   */
  public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
    this.onStateChangeListener = onStateChangeListener;
  }

  /**
   * 获取视频长度（单位：秒）
   */
  public int getMaxDuration() {
    return mMediaPlayer.getDuration();
  }

  /**
   * 获取目前状态
   */
  public MediaState getMediaState() {
    return mMediaState;
  }

  /**
   * n
   * 播放器状态
   */
  public enum MediaState {
    //初始化
    INIT,
    //准备
    PREPARING,
    //播放
    PLAYING,
    //暂停
    PAUSE,
    //释放资源
    RELEASE;
  }

  /**
   * 该接口提供给外部回调,监听播放器的状态变化
   */
  public interface OnStateChangeListener {
    /**
     * 创建
     */
    void onCreate();

    /**
     * 预加载
     */
    void onPrepare();

    /**
     * 缓存
     */
    void onBuffering(int percent);

    /**
     * 播放
     *
     * @param isBegin 开始提供视频信息，可理解为开始渲染第一帧 （一般不用）
     * @param max 视频长度（秒）
     * @param progress 当前进度（秒）
     * @param progressPrercentage 当前进度百分比
     */
    void onSeek(Boolean isBegin, int max, int progress, int progressPrercentage);

    /**
     * 暂停
     */
    void onPause();

    /**
     * 出错了
     */
    void onError(int what);

    /**
     * 播放完成
     */
    void playFinish();

    /**
     * 销毁
     */
    void onDestroy(SurfaceTexture surface);
  }
}
