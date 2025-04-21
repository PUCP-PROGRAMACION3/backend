package com.example.test;

import com.example.dominio.Cliente;
import com.example.negocio.GestionClientes;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteTest {

    private static GestionClientes gestionClientes;
    private static int clienteId;
    private static String dniId;

    @BeforeAll
    public static void setUp() {
        gestionClientes = new GestionClientes();
    }

    @Test
    @Order(1)
    void registrarCliente() {
        // Generar un DNI aleatorio de 8 dígitos
        Random random = new Random();
        dniId = String.format("%08d", random.nextInt(100000000));

        Cliente cliente = new Cliente("Juan", "Pérez", dniId, "Av. Principal 123");
        gestionClientes.registrarCliente(cliente);
        List<Cliente> clientes = gestionClientes.obtenerTodosLosClientes();
        
        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);

        Cliente clienteRegistrado = null;
        for(Cliente c : clientes){
            if (c.getNombre().equals(cliente.getNombre())){
                clienteRegistrado = c;
            }
        }
        assertNotNull(clienteRegistrado);
        clienteId = clienteRegistrado.getId();
        assertEquals(cliente.getNombre(), clienteRegistrado.getNombre());
        assertEquals(cliente.getApellido(), clienteRegistrado.getApellido());
        assertEquals(cliente.getDni(), clienteRegistrado.getDni());
        assertEquals(cliente.getDireccion(), clienteRegistrado.getDireccion());
    }

    @Test
    @Order(2)
    void obtenerCliente() {
        Cliente cliente = gestionClientes.obtenerCliente(clienteId);
        assertNotNull(cliente);
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Pérez", cliente.getApellido());
        assertEquals(dniId, cliente.getDni());
        assertEquals("Av. Principal 123", cliente.getDireccion());
    }
    
    @Test
    @Order(3)
    void actualizarCliente() {
        Cliente cliente = gestionClientes.obtenerCliente(clienteId);
        assertNotNull(cliente);
        cliente.setNombre("Juan Carlos");
        cliente.setDireccion("Av. Nueva 456");
        gestionClientes.actualizarCliente(cliente);
        Cliente clienteActualizado = gestionClientes.obtenerCliente(clienteId);
        assertNotNull(clienteActualizado);
        assertEquals("Juan Carlos", clienteActualizado.getNombre());
        assertEquals("Pérez", clienteActualizado.getApellido());
        assertEquals(dniId, clienteActualizado.getDni());
        assertEquals("Av. Nueva 456", clienteActualizado.getDireccion());
    }

    @Test
    @Order(4)
    void listarClientes(){
        List<Cliente> clientes = gestionClientes.obtenerTodosLosClientes();
        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);
    }

    @Test
    @Order(5)
    void eliminarCliente(){
        gestionClientes.eliminarCliente(clienteId);
        Cliente clienteEliminado = gestionClientes.obtenerCliente(clienteId);
        assertNull(clienteEliminado);
    }
}