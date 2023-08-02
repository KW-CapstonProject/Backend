package com.pocekt.art.service;

import capstonServer.capstonServer.dto.response.Response;
import capstonServer.capstonServer.entity.Contest;
import capstonServer.capstonServer.entity.Likes;
import capstonServer.capstonServer.entity.Users;
import capstonServer.capstonServer.repository.LikeRepository;
import capstonServer.capstonServer.repository.UsersRepository;
import capstonServer.capstonServer.repository.contest.ContestRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UsersRepository usersRepository;
    private final ContestRepository contestRepository;
    private final Response response;


    public ResponseEntity<?> insert(Users user, Long contestId) throws Exception {
        Users users=usersRepository.findById(user.getId())
                .orElseThrow(()->new NotFoundException("Could not found user"));

        Contest contest=contestRepository.findById(contestId)
                .orElseThrow(()->new NotFoundException("Could not found contest"));

        if (likeRepository.findByUsersAndContest(users,contest).isPresent()){
            throw new IllegalArgumentException("already exist data");
        }
        Likes likes = Likes.builder()
                .contest(contest)
                .users(users)
                .build();

        likeRepository.save(likes);
        contestRepository.updateLikeCount(contest);
        return response.success(likes, "좋아요를 눌렀습니다..", HttpStatus.CREATED);
    }


    public ResponseEntity<?> delete(Users user, Long contestId) {

        Users users=usersRepository.findById(user.getId())
                .orElseThrow(()->new NotFoundException("Could not found user"));

        Contest contest=contestRepository.findById(contestId)
                .orElseThrow(()->new NotFoundException("Could not found contest"));

        Likes likes = likeRepository.findByUsersAndContest(users, contest)
                .orElseThrow(() -> new NotFoundException("Could not found heart id"));

        likeRepository.delete(likes);
        contestRepository.subLikeCount(contest);

        return response.success(likes, "좋아요를 취소하였습니다..", HttpStatus.CREATED);
    }
}
