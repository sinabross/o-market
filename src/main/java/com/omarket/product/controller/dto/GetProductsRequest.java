package com.omarket.product.dto;

import com.omarket.product.constant.DeliveryTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class GetProductsRequest {

    @NotNull
    private DeliveryTypeEnum deliveryType;

    @Nullable
    private boolean rocket;

    @NotNull
    @Min(1)
    private int start;

    @NotNull
    @Min(1)
    @Max(120)
    private int listSize;

    @Builder
    public GetProductsRequest(DeliveryTypeEnum deliveryType, boolean rocket, int start, int listSize) {
        this.deliveryType = deliveryType;
        this.rocket = rocket;
        this.start = start;
        this.listSize = listSize;
    }
}
