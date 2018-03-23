package com.crowd.service.impl;

import com.crowd.model.Word;
import com.crowd.repository.ItemRepository;
import com.crowd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marce on 3/21/18.
 */
@Service(value = "itemService")
public class ItemServiceImpl implements ItemService {

    private final int TAMANIO_PAGINA_DEFAULT = 100;
    private final int PAGINA_DEFAULT = 0;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean create(Word word) {
        Word wordSaved = itemRepository.findByNombre(word.getNombre());
        if (null == wordSaved) {
            itemRepository.save(word);
            return true;
        }
        return false;
    }

    @Override
    public List<Word> findAll() {
        return (List<Word>) itemRepository.findAll();
    }

    @Override
    public Page<Word> findAllByCriteria(Integer pagina, Integer tamanio, String criteriaSort) {
        if (tamanio == null || tamanio <= 0) {
            tamanio = TAMANIO_PAGINA_DEFAULT;
        }
        if (pagina == null || pagina < 0) {
            pagina = PAGINA_DEFAULT;
        }

        Sort sort;
        if ("ASC".equals(criteriaSort)) {
            sort = new Sort(Sort.Direction.ASC, "nombre");
        } else {
            sort = new Sort(Sort.Direction.DESC, "nombre");
        }

        Pageable pageable = new PageRequest(pagina, tamanio, sort);
        return itemRepository.findAll(pageable);
    }

    @Override
    public Word finById(long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public Word update(Word word) {
        return itemRepository.save(word);
    }

    @Override
    public void delete(long id) {
        itemRepository.delete(id);
    }
}
