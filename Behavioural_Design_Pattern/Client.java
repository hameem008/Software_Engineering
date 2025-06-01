import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner scc = new Scanner(System.in);
        String name = scc.nextLine();
        Subscriber subscriber = new Subscriber(name);
        new Thread(()->{
            try {
                Socket socket = new Socket("127.0.0.1", 50000);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("Client");
                oos.writeObject(subscriber);
                oos.writeObject("Notification");
                while (true) {
                    try {
                        ArrayList<String> notification = (ArrayList<String>) ois.readObject();
                        if(notification.size()>0) {
                            for(String str : notification) {
                                System.out.println(str);
                            }
                            oos.writeObject("ok");
                        }
                        else {
                            oos.writeObject("not ok");
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                Socket socket = new Socket("127.0.0.1", 50000);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("Client");
                oos.writeObject(subscriber);
                oos.writeObject("Command");
                while (true) {
                    try {
                        Scanner sc = new Scanner(System.in);
                        String str = sc.nextLine();
                        oos.writeObject(str);
                        String[] command = str.split(" ");
                        if(command[0].equals("V")) {
                            ArrayList<String> details = (ArrayList<String>) ois.readObject();
                            for(String st : details) {
                                System.out.println(st);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
