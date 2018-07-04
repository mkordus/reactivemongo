package com.test.reactivemongo.handler;

import com.test.reactivemongo.model.Note;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NoteDto {

    private final String content;

    public NoteDto(Note note) {
        content = note.getContent();
    }
}
