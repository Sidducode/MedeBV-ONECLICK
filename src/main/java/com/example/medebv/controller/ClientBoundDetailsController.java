package com.example.medebv.controller;

import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.request.ClientOutboundRequest;
import com.example.medebv.response.ClientOutBoundResponse;
import com.example.medebv.service.ClientBountDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/update/password")
@RequiredArgsConstructor
public class ClientBoundDetailsController {
    private static final Logger LOGGER = LogManager.getLogger(ClientBoundDetailsController.class);
    private static final String PASSWORD_UPDATED_SUCCESSFULLY = "PASSWORD UPDATED SUCCESSFULLY";

    @Autowired
    private ClientBountDetailsService clientBountDetailsService;

    @PutMapping("/update-password")
    public ResponseEntity<Object> updatePassword(@RequestBody ClientOutboundRequest request) throws MEDEBVCustomException {
        ClientOutBoundResponse clientOutBoundresponse = clientBountDetailsService.updatePassword(request);
        if (!clientOutBoundresponse.getErrorResponseList().isEmpty())
            return new ResponseEntity<>(clientOutBoundresponse.getErrorResponseList(), HttpStatus.BAD_REQUEST);
        LOGGER.info("PASSWORD UPDATED SUCCESSFULLY");
        List<String> responseList = new ArrayList<>();
        responseList.add(PASSWORD_UPDATED_SUCCESSFULLY);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
