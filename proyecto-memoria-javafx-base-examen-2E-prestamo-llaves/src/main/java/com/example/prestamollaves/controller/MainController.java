package com.example.prestamollaves.controller;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.service.PrestamoLlaveService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;

public class MainController {

    @FXML private TextField txtNombreSolicitante;
    @FXML private TextField txtSalon;
    @FXML private ComboBox<String> cbTurno;
    @FXML private ListView<String> lvRegistros;

    private final PrestamoLlaveService service = new PrestamoLlaveService();
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTurnos();
        actualizarLista();
        lvRegistros.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) cargarSeleccion(newVal);
        });
    }

    private void cargarTurnos() {
        cbTurno.getItems().addAll(service.obtenerTurnos());
    }

    @FXML
    public void agregar() {
        String res = service.agregar(txtNombreSolicitante.getText(), txtSalon.getText(), cbTurno.getValue());
        manejarResultado(res);
    }

    @FXML
    public void buscar() {
        PrestamoLlave registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());
        if (registro == null) {
            mostrarMensaje("Aviso", "No encontrado", Alert.AlertType.WARNING);
            return;
        }
        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtSalon.setText(registro.getSalon());
        cbTurno.setValue(registro.getTurno());
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        String res = service.actualizar(nombreOriginal, txtNombreSolicitante.getText(), txtSalon.getText(), cbTurno.getValue());
        manejarResultado(res);
    }

    @FXML
    public void eliminar() {
        String res = service.eliminar(txtNombreSolicitante.getText());
        manejarResultado(res);
    }

    private void manejarResultado(String res) {
        if (res != null) {
            mostrarMensaje("Error", res, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
        }
    }

    @FXML
    public void limpiar() {
        txtNombreSolicitante.clear();
        txtSalon.clear();
        cbTurno.setValue(null);
        lvRegistros.getSelectionModel().clearSelection();
        nombreOriginal = null;
    }

    private void actualizarLista() {
        lvRegistros.getItems().clear();
        for (PrestamoLlave p : service.obtenerTodos()) {
            lvRegistros.getItems().add(p.toString());
        }
    }

    private void cargarSeleccion(String seleccion) {
        for (PrestamoLlave p : service.obtenerTodos()) {
            if (p.toString().equals(seleccion)) {
                txtNombreSolicitante.setText(p.getNombreSolicitante());
                txtSalon.setText(p.getSalon());
                cbTurno.setValue(p.getTurno());
                nombreOriginal = p.getNombreSolicitante();
                break;
            }
        }
    }

    private void mostrarMensaje(String tit, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tit);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}