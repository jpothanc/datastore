package com.ibit.datastore.repositories;
import com.ibit.datastore.models.WebCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebCounterRepository extends JpaRepository<WebCounter, String>{
}
