package com.windfallsheng.myrxjava2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

public class CombineActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = CombineActivity.class.getSimpleName();

    private TextView tvMerge, tvConcat, tvConcatMap, tvBuffer, tvGroupBy, tvScan, tvWindow,
            tvIntervalRange, tvRange, tvRepeat;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CombineActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);
        tvMerge = findViewById(R.id.textview_merge);
        tvConcat = findViewById(R.id.textview_concat);
        tvConcatMap = findViewById(R.id.textview_concatmap);
        tvBuffer = findViewById(R.id.textview_buffer);
        tvGroupBy = findViewById(R.id.textview_groupby);
        tvScan = findViewById(R.id.textview_scan);
        tvWindow = findViewById(R.id.textview_window);
//        tvIntervalRange = findViewById(R.id.textview_intervalrange);
//        tvRange = findViewById(R.id.textview_range);
//        tvRepeat = findViewById(R.id.textview_Repeat);

        tvMerge.setOnClickListener(this);
        tvConcat.setOnClickListener(this);
        tvConcatMap.setOnClickListener(this);
        tvBuffer.setOnClickListener(this);
        tvGroupBy.setOnClickListener(this);
        tvScan.setOnClickListener(this);
        tvWindow.setOnClickListener(this);
//        tvInterval.setOnClickListener(this);
//        tvIntervalRange.setOnClickListener(this);
//        tvRange.setOnClickListener(this);
//        tvRepeat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_merge:
                merge();
                break;
            case R.id.textview_concat:
                concat();
                break;
            case R.id.textview_concatmap:
                concatMap();
                break;
            case R.id.textview_buffer:
                buffer();
                break;
            case R.id.textview_groupby:
                groupBy();
                break;
            case R.id.textview_scan:
                scan();
                break;
            case R.id.textview_window:
                window();
                break;
            case R.id.textview_intervalrange:
                intervalRange();
                break;
            case R.id.textview_range:
                range();
                break;
            case R.id.textview_Repeat:
                repeat();
                break;
            default:
                break;
        }
    }

    /**
     * create an Observable from scratch by calling observer methods programmatically
     * <p>
     * 通过编程调用observer方法从头创建一个Observable
     */
    private void merge() {
        Observable observable1 = Observable.just(9527, 1001, 1002);
        Observable observable2 = Observable.just("C", "D", "E");
        Observable observable3 = Observable.just(1, 2, 3);

        Observable.merge(observable1, observable2, observable3)
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:merge#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(TAG, "metod:merge#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:merge#onNext: o=" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:merge#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:merge#onComplete");

                    }
                });
    }

    /**
     * create an Observable from scratch by calling observer methods programmatically
     * <p>
     * 通过编程调用observer方法从头创建一个Observable
     */
    private void concat() {
        Observable observable1 = Observable.just(9527, 1001, 1002, 1003);
        Observable observable2 = Observable.just("C", "D", "E");
        Observable observable3 = Observable.just(1, 2, 3);

        Observable.concat(observable1, observable2, observable3)
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:concat#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(TAG, "metod:concat#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:concat#onNext: o=" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:concat#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:concat#onComplete");

                    }
                });

    }

    /**
     * just 操作符 创建一个发送特定item的Observable
     * Just操作符将一个item转换为发出该item的Observable。
     * 为了方便使用，just总共有9个重载的方法，最多可以传10个参数，这些对象(具有相同的公共类型)将按照指定的顺序发出。
     * Just操作符将一个项转换为发出该项的可观察对象。
     * 与From类似，但要注意From会插入数组或iterable或类似的东西来提取要发出的项，而just简单地将数组或iterable或诸如此类的东西作为一个单独的项发出。
     * 注意，如果将null传递给Just，它将返回一个可观察的对象，该对象将以项的形式发出null。不要错误地假设这将返回一个空的可观察对象(一个根本不发出任何项的对象)。为此，您将需要空操作符。
     * 默认情况下，just不会对任何特定的Scheduler进行操作。
     */
    private void concatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(9527);
                emitter.onNext(1001);
                emitter.onNext(1002);
                emitter.onNext(1003);
                emitter.onComplete();
            }
        })
//        Observable.just(9527, 1001, 1002, 1003)
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer num) throws Exception {
                        Log.i(TAG, "metod:concatMap#apply: currentThread=" + Thread.currentThread());
                        String str = "我是编号" + num;
                        Log.i(TAG, "metod:concatMap#apply: str=" + str);
                        return Observable.just(str).delay(1, TimeUnit.SECONDS);
                    }
                })
