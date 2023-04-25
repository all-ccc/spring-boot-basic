package com.chaminju.firstproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Example")
@Table(name = "Example") // DB에서 어떤 테이블과 매핑할 건지 명시
public class ExampleEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "example_column1", nullable = false, unique = true) // 제약조건 추가 가능
    private int pk; // 멤버 변수 이름 웬만하면 Column명 따라가는 게 좋음
    private String exampleColumn2;
    private boolean exampleColumn3;
}
