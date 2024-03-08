package com.ecommerce.service.admin;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    //Category operations

    Category createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();


    //Product operations

    Product addProduct(SecondProductDto secondProductDto) throws IOException;

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

    void deleteProduct(Long productId);

    List<ProductDto> searchProductByTitle(String title);

    //Get All Placed Orders

    List<OrderDto> getAllPlacedOrders();

}
