package com.yyq.DesignPattern.bahavioral.iterator;

public interface CourseAggregate {
    void addCourse(Course course);
    void removeCourse(Course course);
    CourseIterator getCourseIterator();
}
