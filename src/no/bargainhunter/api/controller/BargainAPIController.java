package no.bargainhunter.api.controller;

import java.util.Collection;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletResponse;

import no.bargainhunter.api.model.Establishment;
import no.bargainhunter.api.model.Product;
import no.bargainhunter.api.orm.EstablishmentPersistor;
import no.bargainhunter.api.orm.EstablishmentQueryHelper;
import no.bargainhunter.api.orm.PMF;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resources")
public class BargainAPIController {

	private static final long serialVersionUID = 1L;  
	
	private static final Logger log = Logger.getLogger(BargainAPIController.class.getName());

	protected final Log logger = LogFactory.getLog(getClass());
	
	// accepts this: {"name":"test"}
	// example request:
	// curl -i -H "Accept: text/plain" -H "Content-Type: application/json" -X POST -d '{"name":"test"}'  http://127.0.0.1:8888/api/resources/product
	@RequestMapping(value = "/product", method = RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public String addProduct(@RequestBody final Product product,
			HttpServletResponse response, Model model) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			pm.makePersistent(product);
		} finally {
			pm.close();
		}
		
		return "OK";
	}
	
	// example request:
	// curl --H "Accept: text/plain" -H "Content-Type: application/json" -X POST -d "{\"name\": \"Kjetils bar5\",\"type\": \"BAR\",\"latitude\": 1,\"longitude\": 2,\"menuItems\": [{\"product\": {\"name\": \"Hansa\"},\"priceTag\": {\"price\": 10,\"startTime\": 0,           \"endTime\": 0}}]}"
	@RequestMapping(value = "/bar", method = RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public String addBar(@RequestBody final Establishment bar,
			HttpServletResponse response, Model model) {
		
		EstablishmentPersistor establishmentPersistor = new EstablishmentPersistor();
		establishmentPersistor.persistEstablishment(bar);
		
		return "OK";
	}
	
	@RequestMapping(value = "/bar", method = RequestMethod.GET)
	public Collection<Establishment> getBars(@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double radius) {
				
		PersistenceManager pm = PMF.get().getPersistenceManager();
		EstablishmentQueryHelper establishmentQuery = new EstablishmentQueryHelper(pm);
		Collection<Establishment> establishments = null;

		establishments = establishmentQuery.getEstablishments(latitude, longitude, radius);
		pm.close();
		return establishments; 		
	}
	
	 @RequestMapping(value = "/bar", method = RequestMethod.DELETE )
     public void deleteAllBars()  {

		 PersistenceManager pm = PMF.get().getPersistenceManager();
		 Query q = pm.newQuery(Establishment.class);
	 
		 Collection<Establishment> bars = null;
	 		
		 try {
				
			bars = (Collection<Establishment>) q.execute();
			
			// If an object has fields containing child objects that are also 
			// persistent, the child objects are also deleted
			// https://developers.google.com/appengine/docs/java/datastore/jdo/creatinggettinganddeletingdata
			
			pm.deletePersistentAll(bars);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			q.closeAll();
			pm.close();
		}
     }
	 
	 @ExceptionHandler(Exception.class) 
	 public void handleExceptions(Exception anExc) {
		 anExc.printStackTrace(); // do something better than this ;)
	 }
}
