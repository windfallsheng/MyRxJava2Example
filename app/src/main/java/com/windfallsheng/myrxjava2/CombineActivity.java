package com.windfallsheng.myrxjava2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import io.reactivex.functions.Function3;

public class CombineActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = CombineActivity.class.getSimpleName();

    private TextView tvMerge, tvConcat, tvConcatMap, tvZip, tvStartWith, tvCombineLatest, tvJoin;

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
        tvZip = findViewById(R.id.textview_zip);
        tvStartWith = findViewById(R.id.textview_startwith);
        tvCombineLatest = findViewById(R.id.textview_combinelatest);
        tvJoin = findViewById(R.id.textview_join);

        tvMerge.setOnClickListener(this);
        tvConcat.setOnClickListener(this);
        tvConcatMap.setOnClickListener(this);
        tvZip.setOnClickListener(this);
        tvStartWith.setOnClickListener(this);
        tvCombineLatest.setOnClickListener(this);
        tvJoin.setOnClickListener(this);
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
            case R.id.textview_zip:
                zip();
                break;
            case R.id.textview_startwith:
                startwith();
                break;
            case R.id.textview_combinelatest:
                combineLatest();
                break;
            case R.id.textview_join:
                join();
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

    private void zip() {
        Observable observable1 = Observable.just(9527, 1001, 1002, 1003);
        Observable observable2 = Observable.just("C", "D", "E");
        Observable observable3 = Observable.just(1, 2, 3);

        Observable.zip(observable1, observable2, observable3, new Function3() {
            @Override
            public Object apply(Object o, Object o2, Object o3) throws Exception {
                return o.toString() + o2.toString() + o3.toString();
            }
        }).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "metod:zip#onSubscribe: d=" + d);
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "metod:zip#onNext: currentThread=" + Thread.currentThread());
                Log.i(TAG, "metod:zip#onNext: o=" + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "metod:zip#onError: e=" + e.toString());

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "metod:zip#onComplete");
            }
        });
    }

    /**
     * 延迟指定时间发送一个0数值(Long类型)
     * <p>
     * timer操作符主要运行在一个新线程中，也可以自定义线程调度器(第三个参数)
     */
    private void startwith() {
        Observable.just(1001, 1002, 1003)
                .startWith(9527)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer num) throws Exception {
                        Log.i(TAG, "metod:startwith#apply: currentThread=" + Thread.currentThread());
                        String str = "我是编号" + num;
                        Log.i(TAG, "metod:startwith#apply: str=" + str);
                        return Observable.just(str).delay(1, TimeUnit.SECONDS);
                    }
                })
//                .subscribe(new Consumer<String>() {
//
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.i(TAG, "metod:startwith#accept: currentThread=" + Thread.currentThread());
//                        Log.i(TAG, "metod:startwith#accept: s=" + s);
//                    }
//                });
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:startwith#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "metod:startwith#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:startwith#onNext: s=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:startwith#onError: e=" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:startwith#onComplete");
                    }
                });

    }

    /**
     * fromArray 操作符
     * <p>
     * 对一个数组集合进行观察，把数组一次性发给观察者，只会执行一次观察者的onNext，最后默认执行onComplete方法
     */
    private void combineLatest() {
        Observable observable1 = Observable.just("C", "D", "E", "F", "G", "A", "B");
        Observable observable2 = Observable.just(1, 2, 3);

        Observable.combineLatest(observable1, observable2, new BiFunction() {
            @Override
            public Object apply(Object o, Object o2) throws Exception {
                final String str = o.toString() + "+" + o2.toString();
                Log.i(TAG, "metod:combineLatest#apply: str=" + str);
                return str;
            }
        }).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "metod:combineLatest#onSubscribe: d=" + d);
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "metod:combineLatest#onNext: currentThread=" + Thread.currentThread());
                Log.i(TAG, "metod:combineLatest#onNext: o=" + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "metod:combineLatest#onError: e=" + e.toString());

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "metod:combineLatest#onComplete");
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
    private void join() {
        Observable observable1 = Observable.just("C", "D", "E", "F", "G", "A", "B");
        Observable observable2 = Observable.just(1, 2, 3);

        observable1.join(observable2, new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                return null;
            }
        }, new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                return null;
            }
        }, new BiFunction() {
            @Override
            public Object apply(Object o, Object o2) throws Exception {
                return null;
            }
        })
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "metod:combineLatest#onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(TAG, "metod:combineLatest#onNext: currentThread=" + Thread.currentThread());
                        Log.i(TAG, "metod:combineLatest#onNext: o=" + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "metod:combineLatest#onError: e=" + e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "metod:combineLatest#onComplete");
                    }
                });
    }

}
