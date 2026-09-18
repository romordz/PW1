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

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String Nombre = request.getParameter("Nombre");
        String SegundoNombre = request.getParameter("SegundoNombre");
        String Apellido = request.getParameter("Apellido"); 
        String Apellidomat = request.getParameter("Apellidomat");
        String FechaNacimiento = request.getParameter("FechaNacimiento");
        String CorreoElectronico = request.getParameter("CorreoElectronico");
        String NombreUsuario = request.getParameter("NombreUsuario");
        String Contrasena = request.getParameter("Contrasena");
        
        if (Nombre == null || Nombre.isEmpty() || Apellido == null || Apellido.isEmpty() ||
            Apellidomat == null || Apellidomat.isEmpty() || FechaNacimiento == null || FechaNacimiento.isEmpty() || CorreoElectronico == null || CorreoElectronico.isEmpty() ||
            NombreUsuario == null || NombreUsuario.isEmpty() || Contrasena == null || Contrasena.isEmpty()) {
            out.println("Todos los campos son obligatorios.");
            return;
        }

        LocalDate fechaAlta = LocalDate.now();
        LocalDate fechaNacimiento = LocalDate.parse(FechaNacimiento);
        
        try {
            LocalDate fechaActual = LocalDate.now();
            int edad = Period.between(fechaNacimiento, fechaActual).getYears();
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
    
    PreparedStatement pstmt = con.prepareStatement("INSERT INTO Usuario (NombreUsuario, Nombre, FechaNacimiento, CorreoElectronico, FechaAlta, Contrasena, SegundoNombre, Apellido, Edad, Apellidomat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    pstmt.setString(1, NombreUsuario);
    pstmt.setString(2, Nombre);
    pstmt.setString(3, FechaNacimiento);
    pstmt.setString(4, CorreoElectronico);
    pstmt.setObject(5, fechaAlta);
    pstmt.setString(6, Contrasena);
    pstmt.setString(7, SegundoNombre);
    pstmt.setString(8, Apellido);
    pstmt.setInt(9, edad);
    pstmt.setString(10, Apellidomat);
    
    int rowsAffected = pstmt.executeUpdate();
    
    if (rowsAffected > 0) {
        response.sendRedirect("iniciosesion-regstro.jsp");
    } else {
        out.println("Error al registrar el usuario");
    }
    
    con.close();
} catch (SQLException e) {
    e.printStackTrace();
    out.println("Error SQL al registrar el usuario: " + e.getMessage());
} catch (ClassNotFoundException e) {
    e.printStackTrace();
    out.println("Error al cargar el controlador de la base de datos: " + e.getMessage());
} catch (Exception e) {
    e.printStackTrace();
    out.println("Error inesperado al registrar el usuario: " + e.getMessage());
}
    }
}
