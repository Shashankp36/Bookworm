package com.example.service;

import com.example.model.Format;
import java.util.List;
import java.util.Optional;

public interface IFormat {
    Format saveFormat(Format format);
    Optional<Format> getFormatById(int id);
    List<Format> getAllFormats();
    void deleteFormat(int id);
}
