package com.chaminju.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaminju.board.entity.LikeyEntity;
import com.chaminju.board.entity.primaryKey.LikeyPk;

@Repository
public interface LikeyRepository extends JpaRepository<LikeyEntity, LikeyPk> {
    
    List<LikeyEntity> findByBoardNumber(int boardNumber);

    @org.springframework.transaction.annotation.Transactional
    void deleteByBoardNumber (int boardNumber);

}
