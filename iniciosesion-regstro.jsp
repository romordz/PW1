<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Red Social</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+6pB0a7R1JZrA6KK6p0pc1U5Azg8fhFfp9XBOjB" crossorigin="anonymous">
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <div class="row justify-content-center align-items-center min-vh-100">
      <!-- Formulario de inicio de sesión -->
      <div class="col-md-6">
        <form id="loginForm" class="active-form" action="Servlet" method="post"  onsubmit="return validarInicioSesion()">
          <h2>Iniciar sesión</h2>
          <div class="mb-3">
            <label for="loginUsername" class="form-label">Nombre de usuario:</label>
            <input type="text" id="loginUsername" name="usuario" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="loginPassword" class="form-label">Contraseña:</label>
            <input type="password" id="loginPassword" name="contraseña" class="form-control" required>
          </div>
          <button type="submit" href="Servlet" class="btn btn-primary">Iniciar sesión</button>
          <p class="mt-3">¿No tienes una cuenta? <a href="#" onclick="switchForm()">Regístrate</a></p>
        </form>
      </div>
      <!-- Formulario de registro -->
      <div class="col-md-6">
        <form id="registroForm" action="RegistroServlet" method="post">
          <h2>Registro</h2>
          <div class="mb-3">
            <label for="Nombre" class="form-label">Nombre:</label>
            <input type="text" id="Nombre" name="Nombre" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="SegundoNombre" class="form-label">Segundo nombre:</label>
            <input type="text" id="SegundoNombre" name="SegundoNombre" class="form-control">
          </div>
          <div class="mb-3">
            <label for="Apellido" class="form-label">Apellido Paterno:</label>
            <input type="text" id="Apellido" name="Apellido" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="Apellido" class="form-label">Apellido Materno:</label>
            <input type="text" id="Apellidomat" name="Apellidomat" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="FechaNacimiento" class="form-label">Fecha de nacimiento:</label>
            <input type="date" id="FechaNacimiento" name="FechaNacimiento" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="CorreoElectronico" class="form-label">Correo electrónico:</label>
            <input type="email" id="CorreoElectronico" name="CorreoElectronico" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="postImage" class="form-label">Imagen de perfil:</label>
            <input type="file" id="postImage" name="postImage" accept="image/*" class="form-control" required>
          </div>
          <div id="imagePreviewContainer" class="mb-3">
            <img id="imagePreview" class="img-thumbnail" style="max-width: 400px; max-height: 400px;">
        </div>
          <div class="mb-3">
            <button id="toggleImageButton" onclick="toggleImagePreview()" class="btn btn-primary">👁️Mostrar Vista Previa</button>
          </div>
          <div class="mb-3">
            <label for="NombreUsuario" class="form-label">Nombre de usuario:</label>
            <input type="text" id="NombreUsuario" name="NombreUsuario" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="Contrasena" class="form-label">Contraseña:</label>
            <input type="password" id="Contrasena" name="Contrasena" class="form-control" required>
          </div>
          <div class="mb-3">
            <label for="confirmPassword" class="form-label">Confirmar contraseña:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
          </div>
          <button type="submit" href="RegistroServlet" class="btn btn-primary">Registrarse</button>
          <p class="mt-3">¿Ya tienes una cuenta? <a href="#" onclick="switchForm()">Inicia sesión</a></p>
        </form>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-k3LtVWSVvm4jPR2wL4YmFK8f4ihFb9bBbcZxIaEuFpI0WWikd6tgLjSu2kd0vXfg" crossorigin="anonymous"></script>
  <script src="script.js"></script>
</body>
</html>
