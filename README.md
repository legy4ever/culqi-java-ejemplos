# Culqi - Java

![Status](https://travis-ci.org/culqi/Culqi-Java.svg?branch=master)

### Usando el Botón de Pago y JAVA

Este tutorial te ayudará a integrar el Botón de Pago Web de Culqi usando Java

Vamos a crear un simple ejemplo demostrando como aceptar pagos usando el Botón de Pago en el navegador y la librería JAVA de Culqi en tu servidor.

Para comenzar vas a necesitar:

JAVA mayor o igual a la versión X

Instalar manualmente los siguientes JARs:
La librería Java de Culqi
Google Gson - http://mvnrepository.com/artifact/com.google.code.gson/gson/2.3.1
Bouncy Castle Provider - http://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on/1.52

Importamos la librería de Culqi

```Java
import com.culqi.sdk.*;
```

Así podemos crear una venta nueva.

```Java
@RequestMapping(value = "/venta", method = RequestMethod.GET) public String generarVenta(Model model) throws IOException {

Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";
Culqi.codigoComercio = "testc105";
Culqi.servidorBase = "https://integ-pago.culqi.com";

String orden = (new Date()).toString();    
HashMap<String, Object> params = new HashMap<String, Object>();
params.put(Pago.PARAM_NUMERO_PEDIDO, orden);
params.put(Pago.PARAM_MONEDA, "PEN");
params.put(Pago.PARAM_MONTO, 1234);
params.put(Pago.PARAM_DESCRIPCION, "Venta de pruebas.");
Map<String, Object> respuesta = com.culqi.sdk.Pago.crearDatosPago(params);
if (respuesta.get("codigo_respuesta").equals("OK") ) {

return respuesta.get("codigo_respuesta").toString();

} else {

return "error_response";

}

}
```

Para anular una venta:

@ResponseBody @RequestMapping(value = "/anular", method = RequestMethod.POST) public String anularVenta(@RequestBody @Valid VentaAnular ventaAnular, HttpServletRequest request) {      Culqi.llaveSecreta = "zzmxZlgIJtKKy0F71DMsZPWnPVzow4S90abBScLDIrk=";     Culqi.codigoComercio = "testc101";     Culqi.servidorBase = URLModuloPago;      Map<String, Object> anulacion = Pago.anular(ventaAnular.getId_transaccion());      try {         return new ObjectMapper().writeValueAsString(anulacion);
     } catch (JsonProcessingException e) {          throw new RuntimeException(e);      }  }

