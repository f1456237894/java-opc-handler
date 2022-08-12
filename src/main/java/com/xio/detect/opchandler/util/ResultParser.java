package com.xio.detect.opchandler.util;

import com.xio.detect.opchandler.vo.R;
import com.xio.detect.opchandler.vo.impl.Ri;
import org.springframework.stereotype.Component;

@Component
public class ResultParser {
    public R getSuccess() {
        return getResult(0, "操作成功");
    }

    public R getResult(Integer status, String message) {
        return new Ri(status, message);
    }

    public R getFailure() {
        return getResult(1, "操作失败，发生错误");
    }

}
