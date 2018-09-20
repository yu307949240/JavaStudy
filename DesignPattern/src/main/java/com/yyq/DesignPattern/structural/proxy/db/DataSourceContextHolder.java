package com.yyq.DesignPattern.structural.proxy.db;

public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void setDBType(String dbType){
        CONTEXT_HOLDER.set(dbType);
    }

    public static String getDBType(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDBType(){
        CONTEXT_HOLDER.remove();
    }
}
