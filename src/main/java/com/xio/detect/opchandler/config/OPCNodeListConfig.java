package com.xio.detect.opchandler.config;

import com.xio.detect.opchandler.entity.OPCNode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class OPCNodeListConfig {
    @ConfigurationProperties("nodeList")
    private List<OPCNode> nodeList;
}
