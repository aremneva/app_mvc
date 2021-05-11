package com.example.mvc1;

public class Question {
    private  String TextId;
    private String Answer;
    private int PathImg;

    public Question(String textId, String answer, int pathImg) {
        this.TextId = textId;
        this.Answer = answer;
        this.PathImg=pathImg;
    }

    public String isAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getTextId() {
        return TextId;
    }

    public void setTextId(String textId) {
        TextId = textId;
    }

    public int getPathImg() {
        return PathImg;
    }

    public void setPathImg(int pathImg) {
        PathImg = pathImg;
    }
}

