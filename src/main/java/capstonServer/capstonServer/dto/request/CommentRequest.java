package capstonServer.capstonServer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    private Long parentId;
    private String content;

    public CommentRequest(String content) {
        this.content = content;
    }


}
