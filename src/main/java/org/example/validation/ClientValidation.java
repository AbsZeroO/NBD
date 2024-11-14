package org.example.validation;

import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;

public class ClientValidation {
    public static ValidationOptions validationOptions = new ValidationOptions().validator(
            Document.parse("""
                        {
                          $jsonSchema: {
                             bsonType: "object",
                             required: ["firstName", "lastName", "clientType"],
                             properties: {
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
