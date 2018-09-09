package com.yyq.DesignPattern.principle.demeter;

import java.util.List;

public class TeamLeader {
    public void checkNumberOfCourse(List<Course> courseList){
        System.out.println("在线课程的数量是："+courseList.size());
    }
}
