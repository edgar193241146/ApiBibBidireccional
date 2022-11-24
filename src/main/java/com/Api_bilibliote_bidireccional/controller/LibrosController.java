package com.Api_bilibliote_bidireccional.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Api_bilibliote_bidireccional.Entitys.Books;
import com.Api_bilibliote_bidireccional.Entitys.Librarie;
import com.Api_bilibliote_bidireccional.repository.BibliotecaRepository;
import com.Api_bilibliote_bidireccional.repository.LibroRepository;

@RestController
@RequestMapping("/api/libros")
public class LibrosController {

	@Autowired
	private LibroRepository librorepo;

	@Autowired
	private BibliotecaRepository bibliotecarepo;

	@GetMapping
	public ResponseEntity<Page<Librarie>> listarBibliotecas(Pageable pageable) {
		return ResponseEntity.ok(bibliotecarepo.findAll(pageable));
	}

	@PostMapping
	public ResponseEntity<Books> guardarLibro(@Valid @RequestBody Books libro) {
		Optional<Librarie> bibliotecaOptional = bibliotecarepo.findById(libro.getBiblioteca().getId());
		if (!bibliotecaOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		libro.setBiblioteca(bibliotecaOptional.get());
		Books libroGuardado = librorepo.save(libro);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(libroGuardado.getId()).toUri();
		return ResponseEntity.created(ubicacion).body(libroGuardado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Books> guardarLibro(@Valid @RequestBody Books libro, @PathVariable Integer id) {
		Optional<Librarie> bibliotecaOptional = bibliotecarepo.findById(libro.getBiblioteca().getId());
		if (!bibliotecaOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		Optional<Books> libroOptional = librorepo.findById(id);
		if (!libroOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		libro.setBiblioteca(bibliotecaOptional.get());
		libro.setId(libroOptional.get().getId());
		librorepo.save(libro);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Books> eliminarLibro(@PathVariable Integer id) {
		Optional<Books> libroOptional = librorepo.findById(id);
		if (!libroOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		librorepo.delete(libroOptional.get());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Books> listarLibros(@PathVariable Integer id) {
		Optional<Books> libroOptional = librorepo.findById(id);
		if (!libroOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(libroOptional.get());
	}
}
