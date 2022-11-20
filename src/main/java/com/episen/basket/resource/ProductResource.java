package com.episen.basket.resource;

import com.episen.basket.model.Product;
import com.episen.basket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "products", produces = {"application/json"})
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getAll(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(productService.getProducts(request, response), HttpStatus.OK);

    }

    @GetMapping("{label}")
    public ResponseEntity<Product> getOne(@PathVariable("label") String label, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(productService.getProductByLabel(label, request, response), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Product> add(@RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(productService.saveProduct(product, request, response), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(productService.updateProduct(product, request, response), HttpStatus.OK);
    }


    @DeleteMapping("{productname}")
    public ResponseEntity<Product> delete(@PathVariable("productname") String productname, HttpServletRequest request, HttpServletResponse response) {
        productService.removeProduct(productname, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
