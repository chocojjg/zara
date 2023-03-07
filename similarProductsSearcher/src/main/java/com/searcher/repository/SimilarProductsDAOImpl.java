package com.searcher.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.searcher.model.Product;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;
@Repository
public class SimilarProductsDAOImpl implements SimilarProductsDAO{
    @Autowired
    RestTemplate restTemplate;
	public SimilarProductsDAOImpl(RestTemplate restTemplate2) {
		this.restTemplate=restTemplate2;
	}
	@Override
	public List<Product> getSimilarProductsContent(ResponseEntity<String[]> similarProductsResponse)throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
	try {
		List<Product> similarProductsDetails = Arrays.stream(similarProductsResponse.getBody())
		        .map(similarProductId -> callRestTemplateForObject(similarProductId))
		        .collect(Collectors.toList());
		return similarProductsDetails;
	}catch(Exception e) {
		if (e  instanceof HttpClientErrorException) {
			String message=e.getMessage();
			if(message.contains("404")) {
				throw new SimilarProductsSearcherNotFoundException();
			}else {
				throw new SimilarProductsSearcherConnectException();
			}
			
		}else {
			throw new SimilarProductsSearcherConnectException();
		}
		
	}
	}
	@Override
	public ResponseEntity<String[]> getSimilarProductsIds(int productId)throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		try {
			ResponseEntity<String[]> similarProductsResponse = restTemplate.getForEntity(
			        "http://localhost:3001/product/{productId}/similarids",
			        String[].class,
			        productId
			);
			return similarProductsResponse;
		}catch(Exception e) {
			if (e  instanceof HttpClientErrorException) {
				String message=e.getMessage();
				if(message.contains("404")) {
					throw new SimilarProductsSearcherNotFoundException();
				}else {
					throw new SimilarProductsSearcherConnectException();
				}
				
			}else {
				throw new SimilarProductsSearcherConnectException();
			}
			
		}

}
	private Product callRestTemplateForObject(String similarProductId) {
		var product= restTemplate.getForObject(
                "http://localhost:3001/product/{productId}",
                Product.class,
                similarProductId);
		return product;
	}
}
