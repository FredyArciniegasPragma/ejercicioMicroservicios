package com.pragma.crecimiento.microservicios.aplication.service;

import java.util.List;

import com.pragma.crecimiento.microservicios.domain.Imagen;

public interface ImagenServiceInterface {
    
    Imagen registrar(Imagen imagen);

    Imagen obtenerPorId(String id);

    List<Imagen> listarTodos();

    Imagen actualizar(Imagen imagen);

    Imagen eliminar(String id);
}
