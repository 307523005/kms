package com.basewin.kms.entity;

public class Remotecontrol {
    private  int type;
    private  String bx;
    private  String by;
    private  String ex;
    private  String ey;
    private  int pcport;
    private  int appport;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBx() {
        return bx;
    }

    public void setBx(String bx) {
        this.bx = bx;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getEy() {
        return ey;
    }

    public void setEy(String ey) {
        this.ey = ey;
    }

    public int getPcport() {
        return pcport;
    }

    public void setPcport(int pcport) {
        this.pcport = pcport;
    }

    public int getAppport() {
        return appport;
    }

    public void setAppport(int appport) {
        this.appport = appport;
    }
}
