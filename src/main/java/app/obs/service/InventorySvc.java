package app.obs.service;

import app.obs.model.Inventory;
import app.obs.repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventorySvc {
    @Autowired
    private InventoryRepo inventoryRepo;

    public Page<Inventory> getAllInventory(Pageable pageable) {
        return inventoryRepo.findAll(pageable);
    }

    public Inventory getInventoryById(Long id){
        return inventoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    public Inventory createInventory(Inventory inventory){
        return inventoryRepo.save(inventory);
    }

    public Inventory updateInventory(Long id, Inventory inventoryDetails){
        Inventory inventory = getInventoryById(id);
        inventory.setItem(inventoryDetails.getItem());
        inventory.setQty(inventoryDetails.getQty());
        inventory.setType(inventoryDetails.getType());
        return inventoryRepo.save(inventory);
    }

    public void deleteInventory(Long id){
        inventoryRepo.deleteById(id);
    }
}
