delete from COMENTARIO_ENTITY;
delete from MIEMBRO_ENTITY;
delete from PARTICIPANTE_ENTITY;
delete from CONCURSO_ENTITY;
delete from INICIATIVA_ENTITY;
delete from EVENTO_ENTITY;
delete from PARTICIPANTE_ENTITY_EVENTOS;
delete from MIEMBRO_ENTITY_INICIATIVAS;
delete from INICIATIVA_ENTITY_CONCURSOS;


insert into MIEMBRO_ENTITY (id, estado, fecha_inscripcion, codigo, correo_electronico, foto, nombre, tipo) values (1, 2, '2024-09-14', 668057654, 'tconrad0@comsenz.com', 'https://www.publicdomainpictures.net/pictures/20000/velka/sad-woman.jpg', 'Tabor Conrad', 'Supervisor');
insert into MIEMBRO_ENTITY (id, estado, fecha_inscripcion, codigo, correo_electronico, foto, nombre, tipo) values (2, 2, '2024-09-11', 830680038, 'cgerlts1@mayoclinic.com', 'https://www.publicdomainpictures.net/pictures/40000/velka/horse-head-portrait.jpg', 'Colman Gerlts', 'Electrician');
insert into MIEMBRO_ENTITY (id, estado, fecha_inscripcion, codigo, correo_electronico, foto, nombre, tipo) values (3, 0, '2024-02-11', 402724406, 'randrivel2@studiopress.com', 'https://www.publicdomainpictures.net/pictures/170000/velka/portrait-of-a-beautiful-young-woman.jpg', 'Rosabelle Andrivel', 'Construction Manager');

insert into COMENTARIO_ENTITY (id, contenido, fecha, calificacion, foto) values (1, 'Terrible evento!', '2023-10-01', '1', 'https://cdn.pixabay.com/photo/2014/10/06/04/29/sad-476039_960_720.png');
insert into COMENTARIO_ENTITY (id, contenido, fecha, calificacion, foto) values (2, 'Excelente evento!', '2024-04-19', '7', 'https://cdn.pixabay.com/photo/2019/02/19/19/45/thumbs-up-4007573_960_720.png');
insert into COMENTARIO_ENTITY (id, contenido, fecha, calificacion, foto) values (3, 'Es mejor perderse este evento', '2024-09-14', '0', 'https://cdn.pixabay.com/photo/2014/10/06/04/29/sad-476039_960_720.png');





insert into PARTICIPANTE_ENTITY (id, nombre, foto, correo_electronico, cedula, genero, carrera) 
values (1, 'Sofía Martínez', 'https://www.publicdomainpictures.net/pictures/20000/velka/sad-woman.jpg', 'sofia.martinez@example.com', 123456789, 'Femenino', 'Ingeniería Ambiental');

insert into PARTICIPANTE_ENTITY (id, nombre, foto, correo_electronico, cedula, genero, carrera) 
values (2, 'Carlos Hernández', 'https://www.publicdomainpictures.net/pictures/40000/velka/horse-head-portrait.jpg', 'carlos.hernandez@example.com', 987654321, 'Masculino', 'Ciencias de la Computación');

insert into PARTICIPANTE_ENTITY (id, nombre, foto, correo_electronico, cedula, genero, carrera) 
values (3, 'Laura Pérez', 'https://www.publicdomainpictures.net/pictures/170000/velka/portrait-of-a-beautiful-young-woman.jpg', 'laura.perez@example.com', 123987456, 'Femenino', 'Psicología');


insert into CONCURSO_ENTITY (id, titulo, imagen, descripcion, precio, premio, fecha, organizado, ciudad) 
values (1, 'Concurso de Fotografía', 'https://cdn.pixabay.com/photo/2017/06/08/07/36/camera-2382826_960_720.jpg', 'Concurso de fotografía para jóvenes talentos', 50000, 'Cámara profesional', '2024-07-12', 'Asociación de Fotógrafos', 'Bogotá');

insert into CONCURSO_ENTITY (id, titulo, imagen, descripcion, precio, premio, fecha, organizado, ciudad) 
values (2, 'Hackathon de Programación', 'https://cdn-images-1.medium.com/max/1200/1*bUspgFGkssFoFONvZBimAw.jpeg', 'Competencia de desarrollo de software en 24 horas', 200000, 'Laptop de última generación', '2024-08-25', 'Tech Innovators', 'Medellín');

