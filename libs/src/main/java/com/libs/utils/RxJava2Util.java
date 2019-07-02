package com.libs.utils;

import android.support.annotation.NonNull;
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.Observer;
//import io.reactivex.Scheduler;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//import org.reactivestreams.Subscription;

/**
 * @ User：mo
 * <>
 * @ 功能：RxJava2工具类
 * <>
 * @ 使用：通过上游发送器创建一个链接器，然后指定订阅一个下游接收器，同时可以指定上下游所在的线程，并且，下游接收器可以写在其他界面，实现跨界面传递数据
 * 1、just：        Observable.just("aaa","bbb").subscribe(new RxJava2Util.KReceiveConsumer<String>());
 * 简单的发送数据，类型任意但必须统一，最多9个
 * 2、from：        Observable.fromArray("aaa","bbb").subscribe(new RxJava2Util.KReceiveConsumer<String>());
 * 基本同上，
 * 3、repeat：      Observable.just("aaa","bbb").repeat().subscribe(new RxJava2Util.KReceiveConsumer<String>());
 * 重复发送，如果不填参数，则会无限重复，那就热闹了，参数填几重复几次
 * 4、repeatWhen：  Observable.just("aaa","bbb").repeatWhen(Function).subscribe(new RxJava2Util.KReceiveConsumer<String>());
 * new Function后，实现里面的有返回值的抽象方法  return objectObservable.delay(1, TimeUnit.SECONDS);第一个参数是时间，第二个参数是时间单位，
 * 可以理解为 间隔多长时间重复一次
 *5、take：           Observable.fromArray("aaa","bbb").take(3).subscribe(new RxJava2Util.KReceiveConsumer<String>());
 * 仅发射前n个数据。
 * <>
 * @ Time：2018/7/13 0013 9:56
 */

