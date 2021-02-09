package com.example.streams;

import io.reactivex.Observable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.observables.ConnectableObservable;

public class ObserversDemo {

    static int start = 1, count = 2;
    public static void main(String[] args) {
        /*ConnectableObservable<Integer> feed = Observable.just(1,2,3,4,5,6,7,8,9,10).publish();
        feed.subscribe(integer -> {
            System.out.println("Observer 1 : "+ integer);
        });
        sleep(10000);
        feed.filter(integer -> integer % 2 == 0)
                .subscribe(integer -> {
                    System.out.println("Observer 2 : "+ integer);
        });

        feed.connect();
        sleep(10000);*/

        Observable<Integer> observable = Observable.range(1,10);
        observable.subscribe(integer -> System.out.println(integer));

        System.out.println("\n----------------------\n");
        observable = Observable.defer(() -> Observable.range(start, count));
        observable.subscribe(integer -> System.out.println("Observer 1 : "+integer));
        count = 3;
        observable.subscribe(integer -> System.out.println("Observer 2 : "+integer));
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
