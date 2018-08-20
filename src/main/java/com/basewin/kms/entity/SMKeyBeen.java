package com.basewin.kms.entity;

import java.io.Serializable;

public class SMKeyBeen implements Serializable {
    private static final long serialVersionUID = 1L;
    private  Integer id ;
    private  String serialnumber;
    private String pubk;
    private String prik;
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

    public String getPubk() {
        return pubk;
    }

    public void setPubk(String pubk) {
        this.pubk = pubk;
    }

    public String getPrik() {
        return prik;
    }

    public void setPrik(String prik) {
        this.prik = prik;
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
}
