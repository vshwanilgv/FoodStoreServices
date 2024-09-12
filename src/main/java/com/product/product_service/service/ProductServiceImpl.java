package com.product.product_service.service;

import org.springframework.stereotype.Service;
import com.product.product_service.dto.ProductDTO;
import com.product.product_service.model.Product;
import com.product.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.product.product_service.exception.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private final String uploadDir = "uploads/";
    @Override
    public List<ProductDTO> getAllProducts() {
        try {
            logger.info("Fetching all products");
            List<Product> products = productRepository.findAll();
            return products.stream()
                    .map(product -> modelMapper.map(product, ProductDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to fetch products", e);
            throw new RuntimeException("Failed to fetch products");
        }
    }

    @Override
    public ProductDTO getProductById(Long id) {
        try {
            logger.info("Fetching product with ID: {}", id);
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
            return modelMapper.map(product, ProductDTO.class);
        } catch (Exception e) {
            logger.error("Failed to fetch product with ID: " + id, e);
            throw new RuntimeException("Failed to fetch product with id: ");
        }
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, MultipartFile imageFile) {
        try {
            logger.info("Adding a new product");
            Product product = modelMapper.map(productDTO, Product.class);
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = saveImageFile(imageFile); // Save the image and get its URL
                product.setImageUrl(imageUrl); // Set image URL in product
            }
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } catch (Exception e) {
            logger.error("Failed to add product", e);
            throw new RuntimeException("Failed to add product");
        }
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile imageFile) {
        try {
            logger.info("Updating product with ID: {}", id);
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
            modelMapper.map(productDTO, existingProduct);
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = saveImageFile(imageFile); // Save the new image and get the URL
                existingProduct.setImageUrl(imageUrl); // Update the product's image URL
            }

            Product updatedProduct = productRepository.save(existingProduct);
            return modelMapper.map(updatedProduct, ProductDTO.class);
        } catch (Exception e) {
            logger.error("Failed to update product with ID: " + id, e);
            throw new RuntimeException("Failed to update product with id: ");
        }
    }
    private String saveImageFile(MultipartFile file) throws Exception {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            return uploadDir + fileName; // Return the relative path to the file
        } catch (Exception e) {
            logger.error("Failed to save image file", e);
            throw new RuntimeException("Failed to save image file");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            logger.info("Deleting product with ID: {}", id);
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException(id);
            }
            productRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Failed to delete product with ID: " + id, e);
            throw new RuntimeException("Failed to delete product with id: " + id, e);
        }
    }
}
