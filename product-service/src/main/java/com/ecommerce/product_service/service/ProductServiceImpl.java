package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductDto;
import com.ecommerce.product_service.event.Category;
import com.ecommerce.product_service.event.Product;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final String uploadDir = System.getProperty("user.dir")+"/uploads/products/";
    @Override
    public ProductDto createProduct(ProductDto dto) {
        Category category = null;
        if(dto.getCategoryId()!=null){
            category = new Category();
            category.setId(dto.getCategoryId());
        }
        Product product = productMapper.toEntity(dto, category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product doesnt exist"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setDiscountPrice(dto.getDiscountPrice());
        if(dto.getCategoryId()!=null){
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }
        productRepository.save(product);
        return productMapper.toDto(product);

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product doesnt exist"));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto uploadImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product doesnt exist"));
        if(file.isEmpty()){
            throw new RuntimeException("image file is empty");
        }
        long maxSize = 2*1024*1024;
        if(file.getSize()>maxSize){
            throw new RuntimeException("file size must br less than 2 mb");
        }
        List<String> allowedType = List.of("image/jpg","image/png","image/jpeg");
        if(!allowedType.contains(file.getContentType())){
            throw new RuntimeException("only jpg, png and jpeg are allowed");
        }
        String originalName = file.getOriginalFilename();
        if(originalName==null || !originalName.contains(".")){
            throw new RuntimeException("invalid file name");
        }
        String ex = originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase();
        List<String> extention = List.of("jpg", "png", "jpeg");
        if(!extention.contains(ex)){
            throw new RuntimeException("invalid file type");
        }
        File folder = new File(uploadDir);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String fileName = UUID.randomUUID().toString()+"."+extention;
        Path path = Paths.get(uploadDir + fileName);
        Files.write(path,file.getBytes());
        String url = "/products/images"+fileName;
        product.setImageUrl(url);
        return productMapper.toDto(productRepository.save(product));

    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProduct(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productRepository.searchProducts(keyword, pageable);
        return products.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProducts(Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productRepository.advanceFilter(null,categoryId,minPrice,maxPrice,pageable);
        return products.map(productMapper::toDto);

    }

    @Override
    public Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> products = productRepository.advanceFilter(null,categoryId,minPrice,maxPrice,pageable);
        return products.map(productMapper::toDto);

    }
}
