package com.xio.detect.opchandler.dao.impl;

import com.xio.detect.opchandler.config.OPCConfig;
import com.xio.detect.opchandler.dao.OPCDao;
import com.xio.detect.opchandler.entity.OPCNode;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class OPCDaoImpl implements OPCDao {

    @Autowired
    private OPCConfig opcConfig;
    @Override
    public OpcUaClient getConnection(Integer id) throws Exception {
        OPCNode node = this.getNodeById(id);
        if (id == -1) {
            node = this.getStartNode();
        }
        String EndPointUrl = node.getUrl();
        String nodeName = node.getName();
        //安全策略选择
        EndpointDescription[] endpointDescription = UaTcpStackClient.getEndpoints(EndPointUrl).get();
        //过滤掉不需要的安全策略，选择一个自己需要的安全策略
//        EndpointDescription endpoint = Arrays.stream(endpointDescription)
//                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getSecurityPolicyUri()))
//                .findFirst().orElseThrow(() -> new Exception("no desired endpoints returned"));

        EndpointDescription endpoint = Arrays.stream(endpointDescription)
                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.Basic256.getSecurityPolicyUri()))
                .findFirst().orElseThrow(() -> new Exception("no desired endpoints returned"));

        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setApplicationName(LocalizedText.english(nodeName)) // opc ua 定义的名
                .setApplicationUri(EndPointUrl)// 地址
                .setEndpoint(endpoint)// 安全策略等配置
                .setRequestTimeout(UInteger.valueOf(50000)) //等待时间
                .build();

        OpcUaClient opcClient = new OpcUaClient(config);// 准备连接

        //开启连接
        opcClient.connect().get();
        return opcClient;
    }

    public OPCNode getNodeById(Integer id) {
        return opcConfig.getNodes().get(id);
    }

    public OPCNode getStartNode() {
        return opcConfig.getStartNode();
    }
}
