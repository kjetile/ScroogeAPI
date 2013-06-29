package no.bargainhunter.api.persistence;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.PersistenceAware;

import no.bargainhunter.api.model.Bar;
import no.bargainhunter.api.model.Location;
import no.bargainhunter.api.model.MenuItem;
import no.bargainhunter.api.model.PriceTag;
import no.bargainhunter.api.model.Product;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.Point;

public class BarPersistor {
	
	private PersistenceManager pm;
	
	public BarPersistor() {
		pm = PMF.get().getPersistenceManager();
	}
	
	public void persistEstablishment(Bar bar) {
		
		Location location = bar.getLocation();
		
		Point p = new Point(location.getLatitude(), location.getLongitude());
		Collection<String> cells = GeocellManager.generateGeoCell(p);
		location.setCells(cells);
		
		try {
			Bar establishemnt = pm.makePersistent(bar);
			Location persistedLocation = pm.makePersistent(location);	
			persistMenuItems(bar.getMenuItems());
		} finally {
			pm.close();
		}
	}
	
	private void persistMenuItems(Collection<MenuItem> menuItems) {
		for (MenuItem menuItem : menuItems) {
			persistMenuItem(menuItem);
		}
	}
	
	private void persistMenuItem(MenuItem menuItem) {
		pm.makePersistent(menuItem);
		persistProduct(menuItem.getProduct());
		persistPriceTag(menuItem.getPriceTag());
	}
	
	private void persistProduct(Product product) {
		pm.makePersistent(product);
	}
	
	private void persistPriceTag(PriceTag priceTag) {
		pm.makePersistent(priceTag);
	}
}
