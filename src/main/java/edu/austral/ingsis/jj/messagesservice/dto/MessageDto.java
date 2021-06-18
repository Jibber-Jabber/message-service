package edu.austral.ingsis.jj.messagesservice.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageDto {
    @NotNull
    private String message;

    @NotNull
    private String author;

    @NotNull
    private String receiver;
}
