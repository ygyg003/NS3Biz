package com.demo.KaKao.api;

import com.demo.KaKao.service.KaKaoAuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class KaKaoLogin {

    private final KaKaoAuthService service;

    @GetMapping(value="/kakao")
    public String kakaoLogin(@RequestParam(value="code",required = false) String code) {
        System.out.println("code:: " + code);
        String access_Token = service.getAccessToken(code);
        System.out.println("Token :: " + access_Token);

        return access_Token;
    }
}
