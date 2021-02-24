package com.adg.papervit;

public class root1 {

    data1 data;
    metadata metadata;

    public root1(data1 data, com.adg.papervit.metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public data1 getData() {
        return data;
    }

    public void setData(data1 data) {
        this.data = data;
    }

    public com.adg.papervit.metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(com.adg.papervit.metadata metadata) {
        this.metadata = metadata;
    }
}
