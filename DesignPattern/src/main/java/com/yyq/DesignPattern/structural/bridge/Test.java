package com.yyq.DesignPattern.structural.bridge;

/**
 * 桥接模式
 * @author yyq
 * @since 18/09/16
 */
public class Test {
    public static void main(String[] args) {
        Bank icbcBank = new ICBCBank(new DepositAccount());
        Account icbcAccount  = icbcBank.openAccount();
        icbcAccount.showAccountType();

        Bank icbcBank2 = new ICBCBank(new SavingAccount());
        Account icbcAccount2  = icbcBank2.openAccount();
        icbcAccount2.showAccountType();

        Bank abcBank = new ABCBank(new SavingAccount());
        Account abcAccount  = abcBank.openAccount();
        abcAccount.showAccountType();

    }
}
