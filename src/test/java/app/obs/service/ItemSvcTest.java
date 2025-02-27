package app.obs.service;

import app.obs.model.Item;
import app.obs.repository.ItemRepo;
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

@ExtendWith(MockitoExtension.class)
public class ItemSvcTest {
    @Mock
    private ItemRepo itemRepo;

    @InjectMocks
    private ItemSvc itemSvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllItems() {
        Page<Item> page = new PageImpl(Collections.singletonList(new Item()));
        when(itemRepo.findAll(any(Pageable.class))).thenReturn(page);

        Page<Item> result = itemSvc.getAllItems(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getItemById() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemSvc.getItemById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void createItem() {
        Item item = new Item();
        when(itemRepo.save(any(Item.class))).thenReturn(item);

        Item result = itemSvc.createItem(new Item());
        assertNotNull(result);
    }

    @Test
    void updateItem() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepo.save(any(Item.class))).thenReturn(item);

        Item result = itemSvc.updateItem(1L, new Item());
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteItem() {
        doNothing().when(itemRepo).deleteById(1L);
        itemSvc.deleteItem(1L);
        verify(itemRepo, times(1)).deleteById(1L);
    }
}
