package com.yyq.DesignPattern.principle.dependenceinversion;

public class FECourse implements ICourse{
    @Override
    public void studyCourse() {
        System.out.println("学习前端教程");
    }
}
