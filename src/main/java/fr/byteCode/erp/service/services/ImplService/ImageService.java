package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.ImageDao;
import fr.byteCode.erp.persistance.dao.ProductDao;
import fr.byteCode.erp.persistance.dto.ImageDto;
import fr.byteCode.erp.persistance.entities.Image;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.ImageConverter;
import fr.byteCode.erp.service.services.InterfaceService.IImageService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService extends GenericService<Image,Long> implements IImageService {
    private ImageDao imageDao;
    private ProductDao productDao;
    public ImageService(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public ImageDto save(ImageDto imageDto) throws Exception {
        Objects.requireNonNull(imageDto);
        Image image=ImageConverter.dtoToModel(imageDto);
        Image imageSaved= imageDao.saveAndFlush(image);
        return ImageConverter.modelToDto(imageSaved);
    }

    private void checkExistePath(String path) throws Exception {
        if(imageDao.findImageByPath(path)!=null){
            throw new Exception("image path existe deja");
        }
    }

    @Override
    public ImageDto update(ImageDto imageDto) {
        Objects.requireNonNull(imageDto);
        Image image=ImageConverter.dtoToModel(imageDto);
        Image imageSaved= imageDao.saveAndFlush(image);
        return ImageConverter.modelToDto(imageSaved);
    }

    @Override
    public ImageDto findById(Long id) {
        return ImageConverter.modelToDto(Optional.ofNullable(imageDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    public boolean existImageByPath(String path) {
        if(imageDao.findImageByPath(path)!=null){return true;}
        else{
            return false;
        }
    }
    public Image getImageByPath(String path) {
        return imageDao.findImageByPath(path);
    }
    @Override
    public List<ImageDto> findAllImagesDto() {
       List<Image> images=imageDao.findAll();
       List<ImageDto> imageDtos=new ArrayList<>();
       images.forEach(i->{
           imageDtos.add(ImageConverter.modelToDto(i));
       });
       return imageDtos;
    }

}


