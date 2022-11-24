package com.Api_bilibliote_bidireccional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Api_bilibliote_bidireccional.Entitys.Librarie;

@Repository
public interface BibliotecaRepository extends JpaRepository<Librarie, Integer> {

}
