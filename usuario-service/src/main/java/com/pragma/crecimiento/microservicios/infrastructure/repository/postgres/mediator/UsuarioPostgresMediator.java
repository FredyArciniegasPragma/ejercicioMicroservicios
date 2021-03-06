package com.pragma.crecimiento.microservicios.infrastructure.repository.postgres.mediator;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.pragma.crecimiento.microservicios.aplication.UsuarioRepositoryInterface;
import com.pragma.crecimiento.microservicios.domain.Usuario;
import com.pragma.crecimiento.microservicios.domain.exception.UsuarioNoEncontradoException;
import com.pragma.crecimiento.microservicios.infrastructure.repository.postgres.entity.UsuarioPostgresEntity;
import com.pragma.crecimiento.microservicios.infrastructure.repository.postgres.mapper.UsuarioPostgresMapper;
import com.pragma.crecimiento.microservicios.infrastructure.repository.postgres.repository.UsuarioPostgresRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPostgresMediator implements UsuarioRepositoryInterface{

    Logger LOG = Logger.getLogger("UsuarioPostgresMediator");

    @Autowired
    private UsuarioPostgresRepository usuarioRepository;

    @Autowired
    private UsuarioPostgresMapper usuarioMapper;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioPostgresEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        if(usuario.hasImagen()){
            usuarioEntity.setImagenId(usuario.getImagen().getId());
        }
        Usuario usuarioReturn = usuarioMapper.toDomain(usuarioRepository.save(usuarioEntity));
        usuarioReturn.setImagen(usuario.getImagen()); //Para retornar el objeto imagen completo y no sólo el id
        return usuarioReturn;
    }

    @Override
    public Usuario findById(Long id) {
        Optional<UsuarioPostgresEntity> opcionalUsuarioReturn = usuarioRepository.findById(id);
        if(opcionalUsuarioReturn.isPresent()){
            return usuarioMapper.toDomain(opcionalUsuarioReturn.get());
        }
        throw new UsuarioNoEncontradoException("No existe un usuario con el id "+id);
    }

    @Override
    public Usuario findByTipoIdentificacionAndNumeroIdentificacion(String tipoIdentificacion, String numeroIdentificacion) {
        Optional<UsuarioPostgresEntity> opcionalUsuarioReturn = usuarioRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);
        if(opcionalUsuarioReturn.isPresent()){
            return usuarioMapper.toDomain(opcionalUsuarioReturn.get());
        }
        throw new UsuarioNoEncontradoException("No existe un usuario con el documento ["+tipoIdentificacion + "  " + numeroIdentificacion + "]");
    }

    @Override
    public List<Usuario> findAll() {
        LOG.info("USUARIOS LISTADOS: "+usuarioRepository.findAll().size());
        return usuarioRepository.findAll().stream().map(usuarioEntity -> {
            return usuarioMapper.toDomain(usuarioEntity); 
        }).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findByEdadGreaterThanEqual(int edad) {
        return usuarioRepository.findByEdadGreaterThanEqual(edad).stream().map(usuarioEntity -> { 
            return usuarioMapper.toDomain(usuarioEntity); 
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuarioMapper.toEntity(usuario));
    }
    
}
