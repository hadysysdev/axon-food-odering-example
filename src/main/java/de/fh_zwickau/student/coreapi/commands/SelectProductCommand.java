/**
 * 
 */
package de.fh_zwickau.student.coreapi.commands;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Value;

/**
 * @author Hamza
 *
 */
@Value
public class SelectProductCommand {
	
	@TargetAggregateIdentifier UUID foodCartId;
	UUID productId;
	int quantity;

}
