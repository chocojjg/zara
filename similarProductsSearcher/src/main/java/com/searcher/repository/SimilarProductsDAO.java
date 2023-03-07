package com.searcher.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.searcher.model.Product;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;

public interface SimilarProductsDAO {

	 List<Product> getSimilarProductsContent(ResponseEntity<String[]> similarProductsResponse)throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException;
	 ResponseEntity<String[]> getSimilarProductsIds(int productId)throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException;

	
}
