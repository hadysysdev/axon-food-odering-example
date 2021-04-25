/**
 * 
 */
package de.fh_zwickau.student.query;

import java.util.Map;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hamza
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FoodCartView {
	
	@Id UUID foodcartId;
	@ElementCollection(fetch = FetchType.EAGER) Map<UUID,Integer> products;
	


	public void addProducts(UUID productId, int amount) {
		products.compute(productId,(proId,quantity)-> quantity+= amount);
	}
	
	

	public void removeProducts(UUID productId, int amount) {
		final int leftOverQuantity = products.compute(productId, ((proId,quantity)-> quantity-= amount));
		if(leftOverQuantity == 0) {
			products.remove(productId);
		}
	}
	
}
