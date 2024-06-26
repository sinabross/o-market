package com.omarket.product.service;


import com.omarket.global.fixture.ProductFixture.*;
import com.omarket.product.dto.GetProductsRequest;
import com.omarket.product.domain.Product;
import com.omarket.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.omarket.product.constant.DeliveryTypeEnum.*;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("배송 유형이 로켓이면 로켓 배송 가능한 상품 목록을 가져온다.")
    @Test
    public void getProductsByIsRocket() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsRocket(any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("배송 유형이 로켓 프레쉬면 로켓 프레쉬 배송 가능한 상품 목록을 가져온다.")
    @Test
    public void getProductsByIsRocketFresh() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET_FRESH)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsRocketFresh(any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("배송 유형이 로켓 직구면 로켓 직구 배송 가능한 상품 목록을 가져온다.")
    @Test
    public void getProductsByIsRocketGlobal() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET_GLOBAL)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsRocketGlobal(any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("배송 유형이 로켓 프레쉬이고 로켓 배송도 가능해야 하면 로켓 프레쉬와 로켓 배송 모두 가능한 상품 전체를 가져온다.")
    @Test
    public void getProductsByIsRocketAndIsRocketFresh() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET_FRESH)
                .rocket(true)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsRocketAndIsRocketFresh(any(Boolean.class), any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("배송 유형이 로켓 직구이고 로켓 배송도 가능해야 하면 로켓 직구와 로켓 배송 모두 가능한 상품 전체를 가져온다.")
    @Test
    public void getProductsByIsRocketAndIsRocketGlobal() {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET_GLOBAL)
                .rocket(true)
                .start(1)
                .listSize(10)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).getProductsByIsRocketAndIsRocketGlobal(any(Boolean.class), any(Boolean.class), any(Integer.class), any(Integer.class));
    }

    @DisplayName("키워드를 포함하는 상품 목록을 가져온다.")
    @Test
    public void searchProductsByKeyword() {
        // given
        final String keyword = "1";
        final List<Product> products = Arrays.asList(Product1.PRODUCT);
        given(productRepository.getProductsByKeyword(keyword))
                .willReturn(products);

        // then
        assertThat(productRepository.getProductsByKeyword(keyword)).isEqualTo(products);
    }
}
