package com.yyq.DesignPattern.PrototypePattern;

/**
 * 原型模式; http://www.runoob.com/design-pattern/prototype-pattern.html
 * create by yyq on 2018/08/18
 */
public abstract class Shape implements Cloneable{
    private String id;
    protected String type;

    abstract void draw();

    public String getType(){
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object clone(){
        Object clone = null;
        try{
            clone = super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clone;
    }
}

class Rectangle extends Shape{
    public Rectangle(){
        type = "Rectangle";
    }


    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Square extends Shape{
    public Square(){
        type = "Square";
    }


    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

class Circle extends Shape {

    public Circle(){
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
