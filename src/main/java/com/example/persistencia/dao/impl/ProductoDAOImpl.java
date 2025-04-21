package com.example.persistencia.dao.impl;

import com.example.dominio.Producto;
import com.example.persistencia.DBManager;
import com.example.persistencia.dao.ProductoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Se utiliza un solo objeto de conexión a la base de datos para todas las operaciones.
 * Ventaja: Se evita abrir y cerrar la conexión a la base de datos en cada operación, 
 *          esto es mas eficiente en términos de recursos.
 * Desventaja: Se mantiene abierta la conexión a la base de datos durante toda la ejecución, 
 *             lo que puede generar cuellos de botella.
 * Para cerrar la conexión se implementa la interfaz AutoCloseable y se sobreescribe el método close.
 */

public class ProductoDAOImpl implements ProductoDAO, AutoCloseable {
    protected Connection conexion;
    
    public ProductoDAOImpl() {
        try {
            this.conexion = DBManager.getInstance().obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión de la base de datos", e);
        }
    }
    
    protected Producto createProductoFromResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        return producto;
    }

    @Override
    public void agregarProducto(Producto producto) {
        String query = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar producto", e);
        }
    }

    @Override
    public Producto obtenerProducto(int id) {
        String query = "SELECT id, nombre, precio FROM productos WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createProductoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener producto", e);
        }
        return null;
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        String query = "SELECT id, nombre, precio FROM productos";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(createProductoFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar productos", e);
        }
        return productos;
    }

    @Override
    public void actualizarProducto(Producto producto) {
        String query = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminarProducto(int id) {
        String query = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    private void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al cerrar la conexión de la base de datos", e);
            }
        }
    }
    
    @Override
    public void close() {
        cerrarConexion();
    }
}
