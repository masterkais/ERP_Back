package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.Brand;

public interface BrandDao extends BaseRepository<Brand,Long>{
    Brand findByName(String name);
}
