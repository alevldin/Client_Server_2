import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));
        while (true) {
            try
                    (SocketChannel socketChanel = serverChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChanel.isConnected()) {
                    int bytesCount = socketChanel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    System.out.println("Получено сообщение от клиента " + msg);
                    String output = msg.replaceAll("\\s", "");
                    socketChanel.write(ByteBuffer.wrap(("Отправляю строку без пробелов : " + output).getBytes(StandardCharsets.UTF_8)));

                }

            } catch (IOException err) {

            }
        }
    }
}