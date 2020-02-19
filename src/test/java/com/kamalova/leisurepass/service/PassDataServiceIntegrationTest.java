package com.kamalova.leisurepass.service;

import com.kamalova.leisurepass.api.model.PassRequest;
import com.kamalova.leisurepass.dao.PassRepository;
import com.kamalova.leisurepass.dao.model.PassData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
public class PassDataServiceIntegrationTest {

    @MockBean
    PassRepository passRepository;

    @Autowired
    private PassService service;

    private static String vendorId = "vendorId";
    private static String customerId = "customerId";
    private static PassRequest passRequest;
    private static PassData passData;
    private static String passId = UUID.randomUUID().toString();
    
    @BeforeAll
    public static void init() {
        passData = new PassData(passId,
                "customerId",
                "homeCity",
                "passCity",
                100000000L,
                System.currentTimeMillis(),
                "vendorId"
        );
        
        passRequest = new PassRequest(
                "homeCity",
                "passCity",
                100000000L,
                System.currentTimeMillis(),
                vendorId);
    }
    
    @DisplayName("Service should create pass")
    @Test
    public void createPass() {

        given(passRepository.save(passData)).willReturn(passData);
        PassData passDataCreated = service.createPass(customerId, passRequest);
        assertEquals(customerId, passDataCreated.getCustomerId());
        assertEquals(passRequest.getHomeCity(), passDataCreated.getHomeCity());
        assertEquals(passRequest.getPassCity(), passDataCreated.getPassCity());
        assertEquals(passRequest.getStartTime(), passDataCreated.getStartTime());
        assertEquals(passRequest.getPassLength(), passDataCreated.getPassLength());
        assertEquals(passRequest.getVendorId(), passDataCreated.getVendorId());
    }

    @DisplayName("Service should create pass")
    @Test
    public void renewPass() {
        given(passRepository.findById(passId)).willReturn(Optional.of(passData));
        given(passRepository.save(passData)).willReturn(passData);

        PassData passDataChanged = service.renewPass(customerId, passId, passRequest);
        assertEquals(customerId, passDataChanged.getCustomerId());
        assertEquals(passRequest.getHomeCity(), passDataChanged.getHomeCity());
        assertEquals(passRequest.getPassCity(), passDataChanged.getPassCity());
        assertEquals(passRequest.getStartTime(), passDataChanged.getStartTime());
        assertEquals(passRequest.getPassLength(), passDataChanged.getPassLength());
        assertEquals(passRequest.getVendorId(), passDataChanged.getVendorId());
    }

    @DisplayName("Service should verify and cancel pass")
    @Test
    public void verifyPass() {
        given(passRepository.findById(passId)).willReturn(Optional.of(passData));
        assertTrue(service.verifyPass(passId, vendorId));

        service.cancelPass(customerId, passId);
        assertFalse(service.verifyPass(passId, vendorId));
    }

    @DisplayName("Service should throw exception if there no pass")
    @Test
    public void corruptedDataTest() {
        given(passRepository.findById(passId)).willReturn(Optional.of(passData));
        given(passRepository.findById("id")).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.cancelPass("incorrectCustomerId",passId));
        assertThrows(IllegalArgumentException.class, () -> service.cancelPass(customerId,"id"));
        assertThrows(IllegalArgumentException.class, () -> service.renewPass("incorrectCustomerId" ,passId, passRequest));
        assertThrows(IllegalArgumentException.class, () -> service.renewPass(customerId,"id", passRequest));
        assertThrows(IllegalArgumentException.class, () -> service.verifyPass("id", vendorId));
        assertThrows(IllegalArgumentException.class, () -> service.verifyPass(passId, "incorrectVendor"));
    }
}