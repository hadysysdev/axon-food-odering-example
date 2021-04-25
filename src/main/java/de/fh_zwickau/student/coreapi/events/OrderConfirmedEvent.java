/**
 * 
 */
package de.fh_zwickau.student.coreapi.events;

import java.util.UUID;
import lombok.Value;

/**
 * @author Hamza
 *
 */
@Value
public class OrderConfirmedEvent {
	UUID foodCartId;

}
