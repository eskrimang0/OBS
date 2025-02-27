package app.obs.controller;

import app.obs.model.Item;
import app.obs.service.ItemSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemCon {
    @Autowired
    private ItemSvc itemSvc;

    @GetMapping
    public Page<Item> getAllItems(Pageable pageable) {
        return itemSvc.getAllItems(pageable);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemSvc.getItemById(id);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemSvc.createItem(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemSvc.updateItem(id, itemDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemSvc.deleteItem(id);
    }
}
