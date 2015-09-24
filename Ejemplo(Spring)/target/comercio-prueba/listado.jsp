<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
        body { color: #333; font: 12px/1.5 Verdana; text-align: center; }
        table { margin: 1em; border-collapse: collapse; }
        td, th { padding: .3em; border: 1px #ccc solid; }
        thead { background: #DCDCDC; }
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listado de TX</title>
</head>
<body>
    <sql:setDataSource
        var="myDS"
        driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:5406/culqi_db"
        user="culqi_pago" password="12348765"
    />
     
    <sql:query var="result"   dataSource="${myDS}">
        select ht.id, c.id, c.codigo_comercio, c.nombre_comercial, t.numero_pedido, m.nombre,
        t.monto_original, mrc.nombre, iin.iin, aut.ultimos4digitos_tarjeta,
        aut.tarhab_nombres, aut.tarhab_apellidos, aut.tarhab_email,
        ptx.direccion_ip, ptx.fingerprint, ptx.url, ptx.referer, ptx.fecha_hora_inicio, ptx.fecha_hora_fin,
        etx.nombre, tp.codigo_respuesta, tp.mensaje_respuesta, ht.fecha_hora
        from historial_transaccion ht
        inner join transaccion t on t.id = ht.transaccion_id
        inner join comercio c on c.id = t.comercio_id
        inner join moneda m on m.id = t.moneda_id
        left join estado_transaccion etx on etx.id = ht.estado_transaccion_id
        left join peticion_transaccion ptx on ptx.id = ht.peticion_transaccion_id
        left join transaccion_proceso tp on tp.id = ht.transaccion_proceso_id
        left join autorizacion aut on aut.transaccion_proceso_id = tp.id
        left join iin_tarjeta iin on iin.id = aut.iin_tarjeta_id
        left join comercio_procesador_terminal cpt on cpt.id = aut.comercio_procesador_terminal_id
        left join canal_proceso cp on cp.id = cpt.canal_proceso_id
        left join marca mrc on mrc.id = cp.marca_id
        order by ht.id desc;
    </sql:query>
     
    <table border="1">

        <!--
        <thead>
        <tr>
            <c:forEach var="columnName" items="${result.columnNames}">
                <th><c:out value="${columnName}"/></th>
            </c:forEach>
        </tr>
        </thead>
        -->

        <caption><h2>Listado Historial</h2></caption>
            <thead>
            <tr>
                <th>id hist</th>
                <th>id com</th>
                <th>cod com</th>
                <th>nombre com</th>
                <th>nro pedido</th>
                <th>moneda</th>
                <th>monto</th>
                <th>TH marca</th>
                <th>TH bin</th>
                <th>TH ult</th>
                <th>TH nombres</th>
                <th>TH apellidos</th>
                <th>TH email</th>
                <th>ip</th>
                <th>fingerprint</th>
                <th>url</th>
                <th>referer</th>
                <th>fyh envio</th>
                <th>fyh rpta</th>
                <th>estado</th>
                <th>cod rpta</th>
                <th>mensaje rpta</th>
                <th>fecha y hora</th>
            </tr>
            </thead>

        <%-- Output each row of data --%>
        <c:forEach  begin="0" end="200" var="row" items="${result.rowsByIndex}">
            <tr>
                <%-- Output each column of data --%>
                <c:forEach var="col" items="${row}">
                    <td><c:out value="${col}"/></td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

