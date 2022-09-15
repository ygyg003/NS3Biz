package com.demo.KaKao.api;

import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.HttpCallService;
import com.demo.KaKao.entity.TokenEntity;
import com.demo.KaKao.vo.TokenVo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KaKaoLogin extends HttpCallService {

    private String kakaoAuthUrl = "https://kauth.kakao.com";


    private String kakaoApi = "3636ffa50d28afafd41c5a0c184f8210";


    private String redriectURI = "http://localhost:8092/login/oauth_kakao";

    private final TokenRepository tokenRepository;


    // 카카오 로그인 창 호출
    @RequestMapping(value = "/login/getKakaoAuthUrl")
    public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
        log.info("통신시작");
        String reqUrl = kakaoAuthUrl + "/oauth/authorize?client_id="+kakaoApi+"&redirect_uri="+redriectURI+"&response_type=code&scope=talk_message";
        return reqUrl;
    }
    // 카카오 연동정보 조회
    @RequestMapping(value = "/login/oauth_kakao")
    public String oauthKakao(HttpServletRequest request, HttpServletResponse response) throws  Exception {
        String code = request.getParameter("code");
        String error = request.getParameter("error");
        // 카카오 로그인 페이지에서 취소버튼 눌렀을 경우
//        if(error != null) {
//            if(error.equals("access_denied")) {   -> access_denied : 사용자가 동의화면에서 동의하고 계속하기를 누르지 않고 로그인을 취소한 경우.
//                return "redirect:/login";
//            }
//        }
        String accessToken = getAccessToken(code);
        return null;
    }
    // 카카오 로그인 access_token 리턴
    public String getAccessToken(String code) {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";
        MultiValueMap<String ,String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

        parameters.add("code",code);
        parameters.add("grant_type","authorization_code");
        parameters.add("client_id","3636ffa50d28afafd41c5a0c184f8210");
        parameters.add("redirect_url","http://localhost:8092");
        parameters.add("scope","talk_message");
        parameters.add("client_secret","huknc0RAJguKW26UgoW75TO0V3B6gee2");

        HttpEntity<?> requestEntity = httpClientEntity(header,parameters);

        ResponseEntity<String> response = httpRequest("https://kauth.kakao.com/oauth/token", HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.get("access_token").toString();
        refreshToken = jsonData.get("refresh_token").toString();
        String refresh_token_expires_in = jsonData.get("refresh_token_expires_in").toString();

        if(accessToken.isEmpty() || refreshToken.isEmpty()) {
            log.debug("토큰 발급에 실패");
            return "토큰 발급 실패";
        }else {
            log.debug("토큰 발급");
            TokenVo dto = new TokenVo();
//            TokenEntity token = dto.toEntity(accessToken, refreshToken, refresh_token_expires_in, "Y");
            TokenEntity entity = new TokenEntity();
            entity.setAccesstoken(accessToken);
            entity.setRefreshtoken(refreshToken);
            entity.setRefreshtokenexpiresin(Integer.parseInt(refresh_token_expires_in));
            entity.setLogintime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
            entity.setLoginyn("Y");

            tokenRepository.save(entity);
            System.out.println(accessToken);
            return accessToken;
        }
    }

}
