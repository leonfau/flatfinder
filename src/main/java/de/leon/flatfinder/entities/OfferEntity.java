package de.leon.flatfinder.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OfferEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String url;

    public static OfferEntity of(String url){
        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setUrl(url);
        return offerEntity;
    }

}
