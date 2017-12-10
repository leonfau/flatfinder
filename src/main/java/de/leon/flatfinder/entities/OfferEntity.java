package de.leon.flatfinder.entities;

import javax.persistence.*;

@Entity
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
