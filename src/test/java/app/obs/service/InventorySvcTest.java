package app.obs.service;

import app.obs.model.Inventory;
import app.obs.model.Inventory;
import app.obs.repository.InventoryRepo;
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
public class InventorySvcTest {
    @Mock
    private InventoryRepo inventoryRepo;
    
    @InjectMocks
    private InventorySvc inventorySvc;
    
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getAllInventory(){
        Page<Inventory> page = new PageImpl(Collections.singletonList(new Inventory()));
        when(inventoryRepo.findAll(any(Pageable.class))).thenReturn(page);

        Page<Inventory> result = inventorySvc.getAllInventory(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getInventoryById() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        when(inventoryRepo.findById(1L)).thenReturn(Optional.of(inventory));

        Inventory result = inventorySvc.getInventoryById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void createInventory() {
        Inventory inventory = new Inventory();
        when(inventoryRepo.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventorySvc.createInventory(new Inventory());
        assertNotNull(result);
    }

    @Test
    void updateInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        when(inventoryRepo.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryRepo.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventorySvc.updateInventory(1L, new Inventory());
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteInventory() {
        doNothing().when(inventoryRepo).deleteById(1L);
        inventorySvc.deleteInventory(1L);
        verify(inventoryRepo, times(1)).deleteById(1L);
    }
}
