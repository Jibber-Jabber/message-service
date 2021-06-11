package edu.austral.ingsis.jj.messagesservice.repository;

import edu.austral.ingsis.jj.messagesservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
}
