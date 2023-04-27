package com.chaminju.board.dto.request.board;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class patchBoardRequestDto {
    @NotBlank
    @Email
    private String userEmail;
    @NotNull
    private Integer boardNumber; // notNull로 받고 싶어서 Integer로 받음(기본형 타입에는 null 불가능하기 때문)
    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;
    private String boardImageUrl;
}
