package no.bargainhunter.api.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;

import no.bargainhunter.api.utils.ObjectMapperSingelton;

import org.junit.Test;

public class BarTest {

	@Test
	public void testDeserializeBar() throws Exception {
		String barJson = "{\n    \"name\": \"En bar\",\n    \"location\": {\n        \"latitude\": 1,\n        \"longitude\": 2\n    },\n    \"menuItems\": [\n        {\n            \"product\": {\n                \"name\": \"Ringnes\"\n            },\n            \"priceTag\": {\n                \"price\": 58.5,\n                \"startTime\": 0,\n                \"endTime\": 0\n            }\n        },\n        {\n            \"product\": {\n                \"name\": \"Hansa\"\n            },\n            \"priceTag\": {\n                \"price\": 60,\n                \"startTime\": 0,\n                \"endTime\": 0\n            }\n        }\n    ]\n}";
		Establishment bar = ObjectMapperSingelton.getObjectMapper().readValue(barJson, Establishment.class);
		assertNotNull(bar);
		assertEquals("En bar", bar.getName());
		assertEquals(1, bar.getLatitude(), 0.001);
		assertEquals(2, bar.getLongitude(), 0.001);		
	}
	
	@Test
	public void testSerializeBar() throws Exception {
		Establishment bar = new Establishment("En bar");
		bar.setLatitude(1);
		bar.setLongitude(2);
		MenuItem menuItem = new MenuItem();
		Product p = new Product();
		p.setName("Hansa");
		PriceTag price = new PriceTag();
		price.setPrice(10);
		menuItem.setProduct(p);
		menuItem.setPriceTag(price);
		Collection<MenuItem> menuItems = new ArrayList<MenuItem>();
		menuItems.add(menuItem);
		bar.setMenuItems(menuItems);
		
		String json = ObjectMapperSingelton.getObjectMapper().writeValueAsString(bar);
		assertEquals("{\"name\":\"En bar\",\"location\":{\"latitude\":1.0,\"longitude\":2.0},\"menuItems\":[{\"product\":{\"name\":\"Hansa\"},\"priceTag\":{\"price\":10.0,\"startTime\":0,\"endTime\":0}}]}",  json);
	}
	
	@Test
	public void testSerializeProduct() throws Exception {
		Product p = new Product();
		p.setName("test");
		
		String json = ObjectMapperSingelton.getObjectMapper().writeValueAsString(p);
		assertEquals("{\"name\":\"test\"}", json);		
	}
}
