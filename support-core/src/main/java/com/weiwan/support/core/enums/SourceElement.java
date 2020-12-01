package com.weiwan.support.core.enums;


/**
 * @Author: xiaozhennan
 * @Date: 2020/11/24 17:09
 * @Package: com.weiwan.support.core.enums.SourceElement
 * @ClassName: SourceElement
 * @Description:
 **/
public enum SourceElement {

    ExampleSource("com.weiwan.support.plugins.reader.ExampleReader");


    private String sourceType;


    SourceElement(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
