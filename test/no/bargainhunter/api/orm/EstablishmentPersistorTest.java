package no.bargainhunter.api.orm;

import java.util.ArrayList;
import java.util.Collection;

import javax.jdo.PersistenceManager;

import no.bargainhunter.api.model.Establishment;
import no.bargainhunter.api.model.Establishment.Type;
import no.bargainhunter.api.model.MenuItem;
import no.bargainhunter.api.model.PriceTag;
import no.bargainhunter.api.model.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.Point;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class EstablishmentPersistorTest {

	private static final String ESTABLISHMENT_NAME = "Oles Bar";
	private static final double LATITUDE = 50;
	private static final double LONGITUDE = 50;
	private static final Type TYPE = Type.BAR;
	private static final String PRODUCT_NAME1 = "Hansa";
	private static final long START_TIME1 = 1;
	private static final long END_TIME1 = 2;
	
	private final LocalServiceTestHelper helper =
		    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	
	@Before
    public void setUp() {
        helper.setUp();
    }
	
	@After
    public void tearDown() {
        helper.tearDown();
    }
	
	@Test
	public void persistEstablishment() throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Establishment establishment = new Establishment(ESTABLISHMENT_NAME);
		establishment.setLatitude(LATITUDE);
		establishment.setLongitude(LONGITUDE);
		establishment.setType(TYPE);
		Point p = new Point(LATITUDE, LONGITUDE);
		Collection<String> cells = GeocellManager.generateGeoCell(p);
		establishment.setCells(cells);
		
		Product product = new Product();
		product.setName(PRODUCT_NAME1);
		PriceTag price = new PriceTag();
		price.setStartTime(START_TIME1);
		price.setEndTime(END_TIME1);
		MenuItem menuItem = new MenuItem();
		menuItem.setProduct(product);
		menuItem.setPriceTag(price);
		
		Collection<MenuItem> items = new ArrayList<MenuItem>();
		items.add(menuItem);
		establishment.setMenuItems(items);
		
		Establishment persistedEstablishment = pm.makePersistent(establishment);
		Key key = persistedEstablishment.getKey();
		
		Establishment readEstablishment = pm.getObjectById(Establishment.class, key);
		
	}
}
