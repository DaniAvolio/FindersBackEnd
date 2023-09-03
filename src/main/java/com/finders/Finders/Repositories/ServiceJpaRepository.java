package com.finders.Finders.Repositories;

import com.finders.Finders.Models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceJpaRepository extends JpaRepository<Service, Long> {
}
