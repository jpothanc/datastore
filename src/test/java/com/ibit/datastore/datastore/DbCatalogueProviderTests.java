package com.ibit.datastore.datastore;

import com.ibit.datastore.services.CatalogueProvider;
import com.ibit.datastore.models.QueryRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
class DbCatalogueProviderTests {


	@Autowired
	private CatalogueProvider catalogueProvider;

	@Test
	void contextLoads() {

	}
//	@ParameterizedTest
//	@CsvSource({
//			"Trading, Users"
//	})
//	public void when_CatalogueItemIsQueried_ShouldReturnValidQueryResponse(String catalogue, String catalogueItem)
//	{
//		// Arrange
//		var request = new QueryRequest();
//		request.setCatalogueItem(catalogueItem);
//		request.setCatalogue(catalogue);
//		// Act
//		var res = catalogueProvider.queryCatalogueItem(request).join();
//		// Assert
//		assertEquals(res.getCatalogueItem(), catalogueItem);
//	}

}
