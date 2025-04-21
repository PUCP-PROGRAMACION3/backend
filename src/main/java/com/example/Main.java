package com.example;

import com.example.dominio.Cliente;
import com.example.dominio.Producto;
import com.example.negocio.GestionClientes;
import com.example.negocio.GestionProductos;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Crear instancias de las clases de negocio
            GestionClientes gestionClientes = new GestionClientes();

            // 2. Crear objetos de dominio (para pruebas)
            Cliente cliente1 = new Cliente("Juan", "Pérez", "12345678", "Av. Principal 123");

            // 3. Realizar operaciones de negocio (ejemplos)
            gestionClientes.registrarCliente(cliente1);

            // Obtener y mostrar clientes
            System.out.println("Clientes registrados:");
            for (Cliente cliente : gestionClientes.obtenerTodosLosClientes()) {
                System.out.println(cliente.getNombre() + " " + cliente.getApellido() + " - " + cliente.getDni());
            }

            // Actualizar un cliente
            Cliente clienteActualizado = gestionClientes.obtenerCliente(1); // ID 1 (ejemplo)
            if (clienteActualizado != null) {
                clienteActualizado.setNombre("Juan Carlos Pérez");
                gestionClientes.actualizarCliente(clienteActualizado);
                System.out.println("\nCliente actualizado:");
                System.out.println(clienteActualizado.getNombre() + " " + clienteActualizado.getApellido() + " - " + clienteActualizado.getDni());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (GestionProductos gestionProductos = new GestionProductos()) {
            // 2. Crear objetos de dominio (para pruebas)
            Producto producto1 = new Producto(0, "Laptop Dell XPS 13", 1299.99);

            // 3. Realizar operaciones de negocio (ejemplos)
            gestionProductos.agregarProducto(producto1);

            // Obtener y mostrar productos
            System.out.println("\nProductos registrados:");
            for (Producto producto : gestionProductos.obtenerTodosLosProductos()) {
                System.out.println(producto.getNombre() + " - $" + producto.getPrecio());
            }

            // Eliminar un producto
            gestionProductos.eliminarProducto(1); // ID 1 (ejemplo)
            System.out.println("\nProducto eliminado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}