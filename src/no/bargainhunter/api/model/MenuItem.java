package no.bargainhunter.api.model;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class MenuItem {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Product product;
	
	@Persistent
	private PriceTag priceTag;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public PriceTag getPriceTag() {
		return priceTag;
	}
	public void setPriceTag(PriceTag priceTag) {
		this.priceTag = priceTag;
	}
}
