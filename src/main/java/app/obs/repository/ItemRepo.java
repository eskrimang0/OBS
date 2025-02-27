package app.obs.repository;

import app.obs.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    @Query("SELECT COALESCE(SUM(CASE WHEN i.type = 'T' THEN i.qty ELSE -i.qty END), 0) - " +
            "COALESCE(SUM(o.qty), 0) FROM Item item " +
            "LEFT JOIN item.inventories i " +
            "LEFT JOIN item.orders o " +
            "WHERE item.id = :itemId")
    Integer getRemainingStock(@Param("itemId") Long itemId);
}
