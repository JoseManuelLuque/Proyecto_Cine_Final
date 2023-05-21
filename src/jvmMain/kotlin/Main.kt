import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                                label = { Text("Usuario") },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Person Icon"
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            modifier = Modifier.background(Color.White)
                        ){
                            var visible by remember { mutableStateOf(1) }
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Contraseña") },
                                visualTransformation = if (visible == 1) PasswordVisualTransformation() else VisualTransformation.None,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    Icon(
                                        imageVector = if (visible == 1) Icons.Default.Lock else Icons.Default.ThumbUp,
                                        contentDescription = "Lock/Unlock Icon",
                                        modifier = Modifier.clickable {
                                            visible = visible.inv()
                                        }
                                    )
                                }
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
                                label = { Text("Usuario") },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Person Icon"
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row(
                            modifier = Modifier.background(Color.White)
                        ) {
                            var visible by remember { mutableStateOf(1) }
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Contraseña") },
                                visualTransformation = if (visible == 1) PasswordVisualTransformation() else VisualTransformation.None,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    Icon(
                                        imageVector = if (visible == 1) Icons.Default.Lock else Icons.Default.ThumbUp,
                                        contentDescription = "Lock/Unlock Icon",
                                        modifier = Modifier.clickable {
                                            visible = visible.inv()
                                        }
                                    )
                                }
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
                                            else userEcontrado = false
                                        }
                                        while (resultSet1.next()){
                                            val checkPass = resultSet1.getString("Contraseña")

                                            if (checkPass == password){
                                                passwordEncontrado = true
                                            }
                                            else passwordEncontrado = false
                                        }

                                        if (userEcontrado && passwordEncontrado){
                                            println("Inicio de sesión completado")
                                            ventanaActiva = "ventanaUsuario"
                                        }
                                        else println("Usuario no encontrando")

                                        while (resultUserAdmin.next()){
                                            val checkUserAdmin = resultUserAdmin.getString("Usuario")

                                            if (checkUserAdmin == user){
                                                userAdmin = true
                                            }
                                            else userAdmin = false
                                        }
                                        while (resultPassAdmin.next()){
                                            val checkPassAdmin = resultPassAdmin.getString("Contraseña")

                                            if (checkPassAdmin == password){
                                                passwordAdmin = true
                                            }
                                            else passwordAdmin = false
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
                        var posicionCartelera = 1
                        var sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        var statement = conexion.createStatement()
                        var resultSet = statement.executeQuery(sql)
                        var portada1 = ""
                        while (resultSet.next()) {
                            portada1 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 2
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada2 = ""
                        while (resultSet.next()) {
                            portada2 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 3
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada3 = ""
                        while (resultSet.next()) {
                            portada3 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 4
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada4 = ""
                        while (resultSet.next()) {
                            portada4 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 5
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada5 = ""
                        while (resultSet.next()) {
                            portada5 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 6
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada6 = ""
                        while (resultSet.next()) {
                            portada6 = resultSet.getString("PORTADA")
                        }
                            Row {
                                Image(
                                    painter = painterResource(portada1),
                                    contentDescription = "Portada 1",
                                    modifier = Modifier
                                        .size(150.dp)
                                )


                                Image(
                                    painter = painterResource(portada2),
                                    contentDescription = "Portada 2",
                                    modifier = Modifier
                                        .size(150.dp)
                                )

                                Image(
                                    painter = painterResource(portada3),
                                    contentDescription = "Portada 3",
                                    modifier = Modifier
                                        .size(150.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(15.dp))
                            Row {
                                Image(
                                    painter = painterResource(portada4),
                                    contentDescription = "Portada 4",
                                    modifier = Modifier
                                        .size(150.dp)
                                )

                                Image(
                                    painter = painterResource(portada5),
                                    contentDescription = "Portada 5",
                                    modifier = Modifier
                                        .size(150.dp)
                                )

                                Image(
                                    painter = painterResource(portada6),
                                    contentDescription = "Portada 6",
                                    modifier = Modifier
                                        .size(150.dp)
                                )
                            }
                        Spacer(modifier = Modifier.padding(30.dp))
                        Row{
                            Text(text = "Si quiere ver las sesiones inicie sesión", fontSize = 35.sp, color = Color.White)
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

        "ventanaCompra" -> {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ) {
                    val sql = "SELECT PORTADA, TITULO FROM PELICULAS WHERE POSICIONCARTELERA = '$peliActiva'"
                    val statement = conexion.createStatement()
                    val resultSet = statement.executeQuery(sql)
                    var titulo = ""
                    var portada = ""
                    while (resultSet.next()) {
                        titulo = resultSet.getString("TITULO")
                        portada = resultSet.getString("PORTADA")
                    }

                    var cantidadEntradas by remember { mutableStateOf("") }
                    var mensajeConfirmacion by remember { mutableStateOf("") }

                    IconButton(onClick = {ventanaActiva = "ventanaCarteleraCompra"}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                    }

                    Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(titulo, fontSize = 40.sp)
                            Spacer(modifier = Modifier.padding(10.dp))
                            Image(
                                painter = painterResource(portada),
                                contentDescription = "Portada de pelicula a comprar",
                                modifier = Modifier.size(150.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            TextField(
                                value = cantidadEntradas,
                                onValueChange = { cantidadEntradas = it },
                                label = { Text("Cantidad de entradas") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(onClick = {
                                for (i in 1..cantidadEntradas.toInt()){
                                    val comando = conexion.prepareStatement("INSERT INTO ENTRADAS (Codigo, Usuario, Pelicula) VALUES (?, ?, ?)")
                                    val codigo = generarEntrada()
                                    comando.run {
                                        setString(1, codigo)
                                        setString(2, user)
                                        setString(3, titulo)
                                        executeUpdate()
                                    }
                                }
                                mensajeConfirmacion = "Compra realizada. $cantidadEntradas entradas para $titulo."
                            }) {
                                Text("Confirmar compra")
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(mensajeConfirmacion, textAlign = TextAlign.Center)
                        }
                }
            }
        }

       "ventanaCarteleraCompra" -> {
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
                        var posicionCartelera = 1
                        var sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        var statement = conexion.createStatement()
                        var resultSet = statement.executeQuery(sql)
                        var portada1 = ""
                        while (resultSet.next()) {
                            portada1 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 2
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada2 = ""
                        while (resultSet.next()) {
                            portada2 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 3
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada3 = ""
                        while (resultSet.next()) {
                            portada3 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 4
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada4 = ""
                        while (resultSet.next()) {
                            portada4 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 5
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada5 = ""
                        while (resultSet.next()) {
                            portada5 = resultSet.getString("PORTADA")
                        }
                        posicionCartelera = 6
                        sql = "SELECT PORTADA FROM PELICULAS WHERE POSICIONCARTELERA = '$posicionCartelera'"
                        statement = conexion.createStatement()
                        resultSet = statement.executeQuery(sql)
                        var portada6 = ""
                        while (resultSet.next()) {
                            portada6 = resultSet.getString("PORTADA")
                        }
                        Row {
                            Image(
                                painter = painterResource(portada1),
                                contentDescription = "Portada 1",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        peliActiva = "1"
                                        ventanaActiva = "ventanaCompra"
                                    }
                            )


                            Image(
                                painter = painterResource(portada2),
                                contentDescription = "Portada 2",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        peliActiva = "2"
                                        ventanaActiva = "ventanaCompra"
                                    }
                            )

                            Image(
                                painter = painterResource(portada3),
                                contentDescription = "Portada 3",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        peliActiva = "3"
                                        ventanaActiva = "ventanaCompra"
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.padding(15.dp))
                        Row {
                            Image(
                                painter = painterResource(portada4),
                                contentDescription = "Portada 4",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        peliActiva = "4"
                                        ventanaActiva = "ventanaCompra"
                                    }
                            )

                            Image(
                                painter = painterResource(portada5),
                                contentDescription = "Portada 5",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        peliActiva = "5"
                                        ventanaActiva = "ventanaCompra"
                                    }
                            )

                            Image(
                                painter = painterResource(portada6),
                                contentDescription = "Portada 6",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        peliActiva = "6"
                                        ventanaActiva = "ventanaCompra"
                                    }
                            )
                        }
                    }

                }
            }
        }

        "ventanaVerEntradas" -> {

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
                        Row{
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
                                painter = painterResource("botonRegistrarAdministrador.png"),
                                contentDescription = "Boton para registrar nuevo admin",
                                modifier = Modifier
                                    .clickable(onClick = {ventanaActiva = "ventanaAñadirAdmin"
                                        user = ""
                                        password = ""})
                                    .width(150.dp)
                                    .height(50.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row{
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
            var pelicula1 by remember {mutableStateOf("") }
            var pelicula2 by remember {mutableStateOf("") }
            var pelicula3 by remember {mutableStateOf("") }
            var pelicula4 by remember {mutableStateOf("") }
            var pelicula5 by remember {mutableStateOf("") }
            var pelicula6 by remember {mutableStateOf("") }

            val sql = "SELECT TITULO, POSICIONCARTELERA FROM PELICULAS WHERE posicionCartelera IS NOT NULL"
            val statement = conexion.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                when (resultSet.getInt("POSICIONCARTELERA")) {
                    1 -> pelicula1 = resultSet.getString("TITULO")
                    2 -> pelicula2 = resultSet.getString("TITULO")
                    3 -> pelicula3 = resultSet.getString("TITULO")
                    4 -> pelicula4 = resultSet.getString("TITULO")
                    5 -> pelicula5 = resultSet.getString("TITULO")
                    6 -> pelicula6 = resultSet.getString("TITULO")
                }
            }

            MaterialTheme{
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ){
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
                        ) {

                            TextField(
                                value = pelicula1,
                                onValueChange = { pelicula1 = it },
                                label = { Text("Pelicula 1") }
                            )
                            TextField(
                                value = pelicula2,
                                onValueChange = { pelicula2 = it },
                                label = { Text("Pelicula 2") }
                            )
                            TextField(
                                value = pelicula3,
                                onValueChange = { pelicula3 = it },
                                label = { Text("Pelicula 3") }
                            )
                            TextField(
                                value = pelicula4,
                                onValueChange = { pelicula4 = it },
                                label = { Text("Pelicula 4") }
                            )
                            TextField(
                                value = pelicula5,
                                onValueChange = { pelicula5 = it },
                                label = { Text("Pelicula 5") }
                            )
                            TextField(
                                value = pelicula6,
                                onValueChange = { pelicula6 = it },
                                label = { Text("Pelicula 6") }
                            )
                        }
                        Row{
                            Image(
                                painter = painterResource("botonActualizarCartelera.png"),
                                contentDescription = "Boton para actualizar cartelera",
                                modifier = Modifier
                                    .clickable {
                                        val sql = "UPDATE PELICULAS SET PosicionCartelera = NULL"
                                        val statement1 = conexion.createStatement()
                                        statement1.execute(sql)

                                        val sql1 = "UPDATE PELICULAS SET PosicionCartelera = 1 WHERE Titulo = '$pelicula1'"
                                        val sql2 = "UPDATE PELICULAS SET PosicionCartelera = 2 WHERE Titulo = '$pelicula2'"
                                        val sql3 = "UPDATE PELICULAS SET PosicionCartelera = 3 WHERE Titulo = '$pelicula3'"
                                        val sql4 = "UPDATE PELICULAS SET PosicionCartelera = 4 WHERE Titulo = '$pelicula4'"
                                        val sql5 = "UPDATE PELICULAS SET PosicionCartelera = 5 WHERE Titulo = '$pelicula5'"
                                        val sql6 = "UPDATE PELICULAS SET PosicionCartelera = 6 WHERE Titulo = '$pelicula6'"
                                        val statement = conexion.createStatement()
                                        statement.execute(sql1)
                                        statement.execute(sql2)
                                        statement.execute(sql3)
                                        statement.execute(sql4)
                                        statement.execute(sql5)
                                        statement.execute(sql6)

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

fun generarEntrada (): String{
    val url = "jdbc:mysql://localhost:3306/PROGRAMACION"
    val usuario = "root"
    val contrasenia = "Alberti956"
    Class.forName("com.mysql.cj.jdbc.Driver")
    val conexion = DriverManager.getConnection(url, usuario, contrasenia)

    var codigoEntrada = ""

    for (i in 1..12){
        val letraMay = (65..90).random()
        val letraMin = (97..122).random()
        val numero = (48..57).random()
        when ((1..3).random()){
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
}