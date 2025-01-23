    package org.example.repositories.Rent;

    import org.apache.kafka.clients.producer.KafkaProducer;
    import org.apache.kafka.clients.producer.ProducerRecord;
    import org.apache.kafka.clients.producer.RecordMetadata;
    import org.example.mappers.RentMapper;
    import org.example.model.Rent;
    import org.example.red.RentJsonb;
    import org.example.repositories.AbstractRentProducent;

    public class RentProducent extends AbstractRentProducent {

        public void sendRent(Rent rent) {
            RentJsonb rentJ = RentMapper.rentToRedis(rent);

            try {
                getProducer().beginTransaction();

                String rentJson = this.getJsonb().toJson(rentJ);

                ProducerRecord<Integer, String> record = new ProducerRecord<>(TOPIC_NAME , rentJ.getEntityId(), rentJson);
                RecordMetadata metadata = this.getProducer().send(record).get();

                getProducer().commitTransaction();

                System.out.println("Wysłano rentę: " + rentJson);
            } catch (Exception e) {
                getProducer().abortTransaction();
                e.printStackTrace();
            }
        }

        public void close() {
            this.getProducer().close();
        }

    }
