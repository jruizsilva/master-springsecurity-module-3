package masterspringsecurity.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public enum RoleEnum {
    ADMINISTRATOR(List.of(
            RolePermissionEnum.READ_ALL_PRODUCTS,
            RolePermissionEnum.READ_ONE_PRODUCT,
            RolePermissionEnum.CREATE_ONE_PRODUCT,
            RolePermissionEnum.UPDATE_ONE_PRODUCT,
            RolePermissionEnum.DISABLE_ONE_PRODUCT,

            RolePermissionEnum.READ_ALL_CATEGORIES,
            RolePermissionEnum.READ_ONE_CATEGORY,
            RolePermissionEnum.CREATE_ONE_CATEGORY,
            RolePermissionEnum.UPDATE_ONE_CATEGORY,
            RolePermissionEnum.DISABLE_ONE_CATEGORY,

            RolePermissionEnum.READ_MY_PROFILE
    )),
    ASSISTANT_ADMINISTRATOR(List.of(
            RolePermissionEnum.READ_ALL_PRODUCTS,
            RolePermissionEnum.READ_ONE_PRODUCT,
            RolePermissionEnum.UPDATE_ONE_PRODUCT,

            RolePermissionEnum.READ_ALL_CATEGORIES,
            RolePermissionEnum.READ_ONE_CATEGORY,
            RolePermissionEnum.UPDATE_ONE_CATEGORY,

            RolePermissionEnum.READ_MY_PROFILE)),
    CUSTOMER(List.of(RolePermissionEnum.READ_MY_PROFILE));

    private final List<RolePermissionEnum> rolePermissionEnums;
}
