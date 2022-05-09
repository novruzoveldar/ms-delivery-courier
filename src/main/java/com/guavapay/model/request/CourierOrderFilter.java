package com.guavapay.model.request;

import com.guavapay.model.type.DeliveryState;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierOrderFilter {

    @NotNull
    private Long courierId;
    private List<DeliveryState> states;
    private Date from;
    private Date to;
    private int page;
    private int limit;
}
