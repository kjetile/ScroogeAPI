package no.bargainhunter.api.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import com.beoui.geocell.model.GeocellQuery;
import com.beoui.geocell.model.Point;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class EstablishmentPersistorTest {

	private static final String ESTABLISHMENT_NAME = "Oles Bar";
	private static final double LATITUDE = 50;
	private static final double LONGITUDE = 50;
	private static final double EPSILON = 0.001;
	private static final Type TYPE = Type.BAR;
	private static final String PRODUCT_NAME1 = "Hansa";
	private static final long START_TIME1 = 1;
	private static final long END_TIME1 = 2;
	
	private static final double RADIUS = 10;
	private static final int MAX_RESULTS = 40;

	
	private PersistenceManager pm;
	
	private final LocalServiceTestHelper helper =
		    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	
	@Before
    public void setUp() {
        helper.setUp();
        pm = PMF.get().getPersistenceManager();
    }
	
	@After
    public void tearDown() {
        helper.tearDown();
    }
	
	@Test
	public void persistEstablishment() throws Exception {
		Key key = persistTestEntity();
				
		Establishment readEstablishment = pm.getObjectById(Establishment.class, key);
		
		assertEstablishmentValid(readEstablishment);
		
		deleteEstablishment(readEstablishment);
	}
	
	@Test
	public void persistEstablishmentAndDoGeocellQuery() {
		persistTestEntity();
		GeocellQuery baseQuery = new GeocellQuery();
		Point center = new Point(LATITUDE, LONGITUDE);
		Collection<Establishment> establishments = GeocellManager.proximitySearch(center, MAX_RESULTS, RADIUS, Establishment.class, baseQuery, pm);
		
		Iterator<Establishment> establishmentIterator = establishments.iterator();
		assertTrue(establishmentIterator.hasNext());
		Establishment establishment = establishmentIterator.next();
		
		assertEstablishmentValid(establishment);
		
		deleteEstablishment(establishment);
	}
	
	private Key persistTestEntity() {
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
		return key;
	}
	
	private void assertEstablishmentValid(Establishment establishment) {
		assertTrue(establishment.getName().equals(ESTABLISHMENT_NAME));
		assertEquals(establishment.getLatitude(), LATITUDE, EPSILON);
		assertEquals(establishment.getLongitude(), LONGITUDE, EPSILON);

		Iterator<MenuItem> menuIterator = establishment.getMenuItems()
				.iterator();
		assertTrue(menuIterator.hasNext());
		MenuItem menuItem1 = menuIterator.next();
		assertTrue(menuItem1 != null);
		Product readProduct = menuItem1.getProduct();
		assertTrue(readProduct.getName().equals(PRODUCT_NAME1));
	}
	
	private void deleteEstablishment(Establishment establishment) {
		pm.deletePersistent(establishment);
	}
}
