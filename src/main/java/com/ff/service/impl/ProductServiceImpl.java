package com.ff.service.impl;

import com.ff.entity.CategoryEntity;
import com.ff.entity.ProductEntity;
import com.ff.repository.CategoryRepository;
import com.ff.repository.ProductRepository;
import com.ff.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    //    public ProductEntity addNewProduct(ProductEntity product) {
//        return productRepository.save(product);
//    }
    @Override
    public ProductEntity addNewProduct(ProductEntity product, List<String> cateList) {
        productRepository.save(product);
        if (cateList != null)
            for (String cateName : cateList) {
                if (cateName != null && !cateName.isEmpty() && !cateName.isBlank()) {
                    CategoryEntity category = categoryRepository.findCateByName(cateName);
                    if(category != null) {
                        productRepository.updateCate(product.getId(), category.getId());
                    }
                }
            }
        return product;
    }

    @Override
    public ProductEntity removeProduct(String productName) {
        ProductEntity product = productRepository.findByName(productName);
        if (product == null)
            return null;
        else {
            productRepository.delete(product);
            return product;
        }
    }

    @Override
    public List<ProductEntity> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<ProductEntity> findByCategory(String categoryName) {
        return productRepository.findByCategory(categoryName);
    }
}
