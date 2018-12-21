package com.yyq.DesignPattern.bahavioral.iterator;

public class ListImpl implements List {
    private Integer[] items;

    public  ListImpl() {
        items = new Integer[10];
        for(int i=0;i<items.length;i++){
            items[i] = i;
        }
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator<Integer>(items);
    }
}
