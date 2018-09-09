
package com.yyq.DesignPattern.principle.compsitionaggregation;

public class ProductDao{
    private DBConnnection dbConnnection;

    public void setDbConnnection(DBConnnection dbConnnection) {
        this.dbConnnection = dbConnnection;
    }

    public void addProduct(){
        String conn = dbConnnection.getConnection();
        System.out.println("使用"+conn+"增加产品");
    }
}
