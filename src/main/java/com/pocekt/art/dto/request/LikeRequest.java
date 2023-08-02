package com.pocekt.art.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequest {

    private Long usersId;
    private Long contestId;

}
