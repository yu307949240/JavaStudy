package com.yyq.DesignPattern.principle.compsitionaggregation;

/**
 * 合成复用原则，当增加数据源的时候，只需要继承DBConnection即可。
 * create by yyq on 2018/09/08
 */
public class MySQLConnection extends DBConnnection{
    @Override
    public String getConnection() {
        return "MySQL数据库连接";
    }
}
