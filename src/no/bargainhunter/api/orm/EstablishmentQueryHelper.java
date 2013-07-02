package no.bargainhunter.api.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;

import no.bargainhunter.api.model.Establishment;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.GeocellQuery;
import com.beoui.geocell.model.Point;

public class EstablishmentQueryHelper {
	
	public static final int MAX_RESULTS = 40;

	private PersistenceManager pm;
	
	public EstablishmentQueryHelper(PersistenceManager pm) {
		this.pm = pm;
	}

	public Collection<Establishment> getEstablishments(double latitude, double longitude, double radius) {	
		Point center = new Point(latitude, longitude);
		/*
		List<Object> params = new ArrayList<Object>();
		params.add(Establishment.Type.BAR.name());
		GeocellQuery baseQuery = new GeocellQuery("type == typeParam", "String typeParam", params);
		*/
		GeocellQuery baseQuery = new GeocellQuery();
		List<Establishment> establishments = GeocellManager.proximitySearch(center, MAX_RESULTS, radius, Establishment.class, baseQuery, pm);
		return establishments;
	}
}
