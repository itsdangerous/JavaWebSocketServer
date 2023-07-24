/*클라이언트 메시지를 수신하고 처리하는 엔드포인트를 담당*/

package com.pagemo.websocketserver.controller;

import org.springframework.web.bind.annotation.*;


@RestController
public class RestApiController {
    //    테스트
    @GetMapping("/test")
    @ResponseBody
    public String testMessage() {
        return "test...OK";
    }

}