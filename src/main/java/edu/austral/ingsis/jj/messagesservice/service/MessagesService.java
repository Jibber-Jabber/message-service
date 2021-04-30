package edu.austral.ingsis.jj.messagesservice.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MessagesService {

    public List<String> getAllMessages(){
        return Arrays.asList("hello", "world");
    }
}
