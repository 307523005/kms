package com.basewin.kms.service;

import com.basewin.kms.entity.RemotecontrolBeen;

import java.util.List;

public interface RemotecontrolService {
    List<RemotecontrolBeen> getRemotecontrol(RemotecontrolBeen remotecontrolBeen);
    boolean addAppport(RemotecontrolBeen remotecontrolBeen)throws Exception;
    boolean deleteAppport(RemotecontrolBeen remotecontrolBeen)throws Exception;
    boolean occupationAppport(RemotecontrolBeen remotecontrolBeen)throws Exception;
    boolean unoccupationAppport(RemotecontrolBeen remotecontrolBeen)throws Exception;

}
