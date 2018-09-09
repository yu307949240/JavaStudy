package com.yyq.DesignPattern.DecoratorPattern;
/**
 * 装饰器模式; https://www.cnblogs.com/zhangtianq/p/6091047.html
 * create by yyq on 2018/08/18
 */

/***
 * 抽象组件
 */
public interface ICar {
    void move();
}

// ConcreteComponent 具体构件角色（真实对象）
class Car implements ICar{

    @Override
    public void move() {
        System.out.println("陆地上跑");
    }
}

class SuperCar implements ICar{
    private ICar car;
    public SuperCar(ICar car){
        this.car = car;
    }

    @Override
    public void move() {
        car.move();
    }
}

// ConcreteDecorator 具体装饰角色
class FlyCar extends SuperCar{

    public FlyCar(ICar car) {
        super(car);
    }

    public void fly(){
        System.out.println("天上飞");
    }
    @Override
    public void move(){
        super.move();
        fly();
    }
}

// ConcreteDecorator 具体装饰角色
class WaterCar extends SuperCar{

    public WaterCar(ICar car) {
        super(car);
    }

    public void swim(){
        System.out.println("水里游");
    }
    @Override
    public void move(){
        super.move();
        swim();
    }
}

// ConcreteDecorator 具体装饰角色
class AICar extends SuperCar{

    public AICar(ICar car) {
        super(car);
    }

    public void autoMove(){
        System.out.println("自动跑");
    }
    @Override
    public void move(){
        super.move();
        autoMove();
    }
}

