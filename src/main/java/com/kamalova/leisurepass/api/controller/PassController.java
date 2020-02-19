package com.kamalova.leisurepass.api.controller;

import com.kamalova.leisurepass.api.model.PassRequest;
import com.kamalova.leisurepass.dao.model.PassData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;


public interface PassController {
    @ApiOperation(value = "Create pass", response = PassData.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid argument"),
    })
    @PostMapping(value = "customer/{customerId}/create",
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<Object> add(@PathVariable(value = "customerId") String customerId,
                               @RequestBody PassRequest passRequest);

    @ApiOperation(value = "Cancel pass", response = PassData.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid argument"),
            @ApiResponse(code = 404, message = "If the requested pass id does not identify an existing pass.")
    })
    @PutMapping(value = "customer/{customerId}/cancel/{id}",
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<Object> cancel(@PathVariable(value = "customerId") String customerId,
                                  @PathVariable(value = "id") String passId);


    @ApiOperation(value = "Renew pass", response = PassData.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid argument"),
            @ApiResponse(code = 404, message = "If the requested pass id does not identify an existing pass.")
    })
    @PutMapping(value = "customer/{customerId}/renew/{id}",
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<Object> renew(@PathVariable(value = "customerId") String customerId,
                                 @PathVariable(value = "id") String passId,
                                 @RequestBody PassRequest passRequest);


    @ApiOperation(value = "Verify if a pass is active")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved data"),
            @ApiResponse(code = 400, message = "Invalid argument"),
            @ApiResponse(code = 404, message = "If the requested pass id does not identify an existing pass.")
    })
    @GetMapping(value = "/vendor/{vendorId}/verify/{id}",
            produces = {TEXT_PLAIN_VALUE})
    ResponseEntity<Object> verify(@PathVariable(value = "vendorId") String vendorId,
                                  @PathVariable(value = "id") String passId);

}
