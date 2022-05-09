package com.guavapay.controller;

import com.guavapay.integration.client.CourierOrderClient;
import com.guavapay.model.dto.CourierOrderHistoryDto;
import com.guavapay.model.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "courier/order")
public class CourierController {

    private final CourierOrderClient courierOrderClient;

    @PostMapping(value = "/change/state")
    public ResponseEntity<Object> changeState(@Valid @RequestBody OrderStateChangeRequest stateChangeRequest) {
        return courierOrderClient.changeState(stateChangeRequest);
    }

    @PostMapping(value = "/history", consumes = {"application/json"}, produces = {"application/json"})
    public List<CourierOrderHistoryDto> orderHistory(@Valid @RequestBody CourierOrderFilter orderFilter) {
        return courierOrderClient.orderHistory(orderFilter);
    }

    @GetMapping(value = "/detail")
    public CourierOrderHistoryDto orderDetail(@RequestParam(name = "id") Long parcelId) {
        return courierOrderClient.orderDetail(parcelId);
    }

}
