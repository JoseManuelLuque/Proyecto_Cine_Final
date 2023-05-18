import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.sql.DriverManager

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Cine Josema", icon = painterResource("Icono.png")) {
        App()
    }
}

@Composable
@Preview
fun App() {
    //Credenciales para la conexion con la Base de Daros
    val url = "jdbc:mysql://localhost:3306/PROGRAMACION"
    val usuario = "root"
    val contrasenia = "Alberti956"

    //Colores Usados
    val naranja = Color.hsv(25.0.toFloat(), 1.0.toFloat(), 1.0.toFloat())

    //Resto de Variables
    var ventanaActiva by remember {mutableStateOf("ventanaInicio") }
    var user by remember {mutableStateOf("") }
    var password by remember {mutableStateOf("") }
    var peliActiva by remember {mutableStateOf("") }

    //Conexion con Base de datos
    Class.forName("com.mysql.cj.jdbc.Driver")
    val conexion = DriverManager.getConnection(url, usuario, contrasenia)

    when (ventanaActiva) {
        //Parte Usuario SIN REGISTRAR
        "ventanaInicio" -> {
            MaterialTheme {
                //Caja la cual llevará el contenido de la ventana dentro
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ) {
                    //Imagen de Fondo
                    Image(
                        painter = painterResource("ventanaInicio.png"),
                        contentDescription = "Ventana de Inicio",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().fillMaxSize()
                    )
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Image(
                                painter = painterResource("boton_registrarse.png"),
                                contentDescription = "Boton Registro",
                                modifier = Modifier
                                    .clickable { ventanaActiva = "ventanaRegistro" }
                                    .width(150.dp)
                                    .height(50.dp)
                            )

                            //Espacio sentre las 2 Imagenes
                            Spacer(
                                modifier = Modifier
                                    .padding(35.dp)
                            )
                            Image(
                                painter = painterResource("botonCartelera.png"),
                                contentDescription = "Boton para acceder a la cartelera",
                                modifier = Modifier
                                    .clickable(onClick = {ventanaActiva = "ventanaCartelera"})
                                    .width(150.dp)
                                    .height(50.dp)
                            )

                            Spacer(
                                modifier = Modifier
                                    .padding(35.dp)
                            )
                            Image(
                                painter = painterResource("boton_iniciar_sesion.png"),
                                contentDescription = "Boton Iniciar Sesion",
                                modifier = Modifier
                                    .clickable(onClick = {ventanaActiva = "ventanaInicioSesion"})
                                    .width(150.dp)
                                    .height(50.dp)
                            )

                        }
                    }
                }
            }
        }

        "ventanaRegistro" -> {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                    ) {
                    Image(
                        painter = painterResource("ventanaRegistro.png"),
                        contentDescription = "Ventana de Inicio",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {ventanaActiva = "ventanaInicio"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = user,
                                onValueChange = { user = it },
                                label = { Text("Usuario") }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Contraseña") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                        Row {
                            Image(
                                painter = painterResource("boton_registrarse.png"),
                                contentDescription = "Boton Registrarse",
                                modifier = Modifier
                                    .clickable {
                                        val comando = conexion.prepareStatement("INSERT INTO USUARIOS (Usuario , Contraseña) VALUES (?, ?)")
                                        comando.run {
                                            setString(1, user)
                                            setString(2, password)
                                            executeUpdate()
                                        }
                                        println("Registro de $user correcto")
                                        ventanaActiva = "ventanaInicio"
                                    }
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Image(
                                painter = painterResource("botonLimpiar.png"),
                                contentDescription = "Boton Limpiear Celdas",
                                modifier = Modifier
                                    .clickable { user = ""
                                        password = ""}
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }

        "ventanaInicioSesion" -> {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja),
                ) {
                    Image(
                        painter = painterResource("ventanaInicioSesion.png"),
                        contentDescription = "Ventana de Inicio",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {ventanaActiva = "ventanaInicio"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = user,
                                onValueChange = { user = it },
                                label = { Text("Usuario") }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Contraseña") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                        Row {
                            Image(
                                painter = painterResource("boton_iniciar_sesion.png"),
                                contentDescription = "Boton Iniciar Sesion",
                                modifier = Modifier
                                    .clickable(onClick = {
                                        //Consulta Usuario Cliente
                                        val sql = "SELECT USUARIO FROM USUARIOS WHERE USUARIO = '$user'"
                                        val statement = conexion.createStatement()
                                        val resultSet = statement.executeQuery(sql)

                                        //Consulta Contraseña Cliente
                                        val sql1 = "SELECT CONTRASEÑA FROM USUARIOS WHERE CONTRASEÑA = '$password'"
                                        val statement1 = conexion.createStatement()
                                        val resultSet1 = statement1.executeQuery(sql1)

                                        //Consulta Usuario Admin
                                        val sqlUserAdmin = "SELECT USUARIO FROM ADMIN WHERE USUARIO = '$user'"
                                        val statementUserAdmin = conexion.createStatement()
                                        val resultUserAdmin = statementUserAdmin.executeQuery(sqlUserAdmin)

                                        //Consulta Contraseña Admin
                                        val sqlPassAdmin = "SELECT CONTRASEÑA FROM ADMIN WHERE CONTRASEÑA = '$password'"
                                        val statementPassAdmin = conexion.createStatement()
                                        val resultPassAdmin = statementPassAdmin.executeQuery(sqlPassAdmin)

                                        var userEcontrado = false
                                        var passwordEncontrado = false
                                        var userAdmin = false
                                        var passwordAdmin = false

                                        while (resultSet.next()){
                                            val checkUser = resultSet.getString("Usuario")

                                            if (checkUser == user){
                                                userEcontrado = true
                                            }
                                        }
                                        while (resultSet1.next()){
                                            val checkPass = resultSet1.getString("Contraseña")

                                            if (checkPass == password){
                                                passwordEncontrado = true
                                            }
                                        }

                                        if (userEcontrado && passwordEncontrado){
                                            println("Inicio de sesión completado")
                                            ventanaActiva = "ventanaUsuario"
                                        }

                                        while (resultUserAdmin.next()){
                                            val checkUserAdmin = resultUserAdmin.getString("Usuario")

                                            if (checkUserAdmin == user){
                                                userAdmin = true
                                            }
                                        }
                                        while (resultPassAdmin.next()){
                                            val checkPassAdmin = resultPassAdmin.getString("Contraseña")

                                            if (checkPassAdmin == password){
                                                passwordAdmin = true
                                            }
                                        }

                                        if (userAdmin && passwordAdmin){
                                            println("Bienvenido Admin $user")
                                            ventanaActiva = "ventanaAdmin"
                                        }
                                    })
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Image(
                                painter = painterResource("botonLimpiar.png"),
                                contentDescription = "Boton Limpiear Celdas",
                                modifier = Modifier
                                    .clickable { user = ""
                                        password = ""}
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }

        "ventanaCartelera" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ){
                    Image(
                        painter = painterResource("ventanaCartelera.png"),
                        contentDescription = "Ventana de Cartelera",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {ventanaActiva = "ventanaInicio"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row {
                            var posicionCartelera = 3
                            val sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                            val statement = conexion.createStatement()
                            val resultSet = statement.executeQuery(sql)
                            var portada = ""
                            while (resultSet.next()) {
                                portada = resultSet.getString("PORTADA")
                            }

                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada 1",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )


                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada 2",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )

                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada 3",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(50.dp))
                        Row {
                            val titulo = "Avatar: El sentido del agua"
                            val sql = "SELECT PORTADA FROM PELICULAS WHERE TITULO = '$titulo'"
                            val statement = conexion.createStatement()
                            val resultSet = statement.executeQuery(sql)
                            var portada = ""
                            while (resultSet.next()) {
                                portada = resultSet.getString("PORTADA")
                            }

                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada 4",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )

                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada 5",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )

                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada 6",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )
                        }

                    }
                }
            }
        }

        //Parte Usuario Registrado
        "ventanaUsuario" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.background(naranja).fillMaxSize()
                ){
                    Image(
                        painter = painterResource("ventanaInicio.png"),
                        contentDescription = "Ventana de Inicio",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().fillMaxSize()
                    )
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.background(Color.White)
                    ){
                        Text("Bienvenido $user")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row{
                            Image(
                                painter = painterResource("botonCartelera.png"),
                                contentDescription = "Boton para acceder a la cartelera",
                                modifier = Modifier
                                    .clickable(onClick = {ventanaActiva = "ventanaCarteleraCompra"})
                                    .width(150.dp)
                                    .height(50.dp)
                            )

                            Spacer(
                                modifier = Modifier
                                    .padding(35.dp)
                            )

                            Image(
                                painter = painterResource("botonCerrarSesion.png"),
                                contentDescription = "Boton para cerrar sesion",
                                modifier = Modifier
                                    .clickable(onClick = {ventanaActiva = "ventanaInicio"
                                        user = ""
                                        password = ""})
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }

                    }
                }
            }
        }

        "ventanaVacia" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ){

                }
            }
        }

       /*TODO*/"ventanaCarteleraCompra" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ){
                    Image(
                        painter = painterResource("ventanaCartelera.png"),
                        contentDescription = "Ventana de Cartelera",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {ventanaActiva = "ventanaUsuario"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row {
                            val id = 1
                            val sql = "SELECT PORTADA_URL FROM PELICULAS WHERE ID = '$id'"
                            val statement = conexion.createStatement()
                            val resultSet = statement.executeQuery(sql)
                            var aux = ""
                            while (resultSet.next()) {
                                val portadaUrl = resultSet.getString("PORTADA_URL")
                                aux = portadaUrl
                            }

                            Image(
                                painter = painterResource(aux),
                                contentDescription = "Portada 1",
                                modifier = Modifier
                                    .clickable {
                                        ventanaActiva = "ventanaVacia"
                                        val sql = "SELECT PORTADA_URL FROM PELICULAS WHERE ID = '$id'"
                                        val statement = conexion.createStatement()
                                        val resultSet = statement.executeQuery(sql)
                                        var aux = ""
                                        while (resultSet.next()) {
                                            val portadaUrl = resultSet.getString("TITULO")
                                            aux = portadaUrl
                                        }
                                        peliActiva = aux

                                    }
                                    .size(150.dp)
                            )


                            Image(
                                painter = painterResource(aux),
                                contentDescription = "Portada 2",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )

                            Image(
                                painter = painterResource(aux),
                                contentDescription = "Portada 3",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(50.dp))
                        Row {
                            val id = 1
                            val sql = "SELECT PORTADA_URL FROM PELICULAS WHERE ID = '$id'"
                            val statement = conexion.createStatement()
                            val resultSet = statement.executeQuery(sql)
                            var aux = ""
                            while (resultSet.next()) {
                                val portadaUrl = resultSet.getString("PORTADA_URL")
                                aux = portadaUrl
                            }

                            Image(
                                painter = painterResource(aux),
                                contentDescription = "Portada 1",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )

                            Image(
                                painter = painterResource(aux),
                                contentDescription = "Portada 1",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )

                            Image(
                                painter = painterResource(aux),
                                contentDescription = "Portada 1",
                                modifier = Modifier
                                    .clickable {}
                                    .size(150.dp)
                            )
                        }

                    }
                }
            }
        }

        //Parte Administrativa
        "ventanaAdmin" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.background(naranja)
                ){
                    Image(
                        painter = painterResource("ventanaAdmin.png"),
                        contentDescription = "Ventana Administracion",
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource("botonEliminarUsuario.png"),
                            contentDescription = "Boton para elimniar un usuario",
                            modifier = Modifier
                                .clickable {ventanaActiva = "ventanaEliminarUsuario"
                                }
                                .width(150.dp)
                                .height(50.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(20.dp)
                        )

                        Image(
                            painter = painterResource("botonCerrarSesion.png"),
                            contentDescription = "Boton para cerrar sesion",
                            modifier = Modifier
                                .clickable(onClick = {ventanaActiva = "ventanaInicio"
                                    user = ""
                                    password = ""})
                                .width(150.dp)
                                .height(50.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(20.dp)
                        )

                        Image(
                            painter = painterResource("boton_registrarse.png"),
                            contentDescription = "Boton para registrar nuevo admin",
                            modifier = Modifier
                                .clickable(onClick = {ventanaActiva = "ventanaAñadirAdmin"
                                    user = ""
                                    password = ""})
                                .width(150.dp)
                                .height(50.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(20.dp)
                        )
                        Image(
                            painter = painterResource("botonAñadirPelicula.png"),
                            contentDescription = "Boton para registrar nuevo admin",
                            modifier = Modifier
                                .clickable(onClick = {ventanaActiva = "ventanaAñadirPelícula"
                                    user = ""
                                    password = ""})
                                .width(150.dp)
                                .height(50.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(20.dp)
                        )
                        Image(
                            painter = painterResource("botonGestionarCartelera.png"),
                            contentDescription = "Boton para modificar la cartelera",
                            modifier = Modifier
                                .clickable(onClick = {ventanaActiva = "ventanaGestionarCartelera"
                                    user = ""
                                    password = ""})
                                .width(150.dp)
                                .height(50.dp)
                        )
                    }
                }
            }
        }

        "ventanaEliminarUsuario" -> {
            var eliminarUsuario by remember {mutableStateOf("") }
            MaterialTheme{
                Box(
                    modifier = Modifier.background(naranja)
                ){
                    IconButton(onClick = {ventanaActiva = "ventanaAdmin"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = eliminarUsuario,
                            onValueChange = { eliminarUsuario = it },
                            label = { Text("Usuario a eliminar") },
                            modifier = Modifier.background(Color.White)
                        )
                        Image(
                            painter = painterResource("botonEliminarUsuario.png"),
                            contentDescription = "Boton para elimniar un usuario",
                            modifier = Modifier
                                .clickable {
                                    val comandoComprobar = "SELECT USUARIO FROM USUARIOS WHERE USUARIO = '$eliminarUsuario'"
                                    val statement1 = conexion.createStatement()
                                    val resultSet1 = statement1.executeQuery(comandoComprobar)
                                    var aux = ""
                                    while (resultSet1.next()) {
                                        val usuarioExiste = resultSet1.getString("USUARIO")
                                        aux = usuarioExiste
                                    }

                                    if (aux == eliminarUsuario) {
                                        val comando =
                                            conexion.prepareStatement("DELETE FROM USUARIOS WHERE USUARIO = ?")
                                        comando.run {
                                            setString(1, eliminarUsuario)
                                            executeUpdate()
                                            println("Usuario $eliminarUsuario eliminado")
                                        }
                                    }
                                    else println("Usuario $eliminarUsuario no existe")
                                }
                                .width(150.dp)
                                .height(50.dp)
                        )
                    }
                }
            }
        }

        "ventanaAñadirAdmin" -> {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ) {
                    Image(
                        painter = painterResource("ventanaRegistro.png"),
                        contentDescription = "Ventana de Inicio",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {ventanaActiva = "ventanaAdmin"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = user,
                                onValueChange = { user = it },
                                label = { Text("Usuario") }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Contraseña") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))

                        Row {
                            Image(
                                painter = painterResource("boton_registrarse.png"),
                                contentDescription = "Boton Registrarse",
                                modifier = Modifier
                                    .clickable {
                                        val comando = conexion.prepareStatement("INSERT INTO ADMIN (Usuario , Contraseña) VALUES (?, ?)")
                                        comando.run {
                                            setString(1, user)
                                            setString(2, password)
                                            executeUpdate()
                                        }
                                        println("Registro de Admin $user correcto")
                                        ventanaActiva = "ventanaAdmin"
                                    }
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Image(
                                painter = painterResource("botonLimpiar.png"),
                                contentDescription = "Boton Limpiear Celdas",
                                modifier = Modifier
                                    .clickable { user = ""
                                        password = ""}
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }

        "ventanaAñadirPelícula" -> {
            var titulo by remember {mutableStateOf("") }
            var duracion by remember {mutableStateOf("") }
            var genero by remember {mutableStateOf("") }
            var anio by remember {mutableStateOf("") }
            var portada by remember {mutableStateOf("") }
            val ruta = "Películas\\"
            MaterialTheme{
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ) {
                    Image(
                        painter = painterResource("ventanaInicio.png"),
                        contentDescription = "Ventana de Inicio",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(onClick = {ventanaActiva = "ventanaAdmin"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.background(Color.White)
                        ){
                            TextField(
                                value = titulo,
                                onValueChange = { titulo = it },
                                label = { Text("Título") }
                            )
                            TextField(
                                value = duracion,
                                onValueChange = { duracion = it },
                                label = { Text("Duración") }
                            )
                            TextField(
                                value = genero,
                                onValueChange = { genero = it },
                                label = { Text("Género") }
                            )
                            TextField(
                                value = anio,
                                onValueChange = { anio = it },
                                label = { Text("Año de estreno") }
                            )
                            TextField(
                                value = portada,
                                onValueChange = { portada = it },
                                label = { Text("portada") }
                            )
                        }


                        Spacer(modifier = Modifier.padding(10.dp))

                        Row {
                            Image(
                                painter = painterResource("botonAñadirPelicula.png"),
                                contentDescription = "Boton para registrar una nueva película",
                                modifier = Modifier
                                    .clickable {
                                        portada = ruta + portada

                                        val comando = conexion.prepareStatement("INSERT INTO PELICULAS (Titulo, Duracion, Genero, Año, Portada) VALUES (?, ?, ?, ?, ?)")
                                        comando.run {
                                            setString(1, titulo)
                                            setInt(2, duracion.toInt())
                                            setString(3, genero)
                                            setInt(4, anio.toInt())
                                            setString(5, portada)
                                            executeUpdate()
                                        }
                                        println("Pelicula $titulo añadida correctamente")
                                        ventanaActiva = "ventanaAdmin"
                                    }
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Image(
                                painter = painterResource("botonLimpiar.png"),
                                contentDescription = "Boton Limpiear Celdas",
                                modifier = Modifier
                                    .clickable { titulo = ""
                                                 genero = ""
                                                 duracion = ""
                                                 portada = ""
                                                 anio = ""}
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }

        "ventanaGestionarCartelera" -> {
            var Pelicula1 by remember {mutableStateOf("") }
            var Pelicula2 by remember {mutableStateOf("") }
            var Pelicula3 by remember {mutableStateOf("") }
            var Pelicula4 by remember {mutableStateOf("") }
            var Pelicula5 by remember {mutableStateOf("") }
            var Pelicula6 by remember {mutableStateOf("") }
            var posicionCartelera by remember {mutableStateOf(1) }

            val sql = "SELECT TITULO, POSICIONCARTELERA FROM PELICULAS WHERE posicionCartelera IS NOT NULL"
            val statement = conexion.createStatement()
            val resultSet = statement.executeQuery(sql)
            var portada = ""
            while (resultSet.next()) {
                portada = resultSet.getString("TITULO")
            }

            MaterialTheme{
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.background(Color.White)
                        ) {

                            TextField(
                                value = Pelicula1,
                                onValueChange = { Pelicula1 = it },
                                label = { Text("Pelicula 1") },
                                modifier = Modifier.onFocusChanged {
                                    val sql = "SELECT TITULO FROM PELICULAS WHERE posicionCartelera = 1"
                                    val statement = conexion.createStatement()
                                    val resultSet = statement.executeQuery(sql)
                                    var portada = ""
                                    while (resultSet.next()) {
                                        portada = resultSet.getString("TITULO")
                                    }
                                    Pelicula1 = portada
                                }
                            )
                            posicionCartelera = 2
                            TextField(
                                value = Pelicula2,
                                onValueChange = { Pelicula2 = it },
                                label = { Text("Pelicula 2") },
                                modifier = Modifier.onFocusChanged {
                                    val sql = "SELECT TITULO FROM PELICULAS WHERE posicionCartelera = 2"
                                    val statement = conexion.createStatement()
                                    val resultSet = statement.executeQuery(sql)
                                    var portada = ""
                                    while (resultSet.next()) {
                                        portada = resultSet.getString("TITULO")
                                    }
                                    Pelicula1 = portada
                                }
                            )
                            TextField(
                                value = Pelicula3,
                                onValueChange = { Pelicula3 = it },
                                label = { Text("Pelicula 3") }
                            )
                            TextField(
                                value = Pelicula4,
                                onValueChange = { Pelicula4 = it },
                                label = { Text("Pelicula 4") }
                            )
                            TextField(
                                value = Pelicula5,
                                onValueChange = { Pelicula5 = it },
                                label = { Text("Pelicula 5") }
                            )
                            TextField(
                                value = Pelicula6,
                                onValueChange = { Pelicula6 = it },
                                label = { Text("Pelicula 6") }
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Image(
                                painter = painterResource("botonActualizarCartelera.png"),
                                contentDescription = "Boton para actualizar cartelera",
                                modifier = Modifier
                                    .clickable {

                                        ventanaActiva = "ventanaAdmin"
                                    }
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

/*fun generarEntrada (usuario: Usuario): String{
    val url = "jdbc:mysql://localhost:3306/PROGRAMACION"
    val usuario = "root"
    val contrasenia = "Alberti956"
    Class.forName("com.mysql.cj.jdbc.Driver")
    val conexion = DriverManager.getConnection(url, usuario, contrasenia)

    var codigoEntrada = ""
    var contador = 0

    for (i in 1..12){
        val letraMay = (65..90).random()
        val letraMin = (97..122).random()
        val numero = (48..57).random()
        contador = (1..3).random()
        when (contador){
            1 -> { codigoEntrada += letraMay.toChar() }
            2 -> { codigoEntrada += letraMin.toChar() }
            3 -> { codigoEntrada += numero.toChar() }
        }
    }

    val comandoComprobar = "SELECT USUARIO FROM USUARIOS WHERE USUARIO = '$codigoEntrada'"
    val statement1 = conexion.createStatement()
    val resultado = statement1.executeQuery(comandoComprobar)
    var aux = ""
    while (resultado.next()) {
        val usuarioExiste = resultado.getString("USUARIO")
        aux = usuarioExiste
    }

    if (aux == codigoEntrada){
        TODO()
    }

    else{return codigoEntrada}

}*/