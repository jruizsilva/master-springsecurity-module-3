package masterspringsecurity.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public enum Role {
    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),
    ADMINISTRATOR(List.of(Permission.READ_ALL_PRODUCTS,
                          Permission.SAVE_ONE_PRODUCT));

    private final List<Permission> permissions;
}
