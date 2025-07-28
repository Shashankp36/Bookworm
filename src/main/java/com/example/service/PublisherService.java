package com.example.service;

import com.example.model.Publisher;
import com.example.repository.PublisherRepository;
import com.example.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService implements IPublisher {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Optional<Publisher> getPublisherById(int id) {
        return publisherRepository.findById(id);
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public void deletePublisher(int id) {
        publisherRepository.deleteById(id);
    }
}
