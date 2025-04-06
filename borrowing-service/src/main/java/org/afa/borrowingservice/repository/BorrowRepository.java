package org.afa.borrowingservice.repository;

import org.afa.borrowingservice.entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
