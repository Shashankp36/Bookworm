package com.example.service;

import com.example.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface IProduct {
    Product saveProduct(Product product);
    Optional<Product> getProductById(int id);
    List<Product> getAllProducts();
    void deleteProduct(int id);
    
    List<Product> searchByTitle(String keyword);
    List<Product> filterByPriceRange(BigDecimal min, BigDecimal max);
    List<Product> findByAuthorId(int authorId);
    List<Product> findByGenreId(int genreId);
    List<Product> findByPublisherId(int publisherId);
    List<Product> sortByPriceAsc();
    List<Product> sortByPriceDesc();
    List<Product> findAvailableProducts();
    List<Product> findByLanguageName(String languageName);
    List<Product> findByFormatName(String formatName);
    List<Product> findByGenreName(String genreName);
    List<Product> findByAuthorName(String authorName);
    List<Product> findByPublisherName(String publisherName);
    
    public Map<String, List<Product>> groupByPublisher();
    public Map<String, List<Product>> groupByFormat();
    public Map<String, List<Product>> groupByLanguage();
    public Map<String, List<Product>> groupByGenre();
    public Map<String, List<Product>> groupByAuthor();
    
    public List<Product> searchProducts(String title, String author, String language, String genre, String format, String publisher);
    
    public List<Product> getAllProductsSorted(String sortBy, String sortDir);

}
