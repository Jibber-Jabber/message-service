package edu.austral.ingsis.jj.messagesservice.controller;

import edu.austral.ingsis.jj.messagesservice.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessagesController {

    private final MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService){
        this.messagesService = messagesService;
    }

    @GetMapping
    public List<String> getAllMessages(){
        return messagesService.getAllMessages();
    }
}
