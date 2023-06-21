package capstonServer.capstonServer.controller;

import capstonServer.capstonServer.auth.AuthUser;
import capstonServer.capstonServer.entity.Users;
import capstonServer.capstonServer.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("")
    public ResponseEntity<?> insert(@AuthUser Users users, @PathVariable("contestId") Long contestId) throws Exception {
        return likeService.insert(users, contestId);

    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(@AuthUser Users users, @PathVariable("contestId") Long contestId) throws Exception {
        return likeService.delete(users, contestId);

    }
}
