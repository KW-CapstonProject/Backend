package capstonServer.capstonServer.dto.response;

import capstonServer.capstonServer.entity.Comment;
import capstonServer.capstonServer.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private Users author;
    private List<CommentResponse> children = new ArrayList<>();

    public CommentResponse(Long id, String content, Users author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public static CommentResponse convertCommentToDto(Comment comment) {
        return comment.getIsDeleted() ?
                new CommentResponse(comment.getId(), "삭제된 댓글입니다.", null) :
                new CommentResponse(comment.getId(), comment.getContent(),comment.getAuthor());
    }

}
