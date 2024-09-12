package com.product.product_service.service;

import com.product.product_service.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO addProduct(ProductDTO productDTO, MultipartFile imageFile);
    ProductDTO updateProduct(Long id, ProductDTO productDTO,  MultipartFile imageFile);
    void deleteProduct(Long id);
}