package com.windfallsheng.myrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.concat(
                Observable.just(1, 2, 3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9),
                Observable.just(10, 11, 12))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "-->onSubscribe()_开始采用subscribe连接\n");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "-->onNext()_对Next事件[ " + value + " ]作出响应\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "-->onError()_对Error事件作出响应[ " + e.toString() + " ]\n");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "-->onComplete()_对Complete事件作出响应\n");
                    }
                });
    }
}
