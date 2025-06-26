// ruta base de los usuarios y tareas
const API_USUARIOS = "http://localhost:8080/api/usuarios";
const API_TAREAS = "http://localhost:8080/api/tareas";

// ejecutamos al cargar la pantalla
document.addEventListener("DOMContentLoaded", async () =>{
    await cargarUsuarios(); // cargamos todos los usuarios
    await cargarTareas(); // cargamos todas las tareas
});

// escuchamos el envio del formulario por el usuario
document.getElementById("tareaForm").addEventListener("submit", async (e) =>{
    e.preventDefault(); // evitamos que la pantalla se recargue

    // obtenemos los datos del formulario
    const id = document.getElementById("id").value;
    const titulo = document.getElementById("titulo").value;
    const descripcion = document.getElementById("descripcion").value;
    const idUsuario = document.getElementById("usuarioId").value;

    // creamos el obj tarea
    const tarea = {
        id,
        titulo,
        descripcion,
        completada: false, // por defecto no esta completada aun
        usuario: {id:idUsuario}, // asociamos la tarea con el usuario id
    }

    // si hay ID actualizamos sino, creamos
    const metodo = id ? "PUT": "POST";
    const url = id ? `${API_TAREAS}/${id}`:API_TAREAS;

    // hacemos la peticion
    const res = await fetch(url,{
        method: metodo,
        headers: {"Content-type":"application/json"},
        body: JSON.stringify(tarea),
    });

    document.getElementById("tareaForm").reset();
    await cargarTareas();
});

// funcion para cargar usuarios en el select
async function cargarUsuarios() {
    const res = await fetch(API_USUARIOS); // hacemos la peticion
    const usuarios = await res.json(); // la respuesta la convertimos en json

    const select = document.getElementById("usuarioId"); // obtenemos el id del select
    select.innerHTML = `<option value="">--Selecciona un usuario--</option>`; // asginamos un valor default

    usuarios.forEach(usuario => {
        const option = document.createElement("option"); // creamos una etiqueta option
        option.value = usuario.id; // le asignamos value
        option.textContent = usuario.nombre; // le asignamos contexto
        select.appendChild(option);
    });
}

// Función para cargar y mostrar tareas en la tabla
async function cargarTareas() {
    const res = await fetch(API_TAREAS);
    const tareas = await res.json();

    const tabla = document.getElementById("tablaTareas"); // obtenemos la tabla
    tabla.innerHTML = ``; // limpiamos la tabla

    tareas.forEach(tarea =>{
        const fila = document.createElement("tr"); // creamos una fila

        fila.innerHTML= `
            <td>${tarea.id}</td>
            <td>${tarea.titulo}</td>
            <td>${tarea.descripcion}</td>
            <td>${tarea.completada ? "✅" : "❌"}</td>
            <td>
                <button type="button" class="btn btn-secondary" onClick="editar(${tarea.id})">Editar Tarea</button>
                <button type="button" class="btn btn-danger" onClick="eliminar(${tarea.id})">Eliminar Tarea</button>
                <button type="button" class="btn btn-primary" onClick="completarTarea(${tarea.id})">
                    ${tarea.completada ? "Desmarcar" : "Marcar como completada"}
                </button>
            </td>
        `;

        tabla.appendChild(fila);
    });

}

// funcion para eliminar
async function eliminar(id) {
    if(confirm("seguro que desea eliminar esta tarea? ")){
        await fetch(`${API_TAREAS}/${id}`,{
            method: "DELETE"
        });
        await cargarTareas();
    }
}

// funcion para editar
async function editar(id) {
    
    const res = await fetch(`${API_TAREAS}/${id}`);
    const tarea = await res.json();

    // asignamos los valores nuevos 
    document.getElementById("id").value = tarea.id;
    document.getElementById("titulo").value = tarea.titulo;
    document.getElementById("descripcion").value = tarea.descripcion;
    document.getElementById("usuarioId").value = tarea.usuario?.id || "";
}

// funcion para completar tarea
async function completarTarea(id) {
    // hacemos la peticion con el metodo put que actualizará el valor de completada a true o false
    const res = await fetch(`${API_TAREAS}/${id}/completar`,{
        method: "PUT"
    });

    // validamos la respuesta
    if(res.ok){
        await cargarTareas();
    }else{
        alert("no se pudo completar la tarea, contacte al administrador.");
    }
}

