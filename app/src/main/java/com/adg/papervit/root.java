package com.adg.papervit;


public class root {
    data data;
    metadata metadata;

    public root(com.adg.papervit.data data, com.adg.papervit.metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public com.adg.papervit.data getData() {
        return data;
    }

    public void setData(com.adg.papervit.data data) {
        this.data = data;
    }

    public com.adg.papervit.metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(com.adg.papervit.metadata metadata) {
        this.metadata = metadata;
    }
}
