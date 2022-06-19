package com.github.a_oleg.dto;

public class WordDto {
    int id;
    String word;
    int code;
    int codeParent;
    int count;

    public WordDto() {
    }

    public WordDto(int id, String word, int code, int codeParent, int count) {
        this.id = id;
        this.word = word;
        this.code = code;
        this.codeParent = codeParent;
        this.count = count;
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

    public String toString(){
        return getWord() + " - " + getCount() + " повторений";
    }
}
