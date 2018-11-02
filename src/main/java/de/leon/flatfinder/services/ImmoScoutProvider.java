package de.leon.flatfinder.services;

import de.leon.flatfinder.entities.OfferEntity;
import de.leon.flatfinder.repositories.OfferRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImmoScoutProvider implements Flatprovider {
    private static final Logger log = LoggerFactory.getLogger(ImmoScoutProvider.class);

    private final OfferRepository offerRepository;

    @Value("${immoscout.url}")
    private String url;

    @Autowired
    public ImmoScoutProvider(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Set<OfferEntity> getNewOffers() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elementsByAttribute = doc.getElementsByAttribute("data-go-to-expose-id");
        Set<OfferEntity> offers = elementsByAttribute.stream()
                .map(e -> e.attr("data-go-to-expose-id"))
                .distinct()
                .map(e -> "https://www.immobilienscout24.de/expose/" + e)
                .filter(e -> offerRepository.findByUrl(e) == null)
                .map(OfferEntity::of)
                .collect(Collectors.toSet());

        offerRepository.save(offers);
        log.info("Immoscout Page parsed");

        return offers;
    }

}
