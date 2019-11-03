package com.salil.driveme.dataaccessobject;

import com.salil.driveme.domainobject.ManufacturerDO;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long>
{
}
