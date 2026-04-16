package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;
import java.util.List;

public class PrestamoLlaveService {

    private final PrestamoLlaveRepository repository = new PrestamoLlaveRepository();
    private final String[] turnos = {"Matutino", "Vespertino", "Laboratorio"};

    public String[] obtenerTurnos() { return turnos; }

    public List<PrestamoLlave> obtenerTodos() { return repository.obtenerTodos(); }

    public PrestamoLlave buscarPorNombreSolicitante(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        return repository.buscarPorNombreSolicitante(nombre.trim());
    }

    public String agregar(String nombre, String salon, String turno) {
        if (nombre == null || nombre.trim().isEmpty()) return "Nombre obligatorio";
        if (salon == null || salon.trim().isEmpty()) return "Salón obligatorio";
        if (turno == null) return "Turno obligatorio";

        if (repository.buscarPorNombreSolicitante(nombre.trim()) != null) {
            return "El nombre ya está registrado";
        }

        repository.save(new PrestamoLlave(nombre.trim(), salon.trim(), turno));
        return null;
    }

    public String actualizar(String original, String nuevo, String salon, String turno) {
        if (original == null) return "Seleccione un registro primero";
        if (nuevo == null || nuevo.trim().isEmpty()) return "Nuevo nombre obligatorio";
        if (salon == null || salon.trim().isEmpty()) return "Salón obligatorio";
        if (turno == null) return "Turno obligatorio";

        PrestamoLlave p = repository.buscarPorNombreSolicitante(original);
        if (p == null) return "No se encontró el registro original";

        // Si el nombre cambió, validar que el nuevo no esté repetido
        if (!original.equalsIgnoreCase(nuevo.trim()) && repository.buscarPorNombreSolicitante(nuevo.trim()) != null) {
            return "El nuevo nombre ya existe en otro registro";
        }

        p.setNombreSolicitante(nuevo.trim());
        p.setSalon(salon.trim());
        p.setTurno(turno);
        repository.save(p);
        return null;
    }

    public String eliminar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return "Nombre no válido";

        PrestamoLlave p = repository.buscarPorNombreSolicitante(nombre);
        if (p == null) return "No se encontró el registro";

        repository.delete(p);
        return null;
    }
}