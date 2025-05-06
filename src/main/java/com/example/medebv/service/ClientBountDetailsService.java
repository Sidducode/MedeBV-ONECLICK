package com.example.medebv.service;
import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.request.ClientOutboundRequest;
import com.example.medebv.response.ClientOutBoundResponse;

public interface ClientBountDetailsService {
    // Define the methods that this service will implement
    // For example:
    // List<ClientBoundDetails> getAllClientBoundDetails();
    // ClientBoundDetails getClientBoundDetailsById(Long id);
    // void saveClientBoundDetails(ClientBoundDetails clientBoundDetails);
    //void updateClientBoundDetails(Long id, ClientBoundDetails clientBoundDetails);
    // void deleteClientBoundDetails(Long id);

    ClientOutBoundResponse updatePassword(ClientOutboundRequest request) throws MEDEBVCustomException;
}

