package com.example.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.service.IProduct;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    private final IProduct productService;

    @Autowired
    public ProductController(IProduct productService) {
        this.productService = productService;
    }


    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID

    // Create products
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
    	System.out.println("Creating product: " + product);
    	
        return productService.saveProduct(product);
    }
    
    

  

    @GetMapping("/byGenre/all")
    public ResponseEntity<List<Product>> getByGenre(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByGenreName(name));
    }

    // Get products by language
    @GetMapping("/byLanguage/all")
    public ResponseEntity<List<Product>> getByLanguage(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByLanguageName(name));
    }

    // Get products by author
    @GetMapping("/byAuthor/all")
    public ResponseEntity<List<Product>> getByAuthor(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByAuthorName(name));
    }

    // Get products by format
    @GetMapping("/byFormat/all")
    public ResponseEntity<List<Product>> getByFormat(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByFormatName(name));
    }

    // Get products by publisher
    @GetMapping("/byPublisher/all")
    public ResponseEntity<List<Product>> getByPublisher(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByPublisherName(name));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByTitleOrAuthor(@RequestParam String keyword) {
        List<Product> products = productService.searchByTitleOrAuthor(keyword);
        return products.isEmpty()
                ? ResponseEntity.ok(Collections.emptyList()) // return empty list so frontend can show "Not Found"
                : ResponseEntity.ok(products);
    }


    
    
    @GetMapping("/grouped-by-genre")
    public ResponseEntity<Map<String, List<Product>>> getGroupedByGenre() {
        return ResponseEntity.ok(productService.groupByGenre());
    }

    @GetMapping("/grouped-by-language")
    public ResponseEntity<Map<String, List<Product>>> getGroupedByLanguage() {
        return ResponseEntity.ok(productService.groupByLanguage());
    }

    @GetMapping("/grouped-by-author")
    public ResponseEntity<Map<String, List<Product>>> getGroupedByAuthor() {
        return ResponseEntity.ok(productService.groupByAuthor());
    }

    @GetMapping("/grouped-by-format")
    public ResponseEntity<Map<String, List<Product>>> getGroupedByFormat() {
        return ResponseEntity.ok(productService.groupByFormat());
    }

    @GetMapping("/grouped-by-publisher")
    public ResponseEntity<Map<String, List<Product>>> getGroupedByPublisher() {
        return ResponseEntity.ok(productService.groupByPublisher());
    }
    
    
    @GetMapping("/sort")
    public ResponseEntity<List<Product>> sortProducts(
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        List<Product> products = productService.getAllProductsSorted(sortBy, sortDir);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> existing = productService.getProductById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.setProductId(id);
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        Optional<Product> existing = productService.getProductById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterByLanguageAndGenre(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre) {
        List<Product> filteredProducts = productService.filterByLanguageAndGenre(language, genre);
        return ResponseEntity.ok(filteredProducts);
    }

    
}
