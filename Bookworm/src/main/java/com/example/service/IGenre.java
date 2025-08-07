package com.example.service;

import com.example.model.Genre;
import java.util.List;
import java.util.Optional;

public interface IGenre {
    Genre saveGenre(Genre genre);
    Optional<Genre> getGenreById(int id);
    List<Genre> getAllGenres();
    void deleteGenre(int id);
    
}
