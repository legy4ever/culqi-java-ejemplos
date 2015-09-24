<!DOCTYPE html>

<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Comercio Certificacion</title>
    <link rel="icon"
          type="image/png"
          href="/images/favicon-16x16.png" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://pago.culqi.com/culqi.js"></script>

    <style>
        #t_anulacion {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            width: 700px;
            border-collapse: collapse;
        }

        #t_anulacion td, #t_anulacion th {
            font-size: 1em;
            border: 1px solid #0AA6A9;
            padding: 3px 7px 2px 7px;
        }

        #t_anulacion th {
            font-size: 1.1em;
            text-align: left;
            padding-top: 5px;
            padding-bottom: 4px;
            background-color: #0AA6A9;
            color: #ffffff;
        }

        #t_anulacion tr.alt td {
            color: #000000;
            background-color: #0AA6AA;
        }

        #t_autorizacion {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            width: 700px;
            border-collapse: collapse;
        }

        #t_autorizacion td, #t_autorizacion th {
            font-size: 1em;
            border: 1px solid #0AA6A9;
            padding: 3px 7px 2px 7px;
        }

        #t_autorizacion th {
            font-size: 1.1em;
            text-align: left;
            padding-top: 5px;
            padding-bottom: 4px;
            background-color: #0AA6A9;
            color: #ffffff;
        }

        #t_autorizacion tr.alt td {
            color: #000000;
            background-color: #0AA6AA;
        }
        #t_venta {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            width: 400px;
            border-collapse: collapse;
        }

        #t_venta td, #t_autorizacion th {
            font-size: 1em;
            border: 1px solid #0AA6A9;
            padding: 3px 7px 2px 7px;
        }

        #t_venta th {
            font-size: 1.1em;
            text-align: left;
            padding-top: 5px;
            padding-bottom: 4px;
            background-color: #0AA6A9;
            color: #ffffff;
        }

        #t_venta tr.alt td {
            color: #000000;
            background-color: #0AA6AA;
        }
    </style>

</head>
<body>

<br>

<h5>Información de la Venta enviada por el comercio</h5>

<table id="t_venta">
    <tr>
        <th>Campo</th>
        <th>Valor</th>
    </tr>
    <tr>
        <td>Código de comercio:</td>
        <td>${codigoComercio}</td>
    </tr>
    <tr>
        <td>Número de pedido:</td>
        <td>${numero_pedido}</td>
    </tr>
    <tr>
        <td>Descripción:</td>
        <td>${descripción}</td>
    </tr>
    <tr>
        <td>Moneda:</td>
        <td>${moneda}</td>
    </tr>
    <tr>
        <td>Monto:</td>
        <td>${monto}</td>
    </tr>
</table>

<button id="btn_pago" class="boton">Pagar</button>

<button id="btn_reinicio" class="boton">Nueva venta</button>

<button id="btn_anular" class="boton">Anular venta</button>

<p id="texto_anular">Transacción anulada</p>


<h5>Respuesta de la Autorización</h5>

<table id="t_autorizacion">
    <tr>
        <th>Campo</th>
        <th>Valor</th>
    </tr>
    <tr>
        <td>Código de comercio:</td>
        <td><p id="codigo_comercio"></p></td>
    </tr>
    <tr>
        <td>Número de pedido</td>
        <td><p id="nro_pedido"></p></td>
    </tr>
    <tr>
        <td>Código de respuesta</td>
        <td><p id="codigo_respuesta"></p></td>
    </tr>
    <tr>
        <td>Mensaje de respuesta</td>
        <td><p id="mensaje_respuesta"></p></td>
    </tr>
    <tr>
        <td>ID Transacción</td>
        <td><p id="id_transaccion"></p></td>
    </tr>
    <tr>
        <td>Código de referencia</td>
        <td><p id="codigo_referencia"></p></td>
    </tr>
    <tr>
        <td>Código de autorización</td>
        <td><p id="codigo_autorizacion"></p></td>
    </tr>
    <tr>
        <td>Marca</td>
        <td><p id="marca"></p></td>
    </tr>
    <tr>
        <td>Emisor</td>
        <td><p id="emisor"></p></td>
    </tr>
    <tr>
        <td>País de la tarjeta</td>
        <td><p id="pais_tarjeta"></p></td>
    </tr>
    <tr>
        <td>IIN de la tarjeta</td>
        <td><p id="iin_tarjeta"></p></td>
    </tr>
    <tr>
        <td>Nombre del tarjeta habiente</td>
        <td><p id="nombre_tarjetahab"></p></td>
    </tr>
    <tr>
        <td>Apellido del tarjeta habiente</td>
        <td><p id="apellido_tarjetahab"></p></td>
    </tr>

</table>

<h5>Respuesta de la Anulación</h5>

