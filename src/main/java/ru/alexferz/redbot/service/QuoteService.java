package ru.alexferz.redbot.service;

import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.alexferz.redbot.jpa.entity.Quote;
import ru.alexferz.redbot.jpa.repository.QuoteRepositoryJpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {
    private final QuoteRepositoryJpa quoteRepository;

    public QuoteService(QuoteRepositoryJpa quoteRepositoryJpa) {
        this.quoteRepository = quoteRepositoryJpa;
    }

    @Transactional
    public synchronized String giveQuote(Message message) {
        List<Quote> oldestUsedList = quoteRepository.findOldestUsed(PageRequest.of(0, 1));
        return Optional.of(oldestUsedList)
                .filter(CollectionUtils::isNotEmpty)
                .map(list -> list.get(0))
                .map(quote -> {
                    quote.setLastUsageDt(LocalDateTime.now());
                    return quote.getQuote();
                })
                .orElse("");
    }


}
