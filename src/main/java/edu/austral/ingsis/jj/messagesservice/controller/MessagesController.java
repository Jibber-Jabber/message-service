package edu.austral.ingsis.jj.messagesservice.controller;

import edu.austral.ingsis.jj.messagesservice.dto.MessageDto;
import edu.austral.ingsis.jj.messagesservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class MessagesController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendPrivateMessage")
    public void sendPrivateMessage(@Payload MessageDto message) {
        System.out.println(message.getContent());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver().trim(), "/reply", message);
    }

    @MessageMapping("/addPrivateUser")
    @SendTo("/queue/reply")
    public MessageDto addPrivateUser(@Payload MessageDto message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("private-username", message.getSender());
        return message;
    }

    @MessageMapping("/all")
    @SendTo("/topic/all")
    public MessageDto post(@Payload MessageDto message) {
        System.out.println("received message " + message.getContent());
        return message;
    }
}
