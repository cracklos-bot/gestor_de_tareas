// ruta base de la api
const API = "http://localhost:8080/api/usuarios";

// cuando se cargue la pantalla, cargar todas las tareas
document.addEventListener('DOMContentLoaded',cargarUsuarios);

// ejecutamos el evento cuando el usuario envia el formulario
document.getElementById("usuarioForm").addEventListener("submit", async (e) =>{
    e.preventDefault(); // evitamos que se recargue la pagina

    // obtenemos los datos ingresados por el usuario
    const id = document.getElementById("usuarioId").value;
    const nombre = document.getElementById("nombre").value;
    const correo = document.getElementById("correo").value;

    // creamos el obj tarea
    const usuario = {
        nombre,
        correo,
    }

    // validamos si es un id nuevo o existente
    const metodo = id ? 'PUT': 'POST';
    const url = id ? `${API}/${id}` : API;

    // hacemos la peticion
    const res = await fetch(url, {
        method: metodo,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(usuario),
    });

    if(res.ok){
         // limpiamos el formulario y actualizamos la tabla
        document.getElementById("usuarioForm").reset();
        cargarUsuarios();   
    }else{
        const error = res.json();
        alert("error: ",error);
    }

});

// funcion para cargar tareas
async function cargarUsuarios() {
    // hacemos la peticion
    const res = await fetch(API);
    // convertimos la respuesta a json
    const usuarios = await res.json();

    // obtenemos el id donde mostraremos las tareas
    const tabla = document.getElementById("tablaUsuarios");
    tabla.innerHTML = ``; // limpiamos la tabla

    usuarios.forEach(usuario => {
        const fila = document.createElement("tr"); // creamos la fila donde ira el registro

        fila.innerHTML = `
            <td>${usuario.nombre}</td>
            <td>${usuario.correo}</td>
            <td>
                <button type="button" class="btn btn-secondary" onClick="editar(${usuario.id},' ${usuario.nombre}','${usuario.correo}')">Editar usuario</button>
                <button type="button" class="btn btn-danger" onClick="eliminarUsuario(${usuario.id})">Elminar usuario</button>
            </td>
        `;

        // agregamos la fila a la tabla
        tabla.appendChild(fila);
    });

}

// funcion para actualizar un usuario
function editar(id,nombre,correo) {
    document.getElementById("usuarioId").value = id;
    document.getElementById("nombre").value =nombre;
    document.getElementById("correo").value = correo;
}

// eliminar un usuario
async function eliminarUsuario(id){
    const res = await fetch(`${API}/${id}`,{
        method: 'DELETE'
    });

    if(res.ok){
        await cargarUsuarios();
    }else{
        alert("no se pudo eliminar el usuario, contacte al administrador.");
    }
}