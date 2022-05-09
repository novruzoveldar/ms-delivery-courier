package com.guavapay.integration.error;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.error.FeignExceptionConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourierOrderException extends BaseException {

    private String errorReference;
    private String code;
    private String message;
    private Integer status;

    @FeignExceptionConstructor
    public CourierOrderException(String body, Response response) throws IOException {
        super(String.valueOf(response.status()), body);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.readValue(response.body().asInputStream(), CourierOrderException.class);
        this.status = response.status();
    }
}
