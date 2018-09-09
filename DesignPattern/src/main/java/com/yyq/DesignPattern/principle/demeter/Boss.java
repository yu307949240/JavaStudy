package com.yyq.DesignPattern.principle.demeter;

import java.util.ArrayList;
import java.util.List;

public class Boss {
    public void commandCheckNumber(TeamLeader teamLeader){
        List<Course> courseList = new ArrayList<>();
        for(int i=0;i<20;i++){
            courseList.add(new Course());
        }
        teamLeader.checkNumberOfCourse(courseList);
    }
}
