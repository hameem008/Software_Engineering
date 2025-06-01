import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Adminstration {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 50000);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("Admin");
        while (true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            oos.writeObject(str);
        }
    }
}
