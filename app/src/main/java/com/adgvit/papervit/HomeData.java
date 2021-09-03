package com.adgvit.papervit;

public class HomeData {
    HomeMetaData data;
    metadata metadata;

    public HomeData(HomeMetaData data, com.adgvit.papervit.metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public HomeMetaData getData() {
        return data;
    }

    public void setData(HomeMetaData data) {
        this.data = data;
    }

    public com.adgvit.papervit.metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(com.adgvit.papervit.metadata metadata) {
        this.metadata = metadata;
    }
}
