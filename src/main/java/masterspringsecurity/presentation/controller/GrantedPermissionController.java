package masterspringsecurity.presentation.controller;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.GrantedPermissionFacade;
import masterspringsecurity.domain.dto.grantedpermission.GrantedPermissionDto;
import masterspringsecurity.domain.dto.grantedpermission.request.GrantedPermissionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/granted-permissions")
@RequiredArgsConstructor
public class GrantedPermissionController {
    private final GrantedPermissionFacade grantedPermissionFacade;

    @GetMapping
    public ResponseEntity<Page<GrantedPermissionDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(grantedPermissionFacade.findAll(pageable));
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<GrantedPermissionDto> findById(@PathVariable Long permissionId) {
        return ResponseEntity.ok(grantedPermissionFacade.findById(permissionId));
    }

    @PostMapping
    public ResponseEntity<GrantedPermissionDto> createOne(@RequestBody GrantedPermissionRequest grantedPermissionRequest) {
        return ResponseEntity.ok(grantedPermissionFacade.createOne(grantedPermissionRequest));
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long permissionId) {
        return ResponseEntity.ok(grantedPermissionFacade.deleteOneById(permissionId));
    }
}
