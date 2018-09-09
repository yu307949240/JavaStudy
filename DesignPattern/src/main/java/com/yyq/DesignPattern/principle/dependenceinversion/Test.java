package com.yyq.DesignPattern.principle.dependenceinversion;

public class Test {
   /* public static void main(String[] args) {
        Geely geely = new Geely();
        geely.studyFECourse();
        geely.studyJavaCourse();
    }*/
   //v2 接口方法注入方式
   /* public static void main(String[] args) {
        Geely geely = new Geely();
        geely.studyImoocCourse(new JavaCourse());
        geely.studyImoocCourse(new FECourse());
        geely.studyImoocCourse(new PythonCourse());
    }*/

   //v3 构造方法注入
    /*public static void main(String[] args) {
        Geely geely = new Geely(new JavaCourse());
        geely.studyImoocCourse();
    }*/
    //v4 set注入
   public static void main(String[] args) {
       Geely geely = new Geely();
       geely.setiCourse(new JavaCourse());
       geely.studyImoocCourse();
   }

}
