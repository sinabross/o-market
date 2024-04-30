package com.omarket.order.service;

import com.omarket.address.repository.AddressRepository;
import com.omarket.cart.repository.CartRepository;
import com.omarket.global.fixture.AddressFixture.*;
import com.omarket.global.fixture.CartFixture.*;
import com.omarket.global.fixture.PaymentFixture.*;
import com.omarket.global.fixture.ProductFixture.*;
import com.omarket.global.fixture.UserFixture.*;
import com.omarket.order.dto.OrderRequestDto;
import com.omarket.payment.service.PaymentService;
import com.omarket.product.repository.ProductRepository;
import com.omarket.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProductRepository productRepository;

    // TODO(시은): order 테스트코드 재작성
    @DisplayName("장바구니에 담긴 상품을 주문한다.")
    @Test
    public void order() {
//        //given
//        final Optional<Product> FoundProduct = Optional.of(Product1.PRODUCT);
//        final OrderRequestDto dto = OrderRequestDto.builder()
//                .type(Payment1.TYPE)
//                .receiverName(User1.NAME)
//                .receiverPhone(User1.PHONE)
//                .receiverRequest("")
//                .build();
//        given(cartRepository.findByUserId(User1.ID)).willReturn(Arrays.asList(Cart1.CART));
//        given(userRepository.findById(User1.ID)).willReturn(User1.USER);
//        given(addressRepository.findMainContentByUserId(User1.ID)).willReturn(Address1.CONTENT);
//        given(productRepository.findByProductId(Product1.ID)).willReturn(FoundProduct);
//        //when
//        orderService.order(User1.ID, dto);
//        //then
//        then(cartRepository).should(times(1)).deleteCartProducts(Cart1.CART);
    }

    @DisplayName("주문을 요청한 상품이 없는 경우 주문에 실패한다.")
    @Test
    public void NoProductToOrder() {
        //given
        final OrderRequestDto dto = OrderRequestDto.builder()
                .type(Payment1.TYPE)
                .receiverName(User1.NAME)
                .receiverPhone(User1.PHONE)
                .receiverRequest("")
                .build();
        given(cartRepository.findByUserId(User1.ID)).willReturn(Arrays.asList());
        //then
        assertThrows(IllegalArgumentException.class, () -> orderService.order(User1.ID, dto));
    }
}
