package com.pragma.crecimiento.microservicios.aplication.service;

import java.util.List;
import java.util.logging.Logger;

import com.pragma.crecimiento.microservicios.domain.Imagen;
import com.pragma.crecimiento.microservicios.domain.exception.ImagenNoEncontradaException;
import com.pragma.crecimiento.microservicios.domain.exception.ImagenYaRegistradaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImagenServiceImpl implements ImagenServiceInterface{

    Logger LOG = Logger.getLogger("ImagenServiceImpl");

    @Autowired
    private ImagenRepositoryInterface imagenRepository;

    @Override
    @Transactional
    public Imagen registrar(Imagen imagen) {
        try {
            obtenerPorId(imagen.getId());
            throw new ImagenYaRegistradaException("Ya existe una imagen registrada con el id "+imagen.getId());
        } catch (ImagenNoEncontradaException e) {
            imagen.setId(null); //Evita que se guarden las imágenes con id 0. Obliga a autogenerar un id
            return imagenRepository.save(imagen);
        }
    }

    @Override
    public Imagen obtenerPorId(String id){
        return imagenRepository.findById(id);
    }

    @Override
    public List<Imagen> listarTodos() {
        return imagenRepository.findAll();
    }

    @Override
    public Imagen actualizar(Imagen imagen){
        obtenerPorId(imagen.getId()); //Revisa si existe, si no, lanza excepción
        return imagenRepository.save(imagen);
    }

    @Override
    public Imagen eliminar(String id){
        Imagen imagenRegistrada = obtenerPorId(id); //Revisa si existe, si no, lanza excepción
        imagenRepository.delete(imagenRegistrada);
        return imagenRegistrada;
    }

    
    
}
