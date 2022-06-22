package fr.byteCode.erp.persistance.dao;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface RequestTransfertDao extends BaseRepository<RequestTransfert,Long>{
  @Query(value ="select * from t_request_transfert where u_id= :u_id and rt_deleted_token= FALSE" ,nativeQuery = true)
        List<RequestTransfert> getListRequestTransfert(@Param("u_id") Long u_id);
   List<RequestTransfert> getAllByDateCreated(LocalDateTime localDateTime);
}
