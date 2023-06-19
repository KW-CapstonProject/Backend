package capstonServer.capstonServer.repository.contest;

import capstonServer.capstonServer.dto.response.ContestPageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContestRepositoryCustom {
    List<ContestPageResponse> findPageContest(Pageable pageable, String title, String contents);

}
