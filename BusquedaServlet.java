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

@WebServlet(urlPatterns = {"/BusquedaServlet"})
public class BusquedaServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        List<Publicacion> publicaciones = obtenerTodasLasPublicaciones();
        getServletContext().setAttribute("publicaciones", publicaciones);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String from = request.getParameter("from");
    String searchTerm = request.getParameter("searchTerm");
    List<Publicacion> publicaciones = new ArrayList<>();

    if (searchTerm == null) {
        response.sendRedirect("home.jsp");
    }
    else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Publicacion WHERE Titulo LIKE ?");
            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                // Agregar resultados de la búsqueda a la lista de publicaciones
                Publicacion publicacion = new Publicacion();
                publicacion.setTitulo(rs.getString("Titulo"));
                publicacion.setContenido(rs.getString("Contenido"));
                publicacion.setFechaPublicacion(rs.getDate("FechaPublicacion"));
                publicacion.setCategoria(rs.getString("Categoria"));
                publicaciones.add(publicacion);
            }

            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
     request.setAttribute("publicacion", publicaciones);
     
    if ("home".equals(from)) {
        request.getRequestDispatcher("home.jsp").forward(request, response);
    } else if ("publicaciones".equals(from)) {
        request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
    }
    
    }
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        List<Publicacion> publicaciones = new ArrayList<>();
        
        String sql = "SELECT * FROM Publicacion WHERE Titulo LIKE ?";
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb", "root", "abc123");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            
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
