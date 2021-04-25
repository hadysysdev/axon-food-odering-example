/**
 * 
 */
package de.fh_zwickau.student.coreapi.queries;

import java.util.UUID;

import lombok.Value;

/**
 * @author Hamza
 *
 */
@Value
public class FindFoodCartQuery {
	
	UUID foodCartId;
}
