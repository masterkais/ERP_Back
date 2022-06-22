package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.ImageDto;
import fr.byteCode.erp.persistance.entities.Image;

public class ImageConverter {
    public static Image dtoToModel(ImageDto imageDto) {
        return new Image(imageDto.getId(), imageDto.getData());
    }

    public static ImageDto modelToDto(Image image) {
        return new ImageDto(image.getId(), image.getData());
    }

}
