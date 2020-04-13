package com.coder.demosisfo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "mahasiswa")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "password")
	private String password;
	
	@Column(name="no_bp")
	private Integer noBp;
	
	@NotBlank
	@Size(max = 40)
	@Column(name="nama_mhs")
	private String namaMhs;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "gender")
	private String gender;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "nama_fakultas")
	private String namaFakultas;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "nama_jurusan")
	private String namaJurusan;
	
	@Column(name = "semester")
	private Integer semester;
	
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name="daftar_matakuliah")
	private byte[] listCourse;
	
	public Student() {
		
	}

	/**
	 * @param id
	 * @param password
	 * @param noBp
	 * @param namaMhs
	 * @param dob
	 * @param gender
	 * @param namaFakultas
	 * @param namaJurusan
	 * @param semester
	 * @param listCourse
	 */
	public Student(Long id, @NotBlank @Size(max = 20) String password, Integer noBp,
			@NotBlank @Size(max = 40) String namaMhs, Date dob, String gender,
			@NotBlank @Size(max = 50) String namaFakultas, @NotBlank @Size(max = 50) String namaJurusan,
			Integer semester, byte[] listCourse) {
		super();
		this.id = id;
		this.password = password;
		this.noBp = noBp;
		this.namaMhs = namaMhs;
		this.dob = dob;
		this.gender = gender;
		this.namaFakultas = namaFakultas;
		this.namaJurusan = namaJurusan;
		this.semester = semester;
		this.listCourse = listCourse;
	}
	
	
	
}
