package capstonServer.capstonServer.repository;

import capstonServer.capstonServer.entity.Contest;
import capstonServer.capstonServer.entity.Like;
import capstonServer.capstonServer.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository <Like,Long> {
    Optional<Like> findByUsersAndContest(Users users, Contest contest);
}
