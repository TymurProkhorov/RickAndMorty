package com.tim.rickandmorty.entity;

//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//import org.hibernate.annotations.Cascade;
//
//@Entity
//public class Characters {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String name;
//    private String status;
//    private String species;
//    private String gender;
//    private int originId;
//    private int locationId;
//    private String image;
//    private LocalDateTime created;
//
//    @ManyToOne()
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @JoinColumn(name = "origin_id", referencedColumnName = "id")
//    private Locations origin;
//
//    @ManyToOne()
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @JoinColumn(name = "location_id", referencedColumnName = "id")
//    private Locations location;
//
//
//    @ManyToMany(mappedBy = "episodes")
//    private List<Episodes> characters;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getSpecies() {
//        return species;
//    }
//
//    public void setSpecies(String species) {
//        this.species = species;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public int getOriginId() {
//        return originId;
//    }
//
//    public void setOriginId(int originId) {
//        this.originId = originId;
//    }
//
//    public int getLocationId() {
//        return locationId;
//    }
//
//    public void setLocationId(int locationId) {
//        this.locationId = locationId;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    public void setCreated(LocalDateTime created) {
//        this.created = created;
//    }
//}



import lombok.Data;
import org.hibernate.annotations.Cascade;

import com.tim.rickandmorty.enums.Gender;
import com.tim.rickandmorty.enums.Status;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String species;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private Location origin;

    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER,   cascade = CascadeType.MERGE)
    @JoinTable(
            name = "episode_character",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private List<Episode> episode;

    private String image;

    private String type;

    private LocalDateTime created;
}