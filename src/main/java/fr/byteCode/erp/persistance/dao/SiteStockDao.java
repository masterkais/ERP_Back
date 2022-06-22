package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.SiteStock;

public interface SiteStockDao extends BaseRepository<SiteStock, Long> {
    SiteStock findSiteStockByName(String name);
}
