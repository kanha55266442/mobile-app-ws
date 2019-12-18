package com.mobAppWS.ui.repository;

import com.mobAppWS.ui.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,String> {
    UserEntity findByEmail(String email);
}
