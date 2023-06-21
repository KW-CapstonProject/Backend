package capstonServer.capstonServer.repository.contest;

import capstonServer.capstonServer.entity.Contest;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> , ContestCustomRepository {
}
