function switchForm() {
  document.getElementById('loginForm').classList.toggle('active-form');
  document.getElementById('registroForm').classList.toggle('active-form');
}

function logout() {
  window.location.href = "iniciosesion-regstro.html";
}

//login
function loginUser() {
  const isLoggedIn = true;

  if (isLoggedIn) {
    window.location.href = 'home.html';
  }
}

//Registro
const profileImageInput = document.getElementById('profileImage');
// Obtener el elemento de vista previa de la imagen
const imagePreview = document.getElementById('imagePreview');

// Agregar un event listener para el cambio en el input de la imagen
profileImageInput.addEventListener('change', function() {
  // Verificar si se seleccionó una imagen
  if (this.files && this.files[0]) {
    // Crear un objeto URL para la imagen seleccionada
    const reader = new FileReader();
    reader.onload = function(e) {
      // Actualizar el atributo src de la imagen de vista previa con la URL de la imagen seleccionada
      imagePreview.src = e.target.result;
    }
    // Leer el archivo seleccionado como una URL de datos
    reader.readAsDataURL(this.files[0]);
  }
});

function validateRegistration() {
  const firstName = document.getElementById('firstName').value;
  const lastName = document.getElementById('lastName').value;
  const birthdate = document.getElementById('birthdate').value;
  const email = document.getElementById('email').value;
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const confirmPassword = document.getElementById('confirmPassword').value;

  // Validación de caracteres alfabéticos para Nombre(s) y Apellidos
  const nameRegex = /^[A-Za-záéíóúüñÁÉÍÓÚÜÑ\s]+$/;
  if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
    alert('Ingresa solo caracteres alfabéticos para Nombre(s) y Apellidos.');
    return false;
  }

  // Validación de fecha de nacimiento
  const currentDate = new Date();
  if (new Date(birthdate) > currentDate) {
    alert('La Fecha de Nacimiento no puede ser posterior a la fecha actual.');
    return false;
  }

  // Validación de formato de correo electrónico
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    alert('Ingresa un correo electrónico válido.');
    return false;
  }

  // Validación de la contraseña
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}$/;
  if (!passwordRegex.test(password)) {
    alert('La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un signo de puntuación.');
    return false;
  }

  // Validación de coincidencia de contraseña y confirmación
  if (password !== confirmPassword) {
    alert('La contraseña y la confirmación de contraseña deben coincidir.');
    return false;
  }

  // Si todas las validaciones son exitosas, puedes enviar el formulario
  alert('Registro exitoso. Redirigiendo a la página principal.');
  window.location.href = 'home.html';
  return true;
}

//publicaciones
function createPost() {
  const title = document.getElementById('postTitle').value;
  const content = document.getElementById('postContent').value;
  const imageInput = document.getElementById('postImage');
  const category = document.getElementById('postCategory').value;

  // Validar que los campos requeridos estén completos
  if (!title || !content || !category) {
    alert('Todos los campos marcados con (*) son obligatorios.');
    return;
  }

  let image = '';
  if (imageInput.files.length > 0) {
    // Si se seleccionó un archivo, usa el primer archivo
    const selectedFile = imageInput.files[0];
    image = URL.createObjectURL(selectedFile);
  }

  // Limpiar el formulario después de crear la publicación
  document.getElementById('createForm').reset();

  // Crear un nuevo elemento de publicación
  const postElement = document.createElement('div');
  postElement.className = 'post';
  postElement.innerHTML = `
    <h3>${title}</h3>
    <p>${content}</p>
    ${image ? `<img src="${image}" alt="Imagen de la publicación">` : ''}
    <p>Categoría: ${category}</p>
    <button onclick="editPost(this)">Editar</button>
    <button onclick="deletePost(this)">Eliminar</button>
  `;

  // Agregar la nueva publicación a la lista
  postList.appendChild(postElement);
}

