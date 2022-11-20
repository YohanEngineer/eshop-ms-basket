package com.episen.basket.resource;

import com.episen.basket.model.Basket;
import com.episen.basket.model.Product;
import com.episen.basket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "baskets", produces = {"application/json"})
@RequiredArgsConstructor
public class BasketResource {
    private final BasketService basketService;

    @GetMapping()
    public ResponseEntity<List<Basket>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(basketService.getBaskets(request, response), HttpStatus.OK);

    }

    @GetMapping("{userEmail}")
    public ResponseEntity<Basket> getOne(@PathVariable("userEmail") String userEmail, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(basketService.getBasketByUserEmail(userEmail, request, response), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Basket> add(@RequestBody Basket basket, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(basketService.saveBasket(basket, request, response), HttpStatus.CREATED);
    }

    @PostMapping("userEmail")
    public ResponseEntity<Product> add(@PathVariable("userEmail") String userEmail, @RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(basketService.addProductToBasket(userEmail, product, request, response), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Basket> update(@RequestBody Basket basket, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(basketService.updateBasket(basket, request, response), HttpStatus.OK);
    }


    @DeleteMapping("{userEmail}")
    public ResponseEntity<Basket> delete(@PathVariable("userEmail") String userEmail, HttpServletRequest request, HttpServletResponse response) {
        basketService.removeBasket(userEmail, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
