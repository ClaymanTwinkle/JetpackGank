package com.kesar.jetpackgank.base;

import androidx.lifecycle.ViewModel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * SubscriptionViewModel
 *
 * @author andy <br/>
 * create time: 2019/2/20 15:05
 */
public abstract class SubscriptionViewModel extends ViewModel {
    private CompositeSubscription mSubscription = new CompositeSubscription();

    protected <M> void addSubscription(Observable<M> observable, Subscriber<M> subscriber) {
        mSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mSubscription.unsubscribe();
    }
}