function editPost(button) {
  const postElement = button.closest('.post');

  if (!postElement) {
    console.error('No se pudo encontrar el elemento de la publicación.');
    return;
  }

  // Obtener elementos de la publicación
  const titleElement = postElement.querySelector('h3');
  const contentElement = postElement.querySelector('p');
  const imageElement = postElement.querySelector('img');
  const categoryElement = postElement.querySelector('p:last-child');

  // Crear campos de entrada editables
  const titleInput = document.createElement('input');
  titleInput.value = titleElement.innerText;

  const contentInput = document.createElement('textarea');
  contentInput.value = contentElement.innerText;

  const imageInput = document.createElement('input');
  imageInput.value = imageElement ? imageElement.src : '';

  const categoryInput = document.createElement('input');
  categoryInput.value = categoryElement.innerText.replace('Categoría: ', '');

  // Agregar clases para ocultar/mostrar elementos
  titleElement.classList.add('hidden');
  contentElement.classList.add('hidden');
  imageElement && imageElement.classList.add('hidden');
  categoryElement.classList.add('hidden');

  // Insertar campos de entrada en la publicación
  postElement.insertBefore(titleInput, postElement.firstChild);
  postElement.insertBefore(contentInput, postElement.firstChild);
  imageElement && postElement.insertBefore(imageInput, postElement.firstChild);
  postElement.insertBefore(categoryInput, postElement.firstChild);

  // Crear botón de guardar cambios
  const saveButton = document.createElement('button');
  saveButton.textContent = 'Guardar';
  saveButton.addEventListener('click', () => saveChanges(postElement));

  postElement.appendChild(saveButton);
}

// Obtener el elemento de entrada de imagen
const imageInput = document.getElementById('postImage');
// Obtener el contenedor de vista previa de la imagen
const imagePreviewContainer = document.getElementById('imagePreviewContainer');

// Escuchar el evento de cambio en el elemento de entrada de imagen
imageInput.addEventListener('change', function() {
  // Verificar si se seleccionó un archivo de imagen
  if (imageInput.files && imageInput.files[0]) {
    // Crear un objeto de archivo con el archivo seleccionado
    const file = imageInput.files[0];
    // Crear un objeto de lectura de archivo
    const reader = new FileReader();
    
    // Definir una función de devolución de llamada para cuando se cargue la imagen
    reader.onload = function(e) {
      // Crear un elemento de imagen para mostrar la vista previa
      const imgElement = document.createElement('img');
      // Establecer el atributo de origen de la imagen en la URL de la imagen cargada
      imgElement.src = e.target.result;
      // Agregar la clase de Bootstrap para imágenes responsivas
      imgElement.classList.add('img-fluid');
      // Limpiar el contenido del contenedor de vista previa de la imagen
      imagePreviewContainer.innerHTML = '';
      // Agregar la imagen al contenedor de vista previa de la imagen
      imagePreviewContainer.appendChild(imgElement);
    }
    
    // Leer el contenido del archivo como una URL de datos
    reader.readAsDataURL(file);
  }
});

//TODO:hacer que funciones el boton de editar y guardar cambios

function saveChanges(postElement) {
  // Obtener elementos de la publicación
  const titleInput = postElement.querySelector('input');
  const contentInput = postElement.querySelector('textarea');
  const imageInput = postElement.querySelector('input:nth-child(3)');
  const categoryInput = postElement.querySelector('input:last-child');

  // Verificar si los campos de entrada existen
  if (!titleInput || !contentInput || !categoryInput) {
    console.error('No se pudieron encontrar los campos de entrada.');
    return;
  }

  const title = titleInput.value;
  const content = contentInput.value;
  const image = imageInput ? imageInput.value : '';
  const category = categoryInput.value;

  if (!title || !content) {
    console.error('Los campos de entrada no pueden estar vacíos.');
    return;
  }
  console.log('Guardando cambios:', { title, content, image, category });

  // Actualizar elementos de la publicación con los nuevos valores
  postElement.querySelector('h3').innerText = title;
  postElement.querySelector('p').innerText = content;

  if (image) {
    const imgElement = postElement.querySelector('img') || document.createElement('img');
    imgElement.src = image;
    imgElement.alt = 'Imagen de la publicación';
    postElement.insertBefore(imgElement, postElement.querySelector('p:last-child'));
  }

  postElement.querySelector('p:last-child').innerText = `Categoría: ${category}`;

  // Eliminar campos de entrada y mostrar elementos originales
  titleInput.remove();
  contentInput.remove();
  imageInput && imageInput.remove();
  categoryInput.remove();

  postElement.querySelector('h3').classList.remove('hidden');
  postElement.querySelector('p').classList.remove('hidden');
  imageInput && postElement.querySelector('img').classList.remove('hidden');
  postElement.querySelector('p:last-child').classList.remove('hidden');
}

