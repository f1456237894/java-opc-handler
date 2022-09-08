package com.xio.detect.opchandler.controller;

import com.xio.detect.opchandler.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class StartOPCRunner implements ApplicationRunner {

    @Autowired
    private ActionService actionService;

    /**
     * 主方法
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        actionService.startApp();
    }
}
