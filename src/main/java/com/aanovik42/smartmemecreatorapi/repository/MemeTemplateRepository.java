package com.aanovik42.smartmemecreatorapi.repository;

import com.aanovik42.smartmemecreatorapi.entity.MemeTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemeTemplateRepository extends CrudRepository<MemeTemplate, Long> {

    @Override
    List<MemeTemplate> findAll();
}
