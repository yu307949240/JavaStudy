package com.yyq.DesignPattern.creational.simplefactory;

public class Test {
    /*public static void main(String[] args) {
        VideoFactory videoFactory = new VideoFactory();
        Video video = videoFactory.getVideo("java");
        if(video == null){
            return;
        }
        video.produce();
    }*/
    //反射实现简单工厂
    public static void main(String[] args) {
        VideoFactory videoFactory = new VideoFactory();
        Video video = videoFactory.getVideo(JavaVideo.class);
        if(video == null){
            return;
        }
        video.produce();
    }

}
