package de.leon.flatfinder.services;

import de.leon.flatfinder.entities.OfferEntity;
import de.leon.flatfinder.repositories.OfferRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScheduledImmoScoutParser {
    private static final Logger log = LoggerFactory.getLogger(ScheduledImmoScoutParser.class);

    private final OfferRepository offerRepository;
    private final NotificationService notificationService;

    @Autowired
    public ScheduledImmoScoutParser(OfferRepository offerRepository, NotificationService notificationService) {
        this.offerRepository = offerRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 300000)
    public void parsePage() throws IOException {
        String url = "https://www.immobilienscout24.de/Suche/S-2/Wohnung-Miete/Umkreissuche/Hamburg/-/1840/2621814/-/1276006001/4/2,00-/50,00-/EURO--600,00/-/-/-/-/-/true";
        Document doc = Jsoup.connect(url).get();
        Elements elementsByAttribute = doc.getElementsByAttribute("data-go-to-expose-id");
        Set<OfferEntity> offers = elementsByAttribute.stream()
                .map(e -> e.attr("data-go-to-expose-id"))
                .distinct()
                .map(e -> "https://www.immobilienscout24.de/expose/" + e)
                .filter(e -> offerRepository.findByUrl(e) == null)
                .map(OfferEntity::of)
                .collect(Collectors.toSet());

        if (!offers.isEmpty()) {
            offerRepository.saveAll(offers);
            notificationService.notify(offers);
        }

        log.debug("Immoscout Page parsed");
    }

}
