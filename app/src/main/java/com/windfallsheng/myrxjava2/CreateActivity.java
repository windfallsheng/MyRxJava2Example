package com.windfallsheng.myrxjava2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = CreateActivity.class.getSimpleName();

    TextView tvCreate, tvDefer, tvJust, tvFromIterable, tvTimer, tvFromArray, tvInterval, tvIntervalRange, tvRange;


    public static void start(Context context) {
        context.startActivity(new Intent(context, CreateActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        tvCreate = findViewById(R.id.textview_create);
        tvDefer = findViewById(R.id.textview_defer);
        tvJust = findViewById(R.id.textview_just);
        tvFromIterable = findViewById(R.id.textview_fromiterable);
        tvTimer = findViewById(R.id.textview_timer);
        tvFromArray = findViewById(R.id.textview_fromarray);
        tvInterval = findViewById(R.id.textview_interval);
        tvIntervalRange = findViewById(R.id.textview_intervalrange);
        tvRange = findViewById(R.id.textview_range);

        tvCreate.setOnClickListener(this);
        tvDefer.setOnClickListener(this);
        tvJust.setOnClickListener(this);
        tvFromIterable.setOnClickListener(this);
        tvTimer.setOnClickListener(this);
        tvFromArray.setOnClickListener(this);
        tvInterval.setOnClickListener(this);
        tvIntervalRange.setOnClickListener(this);
        tvRange.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_create:
                create();
                break;
            case R.id.textview_defer:
                defer();
                break;
            case R.id.textview_just:
                just();
                break;
            case R.id.textview_fromiterable:
                fromIterable();
                break;
            case R.id.textview_timer:
                timer();
                break;
            case R.id.textview_fromarray:
                fromArray();
                break;
            case R.id.textview_interval:
                interval();
                break;
            case R.id.textview_intervalrange:
                intervalRange();
                break;
            case R.id.textview_range:
                range();
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
    private void create() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    Log.i(TAG, "metod:create#subscribe#emitter.onNext(" + i + ")");
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "metod:create#onSubscribe: d=" + d);
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "metod:create#onNext: currentThread=" + Thread.currentThread());
                Log.i(TAG, "metod:create#onNext: integer=" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "metod:create#onError: e=" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "metod:create#onComplete");
            }
        });
    }

    /**
     * create an Observable from scratch by calling observer methods programmatically
     * <p>
     * 通过编程调用observer方法从头创建一个Observable
     */
    private void defer() {
        Observable defer = Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("defer:C");
                        Log.i(TAG, "metod:defer#subscribe#emitter.onNext(defer:C)");
                        emitter.onNext("defer:D");
                        Log.i(TAG, "metod:defer#subscribe#emitter.onNext(defer:D)");
                        emitter.onNext("defer:E");
                        Log.i(TAG, "metod:defer#subscribe#emitter.onNext(defer:E)");
                        emitter.onComplete();
                    }
                });
            }
        });
        defer.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "metod:defer#onSubscribe: d=" + d);
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "metod:defer#onNext: currentThread=" + Thread.currentThread());
                Log.i(TAG, "metod:defer#onNext: s=" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "metod:defer#onError: e=" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "metod:defer#onComplete");
            }
        });
    }

    /**
     * just 操作符最多可以传10个参数
     */
    private void just() {
        Observable.just("C", "D", "E", "F", "G", "A", "B", "C", "D", "E")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:just#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "metod:just#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:just#onNext: s=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:just#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:just#onComplete");
                    }
                });
    }

    private void fromIterable() {
//        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:fromIterable#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "metod:fromIterable#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:fromIterable#onNext: integer=" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:fromIterable#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:fromIterable#onComplete");
                    }
                });

    }

    /**
     * 延迟指定时间发送一个0数值(Long类型)
     * <p>
     * timer操作符主要运行在一个新线程中，也可以自定义线程调度器(第三个参数)
     */
    private void timer() {
        Observable.timer(2, TimeUnit.SECONDS)
//        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:timer#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG, "metod:timer#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:timer#onNext: aLong=" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:timer#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:timer#onComplete");
                    }
                });
    }

    /**
     * fromArray 操作符
     * <p>
     * 对一个数组集合进行观察，把数组一次性发给观察者，只会执行一次观察者的onNext，最后默认执行onComplete方法
     */
    private void fromArray() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Observable.fromArray(list)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:fromArray#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.i(TAG, "metod:fromArray#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:fromArray#onNext: integers=" + integers.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:fromArray#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:fromArray#onComplete");
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
    private void interval() {
        Observable.interval(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "metod:interval#accept#currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:interval#accept#aLong=" + aLong);//从0开始输出
                    }
                });
        // 或者
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG, "metod:interval#onSubscribe: d=" + d);
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        Log.i(TAG, "metod:interval#onNext: currentThread=" + Thread.currentThread());
//                        Log.i(TAG, "metod:interval#onNext: aLong=" + aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "metod:interval#onError: e=" + e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "metod:interval#onComplete");
//                    }
//                });
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
}
