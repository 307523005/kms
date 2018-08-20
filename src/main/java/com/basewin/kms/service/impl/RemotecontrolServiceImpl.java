package com.basewin.kms.service.impl;

import com.basewin.kms.dao.RemotecontrolDao;
import com.basewin.kms.entity.RemotecontrolBeen;
import com.basewin.kms.service.RemotecontrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional//配置事务处理
public class RemotecontrolServiceImpl implements RemotecontrolService {
    @Autowired
    RemotecontrolDao remotecontroldao;

    @Override
    public List<RemotecontrolBeen> getRemotecontrol(RemotecontrolBeen remotecontrolBeen) {
        return remotecontroldao.getRemotecontrol(remotecontrolBeen);
    }

    @Override
    public boolean addAppport(RemotecontrolBeen remotecontrolBeen) {

        List<RemotecontrolBeen> remotecontrol = remotecontroldao.getRemotecontrol(remotecontrolBeen);
        int num = 0;
        if (remotecontrol.size() == 0) {
            remotecontrolBeen.setIsoccupation(0);
            num = remotecontroldao.insertRemotecontrol(remotecontrolBeen);
        } else {
            remotecontrolBeen.setIsoccupation(0);
            num = remotecontroldao.updateRemotecontrol(remotecontrolBeen);
        }
        if (num > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAppport(RemotecontrolBeen remotecontrolBeen) {
        List<RemotecontrolBeen> remotecontrol = remotecontroldao.getRemotecontrol(remotecontrolBeen);

        if (remotecontrol.size() == 0) {
            return false;
        } else {
            remotecontroldao.deleteRemotecontrol(remotecontrolBeen);
            return true;
        }
    }

    @Override
    public boolean occupationAppport(RemotecontrolBeen remotecontrolBeen) {
        List<RemotecontrolBeen> remotecontrol = remotecontroldao.getRemotecontrol(remotecontrolBeen);

        if (remotecontrol.size() == 0) {
            return false;
        } else {
            remotecontrolBeen.setIsoccupation(1);
            remotecontroldao.updateRemotecontrol(remotecontrolBeen);
            return true;
        }
    }

    @Override
    public boolean unoccupationAppport(RemotecontrolBeen remotecontrolBeen) {
        List<RemotecontrolBeen> remotecontrol = remotecontroldao.getRemotecontrol(remotecontrolBeen);

        if (remotecontrol.size() == 0) {
            return false;
        } else {
            remotecontrolBeen.setIsoccupation(0);
            remotecontroldao.updateRemotecontrol(remotecontrolBeen);
            return true;
        }

    }
}
