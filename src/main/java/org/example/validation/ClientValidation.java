package org.example.validation;

import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;

public class ClientValidation {
    public static ValidationOptions validationOptions = new ValidationOptions().validator(
            Document.parse("""
                        {
                          $jsonSchema: {
                             bsonType: "object",
                             properties: {
                                rents: {
                                bsonType: "int",
                                minimum: 0,
                                maximum:5,
                                description: "rents can have values of 0-5"
                                },
                                firstName: {
                                   bsonType: "string",
                                   description: "Imię klienta"
                                },
                                lastName: {
                                   bsonType: "string",
                                   description: "Nazwisko klienta"
                                }
                             },
                          }
                        }
                    """
            )
    );
}
