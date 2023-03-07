package com.searcher.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.searcher.model.Product;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;
@SpringBootTest
class SimilarProductsDAOImplTest {

	 @Mock
	 RestTemplate template;
	 private SimilarProductsDAO dao;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		 dao= new SimilarProductsDAOImpl(template);
		 
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSimilarProductsIds() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		String[] stringList= {"1","2"};
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		var similarProductId=1;
		
		Mockito.when(template.getForEntity(
		        "http://localhost:3001/product/{productId}/similarids",
		        String[].class,
		        similarProductId
		)).thenReturn(response);
		var responseSimilarProducts= dao.getSimilarProductsIds(similarProductId);
		assertEquals(responseSimilarProducts.getBody().toString(),responseSimilarProducts.getBody().toString());
	}
	@Test
	void testSimilarProductsIdsException() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		String[] stringList= {"1","2"};
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		var similarProductId=1;
		Mockito.when(template.getForEntity(
		        "http://localhost:3001/product/{productId}/similarids",
		        String[].class,
		        similarProductId
		)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "NOT_FOUND"));
		assertThrows(SimilarProductsSearcherNotFoundException.class,() ->{  dao.getSimilarProductsIds(similarProductId);});	
	}
	@Test
	void testSimilarProductsIdsDifferentException() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		String[] stringList= {"1","2"};
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		var similarProductId=1;
		HttpStatusCodeException notFoundException = new HttpStatusCodeException(
                HttpStatus.NOT_FOUND, "El recurso solicitado no se ha encontrado") {

					private static final long serialVersionUID = 1L;
        };
		Mockito.when(template.getForEntity(
		        "http://localhost:3001/product/{productId}/similarids",
		        String[].class,
		        similarProductId
		)).thenThrow(notFoundException);
		assertThrows(SimilarProductsSearcherConnectException.class,() ->{  dao.getSimilarProductsIds(similarProductId);});	
	}
	@Test
	void testSimilarProductsContentException() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		String[] stringList= {"1"};
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		var similarProductId="1";

		HttpStatusCodeException notFoundException = new HttpStatusCodeException(
                HttpStatus.NOT_FOUND, "El recurso solicitado no se ha encontrado") {

					private static final long serialVersionUID = 1L;
        };
		Mockito.when(template.getForObject(
                "http://localhost:3001/product/{productId}",
                Product.class,
                similarProductId
		)).thenThrow(notFoundException);
		assertThrows(SimilarProductsSearcherConnectException.class,() ->{  dao.getSimilarProductsContent(response);});	
	
	}
	@Test
	void testSimilarProductsContentClientError() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		String[] stringList= {"1"};
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		var similarProductId="1";

		Mockito.when(template.getForObject(
                "http://localhost:3001/product/{productId}",
                Product.class,
                similarProductId
		)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "NOT_FOUND"));
		assertThrows(SimilarProductsSearcherNotFoundException.class,() ->{  dao.getSimilarProductsContent(response);});	
	
	}
	@Test
	void testSimilarProductsContent() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		String[] stringList= {"1"};
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		var similarProductId="1";
		Product product= new Product();
		product.setId("1");
		product.setName("Dress");
		product.setPrice(1.0);
		var productList= new ArrayList<Product>();
		productList.add(product);
		Mockito.when(template.getForObject(
                "http://localhost:3001/product/{productId}",
                Product.class,
                similarProductId
        )).thenReturn(product);
		var responseSimilarProducts= dao.getSimilarProductsContent(response);
		assertEquals(responseSimilarProducts.size(),productList.size());	
		assertEquals(responseSimilarProducts.get(0).getName(),product.getName());
		assertEquals(responseSimilarProducts.get(0).getPrice(),product.getPrice());
		assertEquals(responseSimilarProducts.get(0).getId(),product.getId());
	}

}
