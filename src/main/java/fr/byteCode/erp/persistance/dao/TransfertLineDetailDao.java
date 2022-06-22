package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.TransfertLineDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransfertLineDetailDao extends BaseRepository<TransfertLineDetail, Long> {
    @Query(value = "select * from t_transfert_line_detail where tl_id= :tl_id", nativeQuery = true)
    List<TransfertLineDetail> getTransfertLineDetailByIDTranfertLine(@Param("tl_id") Long tl_id);
    @Query(value = "select * from t_transfert_line_detail where tl_id= :tl_id", nativeQuery = true)
    List<TransfertLineDetail> getAllTransfertLineDetailByIDTranfertLine(@Param("tl_id") Long tl_id);

}
