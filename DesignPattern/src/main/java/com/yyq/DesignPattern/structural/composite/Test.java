package com.yyq.DesignPattern.structural.composite;

/**
 * 组合模式
 *
 * @author yyq
 * @since 18/09/20
 */
public class Test {
    public static void main(String[] args) {
        CatalogComponent linuxCourse = new Course("Linux课程", 11);

        CatalogComponent windowsCourse = new Course("Windows课程", 11);

        CatalogComponent javaCourseCatalog = new CourseCatalog("Java课程目录", 2);

        CatalogComponent mmallCourse1 = new Course("Java电商1期", 55);
        CatalogComponent mmallCourse2 = new Course("Java电商2期", 66);
        CatalogComponent designPattern = new Course("Java设计模式", 77);

        javaCourseCatalog.add(mmallCourse1);
        javaCourseCatalog.add(mmallCourse2);
        javaCourseCatalog.add(designPattern);

        CatalogComponent imoocMainCourselog = new CourseCatalog("慕课网课程主目录", 1);
        imoocMainCourselog.add(linuxCourse);
        imoocMainCourselog.add(windowsCourse);
        imoocMainCourselog.add(javaCourseCatalog);

        imoocMainCourselog.print();
    }
}

