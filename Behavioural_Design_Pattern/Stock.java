import java.io.Serializable;
import java.util.ArrayList;

class Stock implements Serializable {
    private String name;
    private int count;
    private double price;
    private ArrayList<Subscriber> list = new ArrayList<Subscriber>();

    Stock(String name, int count, double price) {
        setName(name);
        setCount(count);
        setPrice(price);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setlist(ArrayList<Subscriber> list) {
        this.list = list;
    }

    public ArrayList<Subscriber> getlist() {
        return list;
    }

    void addSubscriber(Subscriber subscriber) {
        Subscriber sc = null;
        for(Subscriber s : list) {
            if(s.getName().equals(subscriber.getName())) {
                sc = s;
                break;
            }
        }
        if(sc == null){
            list.add(subscriber);
        }
    }

    void removeSubscriber(Subscriber subscriber) {
        Subscriber sc = null;
        for (Subscriber s : list) {
            if (s.getName().equals(subscriber.getName())) {
                sc = s;
                break;
            }
        }
        if (sc != null) {
            list.remove(sc);
        }
    }
}