package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.*;

@Service
public class ProductService implements IProduct {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
    
    public List<Product> searchByTitle(String keyword) {
        return productRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Product> filterByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max);
    }

    public List<Product> findByAuthorId(int authorId) {
        return productRepository.findByAuthorAuthorId(authorId);
    }

    public List<Product> findByGenreId(int genreId) {
        return productRepository.findByGenreGenreId(genreId);
    }

    public List<Product> findByPublisherId(int publisherId) {
        return productRepository.findByPublisherPublisherId(publisherId);
    }

    public List<Product> sortByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> sortByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    public List<Product> findAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }
    
    @Override
    public List<Product> findByLanguageName(String languageName) {
        return productRepository.findByLanguage_LanguageNameIgnoreCase(languageName);
    }

    @Override
    public List<Product> findByFormatName(String formatName) {
        return productRepository.findByFormat_FormatNameIgnoreCase(formatName);
    }

    @Override
    public List<Product> findByGenreName(String genreName) {
        return productRepository.findByGenre_GenreNameIgnoreCase(genreName);
    }

    @Override
    public List<Product> findByAuthorName(String authorName) {
        return productRepository.findByAuthor_AuthorNameIgnoreCase(authorName);
    }

    @Override
    public List<Product> findByPublisherName(String publisherName) {
        return productRepository.findByPublisher_PublisherNameIgnoreCase(publisherName);
    }
    
    public Map<String, List<Product>> groupByGenre() {
        return productRepository.findAll().stream()
                .filter(p -> p.getGenre() != null)
                .collect(Collectors.groupingBy(p -> p.getGenre().getGenreName()));
    }

    public Map<String, List<Product>> groupByLanguage() {
        return productRepository.findAll().stream()
                .filter(p -> p.getLanguage() != null)
                .collect(Collectors.groupingBy(p -> p.getLanguage().getLanguageName()));
    }

    public Map<String, List<Product>> groupByAuthor() {
        return productRepository.findAll().stream()
                .filter(p -> p.getAuthor() != null)
                .collect(Collectors.groupingBy(p -> p.getAuthor().getAuthorName()));
    }

    public Map<String, List<Product>> groupByFormat() {
        return productRepository.findAll().stream()
                .filter(p -> p.getFormat() != null)
                .collect(Collectors.groupingBy(p -> p.getFormat().getFormatName()));
    }

    public Map<String, List<Product>> groupByPublisher() {
        return productRepository.findAll().stream()
                .filter(p -> p.getPublisher() != null)
                .collect(Collectors.groupingBy(p -> p.getPublisher().getPublisherName()));
    }

    public List<Product> searchProducts(String title, String author, String language, String genre, String format, String publisher) {
        return productRepository.findAll().stream()
            .filter(p -> (title == null || p.getTitle().toLowerCase().contains(title.toLowerCase())))
            .filter(p -> (author == null || (p.getAuthor() != null && p.getAuthor().getAuthorName().equalsIgnoreCase(author))))
            .filter(p -> (language == null || (p.getLanguage() != null && p.getLanguage().getLanguageName().equalsIgnoreCase(language))))
            .filter(p -> (genre == null || (p.getGenre() != null && p.getGenre().getGenreName().equalsIgnoreCase(genre))))
            .filter(p -> (format == null || (p.getFormat() != null && p.getFormat().getFormatName().equalsIgnoreCase(format))))
            .filter(p -> (publisher == null || (p.getPublisher() != null && p.getPublisher().getPublisherName().equalsIgnoreCase(publisher))))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> getAllProductsSorted(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return productRepository.findAll(sort);
    }

    
}
