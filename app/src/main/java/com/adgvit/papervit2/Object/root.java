package com.adgvit.papervit2.Object;

public class root {
    data data;
    metadata metadata;

    public root(data data, metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public data getData() {
        return data;
    }

    public void setData(data data) {
        this.data = data;
    }

    public metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(metadata metadata) {
        this.metadata = metadata;
    }
}
