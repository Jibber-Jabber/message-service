package edu.austral.ingsis.jj.messagesservice.controller;

import edu.austral.ingsis.jj.messagesservice.dto.ChatInfoDto;
import edu.austral.ingsis.jj.messagesservice.model.ChatMessage;
import edu.austral.ingsis.jj.messagesservice.model.ChatNotification;
import edu.austral.ingsis.jj.messagesservice.service.ChatMessageService;
import edu.austral.ingsis.jj.messagesservice.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class MessagesController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage, true);
        chatMessage.setChatId(chatId.get());

        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName(),
                        chatId.get(),
                        chatMessageService.countNewMessages(saved.getSenderId(), chatMessage.getRecipientId())));
    }

    @GetMapping("/api/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/api/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages (@PathVariable String senderId,
                                                               @PathVariable String recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity<ChatMessage> findMessage (@PathVariable String id) {
        return ResponseEntity.ok(chatMessageService.findById(id));
    }

    @GetMapping("/api/chats/{senderId}")
    public ResponseEntity<List<ChatInfoDto>> findChatsRecipientsBySenderId (@PathVariable String senderId) {
        return ResponseEntity.ok(chatRoomService.findAllRecipientsBySenderId(senderId));
    }
}
