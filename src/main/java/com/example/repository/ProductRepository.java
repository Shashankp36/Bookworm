package com.example.repository;
import com.example.model.*;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	 List<Product> findByTitleContainingIgnoreCase(String keyword);

	    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

	    List<Product> findByAuthorAuthorId(int authorId);

	    List<Product> findByGenreGenreId(int genreId);

	    List<Product> findByPublisherPublisherId(int publisherId);

	    List<Product> findAllByOrderByPriceAsc();

	    List<Product> findAllByOrderByPriceDesc();

	    List<Product> findByAvailableTrue();
	    
	    List<Product> findByLanguage_LanguageNameIgnoreCase(String languageName);
	    
	    List<Product> findByFormat_FormatNameIgnoreCase(String formatName);
	    
	    List<Product> findByGenre_GenreNameIgnoreCase(String genreName);
	    
	    List<Product> findByAuthor_AuthorNameIgnoreCase(String authorName);
	    
	    List<Product> findByPublisher_PublisherNameIgnoreCase(String publisherName);

	
}
