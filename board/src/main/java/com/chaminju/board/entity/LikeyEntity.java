package com.chaminju.board.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.chaminju.board.entity.primaryKey.LikeyPk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Likey")
@Table(name = "Likey")
@IdClass(LikeyPk.class) // id의 타입은 LikeyPk라고 지정(복합키라서)
public class LikeyEntity {
    @Id
    private int boardNumber;
    @Id
    private String userEmail;
}
