package com.yyq.DesignPattern.principle.singleresponsibility;


public class CourseImpl implements ICourseManage,ICourseContent{
    @Override
    public String getCourseName() {
        return null;
    }

    @Override
    public byte[] getCourseVideo() {
        return new byte[0];
    }

    @Override
    public void studyCourse() {

    }

    @Override
    public void refundCourse() {

    }
}
