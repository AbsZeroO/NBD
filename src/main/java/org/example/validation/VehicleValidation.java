package org.example.validation;

import com.mongodb.client.model.ValidationAction;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;

public class VehicleValidation {
    public static ValidationOptions validationOptions = new ValidationOptions().validator(
            Document.parse("""
                        {
                          $jsonSchema: {
                             bsonType: "object",
                             properties: {
                                rented: {
                                   bsonType: "int",
                                   minimum: 0,
                                   maximum: 1,
                                   description: "Pole rented wskazuje, czy pojazd jest aktualnie wypożyczony"
                                },
                                plateNumber: {
                                   bsonType: "string",
                                   description: "Unikalny numer rejestracyjny pojazdu"
                                }
                             },
                          }
                        }
                    """
            )
    ).validationAction(ValidationAction.ERROR);
}
