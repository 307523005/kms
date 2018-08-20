package com.basewin.kms.entity;

import java.io.Serializable;

public class SSLBeen implements Serializable {
    private static final long serialVersionUID = 8655851615465363473L;
    private  Integer id ;
    private  String serialnumber;
    private String bkspath;
    private String cerpath;
    private String addtime;
    private String updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getBkspath() {
        return bkspath;
    }

    public void setBkspath(String bkspath) {
        this.bkspath = bkspath;
    }

    public String getCerpath() {
        return cerpath;
    }

    public void setCerpath(String cerpath) {
        this.cerpath = cerpath;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
 /*   @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", serialnumber='" + serialnumber + '\'' +
                ", bkspath=" + bkspath +
                ", cerpath='" + cerpath + '\'' +
                ", addtime='" + addtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }*/

}
