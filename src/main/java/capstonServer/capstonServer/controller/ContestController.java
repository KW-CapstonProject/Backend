package capstonServer.capstonServer.controller;

import capstonServer.capstonServer.auth.AuthUser;
import capstonServer.capstonServer.dto.request.ContestRequest;
import capstonServer.capstonServer.entity.Users;
import capstonServer.capstonServer.service.ContestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/contest")
@Slf4j
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("")
    public ResponseEntity getContestList(@PageableDefault Pageable pageable, @RequestParam(required = false) String title, @RequestParam(required = false) String contents) {
        return contestService.getContestList(pageable, title,contents);

    }
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{contestId}")
    public ResponseEntity findById(@ApiIgnore @AuthUser Users users, @PathVariable("contestId") Long contestId) {

        return contestService.findById(users,contestId);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity writeStudy(@ApiIgnore @AuthUser Users users,
                                     ContestRequest contestRequest,  @RequestBody(required = false) MultipartFile[] files ) throws IOException {

        return contestService.createContest(users, contestRequest,files);
        //return new ResponseEntity(new ApiRes("스터디 등록 성공", HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(value = "/{contestId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity updateStudy(@ApiIgnore @AuthUser Users users, @PathVariable Long contestId,
                                      ContestRequest contestRequest, @RequestParam (required = false) MultipartFile[] files) throws IOException {
        return contestService.updateContest(users, contestId,contestRequest,files);
        //return new ResponseEntity(new ApiRes("스터디 수정 성공", HttpStatus.OK), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{contestId}/delete")
    public ResponseEntity deleteStudyById(@ApiIgnore @AuthUser Users users,  @PathVariable Long contestId) {
        return contestService.deleteStudyById(users, contestId);


    }

}



