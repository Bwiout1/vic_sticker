package com.example.amusingstickerbox.tools;

public class MyPhotoBean {
    private long id;
    private String pack;
    private String owner;
    private String source;

    public long getId() {
        return id;
    }

    public MyPhotoBean(){}

    public MyPhotoBean(long id,String pack,String owner,String source){
        this.id=id;
        this.owner=owner;
        this.source=source;
        this.pack=pack;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String  getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
