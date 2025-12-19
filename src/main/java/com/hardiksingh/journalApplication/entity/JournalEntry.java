package com.hardiksingh.journalApplication.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//here we will create an entity to store the request body
@Data
@Document(collection = "journal_entries")
public class JournalEntry { // this is a pojo class//
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
}