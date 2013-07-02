package no.bargainhunter.api.orm;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import no.bargainhunter.api.model.Establishment;
import no.bargainhunter.api.model.MenuItem;
import no.bargainhunter.api.model.PriceTag;
import no.bargainhunter.api.model.Product;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.Point;

public class EstablishmentPersistor {
	
	private PersistenceManager pm;
	
	public EstablishmentPersistor() {
		pm = PMF.get().getPersistenceManager();
	}
	
	public void persistEstablishment(Establishment bar) {
		
		Point p = new Point(bar.getLatitude(), bar.getLongitude());
		Collection<String> cells = GeocellManager.generateGeoCell(p);
		bar.setCells(cells);
		
		try {
			Establishment establishemnt = pm.makePersistent(bar);
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
