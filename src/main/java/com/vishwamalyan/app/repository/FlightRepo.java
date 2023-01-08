package com.vishwamalyan.app.repository;

import com.vishwamalyan.app.model.UserIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends CrudRepository<UserIp, Long> {

}