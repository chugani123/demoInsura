package com.demo.spring.datajpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.datajpa.model.Nilai;

@Repository
public interface NilaiRepository extends JpaRepository<Nilai, Long> {
	Optional<Nilai> findByNoIndukAndPelajaranContaining(String induk, String pelajaran);
	List<Nilai> findByPelajaranContaining(String pelajaran);
	List<Nilai> findByNilaiContaining(String nilai);
	
	@Transactional
	void deleteByPelajaran(String pelajaran);
		
}
