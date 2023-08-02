package com.pocekt.art.auth;

import org.springframework.beans.factory.annotation.Value;

public class AuthPropoerties {
    @Value("${app.kakao.clientId}")
    private String client_id;
}
