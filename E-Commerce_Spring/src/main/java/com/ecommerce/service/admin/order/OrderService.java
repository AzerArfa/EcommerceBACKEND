package com.ecommerce.service.admin.order;

import com.ecommerce.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    List<CategoryDto> getAllCategories();

    List<CategoryDto> searchCategoryByTitle(String title);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(Long categoryId);

    ResponseEntity<?> addProductToCart(CartItemsDto cartItemsDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto addMinusOnProduct(QuantityChangeProductDto quantityChangeProductDto);

    OrderDto addPlusOnProduct(QuantityChangeProductDto quantityChangeProductDto);

    OrderDto PlaceOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);

}
