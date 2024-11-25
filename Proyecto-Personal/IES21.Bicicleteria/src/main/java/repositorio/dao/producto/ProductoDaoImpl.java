package repositorio.dao.producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repositorio.dao.ConexionDb;
import modelo.producto.Producto;

import modelo.producto.Categoria;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;
import repositorio.dao.categoria.CategoriaDaoImpl;
import repositorio.dao.marca.MarcaDaoImpl;
import repositorio.dao.modelo.ModeloDaoImpl;
import repositorio.dao.proveedor.ProveedorDaoImpl;
import modelo.producto.marca.Marca;


/**
 *
 * @author rodri
 */

public class ProductoDaoImpl implements IDaoProducto {

    private ConexionDb conexionDb;

    public ProductoDaoImpl() {
        this.conexionDb = new ConexionDb();
    }

    @Override
    public void insertarNuevoProducto(Producto producto) {
        // Obtener el próximo código de producto
        String codigoProducto = getProximoCodigoProducto();  // Método que genera el siguiente código de producto
        System.out.println("Generado código de producto: " + codigoProducto);

        // Datos del producto
        String nombreProducto = producto.getNombre();
        double precio = producto.getPrecio();
        String descripcion = producto.getDescripcion();
        int cantidad = producto.getCantidad();
        String pathImagen = producto.getPathImagen();

        // Códigos de las entidades relacionadas
        String codigoCategoria = producto.getCategoria().getCodigo(); // Código de categoría
        String codigoModelo = producto.getModelo().getCodigo();       // Código de modelo
        String codigoProveedor = producto.getProveedor().getCodigo(); // Código de proveedor
        String codigoMarca = producto.getMarca().getCodigo();         // Código de marca (Nuevo)

        // Validación de códigos
        if (codigoCategoria == null || codigoModelo == null || codigoProveedor == null || codigoMarca == null) {
            throw new IllegalArgumentException("Los códigos de categoría, modelo, proveedor o marca no pueden ser nulos.");
        }

        // Imprimir los valores de los códigos para depuración
        System.out.println("Código de Categoría: " + codigoCategoria);
        System.out.println("Código de Modelo: " + codigoModelo);
        System.out.println("Código de Proveedor: " + codigoProveedor);
        System.out.println("Código de Marca: " + codigoMarca);  // Imprimir código de marca

        // Consulta SQL
        String sqlInsertProducto = "INSERT INTO productos (codigo, nombre, precio, descripcion, cantidad, path_imagen, codigo_categoria, codigo_modelo, codigo_proveedor, codigo_marca) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  // Agregar campo de código de marca

        try (PreparedStatement stmtProducto = conexionDb.obtenerConexion().prepareStatement(sqlInsertProducto)) {
            stmtProducto.setString(1, codigoProducto);  // Código generado para el producto
            stmtProducto.setString(2, nombreProducto);  // Nombre del producto
            stmtProducto.setDouble(3, precio);          // Precio del producto
            stmtProducto.setString(4, descripcion);     // Descripción del producto
            stmtProducto.setInt(5, cantidad);           // Cantidad
            stmtProducto.setString(6, pathImagen);      // Path de la imagen
            stmtProducto.setString(7, codigoCategoria); // Código de categoría
            stmtProducto.setString(8, codigoModelo);    // Código de modelo
            stmtProducto.setString(9, codigoProveedor); // Código de proveedor
            stmtProducto.setString(10, codigoMarca);    // Código de marca

            int affectedRows = stmtProducto.executeUpdate(); // Ejecutar la consulta
            if (affectedRows > 0) {
                System.out.println("Producto " + nombreProducto + " insertado con éxito.");
            } else {
                System.out.println("Error al insertar el producto. No se afectaron filas.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar el producto: " + e.getMessage());
            throw new RuntimeException("Error al insertar el producto", e);  // Re-throwing exception
        }
    }

    @Override
    public void eliminarProducto(String codigoProducto, String nombreProducto) {
        String sqlDeleteByCodigo = "DELETE FROM productos WHERE codigo = ?";
        String sqlDeleteByNombre = "DELETE FROM productos WHERE nombre = ?";

        try {
            if (codigoProducto != null && !codigoProducto.isEmpty()) {
                try (PreparedStatement stmtDelete = conexionDb.obtenerConexion().prepareStatement(sqlDeleteByCodigo)) {
                    stmtDelete.setString(1, codigoProducto);
                    int affectedRows = stmtDelete.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Producto con código " + codigoProducto + " eliminado exitosamente.");
                    } else {
                        System.out.println("No se encontró un producto con código " + codigoProducto + " para eliminar.");
                    }
                }
            } else if (nombreProducto != null && !nombreProducto.isEmpty()) {
                try (PreparedStatement stmtDelete = conexionDb.obtenerConexion().prepareStatement(sqlDeleteByNombre)) {
                    stmtDelete.setString(1, nombreProducto);
                    int affectedRows = stmtDelete.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Producto con nombre " + nombreProducto + " eliminado exitosamente.");
                    } else {
                        System.out.println("No se encontró un producto con nombre " + nombreProducto + " para eliminar.");
                    }
                }
            } else {
                System.out.println("Debe proporcionar al menos un código o un nombre de producto para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @Override
    public void modificarProducto(String codigoProducto, Producto productoModificado) {
        String sqlUpdateProducto = "UPDATE productos SET nombre = ?, precio = ?, descripcion = ?, cantidad = ?, "
                + "codigo_categoria = ?, codigo_modelo = ?, codigo_proveedor = ?, codigo_marca = ?, path_imagen = ? WHERE codigo = ?";

        try {
            if (codigoProducto != null && !codigoProducto.isEmpty()) {
                try (PreparedStatement stmtUpdate = conexionDb.obtenerConexion().prepareStatement(sqlUpdateProducto)) {
                    // Actualizar los campos del producto
                    stmtUpdate.setString(1, productoModificado.getNombre());
                    stmtUpdate.setDouble(2, productoModificado.getPrecio());
                    stmtUpdate.setString(3, productoModificado.getDescripcion());
                    stmtUpdate.setInt(4, productoModificado.getCantidad());

                    // Asignar las relaciones: categoría, modelo, proveedor, marca y ruta de imagen
                    stmtUpdate.setString(5, productoModificado.getCategoria().getCodigo()); // Código de la categoría
                    stmtUpdate.setString(6, productoModificado.getModelo().getCodigo());    // Código del modelo
                    stmtUpdate.setString(7, productoModificado.getProveedor().getCodigo()); // Código del proveedor
                    stmtUpdate.setString(8, productoModificado.getMarca().getCodigo());     // Código de la marca
                    stmtUpdate.setString(9, productoModificado.getPathImagen());           // Ruta de la imagen
                    stmtUpdate.setString(10, codigoProducto); // Código del producto que se está modificando

                    // Ejecutar la actualización
                    int affectedRows = stmtUpdate.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Producto con código " + codigoProducto + " actualizado exitosamente.");
                    } else {
                        System.out.println("No se pudo actualizar el producto con código " + codigoProducto);
                    }
                }
            } else {
                System.out.println("El código del producto no puede ser nulo o vacío.");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    @Override
    public Producto obtenerProducto(String codigoProducto, String nombreProducto, String codigoCategoria,
            String codigoModelo, String codigoProveedor, String codigoMarca, int cantidad) {

        Producto producto = null;
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM productos WHERE 1=1");
        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            // Construcción dinámica de la consulta SQL
            if (codigoProducto != null && !codigoProducto.isEmpty()) {
                sqlQuery.append(" AND codigo = ?");
                param.put(index++, codigoProducto);
            }

            if (nombreProducto != null && !nombreProducto.isEmpty()) {
                sqlQuery.append(" AND nombre LIKE ?");
                param.put(index++, "%" + nombreProducto + "%");
            }

            if (codigoCategoria != null && !codigoCategoria.isEmpty()) {
                sqlQuery.append(" AND codigo_categoria = ?");
                param.put(index++, codigoCategoria);
            }

            if (codigoModelo != null && !codigoModelo.isEmpty() && !codigoModelo.equals("0")) {
                sqlQuery.append(" AND codigo_modelo = ?");
                param.put(index++, codigoModelo);
            }

            if (codigoProveedor != null && !codigoProveedor.isEmpty() && !codigoProveedor.equals("0")) {
                sqlQuery.append(" AND codigo_proveedor = ?");
                param.put(index++, codigoProveedor);
            }

            if (codigoMarca != null && !codigoMarca.isEmpty() && !codigoMarca.equals("0")) {
                sqlQuery.append(" AND codigo_marca = ?");
                param.put(index++, codigoMarca);
            }

            if (cantidad > 0) {
                sqlQuery.append(" AND cantidad = ?");
                param.put(index++, cantidad);
            }

            for (Map.Entry<Integer, Object> entry : param.entrySet()) {
                System.out.println("Índice: " + entry.getKey() + ", Valor: " + entry.getValue());
            }

            // Ejecutar la consulta con los parámetros dinámicos
            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param);

            // Inicialización de los DAOs para obtener las relaciones
            CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
            ModeloDaoImpl modeloDao = new ModeloDaoImpl();
            ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();
            MarcaDaoImpl marcaDao = new MarcaDaoImpl();  // Agregar DAO de Marca

            if (rs.next()) {
                // Recuperar los datos básicos del producto
                String codigoProductoDb = rs.getString("codigo");
                String nombreProductoDb = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
                int cantidadDb = rs.getInt("cantidad");
                String codigoCategoriaDb = rs.getString("codigo_categoria");
                String codigoModeloDb = rs.getString("codigo_modelo");
                String codigoProveedorDb = rs.getString("codigo_proveedor");
                String pathImagen = rs.getString("path_imagen");

                // Obtener las relaciones desde los DAOs
                Categoria categoria = categoriaDao.obtenerCategoria(codigoCategoriaDb, null, null);
                Modelo modelo = modeloDao.obtenerModelo(codigoModeloDb, null, null, null);
                Proveedor proveedor = proveedorDao.obtenerProveedor(codigoProveedorDb, null);
                Marca marca = marcaDao.obtenerMarca(codigoMarca, null);  // Obtener la Marca

                if (categoria == null || modelo == null || proveedor == null || marca == null) {
                    System.out.println("Advertencia: Una relación no se pudo obtener para el producto " + nombreProductoDb);
                }

                // Crear el objeto Producto con la marca incluida
                producto = new Producto(codigoProductoDb, nombreProductoDb, descripcion, categoria, modelo, proveedor, (float) precio, pathImagen, cantidadDb, marca);
                producto.setCodigo(codigoProductoDb);
            } else {
                System.out.println("No se encontró un producto con los parámetros especificados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }

        return producto;
    }

    @Override
    public List<Producto> getProductos(String codigoProducto, String nombreProducto, String codigoCategoria,
            String codigoModelo, String codigoProveedor, int cantidad, String codigoMarca) {
        List<Producto> productos = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM productos WHERE 1=1");
        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            // Construcción dinámica de la consulta SQL
            if (codigoProducto != null && !codigoProducto.isEmpty()) {
                sqlQuery.append(" AND codigo = ?");
                param.put(index++, codigoProducto);
            }

            if (nombreProducto != null && !nombreProducto.isEmpty()) {
                sqlQuery.append(" AND nombre LIKE ?");
                param.put(index++, "%" + nombreProducto + "%");
            }

            if (codigoCategoria != null && !codigoCategoria.isEmpty()) {
                sqlQuery.append(" AND codigo_categoria = ?");
                param.put(index++, codigoCategoria);
            }

            if (codigoModelo != null && !codigoModelo.isEmpty() && !codigoModelo.equals("0")) {
                sqlQuery.append(" AND codigo_modelo = ?");
                param.put(index++, codigoModelo);
            }

            if (codigoProveedor != null && !codigoProveedor.isEmpty() && !codigoProveedor.equals("0")) {
                sqlQuery.append(" AND codigo_proveedor = ?");
                param.put(index++, codigoProveedor);
            }

            if (cantidad > 0) {
                sqlQuery.append(" AND cantidad = ?");
                param.put(index++, cantidad);
            }

            if (codigoMarca != null && !codigoMarca.isEmpty() && !codigoMarca.equals("0")) {
                sqlQuery.append(" AND codigo_marca = ?");
                param.put(index++, codigoMarca);
            }

            // Depuración: Imprimir consulta SQL y parámetros
            System.out.println("Consulta SQL generada: " + sqlQuery.toString());
            System.out.println("Parámetros:");
            for (Map.Entry<Integer, Object> entry : param.entrySet()) {
                System.out.println("Índice: " + entry.getKey() + ", Valor: " + entry.getValue());
            }

            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param);

            // Inicialización de los DAOs para obtener relaciones
            CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
            ModeloDaoImpl modeloDao = new ModeloDaoImpl();
            ProveedorDaoImpl proveedorDao = new ProveedorDaoImpl();

            while (rs.next()) {
                // Recuperar datos básicos del producto
                String codigoProductoDb = rs.getString("codigo");
                String nombreProductoDb = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
                int cantidadDb = rs.getInt("cantidad");
                String codigoCategoriaDb = rs.getString("codigo_categoria");
                String codigoModeloDb = rs.getString("codigo_modelo");
                String codigoProveedorDb = rs.getString("codigo_proveedor");
                String pathImagen = rs.getString("path_imagen");

                // Obtener las relaciones desde los DAOs
                Categoria categoria = categoriaDao.obtenerCategoria(codigoCategoriaDb, null, null);
                Modelo modelo = modeloDao.obtenerModelo(codigoModeloDb, null, null, null);
                Proveedor proveedor = proveedorDao.obtenerProveedor(codigoProveedorDb, null);

                if (categoria == null || modelo == null || proveedor == null) {
                    System.out.println("Advertencia: Una relación no se pudo obtener para el producto " + nombreProductoDb);
                }

                // Crear el objeto Producto
                Producto producto = new Producto(nombreProductoDb, precio, descripcion, cantidadDb, categoria, modelo, proveedor, pathImagen);
                producto.setCodigo(codigoProductoDb);

                // Agregar el producto a la lista
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }

        return productos;
    }

    @Override
    public List<Producto> getProductosComboBox() {
        List<Producto> productos = new ArrayList<>();

        // Actualizamos la consulta SQL para incluir el precio
        String sqlQuery = "SELECT p.codigo, p.nombre, p.precio FROM productos p";

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlQuery);

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio"); // Obtener el precio como double

                // Crear el producto con el precio
                Producto producto = new Producto(codigo, nombre, precio);
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de productos: " + e.getMessage());
        }

        return productos;
    }

    public String getProximoCodigoProducto() {
        String sqlNextCode = "SELECT MAX(id) AS total FROM productos";  // Consulta para obtener el ID más alto de la tabla productos
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);  // Ejecuta la consulta
            if (rs.next()) {
                return "PRO-" + (rs.getInt("total") + 1);  // Si encuentra un máximo, le sumamos 1 y lo formateamos
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el próximo código de producto: " + e.getMessage());
        }

        return "PRO-1";  // Si no hay productos, comenzamos con P-1
    }
}