package com.Letech.ecommerceordermanagement.Service;


import com.Letech.ecommerceordermanagement.Entity.Product;
import com.Letech.ecommerceordermanagement.Exceptions.CatalogItemNotFoundException;
import com.Letech.ecommerceordermanagement.Repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;


    @KafkaListener(topics = "product-created-topic", groupId = "catalog-group")
    public void listenToProductCreation(Product product) {
        catalogRepository.save(product);
    }

    @KafkaListener(topics = "product-updated-topic", groupId = "catalog-group")
    public void listenToProductUpdate(Product updatedProduct) {

        catalogRepository.save(updatedProduct);
    }

    @KafkaListener(topics = "product-deleted-topic", groupId = "catalog-group")
    public void listenToProductDeletion(Product product) {
        catalogRepository.deleteById(product.getProductId());
    }








}


