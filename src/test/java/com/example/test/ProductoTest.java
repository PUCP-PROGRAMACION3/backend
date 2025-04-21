package com.example.test;

import com.example.dominio.Producto;
import com.example.negocio.GestionProductos;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoTest {

    private static int productoId;

    @BeforeAll
    public static void setUp() {
        // No es necesario inicializar GestionProductos aquí
    }

    @Test
    @Order(1)
    void registrarProducto() {
        try (GestionProductos gestionProductos = new GestionProductos()) {
            Producto producto = new Producto(0, "Laptop Dell XPS 13", 1299.99);
            gestionProductos.agregarProducto(producto);
            List<Producto> productos = gestionProductos.obtenerTodosLosProductos();

            assertNotNull(productos);
            assertTrue(productos.size() > 0);

            Producto productoRegistrado = null;
            for (Producto p : productos) {
                if (p.getNombre().equals(producto.getNombre())) {
                    productoRegistrado = p;
                }
            }
            assertNotNull(productoRegistrado);
            productoId = productoRegistrado.getId();
            assertEquals(producto.getNombre(), productoRegistrado.getNombre());
            assertEquals(producto.getPrecio(), productoRegistrado.getPrecio());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al registrar el producto");
        }
    }

    @Test
    @Order(2)
    void obtenerProducto() {
        try (GestionProductos gestionProductos = new GestionProductos()) {
            Producto producto = gestionProductos.obtenerProducto(productoId);
            assertNotNull(producto);
            assertEquals("Laptop Dell XPS 13", producto.getNombre());
            assertEquals(1299.99, producto.getPrecio());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al obtener el producto");
        }
    }

    @Test
    @Order(3)
    void actualizarProducto() {
        try (GestionProductos gestionProductos = new GestionProductos()) {
            Producto producto = new Producto(productoId, "Laptop Dell XPS 15", 1599.99);
            gestionProductos.actualizarProducto(producto);
            Producto productoActualizado = gestionProductos.obtenerProducto(productoId);
            assertNotNull(productoActualizado);
            assertEquals("Laptop Dell XPS 15", productoActualizado.getNombre());
            assertEquals(1599.99, productoActualizado.getPrecio());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al actualizar el producto");
        }
    }

    @Test
    @Order(4)
    void eliminarProducto() {
        try (GestionProductos gestionProductos = new GestionProductos()) {
            gestionProductos.eliminarProducto(productoId);
            Producto productoEliminado = gestionProductos.obtenerProducto(productoId);
            assertNull(productoEliminado);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al eliminar el producto");
        }
    }
}