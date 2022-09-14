package com.demo.KaKao.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Integer refreshTokenExpiresIn;

    @Column(nullable = false)
    private String loginYn;

    @Column(nullable = false)
    private String loginTime;

    @Builder
    public TokenEntity(String accessToken, String refreshToken, Integer refreshTokenExpiresIn, String loginYn, String loginTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.loginYn = loginYn;
        this.loginTime=loginTime;
    }
}