//                .subscribe(new Consumer<String>() {
//
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.i(TAG, "metod:flatMap#accept: currentThread=" + Thread.currentThread());
//                        Log.i(TAG, "metod:flatMap#accept: s=" + s);
//                    }
//                });
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:concatMap#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "metod:concatMap#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:concatMap#onNext: s=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:concatMap#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:concatMap#onComplete");
                    }
                });
    }

    private void buffer() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("我是编号" + 9527);
                emitter.onNext("我是编号" + 1000);
                emitter.onNext("我是编号" + 1001);
                emitter.onNext("我是编号" + 1002);
                emitter.onNext("我是编号" + 1003);
                Log.i(TAG, "metod:buffer#subscribe");
                emitter.onComplete();
            }
        })
                .buffer(2)
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:buffer#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        Log.i(TAG, "metod:buffer#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:buffer#onNext: strings=" + strings);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:buffer#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:buffer#onComplete");
                    }
                });
    }

    /**
     * 延迟指定时间发送一个0数值(Long类型)
     * <p>
     * timer操作符主要运行在一个新线程中，也可以自定义线程调度器(第三个参数)
     */
    private void groupBy() {
        Observable.just(9527, 1001, 1002)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer num) throws Exception {
                        final String str = "我是编号" + num;
                        Log.i(TAG, "metod:groupBy#apply: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:groupBy#apply: str=" + str);
                        return str;
                    }
                })
                .subscribe(new Observer<GroupedObservable<String, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:groupBy#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
                        Log.i(TAG, "metod:groupBy#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:groupBy#onNext: key=" + stringIntegerGroupedObservable.getKey());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:groupBy#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:groupBy#onComplete");

                    }
                });
    }

    /**
     * fromArray 操作符
     * <p>
     * 对一个数组集合进行观察，把数组一次性发给观察者，只会执行一次观察者的onNext，最后默认执行onComplete方法
     */
    private void scan() {
        Observable.just("C", "D", "E", "F", "G", "A", "B", "C")
                .scan(new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String str = s + ":" + s2;
                        Log.i(TAG, "metod:scan#apply: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:scan#apply: s=" + s + " , s2=" + s2);
                        Log.i(TAG, "metod:scan#apply: str=" + str);
                        return str;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:scan#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(String string) {
                        Log.i(TAG, "metod:scan#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:scan#onNext: string=" + string);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:scan#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:scan#onComplete");
                    }
                });
    }

    /**
     * ====================interval  定时器====================
     * <p>
     * 这个相当于定时器，用它可以取代CountDownTimer。它会按照设定的间隔时间，每次发送一个事件，发送的事件序列：默认从0开始，无限递增的整数序列
     * <p>
     * 以下代码输出：   0 ----(5秒后)-----1-----(5秒后)------2---------(5秒后)--------3-------(5秒后)-----.......
     */
    private void window() {
        Observable.just("C", "D", "E", "F", "G", "A", "B", "C")
                .window(2, 3)
                .subscribe(new Observer<Observable<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:window#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Observable<String> stringObservable) {
                        Log.i(TAG, "metod:window#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:window#onNext: stringObservable=" + stringObservable);
                        stringObservable.subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.i(TAG, "metod:window#onNext#accept: s=" + s);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:window#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:window#onComplete");
                    }
                });
    }

    private void intervalRange() {
        /**
         *  参数1：起始发送值
         *  参数2：发送数量
         *  参数3：首次发送延迟事件
         *  参数4：每次发送事件间隔
         *  参数5：时间单位
         *
         */
        Observable.intervalRange(3, 7, 3, 5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "metod:intervalRange#accept#currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:intervalRange#accept#aLong=" + aLong);//从0开始输出
                    }
                });
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG, "metod:intervalRange#onSubscribe: d=" + d);
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        Log.i(TAG, "metod:intervalRange#onNext: currentThread=" + Thread.currentThread());
//                        Log.i(TAG, "metod:intervalRange#onNext: aLong=" + aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "metod:intervalRange#onError: e=" + e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "metod:intervalRange#onComplete");
//                    }
//                });
    }

    /**
     * Range  操作符
     * <p>
     * 作用发送指定范围的序列，可指定范围.作用类似intervalRange，但不同的是range是无延迟发送
     */
    private void range() {
        Observable.range(3, 7)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "metod:range#accept#currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:range#accept#integer=" + integer);
                    }
                });
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG, "metod:range#onSubscribe: d=" + d);
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i(TAG, "metod:range#onNext: currentThread=" + Thread.currentThread());
//                        Log.i(TAG, "metod:range#onNext: integer=" + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "metod:range#onError: e=" + e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "metod:range#onComplete");
//                    }
//                });
    }

    /**
     * Range  操作符
     * <p>
     * 作用发送指定范围的序列，可指定范围.作用类似intervalRange，但不同的是range是无延迟发送
     */
    private void repeat() {
        Observable.just("Hello World!")
                .repeat(3)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:repeat#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "metod:repeat#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:repeat#onNext: s=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:repeat#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:repeat#onComplete");
                    }
                });
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG, "metod:range#onSubscribe: d=" + d);
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i(TAG, "metod:range#onNext: currentThread=" + Thread.currentThread());
//                        Log.i(TAG, "metod:range#onNext: integer=" + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "metod:range#onError: e=" + e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "metod:range#onComplete");
//                    }
//                });
    }

}