package com.yyq.DesignPattern.FacadePattern;

/**
 * 外观模式（门面模式）; https://www.cnblogs.com/lthIU/p/5860607.html
 */
public class Computer {
    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public Computer(){
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void start(){
        cpu.start();
        memory.start();
        disk.start();
    }

    public void shutDown(){
        cpu.shutDown();
        memory.shutDown();
        disk.shutDown();
    }

}

class CPU {
    public void start() {
        System.out.println("cpu is start...");
    }

    public void shutDown() {
        System.out.println("CPU is shutDown...");
    }
}

class Memory{
    public void start() {
        System.out.println("Memory is start...");
    }

    public void shutDown() {
        System.out.println("Memory is shutDown...");
    }
}

class Disk{
    public void start() {
        System.out.println("Disk is start...");
    }

    public void shutDown() {
        System.out.println("Disk is shutDown...");
    }
}