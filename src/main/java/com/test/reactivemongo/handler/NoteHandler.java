package com.test.reactivemongo.handler;

import com.test.reactivemongo.model.Note;
import com.test.reactivemongo.model.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@RequiredArgsConstructor
public class NoteHandler {
    private static final String NOTE_ID = "noteId";
    private final NoteRepository repository;

    public Mono<ServerResponse> getNote(ServerRequest request) {
        String noteId = request.pathVariable(NOTE_ID);
        return repository.findById(noteId)
                .map(note -> new NoteDto(note.getContent()))
                .switchIfEmpty(Mono.just(new NoteDto("")))
                .flatMap(note -> ok().body(Mono.just(new NoteDto(note.getContent())), NoteDto.class));
    }

    public Mono<ServerResponse> updateNote(ServerRequest request) {
        String noteId = request.pathVariable(NOTE_ID);
        return request.bodyToMono(NoteDto.class)
                .map(noteDto -> Note.builder().id(noteId).content(noteDto.getContent()).build())
                .flatMap(note -> repository.save(note))
                .flatMap(note -> ok().body(Mono.just(new NoteDto(note.getContent())), NoteDto.class));
    }
}
