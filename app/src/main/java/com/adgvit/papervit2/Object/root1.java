package com.adgvit.papervit2.Object;

public class root1 {

    data1 data;
    metadata metadata;

    public root1(data1 data, metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(metadata metadata) {
        this.metadata = metadata;
    }

    public data1 getData() {
        return data;
    }

    public void setData(data1 data) {
        this.data = data;
    }
}
