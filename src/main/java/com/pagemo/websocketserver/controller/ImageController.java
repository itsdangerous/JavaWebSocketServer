/*
1. MultipartFile을 사용해서 클라이언트로부터 이미지(base64인코딩됨)를 받음
2. 파이썬 서버가 이를 구독하고, 분석해서 결과를 다시 줌 */

package com.pagemo.websocketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class ImageController {
    private final SimpMessagingTemplate template;

    public ImageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // 리액트 클라이언트가 /app/imageData 경로로 이미지 데이터를 전송하면
    // 자바 서버는 /topic/imageData를 구독하고 있는 파이썬 클라이언트에게 이미지 데이터를 전송.
    @MessageMapping("/imageData")
    public void processImageDataFromReactClient(@Payload byte[] imageBytes) {
        if (imageBytes.length > 0) {
                System.out.println("이미지 데이터를 리액트 클라이언트로부터 받음. ");
            // /topic/imageData를 구독하는 파이썬 클라이언트에게 이미지 데이터 전송
            this.template.convertAndSend("/topic/imageData", imageBytes);
            System.out.println("이미지데이터를 파이썬 클라이언트에게 성공적으로 보냈음.");
        };
    }

    // /app/analyze 경로로 파이썬 클라이언트가 결과를 던져주면,
    // /topic/analyze리액트 클라이언트에게 결과를 전송
    @MessageMapping("/analyzingData")
    public void receiveAnalyzingDataFromPythonClient(@Payload Map<String, Object> analyzingData) {

        // /topic/analyzingData를 구독하는 리액트 클라이언트에게 이미지 데이터 전송
        this.template.convertAndSend("/topic/analyzingData", analyzingData);

    }

}
