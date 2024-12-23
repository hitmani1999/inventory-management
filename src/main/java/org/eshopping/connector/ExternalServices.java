package org.eshopping.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalServices {

    private final RestTemplate restTemplate;


    @Autowired
    public ExternalServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public String fetchData() throws Exception {
        throw new Exception("Simulated failure");
    }

}
