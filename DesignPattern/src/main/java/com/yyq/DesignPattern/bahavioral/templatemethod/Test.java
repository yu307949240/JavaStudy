package com.yyq.DesignPattern.bahavioral.templatemethod;

public class Test {
    public static void main(String[] args) {
        System.out.println("后端设计模式课程start---");
        ACourse designCourse = new DesignPatternCourse();
        designCourse.makeCourse();
        System.out.println("后端设计模式课程end---");

        System.out.println("前端设计模式课程start---");
        ACourse feCourse = new FECourse(false);
        feCourse.makeCourse();
        System.out.println("前端设计模式课程end---");
    }
}
