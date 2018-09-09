package com.yyq.DesignPattern.principle.compsitionaggregation;


public class Test {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        productDao.setDbConnnection(new MySQLConnection());
        productDao.addProduct();
    }
}
