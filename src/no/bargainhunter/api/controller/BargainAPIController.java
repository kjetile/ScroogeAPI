package no.bargainhunter.api.controller;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletResponse;

import no.bargainhunter.api.model.Bar;
import no.bargainhunter.api.persistence.PMF;
	
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
	
	@RequestMapping(value = "/bar", method = RequestMethod.POST, produces="text/plain")
	@ResponseBody
	public String addBar(@RequestBody Bar bar,
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
	public @ResponseBody List<Bar> getBars(@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double radius) {
				
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(Bar.class);
 
		List<Bar> bars = null;
 
		try {
			bars = (List<Bar>) q.execute();
  
		} finally {
			q.closeAll();
			pm.close();
		}
 
		return bars;
	}
}
