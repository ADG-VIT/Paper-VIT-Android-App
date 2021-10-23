package com.adgvit.papervit2.Object;

public class HomeObject {
    int _id;
    String examType;
    String examName;

    public HomeObject(int _id, String examType, String examName) {
        this._id = _id;
        this.examType = examType;
        this.examName = examName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
