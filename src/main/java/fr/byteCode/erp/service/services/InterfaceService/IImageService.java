package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.ImageDto;
import fr.byteCode.erp.persistance.entities.Image;

import java.util.List;

public interface IImageService extends IGenericService<Image,Long>{


    ImageDto save(ImageDto imageDto) throws Exception;

    ImageDto update(ImageDto imageDto);

    ImageDto findById(Long id);

    List<ImageDto> findAllImagesDto();

    void delete(Long id);
}
