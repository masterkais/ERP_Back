package fr.byteCode.erp.persistance.dao;

import fr.byteCode.erp.persistance.entities.Image;
import org.springframework.data.jpa.repository.Query;

public interface ImageDao extends BaseRepository<Image,Long> {
    @Query("select  i from Image i where i.data = :data")
    Image findImageByPath(String data);
}
