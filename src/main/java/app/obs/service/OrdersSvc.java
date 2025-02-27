package app.obs.service;

import app.obs.model.Item;
import app.obs.model.Orders;
import app.obs.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersSvc {
    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private ItemSvc itemSvc;

    public Page<Orders> getAllOrders(Pageable pageable) {
        return ordersRepo.findAll(pageable);
    }

    public Orders getOrdersById(Long orderNo) {
        return ordersRepo.findById(orderNo).orElseThrow(() -> new RuntimeException("Orders not found"));
    }

    @Transactional
    public Orders createOrders(Orders orders) {
        Item item = itemSvc.getItemById(orders.getItem().getId());
        int remainingStock = itemSvc.getRemainingStock(item.getId());
        if (remainingStock < orders.getQty()) {
            throw new InsufficientStockException("Insufficient stock for item: " + item.getName());
        }
        return ordersRepo.save(orders);
    }


    public Orders updateOrders(Long orderNo, Orders ordersDetail) {
        Orders orders = getOrdersById(orderNo);
        orders.setItem(ordersDetail.getItem());
        orders.setQty(ordersDetail.getQty());
        orders.setPrice(ordersDetail.getPrice());
        return ordersRepo.save(orders);
    }

    public void deleteOrders(Long orderNo) {
        ordersRepo.deleteById(orderNo);
    }

    public class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String message) {
            super(message);
        }
    }
}
