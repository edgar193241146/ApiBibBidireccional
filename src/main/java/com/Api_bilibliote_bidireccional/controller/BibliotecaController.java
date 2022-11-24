package com.Api_bilibliote_bidireccional.controller;

import java.net.URI;
import java.util.Optional;

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

import com.Api_bilibliote_bidireccional.Entitys.Librarie;
import com.Api_bilibliote_bidireccional.repository.BibliotecaRepository;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {
	
	@Autowired
	private BibliotecaRepository bibliotecarepo;

	@GetMapping
	public ResponseEntity<Page<Librarie>> listarBibliotecas(Pageable pageable) {
		return ResponseEntity.ok(bibliotecarepo.findAll(pageable));
	}

	@PostMapping
	public ResponseEntity<Librarie> guardarBiblioteca(@RequestBody Librarie librarie) {
		Librarie bibliotecaguardada = bibliotecarepo.save(librarie);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bibliotecaguardada.getId()).toUri();
		return ResponseEntity.created(ubicacion).body(bibliotecaguardada);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Librarie> editarBiblioteca(@PathVariable Integer id, @RequestBody Librarie librarie) {
		Optional<Librarie> bibliotecaoptional = bibliotecarepo.findById(id);
		if (!bibliotecaoptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		librarie.setId(bibliotecaoptional.get().getId());
		bibliotecarepo.save(librarie);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Librarie> eliminarBiblioteca(@PathVariable Integer id) {
		Optional<Librarie> bibliotecaoptional = bibliotecarepo.findById(id);
		if (!bibliotecaoptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		bibliotecarepo.delete(bibliotecaoptional.get());
		return ResponseEntity.noContent().build();

	}

	@GetMapping("/{id}")
	public ResponseEntity<Librarie> obtenerPorId(@PathVariable Integer id) {
		Optional<Librarie> bibliotecaoptional = bibliotecarepo.findById(id);
		if (!bibliotecaoptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(bibliotecaoptional.get());
	}
}
