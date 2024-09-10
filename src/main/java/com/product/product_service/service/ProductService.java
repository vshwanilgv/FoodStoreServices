package com.product.product_service.service;

import com.product.product_service.dto.ProductDTO;
import java.util.List;
import java.util.Map;
public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}