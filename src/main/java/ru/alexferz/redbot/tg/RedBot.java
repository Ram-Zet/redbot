package ru.alexferz.redbot.tg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.alexferz.redbot.service.DecisionService;
import ru.alexferz.redbot.service.QuoteService;

@Component
public class RedBot extends TelegramLongPollingBot {
    private final DecisionService decisionService;
    private final QuoteService quoteService;
    private final String username;

    public RedBot(DecisionService decisionService,
                  QuoteService quoteService,
                  @Value("${redbot.token}") String botToken,
                  @Value("${redbot.username}") String username) {
        super(botToken);
        this.quoteService = quoteService;
        this.decisionService = decisionService;
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (decisionService.decideOnReply(update)){
            sendReplyMessage(update);
        } else if (decisionService.decide(update)) {
            sendMessage(update);
        }
    }

    private void sendReplyMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setChatId(update.getMessage().getChatId());
        String quote = quoteService.giveQuote(update.getMessage());
        message.setText(quote);
        send(message);
    }

    private void sendMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        String quote = quoteService.giveQuote(update.getMessage());
        message.setText(quote);
        send(message);

    }

    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

}
