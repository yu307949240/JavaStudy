package com.yyq.DesignPattern.principle.dependenceinversion;

import javax.swing.*;

public class PythonCourse implements ICourse {
    @Override
    public void studyCourse()  {
        System.out.println("学习python教程");
    }
}
