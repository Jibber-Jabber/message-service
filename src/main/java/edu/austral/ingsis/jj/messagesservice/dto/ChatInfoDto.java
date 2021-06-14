package edu.austral.ingsis.jj.messagesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatInfoDto {

    private String userId;
    private String userName;
    private long unreadCount;
}
