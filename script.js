let totalPosts = 0;
const postsPerPage = 10;
let currentPage = 1;

document.addEventListener('DOMContentLoaded', function () {
  const editProfileButton = document.getElementById('editProfileButton');
  const editProfileContainer = document.getElementById('editProfileContainer');

  editProfileButton.addEventListener('click', function () {
    editProfileContainer.style.display = editProfileContainer.style.display === 'none' ? 'block' : 'none';
  });

  const saveProfileChangesButton = document.getElementById('saveProfileChanges');
  saveProfileChangesButton.addEventListener('click', function () {
    console.log('Guardando cambios en el perfil...');
  });
});

document.getElementById('navbar-placeholder').innerHTML = '<object type="text/html" data="navbar.jsp"></object>';
function switchForm() {
  document.getElementById('loginForm').classList.toggle('active-form');
  document.getElementById('registroForm').classList.toggle('active-form');
}

function logout() {
  window.location.href = "iniciosesion-regstro.jsp";
}

function loginUser() {
  const isLoggedIn = true;

  if (isLoggedIn) {
    window.location.href = 'home.jsp';
  }
}
document.getElementById('editFotoPerfil').addEventListener('change', function(event) {
            const file = event.target.files[0];
            const preview = document.getElementById('fotoPerfilPreview');

            if (file) {
                const reader = new FileReader();

                reader.onload = function(e) {
                    preview.src = e.target.result;
                }

                reader.readAsDataURL(file);
            } else {
                preview.src = "#";
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

  const nameRegex = /^[A-Za-záéíóúüñÁÉÍÓÚÜÑ\s]+$/;
  if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
    alert('Ingresa solo caracteres alfabéticos para Nombre(s) y Apellidos.');
    return false;
  }

  const currentDate = new Date();
  if (new Date(birthdate) > currentDate) {
    alert('La Fecha de Nacimiento no puede ser posterior a la fecha actual.');
    return false;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    alert('Ingresa un correo electrónico válido.');
    return false;
  }

  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}$/;
  if (!passwordRegex.test(password)) {
    alert('La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un signo de puntuación.');
    return false;
  }

  if (password !== confirmPassword) {
    alert('La contraseña y la confirmación de contraseña deben coincidir.');
    return false;
  }

  alert('Registro exitoso. Redirigiendo a la página principal.');
  window.location.href = 'home.jsp';
  return true;
}

function validarInicioSesion() {
    var usuario = document.getElementById("loginUsername").value;
    var contraseña = document.getElementById("loginPassword").value;

    if (usuario.trim() === "" || contraseña.trim() === "") {
      alert("Por favor, complete todos los campos.");
      return false;
    }

    return true;
  }
  
function toggleImagePreview() {
  const imagePreview = document.getElementById('imagePreview');

  if (imagePreview.style.display === 'none' || !imagePreview.src) {
    showImagePreview();
  } else {
    imagePreview.style.display = 'none';
    document.getElementById('toggleImageButton').textContent = '👁️Mostrar Vista Previa';
  }
}

function showImagePreview() {
  const imageInput = document.getElementById('postImage');
  const imagePreview = document.getElementById('imagePreview');
  const toggleButton = document.getElementById('toggleImageButton');

  if (imageInput.files && imageInput.files[0]) {
    const selectedFile = imageInput.files[0];
    const image = URL.createObjectURL(selectedFile);
    imagePreview.src = image;
    imagePreview.style.display = 'block';
    toggleButton.textContent = '👁️Ocultar Vista Previa';
  } else {
    imagePreview.style.display = 'none';
    toggleButton.textContent = '👁️Mostrar Vista Previa';
  }
}



function createPost() {
  const title = document.getElementById('postTitle').value;
  const content = document.getElementById('postContent').value;
  const imageInput = document.getElementById('postImage');
  const category = document.getElementById('postCategory').value;

  if (!title || !content || !category) {
    alert('Todos los campos marcados con (*) son obligatorios.');
    return;
  }

  let image = '';
  if (imageInput.files.length > 0) {
    const selectedFile = imageInput.files[0];
    image = URL.createObjectURL(selectedFile);
  }
  document.getElementById('createForm').reset();

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

  postList.appendChild(postElement);
}

