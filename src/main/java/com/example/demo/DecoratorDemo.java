package com.example.demo;

public class DecoratorDemo {
    public static void main(String[] args) {
        Car car = new BasicCar();
        car = new SportsCar(car);
        car = new LuxuryCar(car);
        car.assemble();
    }
}
