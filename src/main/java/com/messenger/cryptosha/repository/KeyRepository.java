package com.messenger.cryptosha.repository;

import com.messenger.cryptosha.model.KeyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<KeyModel, Integer> {

}
