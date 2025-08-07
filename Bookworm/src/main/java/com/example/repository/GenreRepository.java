package com.example.repository;
import com.example.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
	
	  Optional<Genre> findByGenreNameIgnoreCase(String genreName);
}
