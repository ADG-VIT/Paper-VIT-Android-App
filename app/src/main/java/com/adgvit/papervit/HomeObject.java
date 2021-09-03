package com.adgvit.papervit;

public class HomeObject {
    int _id;
    String examType;

    public HomeObject(int _id, String examType) {
        this._id = _id;
        this.examType = examType;
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
}
