package com.guavapay.integration.client;

import com.guavapay.integration.config.CourierOrderFeignConfig;
import com.guavapay.integration.error.CourierOrderException;
import com.guavapay.model.dto.CourierOrderHistoryDto;
import com.guavapay.model.request.CourierOrderFilter;
import com.guavapay.model.request.OrderStateChangeRequest;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(
        name = "ms-courier-order",
        url = "${application.service.courier-order.url}",
        path = "courier/order",
        primary = false,
        configuration = {CourierOrderFeignConfig.class}
)
public interface CourierOrderClient {

    @ErrorHandling(defaultException = CourierOrderException.class)
    @PostMapping(value = "/change/state")
    ResponseEntity<Object> changeState(@Valid @RequestBody OrderStateChangeRequest stateChangeRequest);

    @ErrorHandling(defaultException = CourierOrderException.class)
    @PostMapping(value = "/history", consumes = {"application/json"}, produces = {"application/json"})
    List<CourierOrderHistoryDto> orderHistory(@Valid @RequestBody CourierOrderFilter orderFilter);

    @ErrorHandling(defaultException = CourierOrderException.class)
    @GetMapping(value = "/detail")
    CourierOrderHistoryDto orderDetail(@RequestParam(name = "id") Long parcelId);

}
