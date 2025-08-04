package com.example.service;

import com.example.model.Publisher;
import java.util.List;
import java.util.Optional;

public interface IPublisher {
    Publisher savePublisher(Publisher publisher);
    Optional<Publisher> getPublisherById(int id);
    List<Publisher> getAllPublishers();
    void deletePublisher(int id);
}
