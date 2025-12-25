package com.hardiksingh.journalApplication.repository;

import com.hardiksingh.journalApplication.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);//spring automatically create a query method here, {find+By+FieldName}
    User deleteByUserName(String userName);
}
