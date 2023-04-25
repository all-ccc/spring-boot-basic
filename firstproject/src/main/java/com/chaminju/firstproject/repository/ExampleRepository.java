package com.chaminju.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaminju.firstproject.entity.ExampleEntity;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Integer> {
    
    public ExampleEntity findByPk(int pk); // 우리가 만들어줌. 0개 or 1개(unique라)
    public List<ExampleEntity> findByExampleColumn3AndExampleColumn2(boolean exampleColumn3, String exampleColumn2);
    // 반환 타입 list인 이유 : unique 아니라 무조건 0개 아님 이상이라서
    
    public boolean existsByExampleColumn3(boolean exampleColumn3); // 해당 조건에 따라 값이 존재하는지 안 하는지 

}
