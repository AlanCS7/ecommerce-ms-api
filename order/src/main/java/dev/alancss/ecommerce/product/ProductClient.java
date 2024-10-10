package dev.alancss.ecommerce.product;

import dev.alancss.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.product-url}")
    private String productUrl;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(requestBody, headers);
        var responseType = new ParameterizedTypeReference<List<PurchaseResponse>>() {
        };

        var responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the product purchase: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }
}
