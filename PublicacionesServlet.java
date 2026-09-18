import Entidades.Publicacion;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import Entidades.Publicacion;

@WebServlet(urlPatterns = {"/PublicacionesServlet"})
public class PublicacionesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String titulo = request.getParameter("postTitle");
        String contenido = request.getParameter("postContent");
        String categoria = request.getParameter("postCategory");
        String imagen = "hola";
        boolean estatus = true;
        LocalDate fechaPublicacion = LocalDate.now();

        HttpSession session = request.getSession();
        int usuarioID = (int) session.getAttribute("usuarioID");
        

        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb", "root", "abc123")) {
            String sql = "INSERT INTO Publicacion (Titulo, Contenido, Imagen, Categoria, Estatus, FechaPublicacion, UsuarioID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, titulo);
                pstmt.setString(2, contenido);
                pstmt.setString(3, imagen);
                pstmt.setString(4, categoria);
                pstmt.setBoolean(5, estatus);
                pstmt.setObject(6, fechaPublicacion);
                pstmt.setInt(7, usuarioID);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect("publicaciones.jsp");
                } else {
                    out.println("<p>Error al crear la publicación.</p>");
                }
            }
        } catch (SQLException e) {
            out.println("<p>Error al conectar con la base de datos: " + e.getMessage() + "</p>");
        }
    }
     String obtenerNombreUsuario(int usuarioID) {
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
                    PreparedStatement pstmt = con.prepareStatement("SELECT NombreUsuario FROM Usuario WHERE UsuarioID = ?");
                    pstmt.setInt(1, usuarioID);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        return rs.getString("NombreUsuario");
                    } else {
                        return "Usuario Desconocido";
                    }
                } catch(Exception e) {
                    return "Error al obtener el nombre de usuario";
                }
            }
}
