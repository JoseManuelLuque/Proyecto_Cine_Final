import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.sql.DriverManager
import java.sql.SQLException

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
    try {
        Class.forName("com.mysql.cj.jdbc.Driver")
        val conexion = DriverManager.getConnection(url, usuario, contrasenia)
        println("Conexión exitosa")
        val stmt = conexion.prepareStatement("SELECT * FROM EMPLEADOS;")
        stmt.run {  }
    }
    catch (e: SQLException) {
        println("Error en la conexión: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("No se encontró el driver JDBC: ${e.message}")
    }


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
                                           val conexion = DriverManager.getConnection(url, usuario, contrasenia)
                                           val stmt = conexion.prepareStatement("INSERT INTO USUARIOS (Usuario , Contraseña) VALUES (?, ?)")
                                           val usario = Usuario()
                                           stmt.run {
                                               setString(1, usuario.user)
                                               setString(2, usuario.password)
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
                                   .clickable(onClick = {ventanaActiva = "ventanaInicioSesion"})
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
       }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Cine Josema", icon = painterResource("Icono.png")) {
        App()
        val naranja = Color.hsv(25.0.toFloat(), 1.0.toFloat(), 1.0.toFloat())
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
        )
    }
}