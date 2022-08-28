package com.xio.detect.opchandler.service;

import org.springframework.stereotype.Service;

@Service
public interface ActionService {
    /**
     * 将停止值写入OPC
     * @return 是否成功
     */
    boolean action(Integer id);

    /**
     * 将启动值写入OPC
     * @return 是否成功
     */
    boolean reset(Integer id);
}