insert into CONCURSO_ENTITY (id, titulo, imagen, descripcion, precio, premio, fecha, organizado, ciudad) 
values (3, 'Concurso de Cocina', 'https://cdn.pixabay.com/photo/2014/10/08/22/52/chef-480955_960_720.jpg', 'Competencia culinaria para chefs amateurs', 30000, 'Juego de utensilios de cocina', '2024-11-10', 'Gastronomía Global', 'Cali');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador) 
values (1, 'Iniciativa Verde', 'https://live.staticflickr.com/2756/4430126790_a7213df27a_b.jpg', 'Proyecto de reforestación urbana', '2023-05-21', true, 'Laura Gómez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador) 
values (2, 'Tech for All', 'https://cdn.wallpapersafari.com/61/53/iL4SYf.jpg', 'Iniciativa para llevar tecnología a zonas rurales', '2022-10-15', true, 'Carlos Pérez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador) 
values (3, 'Cultura Inclusiva', 'https://static.vecteezy.com/system/resources/previews/021/219/960/original/diversity-and-inclusion-logo-with-hand-colorful-logo-symbol-of-harmony-and-togetherness-vector.jpg', 'Promoción de la inclusión social y cultural', '2024-01-10', false, 'María Rodríguez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (4, 'Guerreros Ecológicos', 'https://live.staticflickr.com/2756/4430126790_a7213df27a_b.jpg', 'Protección y concienciación ambiental', '2023-08-15', true, 'Ana Torres');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (5, 'Innovadores Tecnológicos', 'https://cdn.wallpapersafari.com/61/53/iL4SYf.jpg', 'Promoción de avances tecnológicos', '2023-09-10', true, 'Luis Martínez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (6, 'Salud Primero', 'https://live.staticflickr.com/2756/4430126790_a7213df27a_b.jpg', 'Iniciativas de salud y bienestar', '2023-07-20', true, 'María López');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (7, 'Arte para Todos', 'https://cdn.wallpapersafari.com/61/53/iL4SYf.jpg', 'Promoción del arte y la cultura', '2023-06-25', true, 'Carlos Ruiz');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (8, 'Constructores Comunitarios', 'https://static.vecteezy.com/system/resources/previews/021/219/960/original/diversity-and-inclusion-logo-with-hand-colorful-logo-symbol-of-harmony-and-togetherness-vector.jpg', 'Proyectos de desarrollo comunitario', '2023-05-30', true, 'Laura Fernández');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (9, 'Futuro Verde', 'https://live.staticflickr.com/2756/4430126790_a7213df27a_b.jpg', 'Proyectos de desarrollo sostenible', '2023-04-10', true, 'Pedro Sánchez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (10, 'Pioneros Tecnológicos', 'https://cdn.wallpapersafari.com/61/53/iL4SYf.jpg', 'Soluciones tecnológicas innovadoras', '2023-03-15', true, 'Lucía Gómez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (11, 'Vida Saludable', 'https://cdn.wallpapersafari.com/61/53/iL4SYf.jpg', 'Promoción de estilos de vida saludables', '2023-02-20', true, 'Juan Pérez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (12, 'Mentes Artísticas', 'https://static.vecteezy.com/system/resources/previews/021/219/960/original/diversity-and-inclusion-logo-with-hand-colorful-logo-symbol-of-harmony-and-togetherness-vector.jpg', 'Fomento de la expresión artística', '2023-01-25', true, 'Ana Martínez');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (13, 'Ayudantes Comunitarios', 'https://live.staticflickr.com/2756/4430126790_a7213df27a_b.jpg', 'Apoyo a iniciativas comunitarias', '2022-12-30', true, 'Carlos Fernández');

insert into INICIATIVA_ENTITY (id, nombre, logo, descripcion, fecha_creacion, estado, administrador)
values (14, 'Innovadores Ecológicos', 'https://cdn.wallpapersafari.com/61/53/iL4SYf.jpg', 'Proyectos de innovación ambiental', '2022-11-05', true, 'María Torres');

insert into EVENTO_ENTITY (id, titulo, imagen_publicitaria, cupos, descripcion, fecha, duracion, lugar, iniciativa_id)
values (1, 'Taller de Energías Renovables', 'https://www.cmu.edu/energy/news-multimedia/2018/images/energy-week-900x600-min.jpg', 30, 'Taller práctico sobre el uso de energías renovables', '2024-03-15', 4, 'Centro de Convenciones', 1);

insert into EVENTO_ENTITY (id, titulo, imagen_publicitaria, cupos, descripcion, fecha, duracion, lugar, iniciativa_id) 
values (2, 'Curso de Programación en Python', 'https://logosmarken.com/wp-content/uploads/2021/10/Python-Emblem.png', 50, 'Curso introductorio de programación en Python', '2024-06-20', 6, 'Universidad de Tecnología', 2);

insert into EVENTO_ENTITY (id, titulo, imagen_publicitaria, cupos, descripcion, fecha, duracion, lugar, iniciativa_id) 
values (3, 'Seminario sobre Inclusión Social', 'https://static.vecteezy.com/system/resources/previews/021/219/960/original/diversity-and-inclusion-logo-with-hand-colorful-logo-symbol-of-harmony-and-togetherness-vector.jpg', 100, 'Seminario sobre estrategias de inclusión social y cultural', '2024-09-05', 3, 'Teatro Municipal', 3);

INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (1, 1);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (1, 2);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (1, 3);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (2, 1);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (2, 2);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (2, 3);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (3, 1);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (3, 2);
INSERT INTO PARTICIPANTE_ENTITY_EVENTOS (EVENTOS_ID, PARTICIPANTES_ID) VALUES (3, 3);

INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (1, 1);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (1, 2);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (1, 3);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (2, 1);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (2, 2);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (2, 3);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (3, 1);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (3, 2);
INSERT INTO MIEMBRO_ENTITY_INICIATIVAS (INICIATIVAS_ID, MIEMBROS_ID) VALUES (3, 3);

INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (1, 1);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (1, 2);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (1, 3);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (2, 1);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (2, 2);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (2, 3);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (3, 1);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (3, 2);
INSERT INTO INICIATIVA_ENTITY_CONCURSOS(CONCURSOS_ID, INICIATIVAS_ID) VALUES (3, 3);



