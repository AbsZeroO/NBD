package org.example.daoMapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.dao.RentByVehicleDao;

@Mapper
public interface RentByVehicleDaoMapper {
    @DaoFactory
    RentByVehicleDao rentByVehicleDao();
}
