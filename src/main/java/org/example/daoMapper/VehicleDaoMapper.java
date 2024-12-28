package org.example.daoMapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.dao.VehicleDao;

@Mapper
public interface VehicleDaoMapper {
    @DaoFactory
    VehicleDao vehicleDao();
}
