package com.yyq.DesignPattern.bahavioral.templatemethod;

public class FECourse extends ACourse {

    private boolean needWriteArticleFlag = false;

    public FECourse(boolean needWriteArticleFlag){
        this.needWriteArticleFlag = needWriteArticleFlag;
    }

    @Override
    void packageCourse() {
        System.out.println("提供课程的前端代码");
        System.out.println("提供课程的多媒体素材");
    }

    @Override
    protected  boolean needWriteArticle(){
        return this.needWriteArticleFlag;
    }
}
