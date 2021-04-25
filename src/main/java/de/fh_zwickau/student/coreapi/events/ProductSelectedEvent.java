package de.fh_zwickau.student.coreapi.events;

import java.util.UUID;
import lombok.Value;

@Value
public class ProductSelectedEvent {
	UUID foodCartId;
	UUID productId;
	int quantity;

}
