package com.product.product_service.controller;

import com.product.product_service.exception.ApiResponse;
import com.product.product_service.exception.ProductNotFoundException;
import com.product.product_service.dto.ProductDTO;
import com.product.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController

public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @GetMapping
    protected ResponseEntity<List<ProductDTO>> getAllProducts() {
            List<ProductDTO> products = productService.getAllProducts();
            return successResponse(products,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    protected ResponseEntity<ProductDTO>getProductById(@PathVariable Long id) {
            ProductDTO product = productService.getProductById(id);
            return successResponse(product,HttpStatus.OK);

    }

    @PostMapping
    protected ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {

            ProductDTO createdProduct = productService.addProduct(productDTO);
            return createdResponse(createdProduct);

    }

    @PutMapping("/{id}")
    protected ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {

            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return successResponse(updatedProduct,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    protected ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
            return noContentResponse();

    }
}
