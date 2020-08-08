package com.demo.spring.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.datajpa.model.Siswa;

@Repository
public interface SiswaRepository extends JpaRepository<Siswa, Long> {
	List<Siswa> findByNoIndukContaining(String noInduk);
	List<Siswa> findByNamaContaining(String nama);
		
}
