package com.example.medebv.service.impl;

import com.example.medebv.entity.ClientBoundDetails;
import com.example.medebv.exception.MEDEBVCustomException;
import com.example.medebv.jparepository.ClientOutboundDetailsRepository;
import com.example.medebv.model.Client;
import com.example.medebv.model.Environment;
import com.example.medebv.model.Secret;
import com.example.medebv.model.SecretsConfigurations;
import com.example.medebv.request.ClientOutboundRequest;
import com.example.medebv.response.ClientOutBoundResponse;
import com.example.medebv.service.ClientBountDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class ClientboundDetailsImpl implements ClientBountDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(ClientboundDetailsImpl.class);

    private static final Region REGION = Region.EU_NORTH_1;
    @Autowired
    private ClientOutboundDetailsRepository clientOutboundDetailsRepository;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.objectKey}")
    private String objectKey;

    @Override
    public ClientOutBoundResponse updatePassword(ClientOutboundRequest request) throws MEDEBVCustomException {
        List<String> errorResponseList = new ArrayList<>();
        try {

            List<ClientBoundDetails> details = clientOutboundDetailsRepository
                    .findBynClientId(request.getClientName());
            LOGGER.info(details);
            List<Secret> secrets = readSecretsFromS3Bucket(request.getEnvironment(), request.getClientName());
            LOGGER.info(secrets);
            if (secrets == null || secrets.isEmpty()) {
                throw new MEDEBVCustomException("No secrets found for given client/environment", HttpStatus.NOT_FOUND);
            }

            for (ClientBoundDetails detail : details) {
                for (Secret secret : secrets) {

                    if (detail.getClientId().equalsIgnoreCase(secret.getClientId())
                            && detail.getUsername().equalsIgnoreCase(secret.getUsername())
                            && detail.getRequestUrl().equalsIgnoreCase(secret.getRequestUrl())) {
                        detail.setPassword(secret.getPassword());
                        clientOutboundDetailsRepository.save(detail);
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error Occurred while processing : {}", e.getMessage(), e);
            throw new MEDEBVCustomException("Error Occurred while processing " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ClientOutBoundResponse(errorResponseList, HttpStatus.OK);
    }

    public List<Secret> readSecretsFromS3Bucket(String environmentName, String clientName) throws MEDEBVCustomException {

        try {
            S3Client s3Client = S3Client.builder()
                    .region(REGION)
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)
                    ))
                    .build();

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            ResponseInputStream<?> s3Object = s3Client.getObject(getObjectRequest);

            ObjectMapper mapper = new ObjectMapper();
            SecretsConfigurations config = mapper.readValue(s3Object, SecretsConfigurations.class);

            Optional<Environment> envOpt = config.getEnvironments().stream()
                    .filter(env -> env.getName().equalsIgnoreCase(environmentName))
                    .findFirst();

            if (envOpt.isPresent()) {
                Optional<Client> clientOpt = envOpt.get().getClients().stream()
                        .filter(c -> c.getName().equalsIgnoreCase(clientName))
                        .findFirst();

                if (clientOpt.isPresent()) {
                    return clientOpt.get().getSecrets();
                }
            }
            return Collections.emptyList();
        } catch (Exception e) {
            throw new MEDEBVCustomException("Error while reading s3Bucket file " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}