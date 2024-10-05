package dev.alancss.ecommerce.customer;

import dev.alancss.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomerById(String customerId, CustomerRequest request) {
        var customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Cannot update customer: No customer found with the provided ID: %s".formatted(customerId)
                ));

        mapper.copyToCustomer(request, customer);
        repository.save(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::toCustomerResponse)
                .toList();
    }

    public Boolean existsCustomerById(String customerId) {
        return repository.existsById(customerId);
    }

    public CustomerResponse findCustomerById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "No customer found with the provided ID: %s".formatted(customerId)
                ));
    }

    public void deleteCustomerById(String customerId) {
        repository.findById(customerId)
                .ifPresentOrElse(
                        repository::delete,
                        () -> {
                            throw new CustomerNotFoundException(
                                    "No customer found with the provided ID: %s".formatted(customerId)
                            );
                        }
                );
    }

}
