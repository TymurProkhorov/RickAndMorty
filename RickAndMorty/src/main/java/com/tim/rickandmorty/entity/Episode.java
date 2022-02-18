package com.tim.rickandmorty.entity;

//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//public class Episodes{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String name;
//    private LocalDateTime air_date;
//    private LocalDateTime created_at;
//    private String episode;
//
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JoinTable(name = "character_episode",
//            joinColumns = @JoinColumn(name = "chId"),
//            inverseJoinColumns = @JoinColumn (name = "epId"))
//    private List<Characters> episodes;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public LocalDateTime getAir_date() {
//        return air_date;
//    }
//
//    public void setAir_date(LocalDateTime air_date) {
//        this.air_date = air_date;
//    }
//
//    public LocalDateTime getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(LocalDateTime created_at) {
//        this.created_at = created_at;
//    }
//
//    public String getEpisode() {
//        return episode;
//    }
//
//    public void setEpisode(String episode) {
//        this.episode = episode;
//    }
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private Date air_date;
    private LocalDateTime created;


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,mappedBy = "episode" )
    private List<Characters> characters;

    private String episode;
    private String url;
}