function editPost(button) {
  const postElement = button.closest('.post');

  if (!postElement) {
    console.error('No se pudo encontrar el elemento de la publicación.');
    return;
  }

  const titleElement = postElement.querySelector('h3');
  const contentElement = postElement.querySelector('p');
  const imageElement = postElement.querySelector('img');
  const categoryElement = postElement.querySelector('p:last-child');

  const titleInput = document.createElement('input');
  titleInput.value = titleElement.innerText;

  const contentInput = document.createElement('textarea');
  contentInput.value = contentElement.innerText;

  const imageInput = document.createElement('input');
  imageInput.value = imageElement ? imageElement.src : '';

  const categoryInput = document.createElement('input');
  categoryInput.value = categoryElement.innerText.replace('Categoría: ', '');

  titleElement.classList.add('hidden');
  contentElement.classList.add('hidden');
  imageElement && imageElement.classList.add('hidden');
  categoryElement.classList.add('hidden');

  postElement.insertBefore(titleInput, postElement.firstChild);
  postElement.insertBefore(contentInput, postElement.firstChild);
  imageElement && postElement.insertBefore(imageInput, postElement.firstChild);
  postElement.insertBefore(categoryInput, postElement.firstChild);

  const saveButton = document.createElement('button');
  saveButton.textContent = 'Guardar';
  saveButton.addEventListener('click', () => saveChanges(postElement));

  postElement.appendChild(saveButton);
}


function saveChanges(postElement) {
  const titleInput = postElement.querySelector('input');
  const contentInput = postElement.querySelector('textarea');
  const imageInput = postElement.querySelector('input:nth-child(3)');
  const categoryInput = postElement.querySelector('input:last-child');

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

  postElement.querySelector('h3').innerText = title;
  postElement.querySelector('p').innerText = content;

  if (image) {
    const imgElement = postElement.querySelector('img') || document.createElement('img');
    imgElement.src = image;
    imgElement.alt = 'Imagen de la publicación';
    postElement.insertBefore(imgElement, postElement.querySelector('p:last-child'));
  }

  postElement.querySelector('p:last-child').innerText = `Categoría: ${category}`;

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

function viewProfile(button) {
  const postElement = button.closest('.post');

  if (!postElement) {
    console.error('No se pudo encontrar el elemento de la publicación.');
    return;
  }

  const username = postElement.querySelector('.post-header h3').innerText;
  const fullName = postElement.querySelector('.post-header p').innerText;

  const profileContainer = document.createElement('div');
  profileContainer.classList.add('profile-container');

  profileContainer.innerHTML = `
    <h2>${fullName}</h2>
    <p>Nombre de usuario: ${username}</p>
    <!-- Aquí puedes incluir más detalles del perfil, como la foto de perfil, correo electrónico, edad, etc. -->
    <button onclick="editProfile(this)">Editar Perfil</button>
  `;

  postElement.innerHTML = '';
  postElement.appendChild(profileContainer);
}


function editProfile(button) {
  const profileContainer = button.closest('.profile-container');

  if (!profileContainer) {
    console.error('No se pudo encontrar el contenedor del perfil.');
    return;
  }

  const username = profileContainer.querySelector('.profile-info h2').innerText;
  const fullName = profileContainer.querySelector('.profile-info p:nth-of-type(1)').innerText;

  const editProfileContainer = document.createElement('div');
  editProfileContainer.classList.add('profile-container');

  editProfileContainer.innerHTML = `
    <div class="profile-info">
      <label for="editUsername">Nombre de usuario:</label>
      <input type="text" id="editUsername" value="${username}" required>
    </div>
    <button onclick="saveProfileChanges(this)">Guardar Cambios</button>
  `;
  profileContainer.innerHTML = '';
  profileContainer.appendChild(editProfileContainer);
}

function saveProfileChanges(button) {
  const profileContainer = document.querySelector('.profile-container');

  if (!profileContainer) {
    console.error('No se pudo encontrar el contenedor del perfil.');
    return;
  }

  const editedUsername = profileContainer.querySelector('#editUsername').value;
  console.log('Guardando cambios en el perfil:', { editedUsername });

}

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

function generatePaginationButtons() {
  const paginationPages = document.getElementById('paginationPages');

  if (!paginationPages) {
    console.error('El elemento paginationPages no se encontró en el documento.');
    return;
  }

  const totalPpages = Math.ceil(totalPosts / postsPerPage);

  paginationPages.innerHTML = '';

  for (let i = 1; i <= totalPpages; i++) {
    const button = document.createElement('button');
    button.textContent = i;
    button.onclick = () => goToPage(i);
    paginationPages.appendChild(button);
  }
}

function goToPage(pageNumber) {
  currentPage = pageNumber;
  showPosts();
}

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

showPosts();
generatePaginationButtons();

function validarFormulario() {
    var searchTerm = document.getElementById("searchTerm").value;
    if (searchTerm.trim() === "") {
        alert("Por favor ingresa un término de búsqueda.");
        return false;
    }
    return true;
}

function validarFormularioAvanzado() {
    var advancedText = document.getElementById("advancedText").value;
    var advancedCategory = document.getElementById("advancedCategory").value;
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;

    if (advancedText.trim() === "" && advancedCategory.trim() === "" && startDate.trim() === "" && endDate.trim() === "") {
        alert("Por favor ingresa al menos un criterio de búsqueda.");
        window.location.href = "home.jsp";
        return false;
    }

    return true;
}

