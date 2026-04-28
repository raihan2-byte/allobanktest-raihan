package com.example.split_bill.repository;

import com.example.split_bill.entity.BillGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<BillGroup, Long> {
}