package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;

import java.util.List;

public class PrestamoLlaveService {

    private final PrestamoLlaveRepository repository = new PrestamoLlaveRepository();

    private final String[] turnos = {"Matutino", "Vespertino", "Laboratorio"};

    public String[] obtenerTurnos() {
        return turnos;
    }

    public List<PrestamoLlave> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public PrestamoLlave buscarPorNombreSolicitante(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return null;
        }
        return repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
    }

    public String agregar(String nombreSolicitante, String salon, String turno) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre del solicitante no puede estar vacío.";
        }
        if (salon == null || salon.trim().isEmpty()) {
            return "El salón no puede estar vacío.";
        }
        if (turno == null) {
            return "Debes seleccionar un turno.";
        }
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro con ese nombre de solicitante.";
        }
        repository.guardar(new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno));
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "No se ha seleccionado ningún registro para actualizar.";
        }
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "El nombre del solicitante no puede estar vacío.";
        }
        if (salon == null || salon.trim().isEmpty()) {
            return "El salón no puede estar vacío.";
        }
        if (turno == null) {
            return "Debes seleccionar un turno.";
        }
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal.trim());
        if (registro == null) {
            return "No se encontró el registro original.";
        }
        if (!nombreOriginal.trim().equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "Ya existe un registro con ese nuevo nombre de solicitante.";
            }
        }
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setSalon(salon.trim());
        registro.setTurno(turno);
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre del solicitante no puede estar vacío.";
        }
        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) {
            return "No se encontró un registro con ese nombre de solicitante.";
        }
        return null;
    }
}
