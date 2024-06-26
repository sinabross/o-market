package com.omarket.product.service;

import com.omarket.product.constant.DeliveryTypeEnum;
import com.omarket.product.dto.GetProductsRequest;
import com.omarket.product.dto.SimpleProduct;
import com.omarket.product.domain.Product;
import com.omarket.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<SimpleProduct> getProducts(GetProductsRequest dto){
        DeliveryTypeEnum deliveryType = dto.getDeliveryType();
        boolean isRocket = dto.isRocket();
        int listSize = dto.getListSize();
        int startId = dto.getStart();

        List<Product> products = getProductsByDeliveryType(deliveryType, isRocket, listSize, startId);

        return SimpleProduct.toList(products);
    }

    private List<Product> getProductsByDeliveryType (DeliveryTypeEnum deliveryType, boolean isRocket, int listSize, int startId) {
        List<Product> products = new ArrayList<>();

        switch (deliveryType) {
            case ROCKET:
                products = productRepository.getProductsByIsRocket(true, startId, listSize);
                break;
            case ROCKET_FRESH:
                if (isRocket) {
                    products = productRepository.getProductsByIsRocketAndIsRocketFresh(true, true, startId, listSize);
                } else {
                    products = productRepository.getProductsByIsRocketFresh(true, startId, listSize);
                }
                break;
            case ROCKET_GLOBAL:
                if (isRocket) {
                    products = productRepository.getProductsByIsRocketAndIsRocketGlobal(true, true, startId, listSize);
                } else {
                    products = productRepository.getProductsByIsRocketGlobal(true, startId, listSize);
                }
                break;
        }
        return products;
    }

    public List<SimpleProduct> searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.getProductsByKeyword(keyword);
        return SimpleProduct.toList(products);
    }

    public boolean checkIsProductExist(long id){
        return productRepository.findByProductId(id).isPresent();
    }
}