<table id="t_anulacion">
    <tr>
        <th>Campo</th>
        <th>Valor</th>
    </tr>
    <tr>
        <td>Código de respuesta:</td>
        <td><p id="codigo_respuesta_anulacion"></p></td>
    </tr>
    <tr>
        <td>Mensaje de respuesta:</td>
        <td><p id="mensaje_respuesta_anulacion"></p></td>
    </tr>
</table>

<script>

    $(document).ready(function(){

        $("#btn_anular").hide();

        $("#texto_anular").hide();

        //$("#texto_anular_procesando").hide();

        checkout.codigo_comercio = "${codigo_comercio}";
        checkout.informacion_venta = "${informacion_venta}";

        $('#btn_pago').on('click', function(e) {

            checkout.abrir();
            e.preventDefault();

        });

        $('#btn_reinicio').on('click', function(e) {

            location.reload();
            e.preventDefault();

        });

        $('#btn_anular').on('click', function(e) {

            anular();
            e.preventDefault();

        });


    });


    var token;

    function culqi (checkout) {

        $.ajax({
            url: "/respuesta",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(

                    {
                        'respuesta' : checkout.respuesta
                    }),

            success: function(data){

                var obj = JSON.parse(data);

                var codigo_respuesta_venta = obj["codigo_respuesta"];

                console.log(codigo_respuesta_venta);

                if (codigo_respuesta_venta == "OK") {
                    //checkout.autorizado();
                    checkout.cerrar();

                    token = obj["token"];

                    document.getElementById("codigo_comercio").innerHTML = obj["codigo_comercio"];
                    document.getElementById("nro_pedido").innerHTML = obj["nro_pedido"];
                    document.getElementById("codigo_respuesta").innerHTML = obj["codigo_respuesta"];
                    document.getElementById("mensaje_respuesta").innerHTML = obj["mensaje_respuesta"];

                    document.getElementById("id_transaccion").innerHTML = obj["token"];

                    document.getElementById("codigo_referencia").innerHTML = obj["referencia_transaccion"];
                    document.getElementById("codigo_autorizacion").innerHTML = obj["codigo_autorizacion"];
                    document.getElementById("marca").innerHTML = obj["marca"];
                    document.getElementById("emisor").innerHTML = obj["nombre_emisor"];
                    document.getElementById("pais_tarjeta").innerHTML = obj["pais_emisor"];

                    document.getElementById("iin_tarjeta").innerHTML = obj["iin_tarjeta"];

                    document.getElementById("nombre_tarjetahab").innerHTML = obj["nombre_tarjeta_habiente"];
                    document.getElementById("apellido_tarjetahab").innerHTML = obj["apellido_tarjeta_habiente"];


                    $("#btn_pago").hide();

                    $("#btn_reinicio").show();

                    $("#btn_anular").show();


                } else {

                    checkout.cerrar();

                    document.getElementById("codigo_comercio").innerHTML = obj["codigo_comercio"];
                    document.getElementById("nro_pedido").innerHTML = obj["nro_pedido"];
                    document.getElementById("codigo_respuesta").innerHTML = obj["codigo_respuesta"];
                    document.getElementById("mensaje_respuesta").innerHTML = obj["mensaje_respuesta"];

                    document.getElementById("id_transaccion").innerHTML = obj["token"];

                    document.getElementById("codigo_referencia").innerHTML = "-";
                    document.getElementById("codigo_autorizacion").innerHTML = "-";
                    document.getElementById("marca").innerHTML = obj["marca"];
                    document.getElementById("emisor").innerHTML = obj["nombre_emisor"];
                    document.getElementById("pais_tarjeta").innerHTML = obj["pais_emisor"];

                    document.getElementById("iin_tarjeta").innerHTML = obj["iin_tarjeta"];

                    document.getElementById("nombre_tarjetahab").innerHTML = obj["nombre_tarjeta_habiente"];
                    document.getElementById("apellido_tarjetahab").innerHTML = obj["apellido_tarjeta_habiente"];


                    $("#btn_reinicio").show();

                    $("#btn_pago").hide();

                }

            },
            error:function( ){

            }

        });
    };

    $(window).on('popstate', function() {

        checkout.cerrar();

    });

    function anular() {

        $("#btn_anular").hide();

        $.ajax({
            url: "/anular",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(

                    {
                        'id_transaccion' : token
                    }),

            success: function(data){

                var obj = JSON.parse(data);

                var codigo_respuesta_venta = obj["codigo_respuesta"];
                var mensaje_respuesta_venta = obj["mensaje_respuesta"];

                if (codigo_respuesta_venta == "OK") {

                    document.getElementById("codigo_respuesta_anulacion").innerHTML = obj["codigo_respuesta"];
                    document.getElementById("mensaje_respuesta_anulacion").innerHTML = obj["mensaje_respuesta"];


                } else {

                    document.getElementById("codigo_respuesta_anulacion").innerHTML = obj["codigo_respuesta"];
                    document.getElementById("mensaje_respuesta_anulacion").innerHTML = obj["mensaje_respuesta"];

                }

            },
            error:function( ){

            }
        });
    }

</script>

</body>
</html>