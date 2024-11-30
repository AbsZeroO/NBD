package org.example.head;

import org.example.mgd.AddressMgd;
import org.example.mgd.CarMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.mgd.RentMgd;
import org.example.model.ClientType;
import org.example.model.SegmentType;
import org.example.red.AddressJsonb;
import org.example.red.CarJsonb;
import org.example.red.ClientAccountJsonb;
import org.example.red.RentJsonb;
import org.example.repositories.Rent.RentJsonbRepository;
import org.example.repositories.Rent.RentMgdRepository;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.time.LocalDateTime;

@State(Scope.Benchmark)
public class BenchmarkingDataBase {

    private final RentJsonbRepository redisRepository = new RentJsonbRepository();
    private final RentMgdRepository mongoRepository = new RentMgdRepository();
    private RentMgd testRentMgd;
    private RentJsonb testRentJsonb;

    @Setup
    public void setup() {
        redisRepository.clearCashe();
        AddressJsonb address = new AddressJsonb("Łódź", "Radwańska", "40");
        ClientAccountJsonb client = new ClientAccountJsonb(0, "Maciek", "Walaszek",
                address, ClientType.GOLD, false, 0);
        CarJsonb vehicle1 = new CarJsonb(0,"LWD 0000", 25.0, 125, 0, false, SegmentType.B);


        AddressMgd address12 = new AddressMgd("Łódź", "Radwańska", "40");
        ClientAccountMgd client12 = new ClientAccountMgd(0, "Maciek", "Walaszek",
                address12, ClientType.GOLD, false, 0);
        CarMgd vehicle12 = new CarMgd(0,"LWD 0000", 25.0, 125, 0, false, SegmentType.B);

        testRentMgd = new RentMgd(0, client12, vehicle12, LocalDateTime.now());

        testRentJsonb = new RentJsonb(0, client, vehicle1, LocalDateTime.now());

        redisRepository.add(testRentJsonb);
        mongoRepository.add(testRentMgd);
    }

    @Benchmark
    public void benchmarkRedisRead() {
        RentJsonb result = redisRepository.findById(testRentJsonb.getEntityId());
        assert result != null;
    }

    @Benchmark
    public void benchmarkMongoRead() {
        RentMgd result = mongoRepository.findById(testRentMgd.getEntityId());
        assert result != null;
    }
}
