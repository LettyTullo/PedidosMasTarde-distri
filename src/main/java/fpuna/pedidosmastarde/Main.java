package fpuna.pedidosmastarde;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int puerto = args.length > 1 ? Integer.parseInt(args[1]) : 6000;
        int idProducto = args.length > 2 ? Integer.parseInt(args[2]) : 305;
        String requestId = UUID.randomUUID().toString();

        JSONObject solicitud = new JSONObject();
        solicitud.put("tipo", "GET_STOCK");
        solicitud.put("requestId", requestId);
        solicitud.put("idproducto", idProducto);

        DatagramSocket socket = new DatagramSocket();

        try {
            // si ebye no responde en 3 segundos, receive() lanza el error SocketTimeoutException
            socket.setSoTimeout(3000);

            byte[] datos = solicitud.toJSONString().getBytes(StandardCharsets.UTF_8);
            DatagramPacket paquete = new DatagramPacket(datos, datos.length,
                    InetAddress.getByName(host), puerto);
            socket.send(paquete);

            byte[] buffer = new byte[2048];
            DatagramPacket paqueteRespuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteRespuesta);

            String linea = new String(paqueteRespuesta.getData(), 0,
                    paqueteRespuesta.getLength(), StandardCharsets.UTF_8);

            JSONObject respuesta = (JSONObject) new JSONParser().parse(linea);
            if (!Objects.equals(requestId, respuesta.get("requestId"))) {
                throw new IllegalStateException("El requestId de la respuesta no coincide");
            }
            if (!Boolean.TRUE.equals(respuesta.get("ok"))) {
                System.out.println("eBye rechazo la consulta: " + linea);
                return;
            }

            System.out.println(respuesta.toJSONString());
            System.out.println("Stock disponible del producto " + idProducto
                    + ": " + respuesta.get("stock_disponible"));
        } catch (SocketTimeoutException e) {
            System.out.println("eBye no respondio en 3 segundos (servidor apagado o datagrama perdido)");
        } finally {
            socket.close();
        }
    }
}
