package com.kamalova.leisurepass.api.controller;

import com.kamalova.leisurepass.api.model.PassRequest;
import com.kamalova.leisurepass.dao.model.PassData;
import com.kamalova.leisurepass.service.PassService;
import com.kamalova.leisurepass.utils.Messages;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "leisurepass", path = "/leisure-service")
@Api(value = "Leisure Pass Management System")
public class PassControllerImpl implements PassController {

    @Autowired
    private PassService passService;

    @Override
    public ResponseEntity<Object> add(String customerId, PassRequest passRequest) {
        PassData pass = passService.createPass(customerId, passRequest);
        return ResponseEntity.ok(pass);
    }

    @Override
    public ResponseEntity<Object> cancel(String customerId, String passId) {
        PassData pass = passService.cancelPass(customerId, passId);
        return ResponseEntity.ok(pass);
    }

    @Override
    public ResponseEntity<Object> renew(String customerId,
                                        String passId,
                                        PassRequest passRequest) {
        PassData pass = passService.renewPass(customerId, passId, passRequest);
        return ResponseEntity.ok(pass);
    }

    @Override
    public ResponseEntity<Object> verify(String vendorId, String passId) {
        return passService.verifyPass(passId, vendorId) ?
                ResponseEntity.ok(Messages.PASS_ACTIVE.getMessage()) : ResponseEntity.ok(Messages.PASS_EXPIRED.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<String> handleArgumentExceptions(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}