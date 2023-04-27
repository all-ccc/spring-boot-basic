package com.chaminju.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaminju.board.entity.LikeyEntity;
import com.chaminju.board.entity.primaryKey.LikeyPk;

@Repository
public interface LikeyRepository extends JpaRepository<LikeyEntity, LikeyPk> {
    
}
