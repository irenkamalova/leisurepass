package com.kamalova.leisurepass.service;

import com.kamalova.leisurepass.api.model.PassRequest;
import com.kamalova.leisurepass.dao.PassRepository;
import com.kamalova.leisurepass.dao.model.PassData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PassServiceImpl implements PassService {

    @Autowired
    private PassRepository passRepository;

    @Override
    public PassData createPass(String customerId, PassRequest passRequest) {
        UUID uuid = UUID.randomUUID();
        PassData passData = new PassData(
                uuid.toString(),
                customerId,
                passRequest.getHomeCity(),
                passRequest.getPassCity(),
                passRequest.getPassLength(),
                passRequest.getStartTime(),
                passRequest.getVendorId()
        );
        passRepository.save(passData);
        return passData;
    }

    private PassData getPass(String passId) {
        return passRepository.findById(passId)
                .orElseThrow(() -> new IllegalArgumentException("No pass with provided id"));
    }

    @Override
    public PassData renewPass(String customerId, String passId, PassRequest passRequest) {
        PassData passData = getPass(passId);
        if (!passData.getCustomerId().equals(customerId)) {
            throw new IllegalArgumentException("Wrong customer Id");
        }

        passData.setHomeCity(passRequest.getHomeCity());
        passData.setPassCity(passRequest.getPassCity());
        passData.setPassLength(passRequest.getPassLength());
        passData.setStartTime(passRequest.getStartTime());
        passData.setVendorId(passRequest.getVendorId());
        passRepository.save(passData);
        return passData;
    }

    /*
    Remark: I don't delete pass if it's not available because I want to store
    information about all existed passes (for example, for data analytics)
     */
    @Override
    public PassData cancelPass(String customerId, String passId) {
        PassData passData = getPass(passId);
        if (!passData.getCustomerId().equals(customerId)) {
            throw new IllegalArgumentException("Wrong customer Id");
        }

        passData.setPassLength(0L); // now pass is expired
        passRepository.save(passData);
        return passData;
    }

    @Override
    public boolean verifyPass(String passId, String vendorId) {
        PassData pass = getPass(passId);
        if (!pass.getVendorId().equals(vendorId)) {
            throw new IllegalArgumentException("VendorId invalid");
        }
        long passPeriod = pass.getStartTime() + pass.getPassLength();
        long verifiedPeriod = System.currentTimeMillis();
        return pass.getPassLength() != 0L && passPeriod > verifiedPeriod;
    }
}
