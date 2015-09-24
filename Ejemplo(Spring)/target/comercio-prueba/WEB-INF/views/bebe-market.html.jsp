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
  <link rel="stylesheet" href="bebe.css">
  <script src="${URLModuloPago}/culqi.js"></script>

  <link href="//cdn.rawgit.com/noelboss/featherlight/1.3.2/release/featherlight.min.css" type="text/css" rel="stylesheet" title="Featherlight Styles" />
  <script src="//code.jquery.com/jquery-latest.js"></script>
  <script src="//cdn.rawgit.com/noelboss/featherlight/1.3.2/release/featherlight.min.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>

<div class="container">
  <span id="btn_pago" class="boton"><i class="icon-credit-card"></i> Pagar con Tarjeta Visa/Mastercard</span>
</div>
<script>

  $(document).ready(function(){

    $("#lightbox").hide();

    checkout.codigo_comercio = "${codigo_comercio}";
    checkout.informacion_venta = "${informacion_venta}";

    $('#btn_pago').on('click', function(e) {

      checkout.abrir();
      e.preventDefault();

    });

  });

  function culqi (checkout) {

    checkout.cerrar();

    window.location.href = '/bebereinicio.html';

  };

  $(window).on('popstate', function() {

    checkout.cerrar();

  });
</script>

</body>
</html>