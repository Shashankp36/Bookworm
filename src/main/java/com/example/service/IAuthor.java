package com.example.service;

import com.example.model.Author;
import java.util.List;
import java.util.Optional;

public interface IAuthor {
    Author saveAuthor(Author author);
    Optional<Author> getAuthorById(int id);
    List<Author> getAllAuthors();
    void deleteAuthor(int id);
}
