package no.bargainhunter.api.model;

import java.util.Collection;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME)
public class Bar {

	public Bar() {
	}
	
	public Bar(String name, Location location) {
		this.name = name;
		this.location = location;
	}
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent(dependent = "true")
	private Location location;
	
	@Persistent
	@Element(dependent = "true")
	private Collection<MenuItem> menuItems;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Collection<MenuItem>  getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(Collection<MenuItem>  menuItems) {
		this.menuItems = menuItems;
	}
}
