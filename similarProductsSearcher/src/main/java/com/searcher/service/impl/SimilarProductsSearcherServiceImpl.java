package com.searcher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.searcher.model.Product;
import com.searcher.repository.SimilarProductsDAO;
import com.searcher.service.SimilarProdutsSearcherService;
import com.searcher.service.exception.SimilarProductsSearcherBadRequestDataException;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;

@Service
public class SimilarProductsSearcherServiceImpl implements SimilarProdutsSearcherService {
    @Autowired
     SimilarProductsDAO similarProductsDAO;
  
	public SimilarProductsSearcherServiceImpl(SimilarProductsDAO similarProductsDao) {
		this.similarProductsDAO=similarProductsDao;
	}

	@Override
	public List<Product> getSimilarProducts(int productId) throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException, SimilarProductsSearcherBadRequestDataException{
		try {
			if (productId<0 || productId>Integer.MAX_VALUE) {
				throw new SimilarProductsSearcherBadRequestDataException();
			}
			ResponseEntity<String[]> similarProductsResponse = similarProductsDAO.getSimilarProductsIds(productId);
			if (similarProductsResponse.getStatusCode().equals(HttpStatus.OK)) {
				 List<Product> similarProductsDetails = similarProductsDAO.getSimilarProductsContent(similarProductsResponse);
				    if(similarProductsDetails.isEmpty()) {
				        	throw new SimilarProductsSearcherNotFoundException();
				      }
				 return similarProductsDetails;
			}else {
				throw new SimilarProductsSearcherNotFoundException();
			}

	}catch(SimilarProductsSearcherNotFoundException  | SimilarProductsSearcherConnectException ex) {
			throw ex;
		}
	}
}
