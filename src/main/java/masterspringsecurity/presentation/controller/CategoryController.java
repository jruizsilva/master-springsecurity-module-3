package masterspringsecurity.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.CategoryFacade;
import masterspringsecurity.domain.dto.category.CategoryDto;
import masterspringsecurity.domain.dto.category.request.CategoryRequest;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryFacade categoryFacade;

    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')")
    @GetMapping
    public ResponseEntity<Page<CategoryDto>> findAll(@PageableDefault(size = 2)
                                                     Pageable pageable) {
        return ResponseEntity.ok(categoryFacade.findAll(pageable));
    }

    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long categoryId) {
        return categoryFacade.findById(categoryId)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound()
                                                            .build());
    }

    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(categoryFacade.create(categoryRequest));
    }

    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateOneById(@PathVariable Long categoryId,
                                                     @RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryFacade.update(categoryId,
                                                       categoryRequest));
    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<CategoryDto> disableOneById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryFacade.disableOneById(categoryId));
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
