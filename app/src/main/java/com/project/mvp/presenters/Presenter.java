package com.project.mvp.presenters;

import com.project.utils.RxUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by anandmishra on 15/11/16.
 */

//Presenter class for MVP pattern

public abstract class Presenter {

    CompositeSubscription compositeSubscription;

    public void start() {
        compositeSubscription = new CompositeSubscription();
    }

    public void stop() {
        RxUtils.clear(compositeSubscription);
    }

}
