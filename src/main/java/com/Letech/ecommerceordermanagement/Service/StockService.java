package com.Letech.ecommerceordermanagement.Service;

import com.Letech.ecommerceordermanagement.Entity.Product;
import com.Letech.ecommerceordermanagement.Exceptions.StockItemNotFoundException;
import com.Letech.ecommerceordermanagement.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Product> getAllStockItems() {
        return stockRepository.findAll();
    }

    public Optional<Product> getStockItemById(Long itemId) {
        return stockRepository.findById(itemId);
    }

    @KafkaListener(topics = "product-created-topic", groupId = "stock-group")
    public void listenToProductCreation(Product product) {
        stockRepository.save(product);
    }

    @KafkaListener(topics = "product-updated-topic", groupId = "stock-group")
    public void listenToProductUpdate(Product updatedProduct) {
        Product existingStockProduct = stockRepository.findById(updatedProduct.getProductId())
                .orElseThrow(() -> new StockItemNotFoundException("Stock item not found"));

        existingStockProduct.setProductName(updatedProduct.getProductName());
        existingStockProduct.setProductQuantity(updatedProduct.getProductQuantity());
        existingStockProduct.setProductPrice(updatedProduct.getProductPrice());

        stockRepository.save(existingStockProduct);
    }


    @KafkaListener(topics = "product-deleted-topic", groupId = "stock-group")
    public void listenToProductDeletion(Product product) {
        stockRepository.deleteById(product.getProductId());
    }



}

