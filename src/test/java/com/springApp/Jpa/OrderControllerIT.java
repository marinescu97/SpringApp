package com.springApp.Jpa;

import com.springApp.Jpa.entity.Order;
import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.repository.OrderRepository;
import com.springApp.Jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class OrderControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setFirstName("Marcel");
        user.setUsername("marcel1");
        user.setEmail("marcel1@gmail.com");
        testUser = userRepository.save(user);
    }

    @Test
    void placeOrder_ShouldReturnCreatedOrder() throws Exception {
        String product = "Test product";
        Double price = 100.0;

        mockMvc.perform(post("/orders")
                .param("userId", String.valueOf(testUser.getId()))
                .param("product", product)
                .param("price", String.valueOf(price))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value(product))
                .andExpect(jsonPath("$.price").value(price));

        Order order = orderRepository.findByUserId(testUser.getId());

        assertEquals(product, order.getProduct());
        assertEquals(price, order.getPrice());
        assertEquals(testUser.getId(), order.getUser().getId());
    }
}
