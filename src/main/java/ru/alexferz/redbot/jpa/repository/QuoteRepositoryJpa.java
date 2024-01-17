package ru.alexferz.redbot.jpa.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.alexferz.redbot.jpa.entity.Quote;

import java.util.List;

public interface QuoteRepositoryJpa extends JpaRepository<Quote, Long> {

    @Query("SELECT q FROM Quote q ORDER BY q.lastUsageDt NULLS FIRST")
    List<Quote> findOldestUsed(Pageable pageable);
}
