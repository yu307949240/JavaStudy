package com.yyq.DesignPattern.structural.bridge;

/**
 * 定期账号类
 */
public class DepositAccount implements Account {
    @Override
    public Account openAccount() {
        System.out.println("打开活期账号");
        return new DepositAccount();
    }

    @Override
    public void showAccountType() {
        System.out.println("这是一个定期账号");
    }
}
