package com.adgvit.papervit;

import java.util.List;

public class HomeMetaData {
    List<HomeObject> examTypes;

    public HomeMetaData(List<HomeObject> examTypes) {
        this.examTypes = examTypes;
    }

    public List<HomeObject> getExamTypes() {
        return examTypes;
    }

    public void setExamTypes(List<HomeObject> examTypes) {
        this.examTypes = examTypes;
    }

}
