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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImmonetProvider implements Flatprovider {
    private static final Logger log = LoggerFactory.getLogger(ImmonetProvider.class);

    private final OfferRepository offerRepository;

    @Value("${immonet.url}")
    private String url;

    @Autowired
    public ImmonetProvider(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Set<OfferEntity> getNewOffers() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elementsByAttribute = doc.getElementsByAttributeValueStarting("href", "/angebot");
        Set<OfferEntity> offers = elementsByAttribute.stream()
                .map(e -> e.attr("href"))
                .distinct()
                .map(e -> "https://www.immonet.de" + e)
                .filter(e -> offerRepository.findByUrl(e) == null)
                .map(OfferEntity::of)
                .collect(Collectors.toSet());

        offerRepository.save(offers);
        log.info("Immonet Page parsed");

        return offers;
    }
}
