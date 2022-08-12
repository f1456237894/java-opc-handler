package com.xio.detect.opchandler.dao;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *
 */

@Component
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
    static OpcUaClient getConnection() throws Exception {
        String EndPointUrl = "opc.tcp://192.168.1.108:49320";
        //安全策略选择
        EndpointDescription[] endpointDescription = UaTcpStackClient.getEndpoints(EndPointUrl).get();
        //过滤掉不需要的安全策略，选择一个自己需要的安全策略
        EndpointDescription endpoint = Arrays.stream(endpointDescription)
                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getSecurityPolicyUri()))
                .findFirst().orElseThrow(() -> new Exception("no desired endpoints returned"));

        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setApplicationName(LocalizedText.english("test")) // opc ua 定义的名
                .setApplicationUri(EndPointUrl)// 地址
                .setEndpoint(endpoint)// 安全策略等配置
                .setRequestTimeout(UInteger.valueOf(50000)) //等待时间
                .build();

        OpcUaClient opcClient = new OpcUaClient(config);// 准备连接

        //开启连接
        opcClient.connect().get();
        return opcClient;
    }

    /**
     * 获取一个节点对象
     * @param namespaceIndex ns
     * @param identifier i
     * @return 节点对象
     */
    static NodeId node(Integer namespaceIndex, String identifier) {
        return new NodeId(namespaceIndex, identifier);
    }

}
