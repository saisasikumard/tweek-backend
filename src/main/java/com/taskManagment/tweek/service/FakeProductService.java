package com.taskManagment.tweek.service;

import com.taskManagment.tweek.customException.ProductNotFoundException;
import com.taskManagment.tweek.dto.FakeStoreProductDto;
import com.taskManagment.tweek.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeProductService {

    RestTemplate restTemplate;
    public FakeProductService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public Product getProductById(Long id) throws ProductNotFoundException {
//        int x = 1/0;
//        throw new RuntimeException("Something went wrong in service layer");
//        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS",
//                "PRODUCTS_" + id);

//        if(product != null) {
//            // Cache hit
//            return product;
//        }

        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        if(fakeStoreProductDto == null) {
            throw new ProductNotFoundException(id + "Product with id: " + id + " not found");
        }

        Product product = convertFakeStoreDtoToProduct(fakeStoreProductDto);
        //redisTemplate.opsForHash().put("PRODUCTS", "PRODUCTS_" + id, product);
        return product;
    }
    private Product convertFakeStoreDtoToProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId((int)fakeStoreProductDto.getId());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImage(fakeStoreProductDto.getImage());

       // Category category = new Category();
        //category.setDescription(fakeStoreProductDto.getCategory());
        //product.setCategory(category);

        return product;
    }

}
