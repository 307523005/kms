package com.basewin.kms.entity;

import java.io.Serializable;

public class RemotecontrolBeen implements Serializable {
    private static final long serialVersionUID = 8655851615465363474L;
    private  Integer id ;
    private  String serialnumber;
    private Integer appport;
    private Integer isoccupation;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getAppport() {
        return appport;
    }

    public void setAppport(Integer appport) {
        this.appport = appport;
    }

    public Integer getIsoccupation() {
        return isoccupation;
    }

    public void setIsoccupation(Integer isoccupation) {
        this.isoccupation = isoccupation;
    }

    public RemotecontrolBeen( String serialnumber, Integer appport, Integer isoccupation) {
        this.serialnumber = serialnumber;
        this.appport = appport;
        this.isoccupation = isoccupation;
    }

    public RemotecontrolBeen() {
    }
}
