package edu.austral.ingsis.jj.messagesservice.repository;

import edu.austral.ingsis.jj.messagesservice.model.ChatMessage;
import edu.austral.ingsis.jj.messagesservice.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String chatId);

    @Query(value = "update ChatMessage set status = ?3 where senderId = ?1 and recipientId = ?2")
    void updateStatuses(String senderId,String recipientId, MessageStatus delivered);
}