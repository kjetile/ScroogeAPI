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

import com.beoui.geocell.annotations.Geocells;
import com.beoui.geocell.annotations.Latitude;
import com.beoui.geocell.annotations.Longitude;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME)
public class Establishment {
	
	public enum Type {BAR}
	
	public Establishment() {
	}
	
	public Establishment(String name) {
		this.name = name;
	}
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private Type type;
	
	@Persistent
	@Latitude
	private double latitude;
	
	@Persistent
	@Longitude
	private double longitude;
	
	@Persistent
	@Geocells
	private Collection<String> cells;
	
	@Persistent
	@Element(dependent = "true")
	private Collection<MenuItem> menuItems;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Collection<String> getCells() {
		return cells;
	}
	public void setCells(Collection<String> cells) {
		this.cells = cells;
	}
	
	public Collection<MenuItem>  getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(Collection<MenuItem>  menuItems) {
		this.menuItems = menuItems;
	}

	public Key getKey() {
		return key;
	}
}
