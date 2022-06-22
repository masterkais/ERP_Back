package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.ImageDto;
import fr.byteCode.erp.service.services.InterfaceService.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/image")
public class ImageController {

    private IImageService imageService;

    @Autowired
    private ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public ImageDto saveImg(@RequestBody ImageDto imageDto) throws Exception {
        return imageService.save(imageDto);
    }

    @PutMapping("")
    public ImageDto updateImg(@RequestBody ImageDto imageDto) {
        return imageService.update(imageDto);
    }

    @GetMapping("/{id}")
    public ImageDto getImgById(@PathVariable Long id) {
        return imageService.findById(id);
    }

    @GetMapping("/images")
    public List<ImageDto> getAllImgs() {
        return imageService.findAllImagesDto();
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteImg(@PathVariable Long id){
        imageService.delete(id);
        return  true;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
}
