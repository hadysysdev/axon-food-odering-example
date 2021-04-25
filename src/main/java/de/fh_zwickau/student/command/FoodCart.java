package de.fh_zwickau.student.command;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fh_zwickau.student.coreapi.commands.ConfirmOrderCommand;
import de.fh_zwickau.student.coreapi.commands.CreateFoodCartCommand;
import de.fh_zwickau.student.coreapi.commands.DeselectProductCommand;
import de.fh_zwickau.student.coreapi.commands.SelectProductCommand;
import de.fh_zwickau.student.coreapi.events.FoodCartCreatedEvent;
import de.fh_zwickau.student.coreapi.events.OrderConfirmedEvent;
import de.fh_zwickau.student.coreapi.events.ProductDeselectedEvent;
import de.fh_zwickau.student.coreapi.events.ProductSelectedEvent;
import de.fh_zwickau.student.coreapi.exceptions.ProductDeselectionException;


@Aggregate
public class FoodCart {
	
	private static final Logger logger = LoggerFactory.getLogger(FoodCart.class);

	
	public FoodCart() {
		
	}
	
	@AggregateIdentifier
	private UUID foodcartId;
	private Map<UUID, Integer> selectedProducts;
	private boolean confirmed;
	
	@CommandHandler
	public FoodCart(CreateFoodCartCommand command) {
		UUID aggregateID = command.getFoodCartId();
		AggregateLifecycle.apply(new FoodCartCreatedEvent(aggregateID));
	}
	
	@CommandHandler
	private void handle(SelectProductCommand command) {
		AggregateLifecycle.apply(new ProductSelectedEvent(foodcartId, command.getProductId(), command.getQuantity()));
	}
	
	
	
	@CommandHandler
	private void handle(DeselectProductCommand command) throws ProductDeselectionException {
		UUID productId = command.getProductId();
		int quantity = command.getQuantity();
		
		if(!selectedProducts.containsKey(productId)) {
			throw new ProductDeselectionException( 
					"Cannot deselect a product which has not been selected for this Food Cart");
		}
		
		 if (selectedProducts.get(productId) - quantity < 0) {
	         throw new ProductDeselectionException(
	                 "Cannot deselect more products of ID [" + productId + "] than have been selected initially"
	         );
	     }
		
		
		AggregateLifecycle.apply(new ProductDeselectedEvent(foodcartId, productId, command.getQuantity()));
	}
	
	
	@CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (confirmed) {
            logger.warn("Cannot confirm a Food Cart order which is already confirmed");
            return;
        }

        AggregateLifecycle.apply(new OrderConfirmedEvent(foodcartId));
    }
	
	
	
	@EventSourcingHandler
	public void on(FoodCartCreatedEvent event) {
		foodcartId = event.getFoodCartId();
		selectedProducts = new HashMap<>();
	}
	
	
	@EventSourcingHandler
	public void on(ProductSelectedEvent event) {
		selectedProducts.merge(event.getProductId(), event.getQuantity(), Integer::sum);
	}
	
	
	@EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        confirmed = true;
    }
}
