import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static final String INPUT_FILE_NAME = "in.txt";
    public static void main(String[] args) throws Exception {
        ArrayList<Stock> stockList = new ArrayList<Stock>();
        HashMap<String,ArrayList<String>> notify = new HashMap<String,ArrayList<String>>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null)
                break;
            String[] out = line.split(" ");
            Stock stock = new Stock(out[0], Integer.parseInt(out[1]), Double.parseDouble(out[2]));
            stockList.add(stock);
        }
        bufferedReader.close();
        System.out.println("Server started");
        ServerSocket serverSocket = new ServerSocket(50000);
        while (true) {
            Socket socket = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String user = (String) ois.readObject();
            if(user.equals("Client")){
                Object client = ois.readObject();
                Subscriber subscriber = (Subscriber) client;
                Object mode = ois.readObject();
                String threadMode = (String) mode;
                if(threadMode.equals("Notification")) {
                    new Thread(()->{
                        while (true) {
                            try {
                                ArrayList<String> notification = notify.get(subscriber.getName());
                                if(notification == null){
                                    notification = new ArrayList<String>();
                                }
                                oos.writeObject(notification);
                                Object fb = ois.readObject();
                                String sbstr = (String) fb;
                                if(sbstr.equals("ok")) {
                                    notify.remove(subscriber.getName());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                break;
                            }
                        }
                    }).start();
                }
                else if(threadMode.equals("Command")) {
                    new Thread(()->{
                        try {
                            while (true) {
                                try {
                                    Object object = ois.readObject();
                                    String str = (String) object;
                                    String[] command = str.split(" ");
                                    if (command[0].equals("S")) {
                                        Stock stock = null;
                                        for(Stock s : stockList){
                                            if(command[1].equals(s.getName())){
                                                stock = s;
                                                break;
                                            }
                                        }
                                        if(stock!=null){
                                            stock.addSubscriber(subscriber);
                                        }
                                    }
                                    else if(command[0].equals("U")) {
                                        Stock stock = null;
                                        for(Stock s : stockList){
                                            if(command[1].equals(s.getName())){
                                                stock = s;
                                                break;
                                            }
                                        }
                                        if(stock!=null){
                                            stock.removeSubscriber(subscriber);
                                        }
                                    }
                                    else if(command[0].equals("V")) {
                                        ArrayList<String> ret = new ArrayList<String>();
                                        for(Stock st : stockList) {
                                            for(Subscriber sc : st.getlist()){
                                                if(sc.getName().equals(subscriber.getName())) {
                                                    String dum = st.getName()+" "+st.getCount()+" "+ st.getPrice();
                                                    ret.add(dum);
                                                }
                                            }
                                        }
                                        oos.writeObject(ret);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }).start();
                }
            } else if(user.equals("Admin")) {
                Admin admin = new Admin();
                new Thread(()->{
                    while (true) {
                        try{
                            Object object = ois.readObject();
                            String str = (String) object;
                            String[] command = str.split(" ");
                            if(command[0].equals("I")) {
                                admin.changePrice(command[0],command[1],Double.parseDouble(command[2]),stockList);
                                Stock stock = null;
                                for(Stock s :stockList){
                                    if(command[1].equals(s.getName())){
                                        stock = s;
                                        break;
                                    }
                                }
                                if(stock!=null){
                                    String notification = "Price increased of stock " + stock.getName();
                                    for(Subscriber sc : stock.getlist()){
                                        ArrayList<String> dum = notify.get(sc.getName());
                                        if(dum == null){
                                            notify.put(sc.getName(),new ArrayList<String>());
                                        }
                                        notify.get(sc.getName()).add(notification);
                                    }
                                }
                            }
                            else if(command[0].equals("D")) {
                                admin.changePrice(command[0],command[1],Double.parseDouble(command[2]),stockList);
                                Stock stock = null;
                                for(Stock s :stockList){
                                    if(command[1].equals(s.getName())){
                                        stock = s;
                                        break;
                                    }
                                }
                                if(stock!=null){
                                    String notification = "Price decreased of stock " + stock.getName();
                                    for(Subscriber sc : stock.getlist()){
                                        ArrayList<String> dum = notify.get(sc.getName());
                                        if(dum == null){
                                            notify.put(sc.getName(),new ArrayList<String>());
                                        }
                                        notify.get(sc.getName()).add(notification);
                                    }
                                }
                            }
                            else if(command[0].equals("C")) {
                                admin.changeInCount(command[1],Integer.parseInt(command[2]),stockList);
                                Stock stock = null;
                                for(Stock s :stockList){
                                    if(command[1].equals(s.getName())){
                                        stock = s;
                                        break;
                                    }
                                }
                                if(stock!=null){
                                    String notification = "Count changed of stock " + stock.getName();
                                    for(Subscriber sc : stock.getlist()){
                                        ArrayList<String> dum = notify.get(sc.getName());
                                        if(dum == null){
                                            notify.put(sc.getName(),new ArrayList<String>());
                                        }
                                        notify.get(sc.getName()).add(notification);
                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}