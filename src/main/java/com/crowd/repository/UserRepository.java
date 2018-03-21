package com.crowd.repository;

import com.crowd.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by marce on 3/21/18.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String name);
}