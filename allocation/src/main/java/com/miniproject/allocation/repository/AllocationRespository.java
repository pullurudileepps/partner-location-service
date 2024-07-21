package com.miniproject.allocation.repository;

import com.miniproject.allocation.models.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRespository extends JpaRepository<Allocation,Long> {

}
