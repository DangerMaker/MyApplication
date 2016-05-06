package com.example.administrator.myapplication.model;

import com.example.administrator.myapplication.model.EzAction;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-05
 */
public class EzText {

    /**
     * fontsize : 16
     * backcolor : #00FF00
     * textcolor : #FF0000
     * alignment : 1
     * text : The 15th test
     * ezAction : {"ezTargetType":"showPage","ezTargetXib":"EZWebViewController","ezTargetClass":"EZWebViewController","ezTargetData":{"ezDataUrl":"http://www.baidu.com"}}
     */

    private String fontsize;
    private String backcolor;
    private String textcolor;
    private String alignment;
    private String text;
    private EzAction ezAction;

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getBackcolor() {
        return backcolor;
    }

    public void setBackcolor(String backcolor) {
        this.backcolor = backcolor;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EzAction getEzAction() {
        return ezAction;
    }

    public void setEzAction(EzAction ezAction) {
        this.ezAction = ezAction;
    }
}
