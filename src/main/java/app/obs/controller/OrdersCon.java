package app.obs.controller;

import app.obs.model.Orders;
import app.obs.service.OrdersSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersCon {
    @Autowired
    private OrdersSvc ordersSvc;

    @GetMapping
    public Page<Orders> getAllOrders(Pageable pageable) {
        return ordersSvc.getAllOrders(pageable);
    }

    @GetMapping("/{id}")
    public Orders getOrdersById(@PathVariable Long id) {
        return ordersSvc.getOrdersById(id);
    }

    @PostMapping
    public Orders createOrders(@RequestBody Orders orders) {
        return ordersSvc.createOrders(orders);
    }

    @PutMapping("/{id}")
    public Orders updateOrders(@PathVariable Long id, @RequestBody Orders ordersDetails) {
        return ordersSvc.updateOrders(id, ordersDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteOrders(@PathVariable Long id) {
        ordersSvc.deleteOrders(id);
    }
}
