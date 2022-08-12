package com.xio.detect.opchandler.service.impl;

import com.xio.detect.opchandler.dao.OPCDao;
import com.xio.detect.opchandler.service.ActionService;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActionServiceImpl implements ActionService {

    @Override
    public boolean action() {
        return actionByValue(true);
    }

    @Override
    public boolean reset() {
        return actionByValue(false);
    }

    private boolean actionByValue(boolean value) {
        OpcUaClient client;
        try {
            client = OPCDao.getConnection();
            NodeId node = OPCDao.node(2, "通道 1.设备 1.test");
            return OPCDao.writeNode(client, node, value ? Boolean.TRUE : Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
