package com.yyq.DesignPattern.principle.liskovSubstitution;

public class Test {
    public static void resize(Rectangle rectangle){
        while (rectangle.getWidth() <= rectangle.getLength()){
            rectangle.setWidth(rectangle.getWidth()+1);
            System.out.println("width:"+rectangle.getWidth()+" length+"+rectangle.getLength());
        }
        System.out.println(" resize方法结束 width:"+rectangle.getWidth()+" length:"+rectangle.getLength());
    }

   /* public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setLength(20);
        rectangle.setWidth(10);
        resize(rectangle);
    }*/
   public static void main(String[] args) {
       Square square = new Square();
       square.setLength(10);
       resize(square);
   }

}
