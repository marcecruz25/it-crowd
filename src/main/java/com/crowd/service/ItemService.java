package com.crowd.service;

import com.crowd.model.Word;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by marce on 3/21/18.
 */
public interface ItemService {

    boolean create(Word word);
    List<Word> findAll();
    Page findAllByCriteria(Integer pagina, Integer tamanio, String criteriaSort);
    Word finById(long id);
    Word update(Word word);
    void delete(long id);
}
