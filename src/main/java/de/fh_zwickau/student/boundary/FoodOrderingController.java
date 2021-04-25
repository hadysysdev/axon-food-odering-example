/**
 * 
 */
package de.fh_zwickau.student.boundary;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fh_zwickau.student.coreapi.commands.CreateFoodCartCommand;
import de.fh_zwickau.student.coreapi.commands.DeselectProductCommand;
import de.fh_zwickau.student.coreapi.commands.SelectProductCommand;
import de.fh_zwickau.student.coreapi.queries.FindFoodCartQuery;
import de.fh_zwickau.student.query.FoodCartView;

/**
 * @author Hamza
 *
 */
@RestController
public class FoodOrderingController {
	
	
	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	
	public FoodOrderingController(CommandGateway commandGateway,QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}
	
	
	@PostMapping("/create")
	public CompletableFuture<UUID> handle() {
		return commandGateway.send(new CreateFoodCartCommand(UUID.randomUUID()));
	}
	
	
	@GetMapping("/foodcart/{foodCartId}")
	public CompletableFuture<FoodCartView> getFoodCart(@PathVariable String foodCartId) {
		return queryGateway.query(new FindFoodCartQuery(UUID.fromString(foodCartId)), 
				ResponseTypes.instanceOf(FoodCartView.class));
	}
	
	
    @PostMapping("/{foodCartId}/select/{productId}/quantity/{quantity}")
    public void selectProduct(@PathVariable("foodCartId") String foodCartId,
                              @PathVariable("productId") String productId,
                              @PathVariable("quantity") Integer quantity) {
        commandGateway.send(new SelectProductCommand(
                UUID.fromString(foodCartId), UUID.fromString(productId), quantity
        ));
    }

    @PostMapping("/{foodCartId}/deselect/{productId}/quantity/{quantity}")
    public void deselectProduct(@PathVariable("foodCartId") String foodCartId,
                                @PathVariable("productId") String productId,
                                @PathVariable("quantity") Integer quantity) {
        commandGateway.send(new DeselectProductCommand(
                UUID.fromString(foodCartId), UUID.fromString(productId), quantity
        ));
    }
	

}
