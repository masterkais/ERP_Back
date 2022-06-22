package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.LigneSale;
import fr.byteCode.erp.persistance.entities.TransfertLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LigneSaleDao extends BaseRepository<LigneSale,Long>{
    @Query(value="select * from t_lignesale where so_id= :so_id and state= :state",nativeQuery=true)
    List<LigneSale> getAllLigneSalesByIdOrderSalesAndState(@Param("so_id") Long so_id, @Param("state") int state);

}
