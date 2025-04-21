package com.example.persistencia.dao;   

import java.util.List;

import com.example.dominio.Cliente;

public interface ClienteDAO {
    void agregarCliente(Cliente cliente);
    Cliente obtenerCliente(int id);
    List<Cliente> obtenerTodosLosClientes();
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(int id);
} 