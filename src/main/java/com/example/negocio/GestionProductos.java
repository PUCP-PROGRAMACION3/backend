package com.example.negocio;

import com.example.dominio.Producto;
import com.example.persistencia.dao.ProductoDAO;
import com.example.persistencia.dao.impl.ProductoDAOImpl;

import java.util.List;

public class GestionProductos implements AutoCloseable {
    private ProductoDAO productoDAO;

    public GestionProductos() {
        try {
            this.productoDAO = new ProductoDAOImpl();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la conexión de la base de datos", e);
        }
    }

    public Producto obtenerProducto(int id) {
        return productoDAO.obtenerProducto(id);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoDAO.obtenerTodosLosProductos();
    }

    public void agregarProducto(Producto producto) {
        productoDAO.agregarProducto(producto);
    }

    public void actualizarProducto(Producto producto) {
        productoDAO.actualizarProducto(producto);
    }

    public void eliminarProducto(int id) {
        productoDAO.eliminarProducto(id);
    }

    @Override
    public void close() {
        if (productoDAO instanceof ProductoDAOImpl) {
            ((ProductoDAOImpl) productoDAO).close();
        }
    }
}