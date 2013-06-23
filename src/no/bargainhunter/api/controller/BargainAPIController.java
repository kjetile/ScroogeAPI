package no.bargainhunter.api.controller;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletResponse;

import no.bargainhunter.api.model.Bar;
import no.bargainhunter.api.model.Product;
import no.bargainhunter.api.persistence.PMF;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resources")
public class BargainAPIController {

	private static final long serialVersionUID = 1L;  

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
	// curl -i -H "Accept: text/plain" -H "Content-Type: application/json" -X POST -d "{\"name\":\"En bar\",\"location\":{\"latitude\":1.0,\"longitude\":2.0},\"menuItems\":[{\"product\":{\"name\":\"Hansa\"},\"priceTag\":{\"price\":10.0,\"startTime\":0,\"endTime\":0}}]}"  http://127.0.0.1:8888/api/resources/bar
	@RequestMapping(value = "/bar", method = RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public String addBar(@RequestBody final Bar bar,
			HttpServletResponse response, Model model) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			pm.makePersistent(bar);
		} finally {
			pm.close();
		}
		
		return "OK";
	}
	
	@RequestMapping(value = "/bars", method = RequestMethod.GET)
	public Collection<Bar> getBars(@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double radius) {
				
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Bar.class);
 
		Collection<Bar> bars = null;
 		
		try {
			
			bars = pm.detachCopyAll((Collection<Bar>) q.execute());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {

			q.closeAll();
			pm.close();
		}
		return bars;
	}
}
