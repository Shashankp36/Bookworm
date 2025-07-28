package com.example.controller;

import com.example.model.Product;
import com.example.service.IProduct;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProduct productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // Create product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
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

    @GetMapping("/by-genre")
    public ResponseEntity<List<Product>> getByGenre(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByGenreName(name));
    }

    // Get products by language
    @GetMapping("/by-language")
    public ResponseEntity<List<Product>> getByLanguage(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByLanguageName(name));
    }

    // Get products by author
    @GetMapping("/by-author")
    public ResponseEntity<List<Product>> getByAuthor(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByAuthorName(name));
    }

    // Get products by format
    @GetMapping("/by-format")
    public ResponseEntity<List<Product>> getByFormat(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByFormatName(name));
    }

    // Get products by publisher
    @GetMapping("/by-publisher")
    public ResponseEntity<List<Product>> getByPublisher(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByPublisherName(name));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) String publisher
    ) {
        List<Product> products = productService.searchProducts(title, author, language, genre, format, publisher);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
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

    
}
