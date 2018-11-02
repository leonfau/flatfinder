package de.leon.flatfinder.services;

import de.leon.flatfinder.entities.OfferEntity;

import java.io.IOException;
import java.util.Set;

public interface Flatprovider {

    Set<OfferEntity> getNewOffers() throws IOException;
}
