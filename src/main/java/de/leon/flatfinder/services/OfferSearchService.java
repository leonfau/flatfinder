package de.leon.flatfinder.services;

import com.google.common.collect.Sets;
import de.leon.flatfinder.entities.OfferEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class OfferSearchService {

    private final List<Flatprovider> flatprovider;

    private final NotificationService notificationService;

    @Autowired
    public OfferSearchService(List<Flatprovider> flatprovider, NotificationService notificationService) {
        this.flatprovider = flatprovider;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 300000)
    public void getNewOffers() throws IOException {
        Set<OfferEntity> offers = Sets.newHashSet();
        for(Flatprovider provider: flatprovider){
            offers.addAll(provider.getNewOffers());
        }
        if (!offers.isEmpty()) {
            notificationService.notify(offers);
        }
    }
}
