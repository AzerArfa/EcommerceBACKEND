package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.service.admin.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //User Operations

    // Category operations

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtos = orderService.getAllCategories();
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<CategoryDto>> searchCategoryByTitle(@PathVariable("title") String title) {
        List<CategoryDto> categoryDtos = orderService.searchCategoryByTitle(title);
        return ResponseEntity.ok(categoryDtos);
    }


    // Product operations

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = orderService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDto> productDtos = orderService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productDtos);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartItemsDto cartItemsDto) {
        return orderService.addProductToCart(cartItemsDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = orderService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> addMinusOnProduct(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        OrderDto orderDto = orderService.addMinusOnProduct(quantityChangeProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto> addPlusOnProduct(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        OrderDto OrderDto = orderService.addPlusOnProduct(quantityChangeProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        OrderDto OrderDto = orderService.PlaceOrder(placeOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto);
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId) {
        List<OrderDto> orderDtos = orderService.getMyPlacedOrders(userId);
        return ResponseEntity.ok(orderDtos);
    }

}
