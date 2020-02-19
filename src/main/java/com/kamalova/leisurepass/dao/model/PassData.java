package com.kamalova.leisurepass.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leisurepass")
@ApiModel(description = "leisurepass")
public class PassData {
    /**
     * Pass ID should be unique
     */
    @Id
    @Column(unique = true)
    @JsonProperty("passId")
    private String passId;
    /**
     * Customer Data
     */
    @Column
    @JsonProperty("customerId")
    private String customerId;
    @Column
    @JsonProperty("homeCity")
    private String homeCity;
    @Column
    @JsonProperty("passCity")
    private String passCity;
    @Column
    @JsonProperty("passLength")
    private Long passLength;
    @Column
    @JsonProperty("startTime")
    private Long startTime;
    @Column
    @JsonProperty("vendorId")
    private String vendorId;
}
