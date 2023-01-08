package com.vishwamalyan.app.repository;

import com.vishwamalyan.app.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends CrudRepository<User, Long> {
    User findByEmailIdIgnoreCase(String emailId);

}