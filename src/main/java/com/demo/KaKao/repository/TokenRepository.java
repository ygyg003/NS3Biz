package com.demo.KaKao.repository;

import com.demo.KaKao.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity,Long> {
    Optional<TokenEntity> findByLoginyn(String loginyn);
}
