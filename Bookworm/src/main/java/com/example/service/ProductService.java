package com.example.service;

import com.example.model.Product;
import com.example.model.Format;
import com.example.model.Language;
import com.example.model.Genre;
import com.example.model.Author;
import com.example.model.Publisher;
import com.example.repository.ProductRepository;
import com.example.repository.FormatRepository;
import com.example.repository.LanguageRepository;
import com.example.repository.GenreRepository;
import com.example.repository.AuthorRepository;
import com.example.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.*;

@Service
public class ProductService implements IProduct {

    private final ProductRepository productRepository;
    private final FormatRepository formatRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                         FormatRepository formatRepository,
                         LanguageRepository languageRepository,
                         GenreRepository genreRepository,
                         AuthorRepository authorRepository,
                         PublisherRepository publisherRepository) {
        this.productRepository = productRepository;
        this.formatRepository = formatRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public Product saveProduct(Product product) {
        // Fetch and set managed entities for all references
    	System.out.println("Saving product: " + product);
        if (product.getFormat() != null && product.getFormat().getFormatId() != 0) {
            Format format = formatRepository.findById(product.getFormat().getFormatId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid format ID"));
            product.setFormat(format);
        }
        if (product.getLanguage() != null && product.getLanguage().getLanguageId() != 0) {
            Language language = languageRepository.findById(product.getLanguage().getLanguageId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid language ID"));
            product.setLanguage(language);
        }
        if (product.getGenre() != null && product.getGenre().getGenreId() != 0) {
            Genre genre = genreRepository.findById(product.getGenre().getGenreId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID"));
            product.setGenre(genre);
        }
        if (product.getAuthor() != null && product.getAuthor().getAuthorId() != 0) {
            Author author = authorRepository.findById(product.getAuthor().getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid author ID"));
            product.setAuthor(author);
        }
        if (product.getPublisher() != null && product.getPublisher().getPublisherId() != 0) {
            Publisher publisher = publisherRepository.findById(product.getPublisher().getPublisherId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid publisher ID"));
            product.setPublisher(publisher);
        }
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


    public List<Product> searchByTitleOrAuthor(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.searchByTitleOrAuthor(keyword.trim());
    }

    
    @Override
    public List<Product> getAllProductsSorted(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return productRepository.findAll(sort);
    }
    
    
  

    @Override
    public List<Product> filterByLanguageAndGenre(String languageName, String genreName) {
        Language language = null;
        Genre genre = null;

        if (languageName != null && !languageName.isBlank()) {
            language = languageRepository.findByLanguageNameIgnoreCase(languageName)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid language: " + languageName));
        }

        if (genreName != null && !genreName.isBlank()) {
            genre = genreRepository.findByGenreNameIgnoreCase(genreName)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre: " + genreName));
        }

        // Handle all filter combinations
        if (language != null && genre != null) {
            return productRepository.findByLanguageAndGenre(language, genre);
        } else if (language != null) {
            return productRepository.findByLanguage(language);
        } else if (genre != null) {
            return productRepository.findByGenre(genre);
        } else {
            return productRepository.findAll();
        }
    }
}


    
    
