package com.example.controller;

import com.example.model.Product;
import com.example.service.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	
	private final IProduct productService;
	
	@Autowired
	public ProductController(IProduct productService) {
	    this.productService = productService;
	}
	
	// 1. Get all products
	@GetMapping
	public List<Product> getAllProducts() {
	    return productService.getAllProducts();
	}
	
	// 2. Get product by ID
	@GetMapping("/{id}")
	public Optional<Product> getProductById(@PathVariable int id) {
	    return productService.getProductById(id);
	}
	
	// 3. Create new product
	@PostMapping
	public Product saveProduct(@RequestBody Product product) {
	    return productService.saveProduct(product);
	}
	
	// 4. Delete product
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
	    productService.deleteProduct(id);
	}
	
	// 5. Search by title
	@GetMapping("/search")
	public List<Product> searchByTitle(@RequestParam("title") String title) {
	    return productService.searchByTitle(title);
	}
	
	// 6. Filter by price range
	@GetMapping("/filter/price")
	public List<Product> filterByPriceRange(@RequestParam BigDecimal min,
	                                        @RequestParam BigDecimal max) {
	    return productService.filterByPriceRange(min, max);
	}
	
	// 7. Sort by price
	@GetMapping("/sort/price")
	public List<Product> sortByPrice(@RequestParam("order") String order) {
	    return order.equalsIgnoreCase("desc")
	            ? productService.sortByPriceDesc()
	            : productService.sortByPriceAsc();
	}
	
	// 8. Search by author, genre, publisher ID
	@GetMapping("/author/{authorId}")
	public List<Product> getByAuthorId(@PathVariable int authorId) {
	    return productService.findByAuthorId(authorId);
	}
	
	@GetMapping("/genre/{genreId}")
	public List<Product> getByGenreId(@PathVariable int genreId) {
	    return productService.findByGenreId(genreId);
	}
	
	@GetMapping("/publisher/{publisherId}")
	public List<Product> getByPublisherId(@PathVariable int publisherId) {
	    return productService.findByPublisherId(publisherId);
	}
	
	// 9. Available products
	@GetMapping("/available")
	public List<Product> findAvailable() {
	    return productService.findAvailableProducts();
	}
	
	// 10. Search by names
	@GetMapping("/language")
	public List<Product> byLanguage(@RequestParam String name) {
	    return productService.findByLanguageName(name);
	}
	
	@GetMapping("/format")
	public List<Product> byFormat(@RequestParam String name) {
	    return productService.findByFormatName(name);
	}
	
	@GetMapping("/genre")
	public List<Product> byGenre(@RequestParam String name) {
	    return productService.findByGenreName(name);
	}
	
	@GetMapping("/author")
	public List<Product> byAuthor(@RequestParam String name) {
	    return productService.findByAuthorName(name);
	}
	
	@GetMapping("/publisher")
	public List<Product> byPublisher(@RequestParam String name) {
	    return productService.findByPublisherName(name);
	}
	
	// 11. Grouping endpoints
	@GetMapping("/group/publisher")
	public Map<String, List<Product>> groupByPublisher() {
	    return productService.groupByPublisher();
	}
	
	@GetMapping("/group/format")
	public Map<String, List<Product>> groupByFormat() {
	    return productService.groupByFormat();
	}
	
	@GetMapping("/group/language")
	public Map<String, List<Product>> groupByLanguage() {
	    return productService.groupByLanguage();
	}
	
	@GetMapping("/group/genre")
	public Map<String, List<Product>> groupByGenre() {
	    return productService.groupByGenre();
	}
	
	@GetMapping("/group/author")
	public Map<String, List<Product>> groupByAuthor() {
	    return productService.groupByAuthor();
	}
	
	// 12. Search with multiple fields
	@GetMapping("/search/full")
	public List<Product> searchMultiple(@RequestParam(required = false) String title,
	                                    @RequestParam(required = false) String author,
	                                    @RequestParam(required = false) String language,
	                                    @RequestParam(required = false) String genre,
	                                    @RequestParam(required = false) String format,
	                                    @RequestParam(required = false) String publisher) {
	    return productService.searchProducts(title, author, language, genre, format, publisher);
	}
	
	// 13. Generic sort by column
	@GetMapping("/sort")
	public List<Product> sort(@RequestParam String sortBy,
	                          @RequestParam(defaultValue = "asc") String direction) {
	    return productService.getAllProductsSorted(sortBy, direction);
	}
}



