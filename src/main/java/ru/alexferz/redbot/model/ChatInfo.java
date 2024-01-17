package ru.alexferz.redbot.model;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatInfo {
    @Getter
    private final Long chatId;

    private final Set<String> saidQoutes = new HashSet<>();

    public Set<String> getSaidQoutes() {
        return Collections.unmodifiableSet(saidQoutes);
    }

    public void addSaidQuote(String quote, Integer quotesSize) {
        saidQoutes.add(quote);
        if (saidQoutes.size() >= quotesSize) {
            saidQoutes.clear();
        }
    }

    public ChatInfo(Long chatId) {
        this.chatId = chatId;
    }
}
