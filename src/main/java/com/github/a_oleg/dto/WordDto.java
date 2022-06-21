package com.github.a_oleg.dto;

public class WordDto {
    int id;
    String word;
    int code;
    int codeParent;
    int count;
    boolean cyrillic;

    public WordDto(int id, String word, int code, int codeParent, int count, boolean cyrillic) {
        this.id = id;
        this.word = word;
        this.code = code;
        this.codeParent = codeParent;
        this.count = count;
        this.cyrillic = cyrillic;
    }

    public WordDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCodeParent() {
        return codeParent;
    }

    public void setCodeParent(int codeParent) {
        this.codeParent = codeParent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCyrillic() {
        return cyrillic;
    }

    public void setCyrillic(boolean cyrillic) {
        this.cyrillic = cyrillic;
    }

    public String toString(){
        return getWord() + " - " + getCount() + " ����������";
    }
}
