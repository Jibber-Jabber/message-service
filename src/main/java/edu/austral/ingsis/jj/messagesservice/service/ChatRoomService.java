package edu.austral.ingsis.jj.messagesservice.service;

import edu.austral.ingsis.jj.messagesservice.dto.UserDto;
import edu.austral.ingsis.jj.messagesservice.model.ChatMessage;
import edu.austral.ingsis.jj.messagesservice.model.ChatRoom;
import edu.austral.ingsis.jj.messagesservice.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Optional<String> getChatId(ChatMessage chatMessage, boolean createIfNotExist) {

        return chatRoomRepository
                .findBySenderIdAndRecipientId(chatMessage.getSenderId(), chatMessage.getRecipientId())
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }
                    var chatId = String.format("%s_%s", chatMessage.getSenderId(), chatMessage.getRecipientId());

                    ChatRoom senderRecipient = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderId(chatMessage.getSenderId())
                            .senderName(chatMessage.getSenderName())
                            .recipientId(chatMessage.getRecipientId())
                            .recipientName(chatMessage.getRecipientName())
                            .build();

                    ChatRoom recipientSender = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderId(chatMessage.getRecipientId())
                            .senderName(chatMessage.getRecipientName())
                            .recipientId(chatMessage.getSenderId())
                            .recipientName(chatMessage.getSenderName())
                            .build();
                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }

    public Optional<String> findChatIdBySenderAndRecipient(String senderId, String recipientId) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId);
    }

    public List<UserDto> findAllRecipientsBySenderId(String senderId) {
        return chatRoomRepository.findAllBySenderId(senderId).stream()
                .map(chat -> new UserDto(chat.getRecipientId(), chat.getRecipientName())).collect(Collectors.toList());
    }
}
