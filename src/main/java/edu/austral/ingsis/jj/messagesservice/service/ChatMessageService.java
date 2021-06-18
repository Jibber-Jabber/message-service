package edu.austral.ingsis.jj.messagesservice.service;

import edu.austral.ingsis.jj.messagesservice.exceptions.ResourceNotFoundException;
import edu.austral.ingsis.jj.messagesservice.model.ChatMessage;
import edu.austral.ingsis.jj.messagesservice.model.MessageStatus;
import edu.austral.ingsis.jj.messagesservice.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired private ChatMessageRepository chatMessageRepository;
    @Autowired private ChatRoomService chatRoomService;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatRoomService chatRoomService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomService = chatRoomService;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.findChatIdBySenderAndRecipient(senderId, recipientId);
        var chatId2 = chatRoomService.findChatIdBySenderAndRecipient(recipientId, senderId);

        var messages = chatId.map(cId -> chatMessageRepository.findByChatIdAndSenderIdOrChatIdAndRecipientId(cId, senderId, cId, recipientId)).orElse(new ArrayList<>());
        var messages2 = chatId2.map(cId -> chatMessageRepository.findByChatIdAndSenderIdOrChatIdAndRecipientId(cId, senderId, cId, recipientId)).orElse(new ArrayList<>());

        messages.addAll(messages2);

        if(messages.size() > 0) {
            messages.forEach(message -> {
                message.setStatus(MessageStatus.DELIVERED);
                chatMessageRepository.save(message);
            });
        }

        return messages;
    }

    public ChatMessage findById(String id) {
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }
}
