package com.hardiksingh.journalApplication.repository;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

//here we will connect the entity with the db
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    
}