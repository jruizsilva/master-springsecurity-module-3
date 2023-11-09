package masterspringsecurity.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.ProductFacade;
import masterspringsecurity.domain.dto.product.ProductDto;
import masterspringsecurity.domain.dto.product.request.ProductRequest;
import masterspringsecurity.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*@CrossOrigin*/
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductFacade productFacade;
    private final ProductRepository productRepository;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(@PageableDefault(size = 2)
                                                    Pageable pageable) {
        return ResponseEntity.ok(productFacade.findAll(pageable));
    }

    @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long productId) {
        return productFacade.findById(productId)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound()
                                                           .build());
    }

    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(productFacade.create(productRequest));
    }

    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateOneById(@PathVariable Long productId,
                                                    @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productFacade.update(productId,
                                                      productRequest));
    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<ProductDto> disableOneById(@PathVariable Long productId) {
        return ResponseEntity.ok(productFacade.disableOneById(productId));
    }
}
