package edu.austral.ingsis.jj.messagesservice.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageDto {
    @NotNull
    private String content;

    @NotNull
    private String sender;

    @NotNull
    private String receiver;
}
