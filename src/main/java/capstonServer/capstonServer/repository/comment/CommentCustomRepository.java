package capstonServer.capstonServer.repository.comment;

import capstonServer.capstonServer.dto.response.CommentResponse;
import capstonServer.capstonServer.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    List<CommentResponse> findByContestId(Long id);

    Optional<Comment> findCommentByIdWithParent(Long id);
}
