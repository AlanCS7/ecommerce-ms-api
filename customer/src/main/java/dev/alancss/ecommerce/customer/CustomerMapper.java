package dev.alancss.ecommerce.customer;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    public void copyToCustomer(CustomerRequest request, Customer customer) {
        Optional.ofNullable(request.firstname())
                .filter(StringUtils::isNotBlank)
                .ifPresent(customer::setFirstname);

        Optional.ofNullable(request.lastname())
                .filter(StringUtils::isNotBlank)
                .ifPresent(customer::setLastname);

        Optional.ofNullable(request.email())
                .filter(StringUtils::isNotBlank)
                .ifPresent(customer::setEmail);

        Optional.ofNullable(request.address())
                .ifPresent(customer::setAddress);
    }
}
