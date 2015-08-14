<%--
  Created by IntelliJ IDEA.
  User: William
  Date: 7/21/15
  Time: 4:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Efe</title>
  <link rel="stylesheet" href="main.css">
  <script src="jquery-1.11.2.min.js"></script>
  <script src="${URLModuloPago}/culqi.js"></script>

</head>

<body>

<div id="lightbox" class="shadow">
  <div class="mensaje">
    <i class="icon-cancel"></i>
			<span class="logo-comercio">
				<img src="images/logo_efe.png" alt="">
			</span>
    <h2>Gracias</h2>
    <p>Su transacción ha sido exitosa.</p>
  </div>
</div>

<div class="container">
  <span id="btn_pago" class="boton"><i class="icon-credit-card"></i> Pagar</span>
</div>
<script>

  $(document).ready(function(){

    checkout.codigo_comercio = "${codigo_comercio}";
    checkout.informacion_venta = "${informacion_venta}";

    $('#btn_pago').on('click', function(e) {

      checkout.abrir();
      e.preventDefault();

    });

  });

  function culqi (checkout) {

    console.log(checkout.respuesta);

    checkout.cerrar();

    window.location.href = '/efe-respuesta.html';

  };

  $(window).on('popstate', function() {

    checkout.cerrar();

  });
</script>

</body>
</html>