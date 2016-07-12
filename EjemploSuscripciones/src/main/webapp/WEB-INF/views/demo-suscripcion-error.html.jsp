<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Suscripcion</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src='https://integ-pago.culqi.com/api/v1/culqi.js'></script>
</head>
<body>
    <table id="tb_suscripcion">
        <tr>
            <td>Codigo del comercio:</td>
            <td><input id="codigo_comercio"></td>
        </tr>
        <tr>
            <td>Codigo del pais:</td>
            <td><input id="codigo_pais"></td>
        </tr>
        <tr>
            <td>Direccion:</td>
            <td><input id="direccion"></td>
        </tr>
        <tr>
            <td>Ciudad:</td>
            <td><input id="ciudad"></td>
        </tr>
        <tr>
            <td>Telefono:</td>
            <td><input id="telefono"></td>
        </tr>
        <tr>
            <td>Nombre:</td>
            <td><input id="nombre"></td>
        </tr>
        <tr>
            <td>Correo electronico:</td>
            <td><input id="correo_electronico"></td>
        </tr>
        <tr>
            <td>Apellido:</td>
            <td><input id="apellido"></td>
        </tr>
        <tr>
            <td>Usuario ID:</td>
            <td><input id="usuario_id"></td>
        </tr>
        <tr>
            <td>Plan ID:</td>
            <td><input id="plan_id"></td>
        </tr>
        <tr>
            <td>Cargos predefinidos:</td>
            <td><input type="checkbox" id="cargos_predefinidos"></td>
        </tr>
    </table>

    <p><button id="btn_pago">Pagar con Tarjeta</button></p>

    </body>
</html>