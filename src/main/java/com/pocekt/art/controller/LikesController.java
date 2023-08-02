package com.pocekt.art.controller;

import capstonServer.capstonServer.auth.AuthUser;
import capstonServer.capstonServer.entity.Users;
import capstonServer.capstonServer.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LikesController {
    private final LikeService likeService;
    @PreAuthorize("hasAnyRole('USER')")
    @Transactional
    @PostMapping( "/heart/{contestId}")
    public ResponseEntity<?> insert(@ApiIgnore @AuthUser Users users, @PathVariable Long contestId) throws Exception {
        return likeService.insert(users, contestId);

    }

    @PreAuthorize("hasAnyRole('USER')")
    @Transactional
    @DeleteMapping("/unheart/{contestId}")
    public ResponseEntity<?> delete(@ApiIgnore @AuthUser Users users, @PathVariable("contestId") Long contestId) throws Exception {
        return likeService.delete(users, contestId);
    }
}
