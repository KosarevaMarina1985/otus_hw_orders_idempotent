package com.kosmar.billing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedMessageRepository extends CrudRepository<ProcessedMessage, Long> {

}