package com.searcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class SimilarProductsSearcherApplicationTests {
	 @Autowired
	    private MockMvc mvc;
	@Test
	void contextLoads() {
	}
	
	@Test
	public void itShouldHaveOkStatus() throws Exception {
		var id=1;
		mvc.perform(get("/similar-products-details/"+id)).andExpect(status().isOk());
	}
	@Test
	public void itShouldreturnok2() throws Exception {
		var id=2;
		mvc.perform(get("/similar-products-details/"+id)).andExpect(status().isOk());
	}
	@Test
	public void itShouldreturnNoFound() throws Exception {
		var id=10000;
		mvc.perform(get("/similar-products-details/"+id)).andExpect(status().isNoContent());
	}
	@Test
	public void itShouldreturnErrorKo() throws Exception {
		var id=-1;
		mvc.perform(get("/similar-products-details/"+id)).andExpect(status().isBadRequest());
	}
	@Test
	public void itShouldreturnInvalid() throws Exception {
		String id="number";
		mvc.perform(get("/similar-products-details/"+id)).andExpect(status().isBadRequest());
	}

}
