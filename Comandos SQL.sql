--Tabla de Pelis
CREATE TABLE PELICULAS (
Id Numeric(3) PRIMARY KEY,
Titulo varchar(100),
Duracion Numeric(3),
Genero varchar(50),
Año Numeric(4),
Portada MEDIUMBLOB,
Portada_url varchar(100)
);

INSERT INTO PELICULAS VALUES(1,'Super Mario Bros: La película', 92, 'Animación', 2023, 'C:\Users\josem\Escritorio\Programación\ProyectoFinal\src\jvmMain\resources\Películas\SMB.jpg', "Películas\\SMB.jpg");

--Tabla de Empleados
CREATE TABLE EMPLEADOS (
Id_Empleado TINYINT PRIMARY KEY,
Nombre varchar(100),
Sueldo DOUBLE,
Puesto varchar(50)
);

--Insercion de empleado
INSERT INTO EMPLEADOS (1, 'José Manuel Luque', 1250.0, 'Taquillero');

--Tabla de Usuarios
CREATE TABLE USUARIOS (
Usuario varchar(100) PRIMARY KEY,
Contraseña varchar(25)
);

--Tabla de Administradores
CREATE TABLE ADMIN (
Usuario varchar(100) PRIMARY KEY,
Contraseña varchar(25)
);

INSERT INTO ADMIN VALUES('Josema', 'Josema');

--Tabla de Sesiones
CREATE TABLE SESION (
Sala Numeric(2),
Hora VARCHAR(9),

);

--Tabla de Entradas
CREATE TABLE ENTRADAS (
Codigo Numeric(2) PRIMARY KEY,
USUARIO VARCHAR(9),
);