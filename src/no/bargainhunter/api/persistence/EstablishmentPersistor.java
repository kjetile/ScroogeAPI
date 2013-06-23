package no.bargainhunter.api.persistence;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import no.bargainhunter.api.model.Establishment;
import no.bargainhunter.api.model.Location;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.Point;

public class EstablishmentPersistor<T extends Establishment> {
	
	public void persistEstablishment(T establishment) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Location location = establishment.getLocation();
		
		Point p = new Point(location.getLatitude(), location.getLongitude());
		Collection<String> cells = GeocellManager.generateGeoCell(p);
		location.setCells(cells);
		
		try {
			pm.makePersistent(establishment);
		} finally {
			pm.close();
		}
	}
}
