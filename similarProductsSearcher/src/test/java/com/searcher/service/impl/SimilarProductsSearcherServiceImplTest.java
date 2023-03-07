package com.searcher.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.searcher.model.Product;
import com.searcher.repository.SimilarProductsDAO;
import com.searcher.service.SimilarProdutsSearcherService;
import com.searcher.service.exception.SimilarProductsSearcherBadRequestDataException;
import com.searcher.service.exception.SimilarProductsSearcherConnectException;
import com.searcher.service.exception.SimilarProductsSearcherNotFoundException;
@SpringBootTest
class SimilarProductsSearcherServiceImplTest {
	
	 @Mock
	private SimilarProductsDAO similarProductsDao;
	 @Mock
	private SimilarProdutsSearcherService similarProductsSearcherService;

	@BeforeEach
	 void setUpBeforeClass() throws Exception {
		similarProductsSearcherService= new SimilarProductsSearcherServiceImpl(similarProductsDao);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNotFound() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException {
		
			Mockito.when(similarProductsDao.getSimilarProductsIds(0)).thenThrow(new SimilarProductsSearcherNotFoundException());
			assertThrows(SimilarProductsSearcherNotFoundException.class,() ->{ similarProductsSearcherService.getSimilarProducts(0);});
	}
	@Test
	void testSearckOk() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException,SimilarProductsSearcherBadRequestDataException {
		String[] stringList= {"1","2"};
		List<Product> productList= new ArrayList();
		Product product= new Product();
		product.setId("1");
		product.setName("Dress");
		product.setPrice(1.0);
		productList.add(product);
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		Mockito.when(similarProductsDao.getSimilarProductsIds(1)).thenReturn(response);
		Mockito.when(similarProductsDao.getSimilarProductsContent(response)).thenReturn(productList);
		var list=similarProductsSearcherService.getSimilarProducts(1);
		assertEquals(list.size(),productList.size());	
		assertEquals(list.get(0).getName(),productList.get(0).getName());
		assertEquals(list.get(0).getPrice(),productList.get(0).getPrice());
		assertEquals(list.get(0).getId(),productList.get(0).getId());
	}
	@Test
	void testSecondSearckNOOk() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException ,SimilarProductsSearcherBadRequestDataException{
		String[] stringList= {"1","2"};
		List<Product> productList= new ArrayList();
		Product product= new Product();
		product.setId("1");
		product.setName("Dress");
		product.setPrice(1.0);
		productList.add(product);
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		Mockito.when(similarProductsDao.getSimilarProductsIds(1)).thenReturn(response);
		Mockito.when(similarProductsDao.getSimilarProductsContent(response)).thenThrow(new SimilarProductsSearcherNotFoundException());

		assertThrows(SimilarProductsSearcherNotFoundException.class,() ->{ similarProductsSearcherService.getSimilarProducts(1);});	

	}
	@Test
	void testSecondSearckNOOkConnectException() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException ,SimilarProductsSearcherBadRequestDataException{
		String[] stringList= {"1","2"};
		List<Product> productList= new ArrayList();
		Product product= new Product();
		product.setId("1");
		product.setName("Dress");
		product.setPrice(1.0);
		productList.add(product);
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		Mockito.when(similarProductsDao.getSimilarProductsIds(1)).thenReturn(response);
		Mockito.when(similarProductsDao.getSimilarProductsContent(response)).thenThrow(new SimilarProductsSearcherConnectException());

		assertThrows(SimilarProductsSearcherConnectException.class,() ->{ similarProductsSearcherService.getSimilarProducts(1);});	

	}	@Test
	void testIncorrectData() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException ,SimilarProductsSearcherBadRequestDataException{
		String[] stringList= {"1","2"};
		List<Product> productList= new ArrayList();
		Product product= new Product();
		product.setId("1");
		product.setName("Dress");
		product.setPrice(1.0);
		productList.add(product);
		ResponseEntity<String[]> response= ResponseEntity.ok().body(stringList);
		Mockito.when(similarProductsDao.getSimilarProductsIds(1)).thenReturn(response);
		Mockito.when(similarProductsDao.getSimilarProductsContent(response)).thenThrow(new SimilarProductsSearcherConnectException());

		assertThrows(SimilarProductsSearcherBadRequestDataException.class,() ->{ similarProductsSearcherService.getSimilarProducts(-1);});	

	}
	
	@Test
	void testIncorrectReturnCode() throws SimilarProductsSearcherNotFoundException, SimilarProductsSearcherConnectException ,SimilarProductsSearcherBadRequestDataException{
		String[] stringList= {"1","2"};
		List<Product> productList= new ArrayList();
		Product product= new Product();
		product.setId("1");
		product.setName("Dress");
		product.setPrice(1.0);
		productList.add(product);
		ResponseEntity<String[]> response= ResponseEntity.unprocessableEntity().body(stringList);
		Mockito.when(similarProductsDao.getSimilarProductsIds(1)).thenReturn(response);

		assertThrows(SimilarProductsSearcherNotFoundException.class,() ->{ similarProductsSearcherService.getSimilarProducts(1);});	

	}
	

}
