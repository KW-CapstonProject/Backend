package capstonServer.capstonServer.repository.comment;

import capstonServer.capstonServer.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentCustomRepository {
    List<Comment> findAllByUsers_Id(UUID users_id);
}
