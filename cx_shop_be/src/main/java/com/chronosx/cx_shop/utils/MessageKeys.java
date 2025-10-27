package com.chronosx.cx_shop.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MessageKeys {
    LOGIN_SUCCESSFULLY("user.login.login_successfully"),
    LOGIN_FAILED("user.login.login_failed"),
    REGISTER_SUCCESSFULLY("user.register.register_successfully"),
    WRONG_PHONE_PASSWORD("user.login.wrong_phone_password"),
    ROLE_NOT_EXIST("user.login.role_not_exist"),
    PASSWORD_NOT_MATCH("user.register.password_not_match"),
    USER_IS_LOCKED("user.login.user_is_locked"),

    CREATE_CATEGORY_SUCCESSFULLY("category.create_category.create_successfully"),
    DELETE_CATEGORY_SUCCESSFULLY("category.delete_category.delete_successfully"),
    UPDATE_CATEGORY_SUCCESSFULLY("category.update_category.update_successfully"),
    CREATE_CATEGORY_FAILED("category.create_category.create_failed"),

    DELETE_PRODUCT_SUCCESSFULLY("product.delete_product.delete_successfully"),

    DELETE_ORDER_SUCCESSFULLY("order.delete_order.delete_successfully"),
    DELETE_ORDER_DETAIL_SUCCESSFULLY("order.delete_order_detail.delete_successfully"),

    UPLOAD_IMAGES_ERROR_MAX_5_IMAGES("product.upload_images.error_max_5_images"),
    UPLOAD_IMAGES_FILE_LARGE("product.upload_images.file_large"),
    UPLOAD_IMAGES_FILE_MUST_BE_IMAGE("product.upload_images.file_must_be_image");

    String key;
}
