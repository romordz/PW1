import Entidades.Publicacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/BusquedaAvanzadaServlet"})
public class BusquedaAvanzadaServlet extends HttpServlet {
     @Override
    public void init() throws ServletException {
        super.init();
        // Aquí se puede realizar cualquier inicialización necesaria, como la conexión a la base de datos
        List<Publicacion> publicaciones = obtenerTodasLasPublicaciones();
        getServletContext().setAttribute("publicacion", publicaciones);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("from");
        String advancedText = request.getParameter("advancedText");
        String advancedCategory = request.getParameter("advancedCategory");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        System.out.println("advancedText:" + advancedText);
System.out.println("advancedCategory:" + advancedCategory);
System.out.println("startDate:" + startDate);
System.out.println("endDate:" + endDate);
         List<Publicacion> publicaciones;
    
    if (advancedText != null || advancedCategory != null || startDate != null || endDate != null) {
    publicaciones = obtenerPublicacionesFiltradas(advancedText, advancedCategory, startDate, endDate);
    } else {
    publicaciones = obtenerTodasLasPublicaciones();
    }

    
    request.setAttribute("publicacion", publicaciones);
    
    if ("home".equals(from)) {
        request.getRequestDispatcher("home.jsp").forward(request, response);
    } else if ("publicaciones".equals(from)) {
        request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
    }
    }
    
    public List<Publicacion> obtenerPublicacionesFiltradas(String advancedText, String advancedCategory, String startDate, String endDate) {
        List<Publicacion> publicaciones = new ArrayList<>();
        
        String sql = "SELECT * FROM Publicacion WHERE 1=1";
        List<Object> params = new ArrayList<>();
        
        if (advancedText != null && !advancedText.trim().isEmpty()) {
            sql += " AND (Titulo LIKE ? OR Contenido LIKE ?)";
            params.add("%" + advancedText.trim() + "%");
            params.add("%" + advancedText.trim() + "%");
        }
        
        if (advancedCategory != null && !advancedCategory.trim().isEmpty()) {
            sql += " AND Categoria = ?";
            params.add(advancedCategory.trim());
        }
        
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql += " AND FechaPublicacion >= ?";
            params.add(startDate.trim());
        }
        
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql += " AND FechaPublicacion <= ?";
            params.add(endDate.trim());
        }
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb", "root", "abc123");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            int parameterIndex = 1;
            for (Object param : params) {
                pstmt.setObject(parameterIndex++, param);
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Publicacion publicacion = new Publicacion();
    publicacion.setTitulo(rs.getString("Titulo"));
    publicacion.setContenido(rs.getString("Contenido"));
    publicacion.setFechaPublicacion(rs.getDate("FechaPublicacion"));
    publicacion.setCategoria(rs.getString("Categoria"));
    publicaciones.add(publicacion);
    
    System.out.println("Titulo: " + publicacion.getTitulo());
    System.out.println("Contenido: " + publicacion.getContenido());
    System.out.println("Fecha de Publicacion: " + publicacion.getFechaPublicacion());
    System.out.println("Categoria: " + publicacion.getCategoria());
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return publicaciones;
    }
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        List<Publicacion> publicaciones = new ArrayList<>();
        
        String sql = "SELECT * FROM Publicacion";
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb", "root", "abc123");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
    Publicacion publicacion = new Publicacion();
    publicacion.setTitulo(rs.getString("Titulo"));
    publicacion.setContenido(rs.getString("Contenido"));
    publicacion.setFechaPublicacion(rs.getDate("FechaPublicacion"));
    publicacion.setCategoria(rs.getString("Categoria"));
    publicaciones.add(publicacion);

    // Imprimir los datos recuperados en la consola
    System.out.println("Titulo: " + publicacion.getTitulo());
    System.out.println("Contenido: " + publicacion.getContenido());
    System.out.println("Fecha de Publicacion: " + publicacion.getFechaPublicacion());
    System.out.println("Categoria: " + publicacion.getCategoria());
}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return publicaciones;
    }
}