package com.example.estekz.models;

import java.time.OffsetDateTime;

public class Image {
    private long id;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateCreatedGmt;
    private OffsetDateTime dateModified;
    private OffsetDateTime dateModifiedGmt;
    private String src;
    private String name;
    private String alt;

    public String getSrc() {
        return src;
    }
}
