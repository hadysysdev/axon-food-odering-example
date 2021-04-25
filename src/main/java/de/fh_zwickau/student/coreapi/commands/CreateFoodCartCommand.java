/**
 * 
 */
package de.fh_zwickau.student.coreapi.commands;

import java.util.UUID;

import org.axonframework.commandhandling.RoutingKey;

import lombok.Value;

/**
 * @author Hamza
 *
 */
@Value
public class CreateFoodCartCommand {
	@RoutingKey UUID foodCartId;
}
