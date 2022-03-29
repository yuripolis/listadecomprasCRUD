package com.example.listadecompras.repository;

import com.example.listadecompras.model.produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repository extends JpaRepository<produto, Long> {

}
