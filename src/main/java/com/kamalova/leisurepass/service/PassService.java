package com.kamalova.leisurepass.service;

import com.kamalova.leisurepass.api.model.PassRequest;
import com.kamalova.leisurepass.dao.model.PassData;

public interface PassService {

    PassData createPass(String customerId, PassRequest passRequest);

    PassData renewPass(String customerId, String passId, PassRequest passRequest);

    /*
        Remark: I don't delete pass if it not available because I want to store
        information about all existed passes (for example, for data analytics)
         */
    PassData cancelPass(String customerId, String passId);

    boolean verifyPass(String passId, String vendorId);
}
