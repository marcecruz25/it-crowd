Requisitos:
    Tener instalado maven3
    Java 8
    MySQL Server
Pasos para ejecutar:
    1 - En un terminal clonar el repo un git clone https://github.com/marcecruz25/it-crowd.git

    2 - Correr el script alojado en it-crowd/src/main/resources/DumpInicial.sql

        mysql -u root -p < ~/Path_Raiz_donde_esta_el_proyecto/it-crowd/src/main/resources/DumpInicial.sql

        Por Defecto se crea un usuario con los siguientes datos:
            username : testCrowd
            password : crowd

        Para querer ingresar otro usuario lo debe hacer desde la consola
            Ej: INSERT INTO user (id, username, password, age) VALUES (1, 'Test1234', 'string generado desde Bcrypt online', 33);

        Ir al paso 8

    3 - En caso de no haber realizado el paso 2, unicamente hacer los siguientes pasos.
        Acceder a tu base de dato MySQL con el siguiente comando y luego ingresar tu contrasenia:

        mysql -u root -p

    4 - Crear un schema en tu base de datos MySQL con el nombre de "testCrowd" con el siguiente comando

        create database testCrowd;

    5 - Dar todos los privilegios para el esquema creado con el siguiente comando:

        grant all on testCrowd.* TO 'root'@'localhost';

    6 - Agregar Usuario por defecto:

        use testCrowd;
        INSERT INTO user (id, username, password, age) VALUES (2,25,'$2a$04$y5zizkmjZqOCOtPfNQNsyeECGRuRUhE5IsLvA./E8ach9t7exXXhu','testCrowd')

    7 - En el file de application.properties cambiar el usuario al que se le dio el privilegio en el paso anterior y contraseña que corresponda segun tu configuracion. Ejemplo:
        spring.jpa.hibernate.ddl-auto=update /* Sino se quiere ejecutar el script DumpInicial.sql, se puede cambiar la primera vez a create pero tiene que estar generado el schema, luego mantener como update o none */
        spring.datasource.url=jdbc:mysql://localhost:3306/testCrowd
        spring.datasource.username=root
        spring.datasource.password=root

    8 - Estar dentro de la carpeta del proyecto raiz y ejecutar:
            mvn clean install

    9 - Correr la aplicacion
        mvn spring-boot:run

    10 - Para testear la app se hicieron varios end-point para que se ordene los items de distintas maneras.
        Las pruebas se pueden realizar a traves de postman o con un curl, para mayor comodidad configure swagger para las consultas.
        Aclaracion:
            1 - Todos los end-point relacionados a los Items se pueden acceder por postman a traves de un access_token, para obtener dicho token se deben seguir los siguientes pasos:
                    POST a http://localhost:8080/oauth/token
                    Authorization:
                            Type : Basic Auth.
                            Username : crowd-client
                            Password : crowd-secret

                    Body --> Seleccionar x-www-foorm-urlencoded:
                            username : testCrowd
                            password : crowd
                            grant_type : password

                Nos saldria algo asi como ejemplo:
                   {
                       "access_token": "0705dac8-9ff5-4756-8fdd-14d56c5da081",
                       "token_type": "bearer",
                       "refresh_token": "e1389e5f-3a44-40dd-8f9e-0fc8f4979862",
                       "expires_in": 3262,
                       "scope": "read write trust"
                   }

                Luego por ejemplo para acceder a los items deberiamos hacer un GET con el access_token
                Ej: GET a http://localhost:8080/api/v1/items?access_token=0705dac8-9ff5-4756-8fdd-14d56c5da081

            2 - Si las consultas la van a hacer por Swagger, no necesitarian el access_token, eso si, les va a aparecer los end-point del CRUD de User
            que utiliz spring Security per en realidad no lo van a poder operar.
            Distinto el caso del controller de los item que sin el access_token ya van a poder probar.
            Opte por no comentar mucho el codigo, ya que al utilizar Swagger lo deje plasmadoo ahi, que hace el controller item.


    11 - Para habilitar el debug se debera descomentar la linea del pom.xml siguiente y que configurar el puerto especificado:

            <build>
		        <plugins>
			        <plugin>
				        <groupId>org.springframework.boot</groupId>
				        <artifactId>spring-boot-maven-plugin</artifactId>
				        <configuration>
					        <jvmArguments>
						        -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
					        </jvmArguments>
				        </configuration>
			        </plugin>
		        </plugins>
	        </build>


