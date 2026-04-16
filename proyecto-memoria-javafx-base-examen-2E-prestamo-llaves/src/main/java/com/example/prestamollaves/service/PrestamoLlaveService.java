package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;

import java.util.List;

public class PrestamoLlaveService {

    private final PrestamoLlaveRepository repository = new PrestamoLlaveRepository();
    private final String[] turnos = {"Matutino", "Vespertino", "Laboratorio"};

    public String[] obtenerTurnos() {
        return this.turnos;
    }

    public List<PrestamoLlave> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public PrestamoLlave buscarPorNombreSolicitante(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.isBlank()) {
            return null;
        }
        return repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
    }

    public String agregar(String nombreSolicitante, String salon, String turno) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre no puede estar vacío.";
        }

        if (turno == null) return "Seleccione un turno";

        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Ya existe un préstamo";
        }
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon, turno);
        repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.isEmpty()) {
            return "No se selecciono un registro.";
        }
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) return "Registro no encontrado.";
        registro.setNombreSolicitante(nombreNuevo != null ? nombreNuevo.trim() : nombreOriginal);
        registro.setSalon(salon != null ? salon.trim() : "");
        registro.setTurno(turno);

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.isBlank()) {
            return "Ingrese un nombre válido";
        }

        boolean borrado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());

        return borrado ? null : "El registro no existe.";
    }
}
