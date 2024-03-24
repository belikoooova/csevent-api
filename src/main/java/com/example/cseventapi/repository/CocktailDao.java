package com.example.cseventapi.repository;

import com.example.cseventapi.entity.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CocktailDao extends JpaRepository<Cocktail, UUID> {
    List<Cocktail> findAllByEventId(UUID eventId);
}
