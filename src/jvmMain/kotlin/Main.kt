import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.sql.DriverManager

@Composable
@Preview
fun App() {
    val url = "jdbc:mysql://localhost:3306/PROGRAMACION"
    val usuario = "root"
    val contrasenia = "Alberti956"
    val naranja = Color.hsv(25.0.toFloat(), 1.0.toFloat(), 1.0.toFloat())
    var ventanaActiva by remember {mutableStateOf("ventanaInicio") }
    var user by remember {mutableStateOf("") }
    var password by remember {mutableStateOf("") }

    Class.forName("com.mysql.cj.jdbc.Driver")
    val conexion = DriverManager.getConnection(url, usuario, contrasenia)

    when (ventanaActiva) {
        "ventanaInicio" -> {
            MaterialTheme {
                //Caja la cual llevará el contenido de la ventana dentro
                Box(
                    modifier = Modifier.fillMaxSize().background(naranja)
                ) {
                    //Imagen de Fondo
                    Image(
                        painter = painterResource("Pagina_Inicio.png"),
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
                    modifier = Modifier.fillMaxSize().background(naranja),

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
                                label = { Text("Contraseña") }
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

        "ventanaInicioSesion" ->{
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
                                label = { Text("Contraseña") }
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
                            val id = 1
                            val sql = "SELECT PORTADA_URL FROM PELICULAS WHERE ID = '$id'"
                            val statement = conexion.createStatement()
                            val resultSet = statement.executeQuery(sql)
                            var aux = ""
                            while (resultSet.next()) {
                                val portada_url = resultSet.getString("Portada_url")
                                aux = portada_url
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
                                val portada_url = resultSet.getString("Portada_url")
                                aux = portada_url
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
                    IconButton(onClick = {ventanaActiva = "ventanaInicio"}) {
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
                                val portada_url = resultSet.getString("Portada_url")
                                aux = portada_url
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
                                val portada_url = resultSet.getString("Portada_url")
                                aux = portada_url
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

        "ventanaAdmin" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.background(naranja)
                ){
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
                                    val comando = conexion.prepareStatement("DELETE FROM USUARIOS WHERE USUARIO = ?")
                                    comando.run {
                                        setString(1, eliminarUsuario)
                                        executeUpdate()
                                        println("Usuario $eliminarUsuario eliminado")
                                    }

                                }
                                .width(150.dp)
                                .height(50.dp)
                        )
                    }
                }
            }
        }

        "ventanaUsuario" -> {
            MaterialTheme{
                Box(
                    modifier = Modifier.background(naranja).fillMaxSize()
                ){
                    Image(
                        painter = painterResource("Pagina_Inicio.png"),
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
                                .clickable(onClick = {ventanaActiva = "ventanaCartelera"})
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

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Cine Josema", icon = painterResource("Icono.png")) {
        App()
        /*val naranja = Color.hsv(25.0.toFloat(), 1.0.toFloat(), 1.0.toFloat())
        TopAppBar(
            backgroundColor = naranja,
            contentColor = Color.White,
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Ir hacia atras")
                }
            },
            title = { Text(text = "CINES JOSEMA") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    /*Icon(
                        painter = painterResource(/*TODO*/),
                        contentDescription = "Leer después"
                    )*/
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = "Compartir")
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Ver más")
                }
            }
        )*/
    }
}