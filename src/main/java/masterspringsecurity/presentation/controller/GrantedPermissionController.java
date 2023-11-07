package masterspringsecurity.presentation.controller;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.GrantedPermissionFacade;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class GrantedPermissionController {
    private final GrantedPermissionFacade grantedPermissionFacade;

    @GetMapping
    public ResponseEntity<Page<GrantedPermissionEntity>> findAll(Pageable pageable) {
        return ResponseEntity.ok(grantedPermissionFacade.findAll(pageable));
    }
}
