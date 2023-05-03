package capstonServer.capstonServer.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
