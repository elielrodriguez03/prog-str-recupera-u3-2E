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
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "El nombre no puede estar vacio";
        if (salon == null || salon.trim().isEmpty()) return "El salon no puede estar vacio";
        if (turno == null) return "Debe seleccionar un turno";
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un registro con ese nombre de solicitante";
        }
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.isEmpty()) return "No se ha seleccionado ningun registro para actualizar";
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) return "El nombre no puede estar vacio";
        if (salon == null || salon.trim().isEmpty()) return "El salon no puede estar vacio";
        if (turno == null) return "El turno no puede ser nulo";
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) return "El registro ya no existe";
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "El nuevo nombre ya esta en uso";
            }
        }
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setSalon(salon.trim());
        registro.setTurno(turno);
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Ingrese un nombre para eliminar";
        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) {
            return "No se encontro el registro";
        }
        return null;
    }
}
