package com.tim.rickandmorty.entity;

//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//public class Locations {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String name;
//    private String type;
//    private String dimension;
//    private LocalDateTime created;
//
//    public String getName() {
//        return name;
//    }
//
//    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
//    private List<Characters> characters;
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getDimension() {
//        return dimension;
//    }
//
//    public void setDimension(String dimension) {
//        this.dimension = dimension;
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

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String dimension;

    @OneToMany(  mappedBy = "location", cascade = CascadeType.ALL)
    private List<Characters> residents;

    private LocalDateTime created;

}