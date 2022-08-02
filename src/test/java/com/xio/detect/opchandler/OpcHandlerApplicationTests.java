package com.xio.detect.opchandler;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class OpcHandlerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testOPC() {
		try {
			String EndPointUrl = "opc.tcp://127.0.0.1:49320";
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

			NodeId nodeId_Tag1 = new NodeId(2, "通道 1.设备 1.test");  //  namespaceIndex： 是获取的的下标   identifier： 是名称
//			NodeId nodeId_Tag2 = new NodeId(2, "#SYS_CURRENT_TIME");
			DataValue value = opcClient.readValue(0.0, TimestampsToReturn.Both, nodeId_Tag1).get(); // 获取值
//			DataValue value2 = opcClient.readValue(0.0, TimestampsToReturn.Both, nodeId_Tag2).get();

			System.out.println(value.getValue().getValue()); // 输出值
//			System.out.println(value2.getValue().getValue());
			// 写入值
			NodeId nodeId = new NodeId(2,"通道 1.设备 1.test");
			//创建Variant对象和DataValue对象
			Boolean writeValue = Boolean.TRUE;
			// 写入类型根据DateType判断
			Variant v = new Variant(writeValue); // 警告： 写入类型要看opc ua变量名里的dateType是什么类型 否则写入失败 false
			DataValue dataValue = new DataValue(v,null,null);
			StatusCode statusCode = opcClient.writeValue(nodeId,dataValue).get();
			System.out.println(statusCode.isGood());// 写入成功返回值
			opcClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
