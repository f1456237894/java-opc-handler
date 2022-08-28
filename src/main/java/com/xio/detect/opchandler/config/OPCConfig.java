package com.xio.detect.opchandler.config;

import com.xio.detect.opchandler.entity.OPCNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix = "opcconfig")
public class OPCConfig {
    private List<OPCNode> nodes;
}
