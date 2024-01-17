package ru.alexferz.redbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Random;

@Service
public class DecisionService {
    private final int probability;
    private final String username;

    public DecisionService(@Value("${redbot.probability}") int probability,
                           @Value("${redbot.username}") String username) {
        this.probability = probability;
        this.username = username;
    }

    public static final LocalDateTime MIN_DT = LocalDateTime.of(1970, 1,1,0,0,0);

    public boolean decide(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();

            if (message.startsWith("/пизди") ||
                    message.startsWith("/газуй")) {
                return true;
            }

            LocalDateTime messageDt =
                    MIN_DT.plusSeconds(update.getMessage().getDate()).plusSeconds(OffsetDateTime.now().getOffset().getTotalSeconds());
            if (messageDt.plusMinutes(5).isBefore(LocalDateTime.now())) {
                return false;
            }

            int rand = new Random().nextInt(this.probability);
            return rand == 1;
        }
        return false;
    }

    public boolean decideOnReply(Update update) {
        Message message;
        if ((message = update.getMessage().getReplyToMessage()) != null) {
            return message.getFrom().getUserName().equals(username);
        }
        return false;
    }
}
