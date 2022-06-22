package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends  BaseRepository<Category,Long>{
    Category findByName(String name);
}
