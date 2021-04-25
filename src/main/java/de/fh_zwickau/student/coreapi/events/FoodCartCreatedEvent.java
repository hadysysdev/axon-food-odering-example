package de.fh_zwickau.student.coreapi.events;

import java.util.UUID;

import lombok.Value;

@Value
public class FoodCartCreatedEvent {
	UUID foodCartId;
}
