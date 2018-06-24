package com.test.reactivemongo.model;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NoteRepository extends ReactiveMongoRepository<Note, String> {
}
