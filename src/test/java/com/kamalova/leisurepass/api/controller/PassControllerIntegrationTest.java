package com.kamalova.leisurepass.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamalova.leisurepass.api.model.PassRequest;
import com.kamalova.leisurepass.dao.model.PassData;
import com.kamalova.leisurepass.service.PassService;
import com.kamalova.leisurepass.utils.Messages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PassController.class)
public class PassControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PassService service;

    private static String passId = UUID.randomUUID().toString();
    private static String vendorId = "vendorId";
    private static String customerId = "customerId";
    private static PassRequest passRequest;
    private static String requestBody;
    private static PassData passData;

    @BeforeAll
    public static void init() throws Exception {
        passData = new PassData(passId,
                "customerId",
                "homeCity",
                "passCity",
                600000L,
                System.currentTimeMillis(),
                vendorId
        );
        passRequest = new PassRequest(
                "homeCity",
                "passCity",
                600000L,
                System.currentTimeMillis(),
                vendorId);
        ObjectMapper mapper = new ObjectMapper();
        requestBody = mapper.writeValueAsString(passRequest);
    }

    @DisplayName("Customer can add pass")
    @Test
    public void add()
            throws Exception {
        given(service.createPass(customerId, passRequest)).willReturn(passData);

        mvc.perform(post("/leisure-service/customer/" + customerId + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("passId", is(passId)));
    }

    @DisplayName("Customer can renew pass")
    @Test
    public void renew() throws Exception {
        given(service.renewPass(customerId, passId, passRequest)).willReturn(passData);

        mvc.perform(put("/leisure-service/customer/" + customerId + "/renew/" + passId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("passId", is(passId)));
    }

    @DisplayName("Customer can cancel pass")
    @Test
    public void cancel() throws Exception {
        given(service.cancelPass(customerId, passId)).willReturn(passData);

        mvc.perform(put("/leisure-service/customer/" + customerId + "/cancel/" + passId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("passId", is(passId)));
    }

    @DisplayName("Vendor can add verify pass with vendorId")
    @Test
    public void verify() throws Exception {
        given(service.verifyPass(passId, vendorId)).willReturn(true);

        mvc.perform(get("/leisure-service/vendor/" + vendorId + "/verify/" + passId)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(Messages.PASS_ACTIVE.getMessage()));
    }
}
