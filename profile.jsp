<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Mi perfil</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css">
  <link rel="stylesheet" href="styles-profile.css">
</head>
<body>
  <header class="page-header" style="height: 300px;">
    <h1>Perfil de <%= session.getAttribute("usuario") %></h1>
    <iframe src="navbar.jsp" style="width: 100%; height: 290px; border: none;"></iframe>
     <button onclick="window.history.back()" class="back-button" style="background-color: #4267b2; color: #fff; padding: 12px; border: none; border-radius: 6px; cursor: pointer; transition: background-color 0.2s ease-in-out; position: absolute; left: 20px; top: 20px;">Volver</button>
</header>
  
  <div class="container">
    <div class="profile-container">
      <div class="profile-picture">
        <img src="profile.jpg" alt="Foto de perfil" class="rounded-circle img-thumbnail">
      </div>
      <div class="profile-info">
        <h2><%= session.getAttribute("usuario") %></h2>
        <p>Nombre Completo: <%= session.getAttribute("nombreCompleto") %></p>
        <p>Correo electrónico: <%= session.getAttribute("correoElectronico") %></p>
        <p>Edad: <%= session.getAttribute("edad") %> años</p>
        <button class="btn btn-primary" id="editProfileButton">Editar Perfil</button>
      
        <!-- Contenedor para campos de edición de perfil (inicialmente oculto) -->
        <div id="editProfileContainer" style="display: none;">
          <div class="mb-3">
            <label for="editUsername">Nombre de usuario:</label>
            <input type="text" id="editUsername" class="form-control">
          </div>
          <div class="mb-3">
            <label for="editFullName">Nombre Completo:</label>
            <input type="text" id="editFullName" class="form-control">
          </div>
          <div class="mb-3">
            <label for="editEmail">Correo electrónico:</label>
            <input type="email" id="editEmail" class="form-control">
          </div>
          <button class="btn btn-primary" id="saveProfileChanges">Guardar Cambios</button>
        </div>
      </div>
      
    </div>
  </div>
        
   <aside class="bg-light p-4">
    <div class="perfil-container">
        <!-- Mostrar la foto de perfil -->
        <a href="publicaciones.jsp">
            <img src="profile.jpg" alt="Foto de perfil" class="rounded-circle" style="width: 100px;">
        </a>
        <!-- Mostrar el nombre de usuario -->
        <h2 class="mb-0"><%= session.getAttribute("usuario") %></h2>
    </div>
   </aside>

  <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
  <script src="script.js"></script>
</body>
</html>
