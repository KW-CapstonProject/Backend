package capstonServer.capstonServer.auth;

import org.springframework.beans.factory.annotation.Value;

public class AuthPropoerties {
    @Value("${app.kakao.clientId}")
    private String client_id;
}
