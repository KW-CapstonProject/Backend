package com.pocekt.art.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

}
