package com.searcher.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.searcher.model.Product;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;
import com.searcher.service.impl.SimilarProductsSearcherServiceImpl;

@RestController
public class SimilarProductsSearcherControler {
	private final RestTemplate restTemplate = new RestTemplate();
	@Autowired private SimilarProductsSearcherServiceImpl similarProductsSearcherService;
	@GetMapping("/similar-products-details/{productId}")
    public ResponseEntity<List<Product>> getSimilarProductsDetails(@PathVariable int productId) {
        // Get the product Ids similar for the given one
		if(productId<=0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}try {
			var response=similarProductsSearcherService.getSimilarProducts(productId);
	        return ResponseEntity.ok(response);
		}catch(SimilarProductsSearcherNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(SimilarProductsSearcherConnectException e) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }


}
