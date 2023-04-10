package com.ff.controller;

import com.ff.entity.ProductEntity;
import com.ff.repository.ProductRepository;
import com.ff.service.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/manageProduct")
public class ManageProductController {
    @Autowired
    ProductService productService;

//    private static final ObjectMapper objectMapper = new ObjectMapper();

//    @PostMapping("/addNewProduct")
//    public ResponseEntity<ProductEntity> addNewProduct(@RequestBody String json) throws JsonProcessingException {
//        ProductEntity product = objectMapper.readValue(json, ProductEntity.class);
//        return new ResponseEntity<>(productService.addNewProduct(product), HttpStatus.OK);
//    }

//    @PostMapping("/addNewProduct")
//    public ResponseEntity<ProductEntity> addNewProduct(@RequestParam("name") String name,
//                                                       @RequestParam("description") String description,
//                                                       @RequestParam("price") Double price,
//                                                       @RequestParam("quantity") Long quantity,
//                                                       @RequestParam("image") MultipartFile file
//                                                       ) throws IOException {
////        ProductEntity product = objectMapper.readValue(json, ProductEntity.class);
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////        LocalDateTime createdDate = LocalDateTime.parse(created, formatter);
////        LocalDateTime updatedDate = LocalDateTime.parse(updated, formatter);
//        byte[] image = file.getBytes();
////        ProductEntity product = new ProductEntity(name, description, price, quantity, image, createdDate, updatedDate);
//        ProductEntity product = new ProductEntity(name, description, price, quantity, image);
//        return new ResponseEntity<>(productService.addNewProduct(product), HttpStatus.OK);
//    }

    @Autowired
    ProductRepository productRepository;
    @PostMapping("/addNewProduct")
    public ResponseEntity<ProductEntity> addNewProduct(@RequestParam("name") @NotBlank(message = "Name is required.") String name,
                                                       @RequestParam("description") @NotBlank(message = "Description is required.") String description,
                                                       @RequestParam("price") @Positive(message = "Price must be positive.") Double price,
                                                       @RequestParam("quantity") @PositiveOrZero(message = "Quantity must be positive or zero.") Long quantity,
                                                       @RequestParam("image") MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file must be provided.");
        }

        if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(file.getContentType())) {
            throw new IllegalArgumentException("Only JPG, PNG, and GIF images are supported.");
        }

        byte[] imageBytes = file.getBytes();
        ProductEntity product = new ProductEntity(name, description, price, quantity, imageBytes);
//        productRepository.insertProduct(
//                productRepository.count() + 1,
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                product.getQuantity(),
//                product.getImage());
//
//        return new ResponseEntity<>(product, HttpStatus.OK);
        return new ResponseEntity<>(productService.addNewProduct(product), HttpStatus.OK);
    }


    @DeleteMapping("/removeProduct/{productName}")
    public ResponseEntity<ProductEntity> removeProduct(@PathVariable("productName") String productName) {
        ProductEntity product = productService.removeProduct(productName);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
