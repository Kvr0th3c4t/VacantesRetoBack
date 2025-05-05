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

INSERT INTO usuarios (email, nombre, apellidos, password, rol, enabled)
VALUES ('admin@correo.com', 'Admin', 'Sistema', '{noop}admin', 'ADMON', 1);
INSERT INTO usuarios (email, nombre, apellidos, password, rol, enabled)
VALUES ('empresa@correo.com', 'Empresa', 'Empresario', '{noop}empresa', 'EMPRESA', 1);
INSERT INTO usuarios (email, nombre, apellidos, password, rol, enabled)
VALUES ('usuario@correo.com', 'Usuario', 'cliente', '{noop}1234', 'CLIENTE', 1);


-- Categorías
INSERT INTO Categorias (nombre, descripcion) VALUES
('Desarrollo Web', 'Trabajos relacionados con desarrollo frontend, backend y full stack.'),
('Marketing Digital', 'Publicidad en línea, SEO, SEM, redes sociales.'),
('Atención al Cliente', 'Soporte a clientes por teléfono, chat o correo.'),
('Diseño Gráfico', 'Diseño de material visual y branding.');

-- Empresas (asociadas a email ya registrado)
INSERT INTO Empresas (cif, nombre_empresa, direccion_fiscal, pais, email) VALUES
('A12345678', 'Tech Solutions S.A.', 'Calle Falsa 123', 'España', 'empresa@correo.com'),
('B87654321', 'Marketing Pro SL', 'Av. Digital 456', 'España', 'empresa@correo.com'); -- Reutilizamos el mismo email por ahora

INSERT INTO Vacantes (nombre, descripcion, fecha, salario, estatus, destacado, imagen, detalles, id_categoria, id_empresa) VALUES
('Desarrollador Frontend Angular', 'Se busca frontend con experiencia en Angular.', CURDATE(), 28000, 'CREADA', 1, 'https://picsum.photos/id/237/800/400', 'Trabajo presencial en Madrid.', 1, 1),
('Especialista en SEO', 'Optimización de motores de búsqueda.', CURDATE(), 24000, 'CREADA', 0, 'https://images.unsplash.com/photo-1508830524289-0adcbe822b40?auto=format&fit=crop&w=800&q=80', 'Remoto 100%.', 2, 2),
('Atención al cliente - Chat', 'Responder consultas por chat.', CURDATE(), 18000, 'CREADA', 0, 'https://images.unsplash.com/photo-1585222515068-7201a72c4181?auto=format&fit=crop&w=800&q=80', 'Turno de mañana.', 3, 1),
('Diseñador UI/UX', 'Diseño de interfaces y experiencia de usuario.', CURDATE(), 30000, 'CREADA', 1, 'https://picsum.photos/id/1011/800/400', 'Figma, Adobe XD.', 4, 1);



-- Solicitudes (hechas por cliente registrado: usuario@correo.com)
INSERT INTO Solicitudes (fecha, archivo, comentarios, curriculum, id_vacante, email) VALUES
(CURDATE(), 'cv_usuario.pdf', 'Estoy muy interesado en esta vacante.', 'cv_usuario.pdf', 1, 'usuario@correo.com'),
(CURDATE(), 'cv_usuario.pdf', 'Tengo experiencia previa.', 'cv_usuario.pdf', 2, 'usuario@correo.com');








