package com.pocekt.art.repository.comment;

import capstonServer.capstonServer.dto.response.CommentResponse;
import capstonServer.capstonServer.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static capstonServer.capstonServer.dto.response.CommentResponse.convertCommentToDto;
import static capstonServer.capstonServer.entity.QComment.comment;
@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponse> findByContestId(Long id) {

        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.contest.id.eq(id))
                .orderBy(comment.parent.id.asc().nullsFirst(),
                        comment.createDate.asc())
                .fetch();

        List<CommentResponse> commentResponseDTOList = new ArrayList<>();
        Map<Long, CommentResponse> commentDTOHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentResponse commentResponseDTO = convertCommentToDto(c);
            commentDTOHashMap.put(commentResponseDTO.getId(), commentResponseDTO);
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });
        return commentResponseDTOList;
    }

    @Override
    public Optional<Comment> findCommentByIdWithParent(Long id) {

        Comment selectedComment = queryFactory.select(comment)
                .from(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(selectedComment);
    }
}
