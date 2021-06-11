package edu.austral.ingsis.jj.messagesservice.service;

import edu.austral.ingsis.jj.messagesservice.model.Message;
import edu.austral.ingsis.jj.messagesservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MessagesService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<String> getAllMessages(){
        return Arrays.asList("hello", "world");
    }

    public void receiveMessage(Message message) {

    }
}
