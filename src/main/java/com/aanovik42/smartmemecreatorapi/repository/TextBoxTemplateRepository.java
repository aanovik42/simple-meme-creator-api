package com.aanovik42.smartmemecreatorapi.repository;

import com.aanovik42.smartmemecreatorapi.entity.TextBoxTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TextBoxTemplateRepository extends CrudRepository<TextBoxTemplate, Long> {

    @Override
    List<TextBoxTemplate> findAll();
}
