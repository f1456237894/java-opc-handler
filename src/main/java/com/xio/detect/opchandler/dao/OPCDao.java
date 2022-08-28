package com.xio.detect.opchandler.dao;

import com.xio.detect.opchandler.entity.OPCNode;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 *
 */

@Repository
public interface OPCDao {

    /**
     * 将值写入节点
     *
     * @return 是否成功
     */
    static boolean writeNode(OpcUaClient client, NodeId node, Boolean value) {
        Variant writeValue = new Variant(value);
        DataValue dataValue = new DataValue(writeValue, null, null);
        try {
            StatusCode status = client.writeValue(node, dataValue).get();
            if (status.isGood()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取连接
     * @return 连接对象
     */
    OpcUaClient getConnection(Integer id) throws Exception;

    /**
     * 获取一个节点对象
     * @param namespaceIndex ns
     * @param identifier i
     * @return 节点对象
     */
    static NodeId node(Integer namespaceIndex, String identifier) {
        return new NodeId(namespaceIndex, identifier);
    }


    OPCNode getNodeById(Integer id);

}
