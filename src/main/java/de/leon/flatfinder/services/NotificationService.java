package de.leon.flatfinder.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import de.leon.flatfinder.configuration.TelegramConfigurationProperties;
import de.leon.flatfinder.entities.OfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final TelegramConfigurationProperties configuration;

    private final TelegramBot bot;

    @Autowired
    public NotificationService(TelegramConfigurationProperties configuration) {
        this.configuration = configuration;
        this.bot = new TelegramBot(this.configuration.getToken());
    }

    public void notify(Set<OfferEntity> offers) {
        String message = offers.stream()
                .map(OfferEntity::getUrl)
                .collect(Collectors.joining("\n\n"));
        sendTelegram("Neue Angebote gefunden: \n\n" + message);
    }

    private void sendTelegram(String msg) {
        SendMessage request = new SendMessage(configuration.getClientId(), msg)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        bot.execute(request);
    }
}
