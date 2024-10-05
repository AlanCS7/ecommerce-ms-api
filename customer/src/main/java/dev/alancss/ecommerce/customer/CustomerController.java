package dev.alancss.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        String customerId = service.createCustomer(request);
        return ResponseEntity.created(URI.create("/customers/" + customerId)).body(customerId);
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<Void> updateCustomerById(
            @PathVariable("customer-id") String customerId,
            @RequestBody @Valid CustomerRequest request
    ) {
        service.updateCustomerById(customerId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.existsCustomerById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.findCustomerById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("customer-id") String customerId) {
        service.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }
}
