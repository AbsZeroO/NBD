    package org.example.repositories.Rent;

    import org.example.mappers.RentMapper;
    import org.example.model.Rent;
    import org.example.repositories.AbstractRedisRepository;
    import org.example.repositories.IRepo;

    import java.util.List;
    import java.util.stream.Collectors;

    public class RentFailOverRepository extends AbstractRedisRepository implements IRepo<Rent> {

        private final RentJsonbRepository redis;
        private final RentMgdRepository mongodb;
        private final RentProducent rentProducent = new RentProducent();

        public RentFailOverRepository(RentJsonbRepository redis, RentMgdRepository mongodb) {
            this.redis = redis;
            this.mongodb = mongodb;

        }

        @Override
        public boolean add(Rent entity) {
            try {
                rentProducent.sendRent(entity);
                return redis.add(RentMapper.rentToRedis(entity))
                        && mongodb.add(RentMapper.rentToMongo(entity));

            } catch (Exception e) {
                try {
                    return mongodb.add(RentMapper.rentToMongo(entity));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return false;
                }
            }

        }

        @Override
        public Rent findById(int id) {
            try {
                return RentMapper.rentFromRedis(redis.findById(id));
            } catch (Exception e) {
                return RentMapper.rentFromMongo(mongodb.findById(id));
            }
        }

        @Override
        public List<Rent> findAll() {
            try {
                List<Rent> rentsFromRedis = redis.findAll()
                        .stream()
                        .map(RentMapper::rentFromRedis)
                        .collect(Collectors.toList());

                if (rentsFromRedis.isEmpty()) {
                    throw new RuntimeException("Redis: No data found");
                }

                return rentsFromRedis;
            } catch (Exception e) {
                return mongodb.findAll()
                        .stream()
                        .map(RentMapper::rentFromMongo)
                        .collect(Collectors.toList());
            }
        }

        @Override
        public boolean update(Rent entity) {
            try {
                return redis.update(RentMapper.rentToRedis(entity))
                        && mongodb.update(RentMapper.rentToMongo(entity));
            } catch (Exception e) {
                return mongodb.update(RentMapper.rentToMongo(entity));
            }
        }

        @Override
        public boolean delete(int id) {
            try {
                return redis.delete(id)
                        && mongodb.delete(id);
            } catch (Exception e) {
                return mongodb.delete(id);
            }
        }
    }
