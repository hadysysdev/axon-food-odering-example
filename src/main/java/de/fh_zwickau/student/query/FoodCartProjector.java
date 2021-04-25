package de.fh_zwickau.student.query;

import java.util.Collections;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import de.fh_zwickau.student.coreapi.events.FoodCartCreatedEvent;
import de.fh_zwickau.student.coreapi.events.ProductDeselectedEvent;
import de.fh_zwickau.student.coreapi.events.ProductSelectedEvent;
import de.fh_zwickau.student.coreapi.queries.FindFoodCartQuery;

@Component
public class FoodCartProjector {

	private final FoodCartViewRepository repository;

	public FoodCartProjector(FoodCartViewRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	public void on(FoodCartCreatedEvent event) {
		FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
		repository.save(foodCartView);
	}

	@QueryHandler
	public FoodCartView handle(FindFoodCartQuery query) {
		return repository.findById(query.getFoodCartId()).orElse(null);
	}

	@EventHandler
	public void on(ProductSelectedEvent event) {
		repository.findById(event.getFoodCartId())
				.ifPresent(foodCartView -> foodCartView.addProducts(event.getProductId(), event.getQuantity()));
	}

	@EventHandler
	public void on(ProductDeselectedEvent event) {
		repository.findById(event.getFoodCartId())
				.ifPresent(foodCartView -> foodCartView.removeProducts(event.getProductId(), event.getQuantity()));
	}

}
