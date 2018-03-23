package com.crowd.repository;

import com.crowd.model.Word;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by marce on 3/21/18.
 */
public interface ItemRepository  extends PagingAndSortingRepository<Word, Long> {

    Word findByNombre(String character);

}
