package com.example.repository;
import com.example.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

	Optional<Language> findByLanguageNameIgnoreCase(String languageName);
}
