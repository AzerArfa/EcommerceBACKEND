package com.ecommerce.service.admin;

import com.ecommerce.dto.*;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.UserRole;
import com.ecommerce.repo.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepo.findByRole(UserRole.ADMIN);
        if (null == adminAccount) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepo.save(user);
        }
    }

    //Category operations

    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepo.save(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(category -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());
            return categoryDto;
        }).collect(Collectors.toList());
    }

    //Product operations

    @Override
    public Product addProduct(SecondProductDto secondProductDto) throws IOException {
        Product product = new Product();
        product.setName(secondProductDto.getName());
        
        product.setAvailableQuantity(secondProductDto.getAvailableQuantity());
        product.setPrice(secondProductDto.getPrice());
        product.setDescription(secondProductDto.getDescription());
        product.setImg(secondProductDto.getImg().getBytes());
        Category category = categoryRepo.findById(Long.parseLong(secondProductDto.getCategoryId())).orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        return productRepo.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setAvailableQuantity(product.getAvailableQuantity());
            productDto.setPrice(product.getPrice());
           
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setCategoryName(product.getCategory().getName());
            productDto.setReturnedImg(product.getImg());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setAvailableQuantity(product.getAvailableQuantity());
            productDto.setPrice(product.getPrice());
            
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setCategoryName(product.getCategory().getName());
            productDto.setReturnedImg(product.getImg());
            return productDto;
        } else {
            return null;
        }
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
           
            product.setAvailableQuantity(productDto.getAvailableQuantity());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            Category category = new Category();
            category.setId(productDto.getCategoryId());
            product.setCategory(category);
            Product updatedProduct = productRepo.save(product);
            ProductDto updatedProductDto = new ProductDto();
            updatedProductDto.setId(updatedProduct.getId());
            updatedProductDto.setName(updatedProduct.getName());
            updatedProductDto.setDescription(updatedProduct.getDescription());
            updatedProductDto.setAvailableQuantity(updatedProduct.getAvailableQuantity());
            updatedProductDto.setPrice(updatedProduct.getPrice());
           
            updatedProductDto.setCategoryId(updatedProduct.getCategory().getId());
            updatedProductDto.setCategoryName(updatedProduct.getCategory().getName());
            updatedProductDto.setReturnedImg(updatedProduct.getImg());
            return updatedProductDto;
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with id " + productId + " not found");
        }
        productRepo.deleteById(productId);
    }

    @Override
    public List<ProductDto> searchProductByTitle(String title) {
        List<Product> products = productRepo.findAllByNameContaining(title);
        return products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setAvailableQuantity(product.getAvailableQuantity());
            productDto.setPrice(product.getPrice());
           
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setCategoryName(product.getCategory().getName());
            productDto.setReturnedImg(product.getImg());
            return productDto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> getAllPlacedOrders() {
        List<Order> orderList = orderRepo.findAllByStatus(OrderStatus.Submitted);
        return orderList.stream().map(order -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setOrderDescription(order.getOrderDescription());
            orderDto.setDate(order.getDate());
            orderDto.setAmount(order.getAmount());
            orderDto.setPayment(order.getPayment());
            orderDto.setAddress(order.getAddress());
            orderDto.setStatus(order.getStatus());
            orderDto.setUserName(order.getUser().getName());
            return orderDto;
        }).collect(Collectors.toList());
    }


}