//package com.example.controller;
//
//import com.example.model.Product;
//import com.example.service.IProduct;
//import com.example.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/products")
//public class ProductController {
//
//    private final IProduct productService;
//
//    @Autowired
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    // Get all products
//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }
//
//    // Get product by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable int id) {
//        Optional<Product> product = productService.getProductById(id);
//        return product.map(ResponseEntity::ok)
//                      .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Create product
//    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.saveProduct(product);
//    }
//
//    // Update product
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
//        Optional<Product> existing = productService.getProductById(id);
//        if (existing.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        product.setProductId(id);
//        return ResponseEntity.ok(productService.saveProduct(product));
//    }
//
//    // Delete product
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
//        Optional<Product> existing = productService.getProductById(id);
//        if (existing.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/by-genre")
//    public ResponseEntity<List<Product>> getByGenre(@RequestParam String name) {
//        return ResponseEntity.ok(productService.findByGenreName(name));
//    }
//
//    // Get products by language
//    @GetMapping("/by-language")
//    public ResponseEntity<List<Product>> getByLanguage(@RequestParam String name) {
//        return ResponseEntity.ok(productService.findByLanguageName(name));
//    }
//
//    // Get products by author
//    @GetMapping("/by-author")
//    public ResponseEntity<List<Product>> getByAuthor(@RequestParam String name) {
//        return ResponseEntity.ok(productService.findByAuthorName(name));
//    }
//
//    // Get products by format
//    @GetMapping("/by-format")
//    public ResponseEntity<List<Product>> getByFormat(@RequestParam String name) {
//        return ResponseEntity.ok(productService.findByFormatName(name));
//    }
//
//    // Get products by publisher
//    @GetMapping("/by-publisher")
//    public ResponseEntity<List<Product>> getByPublisher(@RequestParam String name) {
//        return ResponseEntity.ok(productService.findByPublisherName(name));
//    }
//    
//    @GetMapping("/search")
//    public ResponseEntity<List<Product>> searchProducts(
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String author,
//            @RequestParam(required = false) String language,
//            @RequestParam(required = false) String genre,
//            @RequestParam(required = false) String format,
//            @RequestParam(required = false) String publisher
//    ) {
//        List<Product> products = productService.searchProducts(title, author, language, genre, format, publisher);
//
//        if (products.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(products);
//    }
//
//    
//    
//    @GetMapping("/grouped-by-genre")
//    public ResponseEntity<Map<String, List<Product>>> getGroupedByGenre() {
//        return ResponseEntity.ok(productService.groupByGenre());
//    }
//
//    @GetMapping("/grouped-by-language")
//    public ResponseEntity<Map<String, List<Product>>> getGroupedByLanguage() {
//        return ResponseEntity.ok(productService.groupByLanguage());
//    }
//
//    @GetMapping("/grouped-by-author")
//    public ResponseEntity<Map<String, List<Product>>> getGroupedByAuthor() {
//        return ResponseEntity.ok(productService.groupByAuthor());
//    }
//
//    @GetMapping("/grouped-by-format")
//    public ResponseEntity<Map<String, List<Product>>> getGroupedByFormat() {
//        return ResponseEntity.ok(productService.groupByFormat());
//    }
//
//    @GetMapping("/grouped-by-publisher")
//    public ResponseEntity<Map<String, List<Product>>> getGroupedByPublisher() {
//        return ResponseEntity.ok(productService.groupByPublisher());
//    }
//    
//    
//    @GetMapping("/sort")
//    public ResponseEntity<List<Product>> sortProducts(
//            @RequestParam(defaultValue = "title") String sortBy,
//            @RequestParam(defaultValue = "asc") String sortDir
//    ) {
//        List<Product> products = productService.getAllProductsSorted(sortBy, sortDir);
//        if (products.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(products);
//    }
//
//    
//}
