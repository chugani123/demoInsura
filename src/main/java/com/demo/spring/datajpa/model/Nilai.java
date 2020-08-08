package com.demo.spring.datajpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "demo_nilai")
public class Nilai implements Serializable { 
	
private String noInduk;
private String pelajaran;
private String nilai;
private Siswa siswa ;


@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
@Column(name = "id")
private long id;

@JoinColumn(name="no_induk",referencedColumnName="no_induk", insertable=false, updatable=false)
@ManyToOne
public Siswa getSiswa() {
	return siswa;
}
public void setSiswa(Siswa siswa) {
	this.siswa = siswa;
}

@Id
@Column(name = "no_induk")
public String getNoInduk() {
	return noInduk;
}
public void setNoInduk(String noInduk) {
	this.noInduk = noInduk;
}

@Column(name = "pelajaran")
public String getPelajaran() {
	return pelajaran;
}
public void setPelajaran(String pelajaran) {
	this.pelajaran = pelajaran;
}

@Column(name = "nilai")
public String getNilai() {
	return nilai;
}
public void setNilai(String nilai) {
	this.nilai = nilai;
}

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

}
