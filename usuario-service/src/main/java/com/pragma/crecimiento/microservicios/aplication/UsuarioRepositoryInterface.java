package com.pragma.crecimiento.microservicios.aplication;

import java.util.List;

import com.pragma.crecimiento.microservicios.domain.Usuario;

public interface UsuarioRepositoryInterface {

    Usuario save(Usuario usuario);

    Usuario findById(Long id);

    Usuario findByTipoIdentificacionAndNumeroIdentificacion(String tipoIdentificacion,
            String numeroIdentificacion);

    List<Usuario> findAll();

    List<Usuario> findByEdadGreaterThanEqual(int edad);

    void delete(Usuario usuario);
    
}
