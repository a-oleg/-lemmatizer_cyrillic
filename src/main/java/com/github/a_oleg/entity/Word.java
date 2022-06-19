package com.github.a_oleg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @Column(name = "iid")
    int id;
    @Column(name = "word")
    String word;
    @Column(name = "code")
    int code;
    @Column(name = "code_parent")
    int codeParent;

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

    public void setCodeParent(int codeParent) { this.codeParent = codeParent;}
}
