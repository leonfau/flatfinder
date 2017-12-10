package de.leon.flatfinder.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import de.leon.flatfinder.entities.OfferEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final static String TOKEN = "464120036:AAEJtOKNPvu4hhy_FfMmhHsqUo7dBxGrpns";
    private final static String CHAT_ID = "127670027";

    private final TelegramBot bot;

    public NotificationService() {
        this.bot = new TelegramBot(TOKEN);
    }

    public void notify(Set<OfferEntity> offers) {
        String message = offers.stream().map(OfferEntity::getUrl).collect(Collectors.joining("\n\n "));
        sendTelegram("Neue Angbote gefunden: \n\n" + message);
    }

    private void sendTelegram(String msg) {
        SendMessage request = new SendMessage(CHAT_ID, msg)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        bot.execute(request);
    }
}
