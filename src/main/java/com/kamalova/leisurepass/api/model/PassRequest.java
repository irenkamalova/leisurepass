package com.kamalova.leisurepass.api.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class PassRequest {
    private final String homeCity;
    private final String passCity;
    /**
     * Period of how long ticket is valid
     * */
    private final Long passLength;
    /**
     * Start time of valid period for the pass
     */
    private final Long startTime;
    /**
     * Vendors who can accept this pass:
     */
    private final String vendorId;
}
