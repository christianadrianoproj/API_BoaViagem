package com.christian.api.BoaViagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.api.BoaViagem.domain.Usuario;


/**
*
* @author Christian
*/
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}