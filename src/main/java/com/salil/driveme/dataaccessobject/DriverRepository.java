package com.salil.driveme.dataaccessobject;

import com.salil.driveme.domainobject.DriverDO;
import com.salil.driveme.domainvalue.OnlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends JpaRepository<DriverDO, Long>, JpaSpecificationExecutor<DriverDO>
{
    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
}
