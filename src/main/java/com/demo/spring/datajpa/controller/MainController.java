package com.demo.spring.datajpa.controller;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import com.demo.spring.datajpa.model.Nilai;
import com.demo.spring.datajpa.model.Siswa;
import com.demo.spring.datajpa.repository.NilaiRepository;
import com.demo.spring.datajpa.repository.SiswaRepository;
import com.demo.spring.datajpa.service.FileService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	SiswaRepository siswaRepository;
	
	@Autowired
	NilaiRepository nilaiRepository;
	
	@Autowired
	FileService fileService;

	@GetMapping("/siswa/get")
	public ResponseEntity<List<?>> getAllSiswa(@RequestParam(required = false) String filter, @RequestParam(required = false) String input) {
		try {
			List<Siswa> siswa = new ArrayList<Siswa>();
			List<Nilai> nilai = new ArrayList<Nilai>();
			String useBean =  null;

			// bisa pakai select case dan juga bisa pakai if 
			
			if (filter == null) {
				siswaRepository.findAll().forEach(siswa::add);
			}else {
				if (filter.equals("noInduk") && input != null ) {
					siswaRepository.findByNoIndukContaining(input).forEach(siswa::add);
					useBean = "siswa";
				}else {
					if (filter.equals("nama") && input != null ) {
						siswaRepository.findByNamaContaining(input).forEach(siswa::add);
						useBean = "siswa";
					}else {
						if (filter.equals("pelajaran") && input != null ) {
							nilaiRepository.findByPelajaranContaining(input).forEach(nilai::add);
							useBean = "nilai";
						}else {
							if (filter.equals("nilai") && input != null ) {
								nilaiRepository.findByNilaiContaining(input).forEach(nilai::add);
								useBean = "nilai";
							}
						}
					}
				}
			}
			

			if (siswa.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			if (!useBean.equals("siswa") || useBean.equals("nilai")) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				if (useBean.equals(siswa)) {
					return new ResponseEntity<>(siswa, HttpStatus.OK);
				}else {
					return new ResponseEntity<>(nilai, HttpStatus.OK);
				}
			}		
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/nilai/update/{noInduk}/{pelajaran}")
	public ResponseEntity<Nilai> updateNilai(@PathVariable("noInduk") String noInduk,@PathVariable("pelajaran") String pelajaran, @RequestBody Nilai nilai) {
		Optional<Nilai> Data = nilaiRepository.findByNoIndukAndPelajaranContaining(noInduk,pelajaran);

		if (Data.isPresent()) {
			Nilai nilaiUpdate = Data.get();
			nilaiUpdate.setNilai(nilai.getNilai());
			return new ResponseEntity<>(nilaiRepository.save(nilaiUpdate), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/nilai/delete/{pelajaran}")
	public ResponseEntity<HttpStatus> deletePelajaran(@PathVariable("pelajaran") String pelajaran) {
		try {
			nilaiRepository.deleteByPelajaran(pelajaran);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping("/upload")
	  public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {
		
		String fileUrl = this.fileService.save(file);
		
	    try {
	    	Reader reader = Files.newBufferedReader(Paths.get(fileUrl));
	    	CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
	    	
	    	if (file.getOriginalFilename().contains("siswa")){
	    		String [] nextRecord;
		    	while ((nextRecord = csvReader.readNext()) != null) {
		    		Siswa siswa = new Siswa ();
		    		siswa.setNoInduk(nextRecord[0]);
		    		siswa.setNama(nextRecord[1]);
		    		siswa.setKelas(nextRecord[2]);
		    		this.siswaRepository.save(siswa);
		    	}
	    	}else {
	    		if (file.getOriginalFilename().contains("nilai")){
	    			String [] nextRecord;
			    	while ((nextRecord = csvReader.readNext()) != null) {
			    		Nilai nilai =  new Nilai();
			    		nilai.setNoInduk(nextRecord[0]);
			    		nilai.setPelajaran(nextRecord[1]);
			    		nilai.setNilai(nextRecord[2]);
			    		this.nilaiRepository.save(nilai);
			    	}
	    		}else {
	    		throw (new Exception("go to exception"));
	    		}
	    	}
	    
	      return new ResponseEntity<>(HttpStatus.OK);	
	    } catch (Exception e) {
	      //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	  }
}
