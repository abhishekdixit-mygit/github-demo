package com.example.streams;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;
import sun.jvm.hotspot.debugger.cdbg.FunctionType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsExample {

    static Integer getSumOfAllNumbers(List<Integer> list, Predicate<Integer> selectorNumber) {
        return list.stream()
                .filter(selectorNumber)
                .reduce(0, (a,b) -> a + b);
    }

    public static void main(String[] args) throws InterruptedException {
        Predicate<Integer> isNumberGreaterThan3 = number -> number > 3;
        Predicate<Integer> isNumberEven = number -> number % 2 == 0;
        Function<Integer,Integer> doubleTheNumber = number -> number * 2;

        Function<Integer, Predicate<Integer>> isGreaterThan = pivot -> number -> number > pivot;
        //BiFunction<List<Integer>, Predicate<Integer>, Integer> getSumOfAllNumbers = number -> number -> number % 2 == 0;

        List<Integer> list = Arrays.asList(1,2,3,5,4,6,7,8);
        System.out.println(list.stream()
                .filter(isGreaterThan.apply(4))
                .filter(isNumberEven)
                .map(doubleTheNumber)
                .findFirst()
                .get());

        int testVar = 2;
        System.out.println(list.stream().mapToInt(e -> e * testVar).sum());

        System.out.println(isNumberGreaterThan3.test(4));
        System.out.println("getSumOfAllNumbers : "+getSumOfAllNumbers(list, isNumberEven));
        System.out.println("Using lambda : "+ list.stream()
                                                .filter(isNumberEven)
                                                .mapToInt(value -> value)
                                                //.reduce(0, (a,b) -> a + b)
                                                .sum());


        /*list = Arrays.asList(1,2,3,5,5,3,7,4,6,7,8);
        System.out.println(list.stream()
                               .collect(Collectors.toMap(e -> e,
                                                        e -> Collectors.toList().characteristics().size())));*/

        list = Arrays.asList(1,2,3,5,5,3,7,4,6,7,8);
        System.out.println(list.stream()
                               .collect(Collectors.groupingBy(e -> e)));

        /*Flowable<Long> feed = Flowable.<Integer>interval(1,1, TimeUnit.SECONDS);
        feed.subscribe(data -> System.out.println("S1 : " + data));
        Thread.sleep(5000);
        feed.subscribe(data -> System.out.println("S2 : " + data));
        Thread.sleep(10000);*/



        /*Flowable<Long> feed = Flowable.<Integer>interval(1,1, TimeUnit.SECONDS).share();
        feed.subscribe(data -> System.out.println("S1 : " + data));
        Thread.sleep(5000);
        feed.subscribe(data -> System.out.println("S2 : " + data));
        Thread.sleep(10000);*/
        Flowable<Integer> feed = Flowable.<Integer> create(emitter -> emit(emitter),
                BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.computation(), true, 2)
                .map(data -> data * 1);
        feed.subscribe(data -> process(data),
                        err -> System.out.println(err),
                        () -> System.out.println("Process completed...")
                );

        /*feed.subscribe(data -> process2(data),
                err -> System.out.println(err),
                () -> System.out.println("Process completed...")
        );*/

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void process(Integer data) {
        System.out.println("S1 : "+data);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void process2(Integer data) {
        System.out.println("S2 : "+data);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void emit(FlowableEmitter<Integer> emitter) {
        int count = 0;
        while(count < 10) {
            emitter.onNext(count);
            count++;
            System.out.println("Emitting data : "+ count);
        }
        emitter.onComplete();
    }
}
