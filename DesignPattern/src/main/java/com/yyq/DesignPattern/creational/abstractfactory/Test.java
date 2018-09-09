package com.yyq.DesignPattern.creational.abstractfactory;

/**
 * @Author yyq
 * @Since 2018/09/09
 *
 * 抽象工厂模式；在一定程度上达到了解耦，不需要在导入其他类包。
 */
public class Test {
    public static void main(String[] args) {
        CourseFactory courseFactory = new JavaCourseFactory();
        Video video = courseFactory.getVideo();
        Article article = courseFactory.getArticle();
        video.produce();
        article.produce();
    }
}
