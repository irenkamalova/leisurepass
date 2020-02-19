package com.kamalova.leisurepass.dao;

import com.kamalova.leisurepass.dao.model.PassData;
import org.springframework.data.repository.CrudRepository;

public interface PassRepository extends CrudRepository<PassData, String> {
}