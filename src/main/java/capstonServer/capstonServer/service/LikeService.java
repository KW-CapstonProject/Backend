package capstonServer.capstonServer.service;

import capstonServer.capstonServer.dto.response.Response;
import capstonServer.capstonServer.entity.Contest;
import capstonServer.capstonServer.entity.Like;
import capstonServer.capstonServer.entity.Users;
import capstonServer.capstonServer.repository.LikeRepository;
import capstonServer.capstonServer.repository.UsersRepository;
import capstonServer.capstonServer.repository.contest.ContestRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UsersRepository usersRepository;
    private final ContestRepository contestRepository;
    private final Response response;

    @Transactional
    public ResponseEntity<?> insert(Users user, Long contestId) throws Exception {
        Users users=usersRepository.findById(user.getId())
                .orElseThrow(()->new NotFoundException("Could not found user"));

        Contest contest=contestRepository.findById(contestId)
                .orElseThrow(()->new NotFoundException("Could not found contest"));

        if (likeRepository.findByUsersAndContest(users,contest).isPresent()){
            throw new IllegalArgumentException("already exist data");
        }
        Like like= Like.builder()
                .contest(contest)
                .users(users)
                .build();

        likeRepository.save(like);
        contestRepository.updateLikeCount(contest);
        return response.success(like, "좋아요를 눌렀습니다..", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> delete(Users user, Long contestId) {

        Users users=usersRepository.findById(user.getId())
                .orElseThrow(()->new NotFoundException("Could not found user"));

        Contest contest=contestRepository.findById(contestId)
                .orElseThrow(()->new NotFoundException("Could not found contest"));

        Like like = likeRepository.findByUsersAndContest(users, contest)
                .orElseThrow(() -> new NotFoundException("Could not found heart id"));

        likeRepository.delete(like);
        contestRepository.subLikeCount(contest);

        return response.success(like, "좋아요를 취소하였습니다..", HttpStatus.CREATED);
    }
}
