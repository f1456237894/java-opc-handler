package com.xio.detect.opchandler.service.impl;

import com.xio.detect.opchandler.dao.OPCDao;
import com.xio.detect.opchandler.entity.OPCNode;
import com.xio.detect.opchandler.service.ActionService;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;


@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private OPCDao opcDao;

    @Override
    public boolean action(Integer id) {
        return actionByValue(true, id);
    }

    @Override
    public boolean reset(Integer id) {
        return actionByValue(false, id);
    }

    private boolean actionByValue(boolean value, Integer id) {
        OpcUaClient client;
        OPCNode operateNode = opcDao.getNodeById(id);
        int nameSpaceIndex = operateNode.getNamespaceIndex();
        String identifier = operateNode.getIdentifier();
        try {
            client = opcDao.getConnection(id);
            NodeId node = OPCDao.node(nameSpaceIndex, identifier);
            return OPCDao.writeNode(client, node, value ? Boolean.TRUE : Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
