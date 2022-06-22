package fr.byteCode.erp.service.services.InterfaceService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface IGenericService<T, D extends Serializable> {

    List<T> findAll();
    T findOne(D id);
    <S extends T> S saveAndFlush(S entity);
    void delete(D id);
    void delete(T entity);
    void deleteList(Iterable<T> entities);
    boolean existById(D id);



}
