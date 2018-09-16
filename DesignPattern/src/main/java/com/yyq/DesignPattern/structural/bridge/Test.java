package com.yyq.DesignPattern.structural.bridge;

/**
 * 桥接模式
 * @author yyq
 * @since 18/09/16
 */
public class Test {
    public static void main(String[] args) {
        RemoteControl remoteControl = new ConcreteRemoteControl(new Sony());
        remoteControl.on();
        remoteControl.off();
        remoteControl.tuneChannel();

    }
}
