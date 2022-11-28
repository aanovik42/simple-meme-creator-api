package com.aanovik42.smartmemecreatorapi.repository;

import com.aanovik42.smartmemecreatorapi.entity.Meme;
import org.springframework.data.repository.CrudRepository;

public interface MemeRepository extends CrudRepository<Meme, Long> {
}
