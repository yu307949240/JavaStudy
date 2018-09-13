package com.yyq.DesignPattern.creational.prototype;

public class Test implements Cloneable{
    public static void main(String[] args) throws CloneNotSupportedException {
        Mail mail = new Mail();
        mail.setContent("初始化模板");
        System.out.println("初始化mail："+mail); //直接输出mail.toString()
        for(int i=0;i<10;i++){
            Mail mailTemp = (Mail) mail.clone();
            mailTemp.setName("姓名"+i);
            mailTemp.setEmailAddress("姓名"+i+"@imooc.com");
            mailTemp.setContent("恭喜你，此次慕课网活动中奖了");
            MailUtil.sendMail(mailTemp);
            System.out.println("克隆的mailTemp："+mailTemp);

        }
        MailUtil.saveOriginMailRecord(mail);
    }
}
