package com.example.demo;

public abstract class DecoratorClass implements Car{
    protected Car car;

    public DecoratorClass(Car car) {
        this.car = car;
    }
}
