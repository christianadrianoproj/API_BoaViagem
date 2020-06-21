package com.christian.api.BoaViagem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.christian.api.BoaViagem.domain.Usuario;
import com.christian.api.BoaViagem.repository.UsuarioRepository;

/**
*
* @author Christian
*/

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
	@Autowired
	private UsuarioRepository repository;

	@GetMapping
	public List<Usuario> findAll() {
		return repository.findAll(Sort.by("nome"));
	}
	
	@PostMapping("/salvaUsuario")
	public Usuario salvaUsuariol(@RequestBody Usuario v) {
		return repository.save(v);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable("id") Integer id) {
		Optional<Usuario> usuario = repository.findById(id);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/deletaUsuario/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
	
	@PostMapping("/autentica")
	public Usuario validaLogin(@RequestBody Usuario login) {
		Usuario retorno = new Usuario();
		List<Usuario> lista = repository.findAll();
		for (Usuario l : lista) {
			if (l.getUserName().equalsIgnoreCase(login.getUserName())) {
				if (l.getPassword().equals(login.getPassword())) {
					retorno = l;
				}
				break;
			}
		}
		return retorno;
	}

}