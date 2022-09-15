package com.demo.KaKao.entity;

import com.demo.KaKao.vo.TokenVo;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Data
//@NoArgsConstructor(access = PROTECTED)
@NoArgsConstructor
@Table(name = "login_token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accesstoken;

    @Column(nullable = false)
    private String refreshtoken;

    @Column(nullable = false)
    private Integer refreshtokenexpiresin;

    @Column(nullable = false)
    private String loginyn;

    @Column(nullable = false)
    private String logintime;

//    public TokenEntity(TokenVo vo) {
//        this.accesstoken = vo.getAccessToken();
//        this.refreshtoken = vo.getAccessToken();
//        this.refreshtokenexpiresIn = vo.getRefreshTokenExpiresIn();
//        this.loginyn = vo.getLoginYn();
//        this.logintime = vo.getAccessToken();
//    }
}