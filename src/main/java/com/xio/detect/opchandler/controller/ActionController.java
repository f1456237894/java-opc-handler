package com.xio.detect.opchandler.controller;

import com.xio.detect.opchandler.service.ActionService;
import com.xio.detect.opchandler.util.ResultParser;
import com.xio.detect.opchandler.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    ActionService actionService;

    @Autowired
    ResultParser resultParser;

    @GetMapping("/stop")
    public R stopAction() {
        boolean status = actionService.action();
        return status ? resultParser.getSuccess() : resultParser.getFailure();
    }

    @GetMapping("/reset")
    public R resetAction() {
        boolean status = actionService.reset();
        return status ? resultParser.getSuccess() : resultParser.getFailure();
    }
}
