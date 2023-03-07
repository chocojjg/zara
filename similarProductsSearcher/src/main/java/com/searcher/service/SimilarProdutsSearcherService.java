package com.searcher.service;

import java.util.List;

import com.searcher.model.Product;
import com.searcher.service.exception.SimilarProductsSearcherBadRequestDataException;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;

public interface SimilarProdutsSearcherService {

	public List<Product> getSimilarProducts(int productId)throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException,SimilarProductsSearcherBadRequestDataException;
}
