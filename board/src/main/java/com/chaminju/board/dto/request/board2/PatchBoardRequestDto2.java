package com.chaminju.board.dto.request.board2;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.chaminju.board.dto.request.board.PatchBoardRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchBoardRequestDto2 {
    @NotNull
    private Integer boardNumber; // notNull로 받고 싶어서 Integer로 받음(기본형 타입에는 null 불가능하기 때문)
    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;
    private String boardImageUrl;

    public PatchBoardRequestDto2(PatchBoardRequestDto dto) {
        this.boardNumber = dto.getBoardNumber();
        this.boardTitle = dto.getBoardTitle();
        this.boardContent = dto.getBoardContent();
        this.boardImageUrl = dto.getBoardImageUrl();
    }
    
}
