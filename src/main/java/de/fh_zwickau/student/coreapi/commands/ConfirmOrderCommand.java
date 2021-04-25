/**
 * 
 */
package de.fh_zwickau.student.coreapi.commands;

import java.util.UUID;
import lombok.Value;

/**
 * @author Hamza
 *
 */
@Value
public class ConfirmOrderCommand {
	
	UUID foodCartId;

}
