import java.io.Serializable;
import java.net.Socket;

class Subscriber implements Serializable {
    private String name;

    Subscriber(String name) {
        setName(name);
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}