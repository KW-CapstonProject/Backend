package capstonServer.capstonServer.repository;

import capstonServer.capstonServer.entity.Contest;
import capstonServer.capstonServer.entity.Likes;
import capstonServer.capstonServer.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository <Likes,Long> {
    Optional<Likes> findByUsersAndContest(Users users, Contest contest);
}