public class RxJava2Util {
  ///**
  // * 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
  // */
  //public static final Scheduler S_IO = Schedulers.io();
  ///**
  // * 代表CPU计算密集型的操作, 例如需要大量计算的操作
  // */
  //public static final Scheduler S_COMPUTATION = Schedulers.computation();
  ///**
  // * 代表一个常规的新线程
  // */
  //public static final Scheduler S_NEW_THREAD = Schedulers.newThread();
  ///**
  // * 代表Android的主线程
  // */
  //public static final Scheduler S_MAIN_THREAD = AndroidSchedulers.mainThread();
  //
  ///**
  // * 开启rxjava
  // *
  // * @param subscribe 订阅者==上游发送器  默认在一个新线程里执行
  // * @param observer 观察者=下游接收器  默认在一个主线程里执行
  // * @param <T> 数据类型
  // */
  //public static <T> void actionRxJava(ObservableOnSubscribe<T> subscribe, Observer<T> observer) {
  //  actionRxJava(subscribe, observer, S_NEW_THREAD, S_MAIN_THREAD);
  //}
  //
  ///**
  // * 开启rxjava
  // *
  // * @param subscribe 订阅者==上游发送器
  // * @param observer 观察者=下游接收器
  // * @param subscribeThread 指定发送事件所在线程 订阅者执行的线程
  // * @param observeThread 指定接收事件所在线程  观察者执行的线程
  // * @param <T> 数据类型
  // */
  //public static <T> void actionRxJava(ObservableOnSubscribe<T> subscribe, Observer<T> observer,
  //    Scheduler subscribeThread, Scheduler observeThread) {
  //  if (subscribe == null || observer == null) {
  //    return;
  //  }
  //  getObservable(subscribe)
  //      //指定发送事件所在线程
  //      .subscribeOn(subscribeThread == null ? S_MAIN_THREAD : subscribeThread)
  //      //指定接收事件所在线程
  //      .observeOn(observeThread == null ? S_MAIN_THREAD : observeThread)
  //      .subscribe(observer);
  //}
  //
  ///**
  // * 开启rxjava
  // *
  // * @param subscribe 订阅者==上游发送器  默认在一个新线程里执行
  // * @param consumer 观察者=下游接收器  默认在一个主线程里执行
  // * @param <T> 数据类型
  // */
  //public static <T> void actionRxJava(ObservableOnSubscribe<T> subscribe, Consumer<T> consumer) {
  //  actionRxJava(subscribe, consumer, S_NEW_THREAD, S_MAIN_THREAD);
  //}
  //
  ///**
  // * 开启rxjava
  // *
  // * @param subscribe 订阅者==上游发送器
  // * @param consumer 观察者=下游接收器
  // * @param subscribeThread 指定发送事件所在线程 订阅者执行的线程
  // * @param observeThread 指定接收事件所在线程  观察者执行的线程
  // * @param <T> 数据类型
  // */
  //public static <T> void actionRxJava(ObservableOnSubscribe<T> subscribe, Consumer<T> consumer,
  //    Scheduler subscribeThread, Scheduler observeThread) {
  //  if (subscribe == null || consumer == null) {
  //    return;
  //  }
  //  getObservable(subscribe)
  //      //指定发送事件所在线程
  //      .subscribeOn(subscribeThread == null ? S_MAIN_THREAD : subscribeThread)
  //      //指定接收事件所在线程
  //      .observeOn(observeThread == null ? S_MAIN_THREAD : subscribeThread)
  //      .subscribe(consumer);
  //}
  //
  ///**
  // * 获取链接器
  // *
  // * @param onSubscribe 订阅者==上游发送器
  // * @param <T> 数据类型
  // */
  //public static <T> Observable getObservable(ObservableOnSubscribe<T> onSubscribe) {
  //  return onSubscribe == null ? null : Observable.create(onSubscribe);
  //}
  //
  ///**
  // * 获取链接器
  // *
  // * @param objs 参数最多为9个
  // */
  //public static <T> Observable getObservable(T[] objs) {
  //  return Observable.just(objs);
  //}
  //
  ///**
  // * 订阅者==上游发送器
  // *
  // * @param <T> 数据类型
  // */
  //public static class KSendObservableOnSubscribe<T> implements ObservableOnSubscribe<T> {
  //  /**
  //   * 订阅==在这里发送用户想要处理的数据 e.onNext("数据");可多次发送，
  //   *
  //   * @param e 发射器：可以发出三种类型的事件，通过调用emitter的onNext(T value)、onComplete()和onError(Throwable
  //   * error)就可以分别发出next事件、complete事件和error事件
  //   * 注意事项：
  //   * 1.可以发送无限个onNext，就可接收无限个onNext。
  //   * 2.发送onComplete后，可以继续发送onNext，但不会接收到事件了。
  //   * 3.onError同上
  //   * 4.可以不发送onComplete、onError
  //   * 5.onComplete、onError唯一且互斥，不能多次发送onComplete、onError，也不可发送onComplete后再发送onError，也不可发送onError后再发送onComplete
  //   * @throws Exception
  //   */
  //  @Override
  //  public void subscribe(ObservableEmitter<T> e) throws Exception {
  //
  //  }
  //}
  //
  ///**
  // * 观察者=下游接收器
  // * Consumer只关注onNext事件，其他的事件都会略过
  // *
  // * @param <T> 数据类型
  // */
  //public static class KReceiveConsumer<T> implements Consumer<T> {
  //
  //  @Override public void accept(@NonNull T t) throws Exception {
  //
  //  }
  //}
  //
  ///**
  // * 观察者=下游接收器
  // *
  // * @param <T> 数据类型
  // */
  //public static class KReceiveObserver<T> implements Observer<T> {
  //  /**
  //   * 当接收到的时候  这时候可以截断
  //   * Disposable ：可处理的，可以理解为可断开的，disposable.dispose()方法被调用后，断开连接，接收方不再接收事件，但是发送方可以继续
  //   */
  //  @Override
  //  public void onSubscribe(Disposable disposable) {
  //    LogUtil.i("接收");
  //  }
  //
  //  /**
  //   * 执行
  //   */
  //  @Override
  //  public void onNext(T t) {
  //    LogUtil.i("执行");
  //  }
  //
  //  /**
  //   * 报错了
  //   */
  //  @Override
  //  public void onError(Throwable throwable) {
  //    LogUtil.i("报错");
  //  }
  //
  //  /**
  //   * 完成
  //   */
  //  @Override
  //  public void onComplete() {
  //    LogUtil.i("完成");
  //  }
  //}
}
