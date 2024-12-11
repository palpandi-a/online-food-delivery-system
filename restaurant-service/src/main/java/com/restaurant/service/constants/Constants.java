package com.restaurant.service.constants;

public class Constants {

    public static final String TABLE_NAME = "restaurant_details";

    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static final String PHONE_NO_REGEX = "^(\\+[0-9]{1,2})*[0-9]{10,12}$";

    public static final String UNPROCESSABLE_ENTITY_ERROR_MESSAGE = "unprocessableEntity";

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "internalServerError";

    public static final String BAD_REQUEST_ERROR_MESSAGE = "badRequest";

    public static final String INVALID_PAYLOAD = "Invalid payload";

    public static final String INVALID_NAME = "Invalid name";

    public static final String INVALID_EMAIL = "Invalid email";

    public static final String INVALID_PHONE_NO = "Invalid phoneNo";

    public static final String INVALID_ADDRESS = "Invalid address";

    public static final String INVALID_OPERATING_HOURS = "Invalid operatingHours";

    public static final String TIME_VALIDATE_REGEX = "^(0?[1-9]|1[0-2]):([0-5][0-9])( ?[APap][Mm])?$";

}
