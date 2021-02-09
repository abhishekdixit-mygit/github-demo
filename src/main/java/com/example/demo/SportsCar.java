package com.example.demo;

public class SportsCar extends DecoratorClass {
    public SportsCar(Car car) {
        super(car);
    }

    @Override
    public void assemble() {
        car.assemble();
        System.out.println("Assembling sports car...");
    }
}
