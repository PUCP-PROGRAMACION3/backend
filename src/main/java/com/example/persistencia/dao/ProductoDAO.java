package com.example.persistencia.dao;

import java.util.List;

import com.example.dominio.Producto;

public interface ProductoDAO {
    void agregarProducto(Producto producto);
    Producto obtenerProducto(int id);
    List<Producto> obtenerTodosLosProductos();
    void actualizarProducto(Producto producto);
    void eliminarProducto(int id);
} 