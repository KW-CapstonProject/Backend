package capstonServer.capstonServer.repository.comment;

import capstonServer.capstonServer.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentCustomRepository {
}
