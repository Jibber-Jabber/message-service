package edu.austral.ingsis.jj.messagesservice.controller;

import edu.austral.ingsis.jj.messagesservice.dto.MessageDto;
import edu.austral.ingsis.jj.messagesservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class MessagesController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendPrivateMessage")
    @SendTo("/queue/reply")
    public void sendPrivateMessage(@Payload MessageDto message) {
        System.out.println(message.getMessage());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver().trim(), "/reply", message);
    }

    @MessageMapping("/all")
    @SendTo("/topic/all")
    public MessageDto post(@Payload MessageDto message) {
        System.out.println("received message " + message.getMessage());
        return message;
    }
}
