package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.RequestTransfert;
import fr.byteCode.erp.persistance.entities.TransfertLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TransfertLineDao extends BaseRepository<TransfertLine, Long> {
    @Query(value="select * from t_transfert_line where rt_id= :rt_id and state= :state",nativeQuery=true)
    List<TransfertLine> getAllTransfertLineByIdRequestAndState(@Param("rt_id") Long rt_id,@Param("state") int state);
    @Query(value="select * from t_transfert_line where rt_id= :rt_id and tl_is_deleted=FALSE",nativeQuery=true)
    List<TransfertLine> getAllTransfertLineByIdRequestId(@Param("rt_id") Long rt_id);

}