function deletePost(button) {
  const postElement = button.parentNode;
  postElement.remove();
}

// Función para visualizar el perfil
function viewProfile(button) {
  const postElement = button.closest('.post');

  if (!postElement) {
    console.error('No se pudo encontrar el elemento de la publicación.');
    return;
  }

  // Obtener información del usuario desde la publicación
  const username = postElement.querySelector('.post-header h3').innerText;
  const fullName = postElement.querySelector('.post-header p').innerText;

  // Crear contenedor para mostrar la información del perfil
  const profileContainer = document.createElement('div');
  profileContainer.classList.add('profile-container');

  // Mostrar información del perfil
  profileContainer.innerHTML = `
    <h2>${fullName}</h2>
    <p>Nombre de usuario: ${username}</p>
    <!-- Aquí puedes incluir más detalles del perfil, como la foto de perfil, correo electrónico, edad, etc. -->
    <button onclick="editProfile(this)">Editar Perfil</button>
  `;

  // Reemplazar contenido de la publicación con el perfil
  postElement.innerHTML = '';
  postElement.appendChild(profileContainer);
}

// Función para editar el perfil
function editProfile(button) {
  const postElement = button.closest('.post');

  if (!postElement) {
    console.error('No se pudo encontrar el elemento de la publicación.');
    return;
  }

  // Obtener información del usuario desde la publicación
  const username = postElement.querySelector('.post-header h3').innerText;
  const fullName = postElement.querySelector('.post-header p').innerText;

  // Crear contenedor para mostrar el formulario de edición del perfil
  const editProfileContainer = document.createElement('div');
  editProfileContainer.classList.add('profile-container');

  // Mostrar formulario de edición del perfil con información actual
  editProfileContainer.innerHTML = `
    <h2>${fullName}</h2>
    <label for="editUsername">Nombre de usuario:</label>
    <input type="text" id="editUsername" value="${username}" required>

    <!-- Aquí puedes incluir más campos para la edición del perfil -->

    <button onclick="saveProfileChanges(this)">Guardar Cambios</button>
  `;

  // Reemplazar contenido de la publicación con el formulario de edición
  postElement.innerHTML = '';
  postElement.appendChild(editProfileContainer);
}

// Función para guardar cambios en el perfil
function saveProfileChanges(button) {
  const postElement = button.closest('.post');

  if (!postElement) {
    console.error('No se pudo encontrar el elemento de la publicación.');
    return;
  }

  // Obtener valores editados desde el formulario
  const editedUsername = postElement.querySelector('#editUsername').value;
  console.log('Guardando cambios en el perfil:', { editedUsername });

  // Mostrar el perfil actualizado
  viewProfile(postElement.querySelector('button')); // Llamamos a viewProfile para volver a mostrar el perfil después de editar
}

// Variables para la paginación
const postsPerPage = 10;
let currentPage = 1;

// Función para mostrar las publicaciones según la página actual
function showPosts() {
  const allPosts = document.querySelectorAll('.post');
  const startIndex = (currentPage - 1) * postsPerPage;
  const endIndex = startIndex + postsPerPage;

  allPosts.forEach((post, index) => {
    if (index >= startIndex && index < endIndex) {
      post.style.display = 'block';
    } else {
      post.style.display = 'none';
    }
  });
}

// Funciones para la navegación de páginas
function prevPage() {
  if (currentPage > 1) {
    currentPage--;
    showPosts();
  }
}

function nextPage() {
  currentPage++;
  showPosts();
}

// Función para generar los botones de páginas en el paginador
function generatePaginationButtons() {
  // Lógica para calcular el número total de páginas
  const totalPpages = Math.ceil(totalPosts / postsPerPage);

  const paginationPages = document.getElementById('paginationPages');
  paginationPages.innerHTML = '';

  for (let i = 1; i <= totalPpages; i++) {
    const button = document.createElement('button');
    button.textContent = i;
    button.onclick = () => goToPage(i);
    paginationPages.appendChild(button);
  }
}

// Función para ir a una página específica al hacer clic en el botón de página
function goToPage(pageNumber) {
  currentPage = pageNumber;
  showPosts();
}

// Inicializar la paginación al cargar la página
showPosts();
generatePaginationButtons();
