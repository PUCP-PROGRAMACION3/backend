package com.example.persistencia.dao.impl;

import com.example.dominio.Cliente;
import com.example.persistencia.DBManager;
import com.example.persistencia.dao.ClienteDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 *  Cada metodo del CRUD abre y cierra la conexión a la base de datos.
 *  Ventaja: Se liberan recursos de la base de datos, evitando posibles cuellos de botella.
 *  Desventaja: Se abre y cierra la conexión a la base de datos en cada operación.
 */
public class ClienteDAOImpl implements ClienteDAO {

    protected Cliente createClienteFromResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setDni(rs.getString("dni"));
        cliente.setDireccion(rs.getString("direccion"));
        return cliente;
    }

    @Override
    public void agregarCliente(Cliente cliente) {
        String query = "INSERT INTO clientes (nombre, apellido, dni, direccion) VALUES (?, ?, ?, ?)";
        try (Connection conexion = DBManager.getInstance().obtenerConexion();
             var ps = conexion.prepareStatement(query)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar cliente", e);
        }
    }

    @Override
    public Cliente obtenerCliente(int id) {
        String query = "SELECT id, nombre, apellido, dni, direccion FROM clientes WHERE id = ?";
        try (Connection conexion = DBManager.getInstance().obtenerConexion();
             var ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createClienteFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener cliente", e);
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        String query = "SELECT id, nombre, apellido, dni, direccion FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexion = DBManager.getInstance().obtenerConexion();
             var ps = conexion.prepareStatement(query);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(createClienteFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener clientes", e);
        }
        return clientes;
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        String query = "UPDATE clientes SET nombre = ?, apellido = ?, dni = ?, direccion = ? WHERE id = ?";
        try (Connection conexion = DBManager.getInstance().obtenerConexion();
             var ps = conexion.prepareStatement(query)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getDireccion());
            ps.setInt(5, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    @Override
    public void eliminarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (Connection conexion = DBManager.getInstance().obtenerConexion();
             var ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }
}
