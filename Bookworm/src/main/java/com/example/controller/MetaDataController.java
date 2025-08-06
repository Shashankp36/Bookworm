package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Author;
import com.example.model.Format;
import com.example.model.Genre;
import com.example.model.Language;
import com.example.model.Publisher;
import com.example.service.IAuthor;
import com.example.service.IFormat;
import com.example.service.IGenre;
import com.example.service.ILanguage;
import com.example.service.IPublisher;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/meta")
=======

@RequestMapping("/api/meta")

>>>>>>> f2c0038a01c244c396ab81dffdd1fa9e19cfd5b4
public class MetaDataController {

    @Autowired private IGenre genreService;
    @Autowired private ILanguage languageService;
    @Autowired private IFormat formatService;
    @Autowired private IAuthor authorService;
    @Autowired private IPublisher publisherService;

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/formats")
    public ResponseEntity<List<Format>> getAllFormats() {
        return ResponseEntity.ok(formatService.getAllFormats());
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/publishers")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }
}
