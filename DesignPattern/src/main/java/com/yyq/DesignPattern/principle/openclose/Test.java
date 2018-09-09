package com.yyq.DesignPattern.principle.openclose;

public class Test {
    public static void main(String[] args) {
        ICourse javaCourse = new JavaDiscountCourse(96,"Java",98d);
        JavaDiscountCourse jdCourse = (JavaDiscountCourse) javaCourse;
        System.out.println(javaCourse.getId());
        System.out.println(javaCourse.getName());
        System.out.println(javaCourse.getPrice());
    }
}
