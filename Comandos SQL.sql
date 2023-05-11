--Tabla de Pelis
CREATE TABLE PELICULAS (
Titulo varchar(100) PRIMARY KEY,
Duracion Numeric(3),
Genero varchar(50),
Año Numeric(4)
);

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