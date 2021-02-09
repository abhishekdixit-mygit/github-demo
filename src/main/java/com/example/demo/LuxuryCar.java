package com.example.demo;

public class LuxuryCar extends DecoratorClass {
    public LuxuryCar(Car car) {
        super(car);
    }

    @Override
    public void assemble() {
        car.assemble();
        System.out.println("Assembling Luxury car...");
    }
}
