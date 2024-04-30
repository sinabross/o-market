package com.omarket.product.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class SaveToCartRequest {

	@NotNull
	private long productId;

	@NotNull
	private int productNum;

	@Builder
	public SaveToCartRequest(long productId, int productNum){
		this.productId = productId;
		this.productNum = productNum;
	}
}