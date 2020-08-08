package com.demo.spring.datajpa.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "demo_siswa")
public class Siswa implements Serializable {
	private String noInduk;
	private String nama;
	private String kelas;
	private Set <Nilai> daftarNilai ; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToMany(mappedBy = "siswa", fetch=FetchType.EAGER)
	public Set<Nilai> getDaftarNilai() {
		return daftarNilai;
	}
	public void setDaftarNilai(Set<Nilai> daftarNilai) {
		this.daftarNilai = daftarNilai;
	}
	
	@Id
	@Column(name = "no_induk")
	public String getNoInduk() {
		return noInduk;
	}
	public void setNoInduk(String noInduk) {
		this.noInduk = noInduk;
	}
	
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	@Column(name = "kelas")
	public String getKelas() {
		return kelas;
	}
	public void setKelas(String kelas) {
		this.kelas = kelas;
	}
}
