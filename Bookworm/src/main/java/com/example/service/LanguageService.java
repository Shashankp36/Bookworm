package com.example.service;

import com.example.model.Language;
import com.example.repository.LanguageRepository;
import com.example.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageService implements ILanguage {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language saveLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Optional<Language> getLanguageById(int id) {
        return languageRepository.findById(id);
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public void deleteLanguage(int id) {
        languageRepository.deleteById(id);
    }
}
