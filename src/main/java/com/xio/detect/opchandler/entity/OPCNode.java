package com.xio.detect.opchandler.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OPCNode {
    private String url;
    private String name;
    private Integer namespaceIndex;
    private String identifier;
}
