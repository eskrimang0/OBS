package app.obs.service;

import app.obs.model.Item;
import app.obs.model.Orders;
import app.obs.repository.InventoryRepo;
import app.obs.repository.ItemRepo;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

@Service
public class ItemSvc {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private InventoryRepo inventoryRepo;

    public Page<Item> getAllItems(Pageable pageable) {
        return itemRepo.findAll(pageable);
    }

    public Item getItemById(Long id) {
        return itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Item createItem(Item item) {
        return itemRepo.save(item);
    }

    public Item updateItem(Long id, Item itemDetails) {
        Item item = getItemById(id);
        item.setName(itemDetails.getName());
        item.setPrice(itemDetails.getPrice());
        return itemRepo.save(item);
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Integer getRemainingStock(Long itemId) {
        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        int stock = item.getInventories().stream()
                .mapToInt(inv -> inv.getType() == 'T' ? inv.getQty() : -inv.getQty())
                .sum();
        stock -= item.getOrders().stream().mapToInt(Orders::getQty).sum();
        return stock;
    }
}
