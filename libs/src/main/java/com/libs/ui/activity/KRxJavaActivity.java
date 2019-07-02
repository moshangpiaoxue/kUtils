package com.libs.ui.activity;

//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.disposables.Disposable;

/**
 * @ User：mo
 * <>
 * @ 功能：RxJava相关activity基类
 * <>
 * @ 入口：
 * <>
 * @ Time：2018/7/13 0013 10:27
 */

public class KRxJavaActivity extends KPermissionsActivity {
  //protected Disposable disposable = null;

  ///**
  // * 开启rxjava
  // * 默认新开线程处理发送，主线程处理接收
  // */
  //protected void actionRxJava(final Object obj) {
  //  Observable.create(new RxJava2Util.KSendObservableOnSubscribe() {
  //    @Override public void subscribe(ObservableEmitter e) throws Exception {
  //      super.subscribe(e);
  //      rxSend(e, obj);
  //    }
  //  }).subscribeOn(RxJava2Util.S_NEW_THREAD)
  //      .observeOn(RxJava2Util.S_MAIN_THREAD)
  //      .subscribe(new RxJava2Util.KReceiveObserver<Object>() {
  //        @Override public void onSubscribe(@NonNull Disposable d) {
  //          rxOnSubscribe(d);
  //        }
  //
  //        @Override public void onNext(@NonNull Object t) {
  //          rxOnNext(t);
  //        }
  //
  //        @Override public void onError(@NonNull Throwable e) {
  //          rxOnError(e);
  //        }
  //
  //        @Override public void onComplete() {
  //          rxOnComplete();
  //        }
  //      });
  //}

  ///**
  // * 处理、发送数据  可发送多次
  // */
  //protected void rxSend(ObservableEmitter<Object> e, Object obj) {
  //}
  //
  ///**
  // * 接收完成
  // */
  //protected void rxOnComplete() {
  //}
  //
  ///**
  // * 接收报错
  // */
  //protected void rxOnError(Throwable e) {
  //}
  //
  ///**
  // * 接收数据  可接收多次
  // */
  //protected void rxOnNext(Object obj) {
  //}
  //
  ///**
  // * 接收数据  可断开,d.dispose();执行后，rxOnNext、rxOnError、rxOnComplete 这三个方法都不执行
  // */
  //protected void rxOnSubscribe(Disposable d) {
  //  disposable = d;
  //}
  //
  ///**
  // * 在界面销毁的时候，截断接收
  // */
  //@Override protected void onDestroy() {
  //  super.onDestroy();
  //  if (disposable != null) {
  //    disposable.dispose();
  //  }
  //}
}
