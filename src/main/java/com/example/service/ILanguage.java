package com.example.service;

import com.example.model.Language;
import java.util.List;
import java.util.Optional;

public interface ILanguage {
    Language saveLanguage(Language language);
    Optional<Language> getLanguageById(int id);
    List<Language> getAllLanguages();
    void deleteLanguage(int id);
}
