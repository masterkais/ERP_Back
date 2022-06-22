package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.SiteStockDto;
import fr.byteCode.erp.persistance.entities.SiteStock;

import java.util.List;

public interface ISiteStockService extends IGenericService<SiteStock,Long> {
    SiteStockDto save(SiteStockDto siteStockDto);

    SiteStockDto update(SiteStockDto siteStockDto);

    SiteStockDto findById(Long id);

    List<SiteStockDto> findAllSiteStocks();

    void delete(Long id);

    SiteStockDto findByName(String name);
}
