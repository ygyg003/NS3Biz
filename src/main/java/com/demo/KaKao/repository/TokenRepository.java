package com.demo.KaKao.repository;

import com.demo.KaKao.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity,Long> {

}
