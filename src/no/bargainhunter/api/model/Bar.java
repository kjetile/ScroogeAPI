package no.bargainhunter.api.model;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Bar extends Establishment {
	
	public Bar(String name, Location location) {
		super(name, location);
	}
}
