package com.adgvit.papervit2.Object;

public class HomeData {
    HomeMetaData data;
    metadata metadata;

    public HomeData(HomeMetaData data,metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public HomeMetaData getData() {
        return data;
    }

    public void setData(HomeMetaData data) {
        this.data = data;
    }

    public metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(metadata metadata) {
        this.metadata = metadata;
    }
}
