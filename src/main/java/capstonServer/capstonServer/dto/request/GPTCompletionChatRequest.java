package capstonServer.capstonServer.dto.request;



import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GPTCompletionChatRequest {
//    private String model;
//
//    private String role;

    private String content;

    private Integer maxTokens;


    public static ChatCompletionRequest of(GPTCompletionChatRequest request) {
        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(convertChatMessage("user", request.getContent()))
                .maxTokens(request.getMaxTokens() != null ? request.getMaxTokens() : 1000)
                .build();
    }

    private static List<ChatMessage> convertChatMessage(String role, String content) {
        return List.of(new ChatMessage(role, content));
    }
}