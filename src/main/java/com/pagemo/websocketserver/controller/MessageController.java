/*클라이언트 메시지를 수신하고 처리하는 엔드포인트를 담당*/

package com.pagemo.websocketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MessageController {

    private final SimpMessagingTemplate template;

    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

//    클라이언트가 /app/send로 메시지를 보낼 때마다
//    그 메시지를 그대로 /topic/messages 주제를 구독하는 모든 클라이언트에게 보냄
    @MessageMapping("/receive")
    public void processMessageFromClient(@Payload Map<String, String> messageData) throws Exception {
        // 메시지 데이터를 출력
        System.out.println("모델 클라이언트에서 받은 메세지 : " + messageData);
        this.template.convertAndSend("/topic/messages", "잘 받았다. ㅋㅋ"); // http post 요청의 본문을 웹소켓 클라이언트에게 보냄
    }

    @MessageMapping("/message")
    public void processMessageFromAllClient(@Payload String messageData) {
        this.template.convertAndSend("/topic/message", messageData); // http post 요청의 본문을 웹소켓 클라이언트에게 보냄
        System.out.println("클라이언트로부터 데이터 받음 : " + messageData);
    }

}