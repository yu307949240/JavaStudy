package com.yyq.DesignPattern.bahavioral.iterator;

public class Test {
    public static void main(String[] args) {
        List list = new ListImpl();
        Iterator<Integer> iterator = list.createIterator();
        while(iterator.hashNext()){
            System.out.println(iterator.next());
        }
    }
}
