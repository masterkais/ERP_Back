package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.Image;
import fr.byteCode.erp.persistance.entities.Vehicule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeDao extends BaseRepository<Vehicule,Long>{
    @Query("select  v from Vehicule v where v.state = :state")
    List<Vehicule> getListVehiculeByState(boolean state);
    @Query(value = "select * from t_vehicule where delivery_man_u_id = :id",nativeQuery = true)
    Vehicule getVehiculeByDeliveryId(@Param("id") Long id);


}
