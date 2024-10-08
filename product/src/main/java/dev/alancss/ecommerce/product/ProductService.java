package dev.alancss.ecommerce.product;

import dev.alancss.ecommerce.category.CategoryRepository;
import dev.alancss.ecommerce.exception.InvalidCategoryException;
import dev.alancss.ecommerce.exception.ProductNotFoundException;
import dev.alancss.ecommerce.exception.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new InvalidCategoryException(
                        "Category with ID '%s' not found.".formatted(request.categoryId())
                ));

        var product = mapper.toProduct(request);
        product.setCategory(category);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = extractProductIds(request);

        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        validateProductsExist(productIds, storedProducts);

        var storedRequest = sortRequestsByProductId(request);

        return processPurchases(storedProducts, storedRequest);
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with ID '%s' not found.".formatted(productId)
                ));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    private List<Integer> extractProductIds(List<ProductPurchaseRequest> request) {
        return request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
    }

    private void validateProductsExist(List<Integer> productIds, List<Product> storedProducts) {
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Some requested products could not be found.");
        }
    }

    private List<ProductPurchaseRequest> sortRequestsByProductId(List<ProductPurchaseRequest> request) {
        return request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
    }

    private List<ProductPurchaseResponse> processPurchases(List<Product> storedProducts, List<ProductPurchaseRequest> storedRequest) {
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            validateStockAvailability(product, productRequest);

            updateProductStock(product, productRequest.quantity());
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    private void validateStockAvailability(Product product, ProductPurchaseRequest productRequest) {
        if (product.getAvailableQuantity() < productRequest.quantity()) {
            throw new ProductPurchaseException(
                    "Insufficient stock quantity for product with ID " + productRequest.productId()
            );
        }
    }

    private void updateProductStock(Product product, double quantityPurchased) {
        var newAvailableQuantity = product.getAvailableQuantity() - quantityPurchased;
        product.setAvailableQuantity(newAvailableQuantity);
        productRepository.save(product);
    }
}
