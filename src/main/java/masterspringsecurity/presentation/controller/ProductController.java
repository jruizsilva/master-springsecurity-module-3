package masterspringsecurity.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductFacade productFacade;
    private final ProductRepository productRepository;

    /*@PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productFacade.findAll());
    }*/

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(@PageableDefault(size = 2)
                                                    Pageable pageable) {
        return ResponseEntity.ok(productFacade.findAll(pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long productId) {
        return productFacade.findById(productId)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound()
                                                           .build());
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(productFacade.create(productRequest));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateOneById(@PathVariable Long productId,
                                                    @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productFacade.update(productId,
                                                      productRequest));
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<ProductDto> disableOneById(@PathVariable Long productId) {
        return ResponseEntity.ok(productFacade.disableOneById(productId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e,
                                                                      HttpServletRequest request) {
        Map<String, String> apiError = new HashMap<>();
        apiError.put("message",
                     e.getLocalizedMessage());
        apiError.put("timestamp",
                     new Date().toString());
        apiError.put("path",
                     request.getRequestURI());
        apiError.put("http-method",
                     request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        apiError.put("status",
                     status.toString());
        apiError.put("error",
                     e.getClass()
                      .getSimpleName());

        return ResponseEntity.status(status)
                             .body(apiError);

    }
}
