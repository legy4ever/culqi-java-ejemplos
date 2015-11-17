# Integrando el Botón de Pago Web en una aplicación Java

1. [Introducción](#1-introducción)
2. [Requerimientos](#2-requerimientos)
3. [Instalación](#3-instalación)
4. [Comercio de Prueba](#4-comercio-de-prueba)
5. [Configuración](#5-configuración)
6. [Operación de Autorización](#6-operación-de-autorización)
  1. [Creando una Venta](#61-creando-una-venta)
    * [Parámetros de envío obligatorios](#a-parámetros-de-env%C3%ADo-obligatorios)
    * [Parámetros de envío opcionales](#b-parámetros-de-env%C3%ADo-opcionales)
    * [Parámetros de respuesta](#c-parámetros-de-respuesta)     
    * [Códigos de respuesta](#d-códigos-de-respuesta)
  2. [Procesando una Venta](#62-procesando-una-venta)
    * [Parámetros de envío](#a-parámetros-de-env%C3%ADo)
    * [Enviando la respuesta a tu servidor](#b-enviando-la-respuesta-a-tu-servidor)
    * [Decrifrando la respuesta](#c-descrifrando-la-respuesta)
    * [Códigos de respuesta](#d-códigos-de-respuesta-1)
7. [Operación de Consulta de una venta](#7-operación-de-consulta-de-una-venta)
  1. [Parámetros de envío obligatorios](#a-parámetros-de-env%C3%ADo-1)
  2. [Parámetros de respuesta](#b-parámetros-de-respuesta)
8. [Operación de Anulación de una venta](#8-operación-de-anulación-de-una-venta)
  1. [Parámetros de envío obligatorios](#a-parámetros-de-env%C3%ADo-2)
  2. [Parámetros de respuesta](#b-parámetros-de-respuesta-1)
  3. [Códigos de respuesta](#c-códigos-de-respuesta)

##1. Introducción
Este documento tiene como intención ser una Guía rápida parvea que el desarrollador pueda integrar rápidamente el Botón de Pago Web de Culqi.

Para realizar una operación de autorización, se debe realizar 2 pasos: 
   - Crear un Venta --> se validará los datos del comercio y de la compra.
   - Procesar la Venta --> se solicitará los datos de la tarjeta y se procesará con la marca correspondiente.

Adicionalmente, se podrá realizar las siguientes operaciones:
   - Consultar una Venta --> obtendrás el estado de la venta y sus datos.
   - Anular una Venta --> se procesará la anulación siempre y cuando la venta esté autorizada.


##2. Requerimientos

Para que la aplicación o proyecto que estes desarrollando pueda utilizar el Botón de Pago Web de Culqi, debes instalar lo siguiente:

* PHP 5.3.3 o posterior.
* [Mcrypt](http://php.net/manual/es/book.mcrypt.php)
* [CURL](http://php.net/manual/es/book.curl.php)
* [Ctype](http://php.net/manual/es/book.ctype.php)


##3. Instalación

> Culqi ha desarrollado una libreria en PHP para simplificar la implementación del Botón de Pago Web en tu aplicación o proyecto. Puedes descargar la última versión de la librería de PHP e importarla a tu proyecto:

```php
require 'culqi.php';
```

##4. Comercio de prueba

Para facilitarle la implementación a nuestro Entorno de Integración, hemos creado un comercio de prueba denominado "Comercio Demo", el cual considera el logotipo de Culqi, asi como los siguientes datos que deberás utilizar en los próximos pasos.

  * Código de comercio: **demo**
  * Llave del comercio: **JlhLlpOB5s1aS6upiioJkmdQ0OYZ6HLS2+/o4iYO2MQ=**

Te brindamos algunas tarjetas de diferentes marcas que podrás utilizar una vez que te integres mediante del Botón de Pago Web:

Marca | Número de tarjeta | Fecha de expiración | CVV
-------------- | -------------- | -------------- | --------------
Visa | 4111 1111 1111 1111 | 09/2020 | 123
Visa | 4444 3333 2222 1111 | 09/2019 | 123
MasterCard | 5111 1111 1111 1118 | 06/2020 | 472
Amex | 3712 121212 12122 | 11/2017 | 2841
Diners | 3600 121212 1210 | 04/2018 | 964

<aside class="notice">
Si necesitas alguna ayuda u orientación, puedes comunicarte con nosotros vía email a soporte@culqi.com.
</aside>


##5. Configuración

Para empezar debes de configurar la librería en tu proyecto e iniciar las variables con los datos del "Comercio Demo":

```php
<?php$
Culqi::$codigoComercio = "demo";
Culqi::$llaveSecreta = "JlhLlpOB5s1aS6upiioJkmdQ0OYZ6HLS2+/o4iYO2MQ=";
Culqi::$servidorBase = 'https://integ-pago.culqi.com';
?>
```

> Los valores de las variables "llave_secreta" y "codigo_comercio" son los provistos para el "Comercio Demo". Cuando obtengas los valores de esas variables de tu comercio que debes solicitar a Culqi, solo reemplázalos. El valor de la variable "servidorBase", esta apuntando por defecto al Entorno de Integración de Culqi.

Estos son los parámetros de configuración:

Parámetro | Descripción
--------- | -----------
llaveSecreta | Llave secreta del comercio
codigoComercio | Código del comercio asignado por Culqi.
servidorBase | URL de Culqi a la que te conectarás. 
 | `Entorno de Integración la URL es: https://integ-pago.culqi.com` 
 | `Entorno de Producción: la URL es: https://pago.culqi.com`


##6. Operación de Autorización

###6.1 Creando una venta

Este paso es para pre-registrar y validar los datos de la venta del Comercio en la Pasarela de Pagos de Culqi, antes de solicitar los datos de la tarjeta al cliente. Si la respuesta es satisfactoria se debe proseguir con el siguiente paso, caso contrario, ustede debe revisar el código y mensaje de la respuesta que se le brinde.

Para crear una nueva venta deberá configurar la información de la misma, mediante los valores que establezca en los parámetros obligatorios.

####a. Parámetros de envío obligatorios

Nombre | Parámetro | Descripción | Tipo | Tamaño Mínimo| Tamaño Máximo
--------- | --------- | ------- | ----------- | ----------- | -----------
Número de Pedido | PARAM_NUM_PEDIDO | Número de pedido de la venta. ***Debe ser único por cada venta.*** | AN | 1 caracteres | 33 caracteres
Moneda | PARAM_MONEDA | Código [ISO-4217](https://es.wikipedia.org/wiki/ISO_4217) de la Moneda de la venta . Ej: Nuevos Soles: PEN , Dólares: USD | N | 3 caracteres | 3 caracteres
Monto | PARAM_MONTO | Monto de la venta, sin punto decimal Ej: 100.25 sería 10025 | N | 3 caracteres | 9 caracteres
Descripción | PARAM_DESCRIPCION | Breve descripción del producto o servicio brindado. | AN | 5 caracteres | 80 caracteres
Correo Electrónico | correo_electronico | Dirección del correo electrónico del cliente. | AN | 5 caracteres | 50 caracteres
País | PARAM_COD_PAIS | Código [ISO-3166-1 Alfa 2](https://es.wikipedia.org/wiki/ISO_3166-1) del País del cliente. Ej. Perú : PE | A | 2 caracteres | 2 caracteres
Ciudad | PARAM_CIUDAD | Ciudad del cliente. | A | 2 caracteres | 30 caracteres
Dirección | PARAM_DIRECCION | Dirección del cliente. | AN | 5 caracteres | 100 caracteres
Teléfono | PARAM_NUM_TEL | Número de teléfono del cliente. | N | 5 caracteres  | 15 caracteres
ID Usuario | id_usuario_comercio | Identificador del usuario. | N | 2 caracteres | 15 caracteres
Nombres | nombres | Nombres del cliente. | A | 2 caracteres | 50 caracteres
Apellidos | apellidos | Apellidos del cliente. | A | 2 caracteres | 50 caracteres


`AN = Alfanumérico` 
`N = Numérico` 

####b. Parámetros de envío opcionales

Nombre | Parámetro | Descripción | Tipo | Tamaño Máximo
--------- | --------- | ------- | ----------- | -----------
Vigencia | PARAM_VIGENCIA | Cantidad de minutos en los que el cliente puede realizar el pago. | N | 2 caracteres

`N = Numérico` 
`El tiempo de la vigencia es por defecto 10 minutos. Si va a usar este campo con otro valor, contáctese con Culqi para su habilitación.` 

Ejemplo de código para crear la venta:

```java
import com.culqi.*;

import com.culqi.sdk.Pago;
import java.util.HashMap;
import java.util.Map;

.
.
.

HashMap<String, Object> params = new HashMap<String, Object>();
params.put(Pago.PARAM_NUMERO_PEDIDO, "NO0001");
params.put(Pago.PARAM_MONEDA, "PEN");
params.put(Pago.PARAM_MONTO, 99999);
params.put(Pago.PARAM_DESCRIPCION, "Venta de prueba");
params.put(Pago.PARAM_COD_PAIS, "PE");
params.put(Pago.PARAM_CIUDAD, "Lima");
params.put(Pago.PARAM_DIRECCION, "Avenida Lima Nº123432");
params.put(Pago.PARAM_NUM_TEL, "016663420");
params.put("id_usuario_comercio", "016663420");
params.put("nombres", "William Oswaldo");
params.put("apellidos", "Muro Valencia");
params.put("correo_electronico", "wmuro@me.com");

params.put(Pago.PARAM_VIGENCIA, 60);

Map<String, Object> respuesta = com.culqi.sdk.Pago.crearDatosPago(params);

String informacion_venta = (String)respuesta.get(Pago.PARAM_INFO_VENTA);

//Codigo del comercio
System.out.println("Código Comercio" + respuesta("codigo_comercio"));

//Número de pedido
System.out.println("Número de pedido" + respuesta("numero_pedido"));

//Código de respuesta
System.out.println("Código Respuesta" + respuesta("codigo_respuesta"));

//Mensaje de respuesta
System.out.println("Mensaje Respuesta" + respuesta("mensaje_respuesta"));

//Mensaje de respuesta usuario
System.out.println("Mensaje Respuesta" + respuesta("mensaje_respuesta_usuario"));
```

La respuesta que obtendrás, si la creación fue exitosa, será una cadena cifrada que contiene un JSON.

```json
{"info_venta":"dkladkldlakdmdaaldklakd",
 "codigo_comercio":"testc101",
 "numero_pedido":"testc101",
 "codigo_respuesta":"venta_registrada",
 "mensaje_respuesta":"Venta creada exitosamente.",
 "ticket":"PqHLeGVGBniY7i4XN1N94QIx4MyHHYZhztE"}
```

####c. Parámetros de respuesta

Nombre | Parámetro | Descripción | Tipo
--------- | --------- | ------- | -----------
Informacion de Venta | informacion_venta | La información de la venta que se usa para configurar el botón de pago de Culqi. | AN
Código de Comercio | codigo_comercio | Código del comercio en Culqi. | AN
Número de Pedido | numero_pedido | Número de orden de la venta. | AN
Código de Respuesta | codigo_respuesta | Código de la respuesta. | AN
Mensaje de Respuesta | mensaje_respuesta | Mensaje de respuesta al desarrollador. | AN
Ticket | ticket | Ticket de la transacción. | AN

`AN = Alfanumérico` 

> El parámetro "informacion_venta" contenido en la respuesta del servidor de Culqi, debe de ser usado para configurar el Botón de Pago Web en la página del comercio como siguiente paso, ya que asi se inicia la solicitud de los datos de la tarjeta al cliente.

> Es importante que almacenes estos datos, ya que el parámetro "Ticket" lo usarás para otras operaciones.

####d. Códigos de respuesta

El parámetro "codigo_respuesta" puede tener los siguientes valores:

| Código Respuesta |	Descripción Genérica |
| ---------------- | -------------------- |
| venta_registrada | 	Se han validado y registrado exitosamente los datos de la venta
| comercio_invalido |	El comercio no está en condiciones de iniciar una venta
| parametro_invalido |	Los valores de los parámetros utilizados son erróneos o no tienen validez
| error_procesamiento |	Ha ocurrido un error mientras CULQI procesaba la transacción

> El parámetro "mensaje_respuesta" puede tener diferentes contenidos, con el mismo parámetro de "codigo_respuesta".

###6.2 Procesando una Venta

Para empezar, agrega el siguiente código en JavaScript en la página web donde tendrás el Botón de Pago Web:

`<script type="text/javascript" src="https://integ-pago.culqi.com/api/v1/culqi.js"></script>`

Usar una copia local no está soportado, y puede resultar en errores visibles por el usuario.

Esta integración te permite crear un botón customizado y pasar la respuesta de venta de Culqi a un callback (Función `Culqi()`) en Javacript. Puedes usar cualquier elemento HTML o evento JavaScript para abrir el formulario de pagos, y es independiente del lenguaje de programación que uses en tu backend.

> Puedes usar Culqi.js de la siguiente manera usando Jquery.

```javascript
//Puedes crear un botón de pago
<button id="btn_pago">Pagar</button>

//Aqui configuramos el botón de pago de Culqi.
<script>

//El código del comercio
checkout.codigo_comercio = "demo";

//La información de la venta: "informacion_venta" es el contenido del parámetro que recibiste en la creación.
checkout.informacion_venta = informacion_venta;


$('#btn_pago').on('click', function(e) {

// Abre el formulario de pago de Culqi.
checkout.abrir();

e.preventDefault();

});

//Esta función es llamada al terminar el proceso de pago.
//Debe de ser usada siempre, para poder obtener la respuesta.
function culqi (checkout) {

//Aquí recibes la respuesta del formulario de pago.
console.log(checkout.respuesta);

// Cierra el formulario de pago de Culqi.
checkout.cerrar();

};

</script>
```

####a. Parámetros de envío

Nombre | Parámetro | Descripción | Tipo
--------- | --------- | ------- | -----------
Código de Comercio | codigo_comercio | Código de comercio en Culqi. | AN
Información Venta | informacion_venta | Información de la venta cifrada.  | AN

Es muy importante que entiendas que la variable `codigo_comercio` se encarga de identificar a tu comercio en la comunicación con los servidores de Culqi, y la variable `informacion_venta` se encarga de enviar la información de la venta.

En este punto, debes visualizar el formulario de pago de Culqi. Luego que el cliente ingrese los datos de la tarjeta y se procese la venta, obtendrás como respuesta una cadena de texto, que puedes leer usando la variable `checkout.respuesta` que lo encuentras en el ejemplo de Javascript que se mostró previamente. Este contiene un JSON cifrado y se imprime en el log del navegador web. 

<aside class="error">
Es de suma importancia que envíes el contenido de la variable "checkout.respuesta" a tus servidores para decrifrarlo usando la librería "culqi.php", ya que la llave no debe ser usada en el navegador web por tu seguridad como comercio.</aside>

####b. Enviando la respuesta a tu servidor

Una vez que obtengas la respuesta de Culqi en tu página web es necesario que la envíes a tu servidor para decifrarla y poder mostrar al usuario el resultado de la transacción. A continuación un ejemplo utilizando Ajax y que invoca a una entrada llamada "/respuesta":

```javascript
$.ajax({
            url: "/respuesta.php",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(
                    {
                        'respuesta' : checkout.respuesta
                    }),
            success: function(data){
                var obj = JSON.parse(data);
                var tipo_respuesta_venta = obj["codigo_respuesta"];
                if (tipo_respuesta_venta == "venta_registrada") {
                    checkout.cerrar();
                } else {
                    // Brindale un mensaje amigable al cliente (Puedes usar el mensaje que Culqi recomienda o usar uno tuyo) e invitalo a reintentar la compra.
                    checkout.cerrar();
                }
            },
            error:function( ){
            }
        });
```

####c. Descrifrando la respuesta

Una vez recibida la respuesta, puedes decifrarla utilizando la librería PHP.

```java

...
import com.culqi.sdk.rest.RestConsumer;

/**
* Ejemplo de descifrado de una respuesta de transacción
*/

Culqi.llaveSecreta = "JlhLlpOB5s1aS6upiioJkmdQ0OYZ6HLS2+/o4iYO2MQ=";
Culqi.codigoComercio = "demo";
Culqi.servidorBase = "https://integ-pago.culqi.com";

//Retorna el JSON Descifrado
String respuesta_descfirada = Culqi.decifrar("<checkout.respuesta>");

Map<String, Object> respuesta = RestConsumer.gson.fromJson(respuesta_descfirada, Map.class);

//Codigo del comercio
System.out.println("Código Comercio" + respuesta.get("codigo_comercio"));

//Número de pedido
System.out.println("Número de pedido" + respuesta.get("numero_pedido"));

//Código de respuesta
System.out.println("Código Respuesta" + respuesta.get("codigo_respuesta"));

//Mensaje de respuesta
System.out.println("Mensaje Respuesta" + respuesta.get("mensaje_respuesta"));

//Mensaje de respuesta
System.out.println("Mensaje Respuesta" + respuesta.get("mensaje_respuesta_usuario"));

//ID de la Transacción
System.out.println("ID Transacción" + respuesta.get("id_transaccion"));

//Código de referencia
System.out.println("Código Referencia" + respuesta.get("codigo_referencia"));

//Código de autorización
System.out.println("Código Autorización" + respuesta.get("codigo_autorizacion"));

//Marca de la tarjeta
System.out.println("Marca" + respuesta.get("marca"));

//Emisor de la tarjeta
System.out.println("Emisor" + respuesta.get("emisor"));

//País de la tarjeta
System.out.println("País Tarjeta" + respuesta.get("pais_tarjeta"));

}
```

Obtendrás un JSON que contendrá los siguientes parámetros:

Nombre | Parámetro | Descripción | Tipo
--------- | ------- | ----------- | -----------
Código de Comercio | codigo_comercio | Código del comercio en Culqi. | AN
Número de Pedido | numero_pedido | Número de orden de la venta. | AN
Código de Respuesta | codigo_respuesta | Código de la respuesta.  | AN
Mensaje de Respuesta | mensaje_respuesta | Mensaje de respuesta al desarrollador. | AN
Mensaje de Respuesta Usuario | mensaje_respuesta_usuario | Mensaje de respuesta que se recomienda mostrar al usuario. | AN
ID Transacción | id_transaccion | ID de la transacción. | AN
Código Referencia | referencia_transaccion | Código de referencia de la transacción. | AN
Código Autorización | codigo_autorizacion | Código de autorización de la transacción. | AN
Marca | marca | Marca de la tarjeta usada para realizar el pago. | AN
Emisor | emisor | Banco emisor de la tarjeta usada para realizar el pago. Es referencial. | AN
País Tarjeta | pais_emisor | País de origen de la tarjeta usada para realizar el pago. Es referencial. | AN
Numero Tarjeta | numero_tarjeta | Número de la tarjeta enmascarada usada para realizar el pago. | N
Nombre Tarjeta Habiente | nombre_tarjeta_habiente | Nombre que se usó para realizar el pago. | A
Apellido Tarjeta Habiente | apellido_tarjeta_habiente | Apellido que se usó para realizar el pago. | A
`AN = Alfanumérico` 

> Almacena estos datos por cada petición que realices, y considera que los reintentos esta relacionado al mismo número de pedido, por ello usamos el parámetro de código de referencia.

####d. Códigos de respuesta

El parámetro "codigo_respuesta" puede tener los siguientes valores:

| Código Respuesta |	Descripción Genérica |
| ---------------- | -------------------- |
| venta_exitosa |	Se ha realizado una venta de manera exitosa |
| comercio_invalido |	El comercio no está en condiciones de iniciar una venta |
| parametro_invalido |	Los valores de los parametros utilizados son erróneos o no tienen validez |
| expiracion_invalida |	La fecha de vencimiento de la tarjeta es inválida |
| cvv_invalido |	El código de seguridad (CVV) de la tarjeta es inválido |
| operacion_denegada |	La operación ha sido denegada por el banco que emitió la tarjeta |
| fondos_insuficientes |	La tarjeta no dispone de fondos suficientes para realizar la compra |
| contactar_emisor |	La operación ha sido denegada por el banco que emitió la tarjeta. Se sugiere que el cliente se comunique con el banco |
| error_procesamiento |	Ha ocurrido un error mientras CULQI procesaba la transacción |
| tarjeta_perdida |	La tarjeta ha sido reportada como perdida |
| tarjeta_robada |	La tarjeta ha sido reportada como robada |
| tarjeta_vencida |	La tarjeta está vencida |

> Los parámetros "mensaje_respuesta" y "mensaje_respuesta_usuario" pueden tener diferentes contenidos, con el mismo parámetro de "codigo_respuesta".


##7. Operación de Consulta de una venta

Para consultar una venta debes de enviar el ticket de la transacción (que debes haber guardado) usando la librería de Culqi.

```java
/**
* Ejemplo de Consulta de una transacción
*/

Culqi.llaveSecreta = "JlhLlpOB5s1aS6upiioJkmdQ0OYZ6HLS2+/o4iYO2MQ=
Culqi.codigoComercio = "demo";
Culqi.servidorBase = "https://integ-pago.culqi.com";

//Se envía el ticket de la transacción
Map<String, Object> consulta = Pago.consultar("ticket");

//Y se obtiene como respuesta:

//Codigo del comercio
System.out.println("Código Comercio" + consulta.get("codigo_comercio"));

//Número de Pedido
System.out.println("Número de pedido" + consulta.get("numero_pedido"));

//Ticket de la transacción
System.out.println("Ticket" + consulta.get("ticket"));

//Estado de la transacción
System.out.println("Estado de la transacción" + consulta.get("estado_transaccion"));

//Código de respuesta
System.out.println("Código de Respuesta" + consulta.get("codigo_respuesta"));

//Mensaje de respuesta
System.out.println("Mensaje de Respuesta" + consulta.get("mensaje_respuesta"));

```


###a. Parámetros de envío

Nombre | Parámetro| Descripción | Tipo 
--------- | ----------- | ----------- | -----------
Ticket | ticket | El código de la transacción que quieres consultar. | AN


###b. Parámetros de respuesta

Nombre | Parámetro| Descripción | Tipo 
--------- | --------- | ----------- | -----------
Código de Comercio | codigo_comercio | El código del comercio en Culqi. | AN
Número de Pedido | numero_pedido | El número de orden de tu venta. | AN
Ticket | Ticket | El código de la transacción. | AN
Estado de Transacción | estado_transaccion | El estado de la transacción. | AN

El parámetro "estado_transaccion" puede tener los siguientes valores:

| Código Respuesta |	Descripción Genérica |
| ---------------- | -------------------- |
| devolucion_exitosa |	Se ha realizado la anulación/devolución de manera exitosa |
| comercio_invalido |	El comercio no está en condiciones de iniciar una venta |
| error_procesamiento |	Ha ocurrido un error mientras CULQI procesaba la transacción |

| Estado | Descripción Genérica |
| ---------------- | -------------------- |
| Creada | El cliente esta realizando la compra. |
| Abandonada | El cliente ha cerrado el formulario de pago. |
| Denegada | La venta ha sido rechazada. |
| Exitosa | La venta ha sido exitosa. |
| Depositada | La venta esta en proceso de liquidación y pago. |
| Devuelta | La venta ha sido anulada o devuelta. |

##8. Operación de Anulación de una venta

Para anular una venta debes de enviar el ticket de la transacción usando la librería de Culqi.


```java
/**
* Ejemplo de Anulación de una transacción
*/

Culqi.llaveSecreta = "JlhLlpOB5s1aS6upiioJkmdQ0OYZ6HLS2+/o4iYO2MQ=
Culqi.codigoComercio = "demo";
Culqi.servidorBase = "https://integ-pago.culqi.com";

//Se envía el ticket de la transacción
Map<String, Object> anulacion = Pago.anular("ticket");

//Y se obtiene como respuesta:

//Codigo del comercio
System.out.println("Código Comercio" + anulacion.get("codigo_comercio"));

//Número de Pedido
System.out.println("Número de pedido" + anulacion.get("numero_pedido"));

//Ticket de la transacción
System.out.println("Ticket" + anulacion.get("ticket"));

//Código de respuesta
System.out.println("Código de Respuesta" + anulacion.get("codigo_respuesta"));

//Mensaje de respuesta
System.out.println("Mensaje de Respuesta" + anulacion.get("mensaje_respuesta"));
```


###a. Parámetros de envío

Parámetro | Tipo | Descripción
--------- | ----------- | -----------
ticket | AN | El código de la transacción que quieres anular.


###b. Parámetros de respuesta

Nombre | Parámetro | Tipo | Descripción
--------- | --------- | ----------- | -----------
Código de comercio | codigo_comercio| El código del comercio en Culqi. | AN 
Número de pedido | numero_pedido| El número de pedido de tu venta. | AN 
Ticket | ticket| El código de la transacción. | AN 
Código de Respuesta | codigo_respuesta | Código de la respuesta. | AN
Mensaje de Respuesta | mensaje_respuesta | Mensaje de respuesta al desarrollador. | AN

####c. Códigos de respuesta

El parámetro "codigo_respuesta" puede tener los siguientes valores:

| Código Respuesta |	Descripción Genérica |
| ---------------- | -------------------- |
| devolucion_exitosa |	Se ha realizado la anulación/devolución de manera exitosa |
| comercio_invalido |	El comercio no está en condiciones de iniciar una venta |
| error_procesamiento |	Ha ocurrido un error mientras CULQI procesaba la transacción |

> El parámetro "mensaje_respuesta" puede tener diferentes contenidos, con el mismo parámetro de "codigo_respuesta".
