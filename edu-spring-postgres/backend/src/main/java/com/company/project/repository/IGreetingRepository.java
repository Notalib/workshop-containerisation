package com.company.project.repository;

import com.company.project.entity.Greeting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGreetingRepository extends CrudRepository<Greeting, UUID> {
  Optional<Greeting> findGreetingByName(String name);
}
