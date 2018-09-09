package com.yyq.DesignPattern.principle.dependenceinversion;

public class Geely {
    private ICourse iCourse;

    /*public Geely(ICourse iCourse){
        this.iCourse = iCourse;
    }*/

    public void setiCourse(ICourse iCourse){
        this.iCourse = iCourse;
    }

    public void studyImoocCourse(){
        iCourse.studyCourse();
    }
}
