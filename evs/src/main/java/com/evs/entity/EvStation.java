package com.evs.entity;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EvStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String evname;
    private String address;
    private String city;
    private String pincode;
    private String state;
    private double latitude;
    private double longitude;
    private String evimg;


}
