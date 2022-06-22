package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.ExitVoucherDao;
import fr.byteCode.erp.persistance.dto.ExitVoucherDto;
import fr.byteCode.erp.persistance.entities.ExitVoucher;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.ExitVoucherConverter;
import fr.byteCode.erp.service.services.InterfaceService.IExitVoucherService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class ExitVoucherService extends GenericService<ExitVoucher, Long> implements IExitVoucherService {
    private ExitVoucherDao exitVoucherDao;

    @Autowired
    private ExitVoucherService(ExitVoucherDao exitVoucherDao) {
        this.exitVoucherDao = exitVoucherDao;
    }

    @Override
    public ExitVoucherDto save(ExitVoucherDto exitVoucherDto) {
        Objects.requireNonNull(exitVoucherDto);
        ExitVoucher exitVoucherSaved = exitVoucherDao.saveAndFlush(ExitVoucherConverter.dtoToModel(exitVoucherDto));
        log.info(LOG_ENTITY_CREATED, exitVoucherDto);
        return ExitVoucherConverter.modelToDto(exitVoucherSaved);

    }

    @Override
    public ExitVoucherDto update(ExitVoucherDto exitVoucherDto) {
        Objects.requireNonNull(exitVoucherDto);
        ExitVoucher exitVoucherSaved = exitVoucherDao.saveAndFlush(ExitVoucherConverter.dtoToModel(exitVoucherDto));
        log.info(LOG_ENTITY_CREATED, exitVoucherDto);
        return ExitVoucherConverter.modelToDto(exitVoucherSaved);
    }

    @Override
    public ExitVoucherDto findById(Long id) {
        return ExitVoucherConverter.modelToDto(Optional.ofNullable(exitVoucherDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<ExitVoucherDto> findAllExitVouchers() {
        List<ExitVoucher> exitVouchers = exitVoucherDao.findAll();
        List<ExitVoucherDto> exitVoucherDtos = new ArrayList<>();
        exitVouchers.forEach(exitVoucher -> {
            exitVoucherDtos.add(ExitVoucherConverter.modelToDto(exitVoucher));
        });
        return exitVoucherDtos;
    }
}
