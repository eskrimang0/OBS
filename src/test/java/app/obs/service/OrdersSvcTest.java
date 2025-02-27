package app.obs.service;

import app.obs.model.Orders;
import app.obs.repository.InventoryRepo;
import app.obs.repository.OrdersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OrdersSvcTest {
    @Mock
    private OrdersRepo ordersRepo;

    @InjectMocks
    private OrdersSvc ordersSvc;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders(){
        Page<Orders> page = new PageImpl(Collections.singletonList(new Orders()));
        when(ordersRepo.findAll(any(Pageable.class))).thenReturn(page);

        Page<Orders> result = ordersSvc.getAllOrders(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getOrdersById() {
        Orders orders = new Orders();
        orders.setOrderNo(1L);
        when(ordersRepo.findById(1L)).thenReturn(Optional.of(orders));

        Orders result = ordersSvc.getOrdersById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getOrderNo());
    }

    @Test
    void createOrders() {
        Orders orders = new Orders();
        when(ordersRepo.save(any(Orders.class))).thenReturn(orders);

        Orders result = ordersSvc.createOrders(new Orders());
        assertNotNull(result);
    }

    @Test
    void updateOrders() {
        Orders orders = new Orders();
        orders.setOrderNo(1L);
        when(ordersRepo.findById(1L)).thenReturn(Optional.of(orders));
        when(ordersRepo.save(any(Orders.class))).thenReturn(orders);

        Orders result = ordersSvc.updateOrders(1L, new Orders());
        assertNotNull(result);
        assertEquals(1L, result.getOrderNo());
    }

    @Test
    void deleteOrders() {
        doNothing().when(ordersRepo).deleteById(1L);
        ordersSvc.deleteOrders(1L);
        verify(ordersRepo, times(1)).deleteById(1L);
    }
}
