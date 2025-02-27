package app.obs.controller;

import app.obs.model.Inventory;
import app.obs.service.InventorySvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
public class InventoryCon {
    @Autowired
    private InventorySvc inventorySvc;

    @GetMapping
    public Page<Inventory> getAllInventories(Pageable pageable) {
        return inventorySvc.getAllInventory(pageable);
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Long id) {
        return inventorySvc.getInventoryById(id);
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventorySvc.createInventory(inventory);
    }

    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        return inventorySvc.updateInventory(id, inventoryDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventorySvc.deleteInventory(id);
    }
}
