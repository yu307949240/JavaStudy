package com.yyq.DesignPattern.bahavioral.iterator;

public class ConcreteIterator<Item> implements Iterator {
    private Item[] items;
    private int position = 0;

    public ConcreteIterator(Item[] items) {
        this.items = items;
    }

    @Override
    public boolean hashNext() {
        return position < items.length;
    }

    @Override
    public Object next() {
        return items[position++];
    }
}
