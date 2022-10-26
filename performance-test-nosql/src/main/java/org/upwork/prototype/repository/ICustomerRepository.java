package org.upwork.prototype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.upwork.prototype.entity.OpCustomer;

/**
 * Customer Repository
 *
 * @author prasadm
 * @since 29 May 2022
 */

@Repository
public interface ICustomerRepository extends MongoRepository<OpCustomer,Long>
{
}
