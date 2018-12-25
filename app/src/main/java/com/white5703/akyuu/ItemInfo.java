package com.white5703.akyuu;

public class ItemInfo {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    protected String text;
    protected String priority;
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemInfo(String text, String priority,long id){
        this.text = text;
        this.priority = priority;
        this.id = id;
    }
    public ItemInfo(){}
}
