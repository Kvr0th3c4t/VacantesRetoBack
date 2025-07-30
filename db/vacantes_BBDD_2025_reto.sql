create database vacantes_BBDD_2025_RETO;
use vacantes_BBDD_2025_RETO;

CREATE TABLE `Categorias` (
  id_categoria int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre varchar(100) NOT NULL,
  descripcion varchar(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- DROP TABLE IF EXISTS `Usuarios`;
CREATE TABLE `Usuarios` (
  email varchar(45) NOT NULL PRIMARY KEY,
  nombre varchar(45) NOT NULL,
  apellidos varchar(100) not null,
  password varchar(100) NOT NULL,
  enabled int NOT NULL DEFAULT 1,
  fecha_Registro date,
  rol varchar(15) not null,
  CHECK(ROL IN ('EMPRESA', 'ADMON', 'CLIENTE'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


create table empresas
(id_empresa int not null auto_increment primary key,
cif varchar(10) not null unique,
nombre_empresa varchar(100) not null,
direccion_fiscal varchar(100),
pais varchar(45),
email varchar(45),
foreign key(email) references usuarios(email)
);
-- DROP TABLE IF EXISTS `Vacantes`;
CREATE TABLE `Vacantes` (
  id_vacante int NOT NULL AUTO_INCREMENT,
  nombre varchar(200) NOT NULL,
  descripcion text NOT NULL,
  fecha date NOT NULL,
  salario double NOT NULL,
  estatus enum('CREADA','CUBIERTA','CANCELADA') NOT NULL,
  destacado tinyint NOT NULL,
  imagen varchar(250) NOT NULL,
  detalles text NOT NULL,
  id_Categoria int NOT NULL,
  id_empresa int not null,
  PRIMARY KEY (id_vacante),
  FOREIGN KEY (id_categoria) REFERENCES `Categorias` (id_categoria),
  FOREIGN KEY (id_empresa) REFERENCES `Empresas` (id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- DROP TABLE IF EXISTS `Solicitudes`;
CREATE TABLE `Solicitudes` (
  id_solicitud int NOT NULL AUTO_INCREMENT,
  fecha date NOT NULL,
  archivo varchar(250) NOT NULL,
  comentarios varchar(2000),
  estado  tinyint NOT NULL default 0,
  -- 0 presentada, 1 adjudicada
  curriculum varchar(45),
  id_Vacante int NOT NULL,
  email varchar(45) NOT NULL,
  PRIMARY KEY (id_solicitud),
  UNIQUE(id_Vacante,email),
  FOREIGN KEY (email) REFERENCES `Usuarios` (email),
  FOREIGN KEY (id_Vacante) REFERENCES `Vacantes` (id_vacante)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE USER 'vacantes_user'@'localhost' IDENTIFIED BY 'vacantespass2025';
GRANT ALL PRIVILEGES ON vacantes_BBDD_2025_RETO.* TO 'vacantes_user'@'localhost';
FLUSH PRIVILEGES;









