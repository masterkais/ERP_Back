package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.SiteStockDto;
import fr.byteCode.erp.persistance.entities.SiteStock;

import java.util.List;

public final class SiteStockConverter {
    public static SiteStock dtoToModel(SiteStockDto siteStockDto) {
        return new SiteStock(siteStockDto.getId(), siteStockDto.getName(), siteStockDto.getDescription(), siteStockDto.getAdress(),siteStockDto.isState());
    }
    public static SiteStockDto modelToDto(SiteStock siteStock){
        return  new SiteStockDto(siteStock.getId(), siteStock.getName(), siteStock.getDescription(), siteStock.getAdress(), siteStock.isState());
    }
}
