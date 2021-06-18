package edu.austral.ingsis.jj.messagesservice.repository;

import edu.austral.ingsis.jj.messagesservice.model.ChatMessage;
import edu.austral.ingsis.jj.messagesservice.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    // (A and B) or (A and C)
    List<ChatMessage> findByChatIdAndSenderIdOrChatIdAndRecipientId(String chatId, String senderId, String chatId2, String recipientId);

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);
}
