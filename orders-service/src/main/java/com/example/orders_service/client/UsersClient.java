package com.example.orders_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UsersClient {

    private final RestClient restClient;


    @Value("${users.service.base-url}")
    private String baseUrl;

    public UsersClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean userExists(Long userId) {
        try {
            restClient.get()
                    .uri(baseUrl + "/api/users/" + userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> { throw new RuntimeException("not found"); })
                    .toBodilessEntity();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
