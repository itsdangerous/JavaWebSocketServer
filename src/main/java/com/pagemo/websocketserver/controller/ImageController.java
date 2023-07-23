/*
1. MultipartFile을 사용해서 클라이언트로부터 이미지(base64인코딩됨)를 받음
2. 파이썬 서버가 이를 구독하고, 분석해서 결과를 다시 줌 */

package com.pagemo.websocketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.Map;


@RestController
public class ImageController {
    private final SimpMessagingTemplate template;
    private long start;

    public ImageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @PostMapping("/image")
    public String image(@RequestBody byte[] imageBytes) {
        start = System.currentTimeMillis();
        this.template.convertAndSend("/topic/image", imageBytes); // http post 요청의 본문을 웹소켓 클라이언트에게 보냄
        return "이미지 잘 받음";

    }

    @MessageMapping("/analyze")
    public void processMessageFromClient(@Payload Map<String, Object> messageData) {
        long end = System.currentTimeMillis();
        System.out.println("서버가 받은 이미지 분석 결과: " + messageData);
        System.out.println("걸린 시간 : " + (end - start) + "ms");
    }

}
