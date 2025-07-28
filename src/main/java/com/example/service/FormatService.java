package com.example.service;

import com.example.model.Format;
import com.example.repository.FormatRepository;
import com.example.service.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FormatService implements IFormat {

    private final FormatRepository formatRepository;

    @Autowired
    public FormatService(FormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

    public Format saveFormat(Format format) {
        return formatRepository.save(format);
    }

    public Optional<Format> getFormatById(int id) {
        return formatRepository.findById(id);
    }

    public List<Format> getAllFormats() {
        return formatRepository.findAll();
    }

    public void deleteFormat(int id) {
        formatRepository.deleteById(id);
    }
}
