package com.Letech.ecommerceordermanagement.Service;

import com.Letech.ecommerceordermanagement.Entity.Product;
import com.Letech.ecommerceordermanagement.Exceptions.ProductNotFoundException;
import com.Letech.ecommerceordermanagement.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }


    public ResponseEntity<Product> getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Product> createProduct(Product product) {

        validateProduct(product);
        Product savedProduct = productRepository.save(product);
        kafkaTemplate.send("product-created-topic", savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    public ResponseEntity<Product> updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        validateUpdatedProduct(existingProduct, updatedProduct);

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
        existingProduct.setProductPrice(updatedProduct.getProductPrice());

        Product savedProduct = productRepository.save(existingProduct);
        kafkaTemplate.send("product-updated-topic", savedProduct);
        return ResponseEntity.ok(savedProduct);
    }


    public ResponseEntity<Void> deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(existingProduct);
        kafkaTemplate.send("product-deleted-topic", existingProduct);
        return ResponseEntity.noContent().build();
    }

    public boolean productExists(Long productId) {
        return productRepository.existsById(productId);
    }

    private void validateProduct(Product product) {
        if (!isValidProduct(product)) {
            throw new IllegalArgumentException("Invalid product attributes");
        }
    }

    private void validateUpdatedProduct(Product existingProduct, Product updatedProduct) {
        if (updatedProduct == null ||
                !isValidProductName(updatedProduct.getProductName()) ||
                updatedProduct.getProductQuantity() == null ||
                updatedProduct.getProductPrice() == null ||
                updatedProduct.getProductName().equals(existingProduct.getProductName())) {
            throw new IllegalArgumentException("Invalid updated product attributes");
        }
    }

    private boolean isValidProduct(Product product) {
        return isValidProductName(product.getProductName()) &&
                isValidProductQuantity(product.getProductQuantity()) &&
                isValidProductPrice(product.getProductPrice());
    }

    private boolean isValidProductName(String productName) {
        return productName != null && !productName.trim().isEmpty();
    }

    private boolean isValidProductQuantity(Long productQuantity) {
        return productQuantity != null && productQuantity >= 0;
    }

    private boolean isValidProductPrice(Long productPrice) {
        return productPrice != null && productPrice >= 0;
    }
}



