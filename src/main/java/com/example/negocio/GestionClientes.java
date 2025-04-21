package com.example.negocio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.dominio.Cliente;
import com.example.persistencia.dao.ClienteDAO;
import com.example.persistencia.dao.impl.ClienteDAOImpl;

public class GestionClientes {
    private static final Logger logger = Logger.getLogger(GestionClientes.class.getName());
    private ClienteDAO clienteDAO;

    public GestionClientes() {
        this.clienteDAO = new ClienteDAOImpl();
    }

    public Cliente obtenerCliente(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("El ID del cliente debe ser mayor que cero.");
            }
            return clienteDAO.obtenerCliente(id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener el cliente con ID: " + id, e);
            throw new RuntimeException("Error al obtener el cliente", e);
        }
    }

    public List<Cliente> obtenerTodosLosClientes() {
        try {
            return clienteDAO.obtenerTodosLosClientes();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener todos los clientes", e);
            throw new RuntimeException("Error al obtener todos los clientes", e);
        }
    }

    public void registrarCliente(Cliente cliente) {
        try {
            validarCliente(cliente);
            clienteDAO.agregarCliente(cliente);
            logger.log(Level.INFO, "Cliente registrado exitosamente: " + cliente);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al registrar el cliente: " + cliente, e);
            throw new RuntimeException("Error al registrar el cliente", e);
        }
    }

    public void actualizarCliente(Cliente cliente) {
        try {
            validarCliente(cliente);
            clienteDAO.actualizarCliente(cliente);
            logger.log(Level.INFO, "Cliente actualizado exitosamente: " + cliente);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar el cliente: " + cliente, e);
            throw new RuntimeException("Error al actualizar el cliente", e);
        }
    }

    public void eliminarCliente(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("El ID del cliente debe ser mayor que cero.");
            }
            clienteDAO.eliminarCliente(id);
            logger.log(Level.INFO, "Cliente eliminado exitosamente con ID: " + id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar el cliente con ID: " + id, e);
            throw new RuntimeException("Error al eliminar el cliente", e);
        }
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        if (cliente.getApellido() == null || cliente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido del cliente no puede estar vacío.");
        }
        if (cliente.getDni() == null || cliente.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI del cliente no puede estar vacío.");
        }
    }
}