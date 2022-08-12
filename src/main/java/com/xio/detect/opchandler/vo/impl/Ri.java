package com.xio.detect.opchandler.vo.impl;

import com.xio.detect.opchandler.vo.R;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ri implements R {
    private Integer status;
    private String message;
}
