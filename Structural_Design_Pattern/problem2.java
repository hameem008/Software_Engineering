import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class Component {
    private String name;
    private String type;
    private int size;
    private String directory;
    private int componentCount;
    private String creationTime;
    private ArrayList<Component> list = new ArrayList<Component>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        if (!type.equals("File")) {
            size = 0;
            for (Component component : list) {
                size += component.getSize();
            }
        }
        return size;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public int getComponentCount() {
        componentCount = list.size();
        return componentCount;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setList(ArrayList<Component> list) {
        this.list = list;
    }

    public ArrayList<Component> getList() {
        return list;
    }

    Component(String name, String type, int size, String directory, String creationTime) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.directory = directory;
        this.componentCount = 0;
        this.creationTime = creationTime;
    }

    void details() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Size: " + getSize() + " kB");
        System.out.println("Directory: " + directory);
        System.out.println("Component Count: " + list.size());
        System.out.println("Creation Time: " + creationTime);
    }

    void listing() {
        for (Component component : list) {
            System.out.println(component.getName() + " " + component.getSize() + " " + component.getCreationTime());
        }
    }

    void delete(String name) {
        Component component = null;
        for (Component c : list) {
            if (c.getName().equals(name)) {
                component = c;
                break;
            }
        }
        if (component != null) {
            if (component.getComponentCount() == 0) {
                list.remove(component);
            }
        }
    }

    void recursiveDelete(String name) {
        Component component = null;
        for (Component c : list) {
            if (c.getName().equals(name)) {
                component = c;
                break;
            }
        }
        if (component != null) {
            if (component.getType().equals("File")) {
                System.out.println("Warning: Recursively deleted a file.");
                list.remove(component);
            } else {
                recDel(component);
                list.remove(component);
            }
        }
    }

    private void recDel(Component component) {
        if (component.getComponentCount() == 0)
            return;
        for (Component c : component.getList()) {
            recDel(c);
        }
        component.getList().clear();
    }
}

public class problem2 {
    public static void main(String[] args) {
        String directory = "";
        Component root = new Component("Root", "Root Drive", 0, "", "");
        Component currentComponent = root;
        while (true) {
            Scanner scn = new Scanner(System.in);
            String str = scn.nextLine();
            String[] tokenStrings = str.split("[ \\\\]");
            if (tokenStrings[0].equals("cd")) {
                if (tokenStrings[1].equals("~")) {
                    directory = "";
                    currentComponent = root;
                } else {
                    if (currentComponent.getType().equals("Root Drive")) {
                        for (Component component : currentComponent.getList()) {
                            if (component.getName().equals(tokenStrings[1])) {
                                currentComponent = component;
                                break;
                            }
                        }
                        if (currentComponent.getType().equals("Root Drive")) {
                            System.out.println("Warning: Drive dosen't exist.");
                        } else {
                            directory += currentComponent.getDirectory();
                        }
                    } else {
                        Component tempComponent = null;
                        for (Component component : currentComponent.getList()) {
                            if (component.getName().equals(tokenStrings[1])) {
                                tempComponent = component;
                                break;
                            }
                        }
                        if (tempComponent == null) {
                            System.out.println("Warning: Folder dosen't exist.");
                        } else if (tempComponent.getType().equals("File")) {
                            System.out.println("Warning: It is a file. Directory can't be changed.");
                        } else {
                            currentComponent = tempComponent;
                            directory = currentComponent.getDirectory();
                        }
                    }
                }
            } else if (tokenStrings[0].equals("ls")) {
                if (!currentComponent.getType().equals("Root Drive")
                        && currentComponent.getName().equals(tokenStrings[1])) {
                    currentComponent.details();
                }
            } else if (tokenStrings[0].equals("list")) {
                if (!currentComponent.getType().equals("Root Drive")) {
                    currentComponent.listing();
                }
            } else if (tokenStrings[0].equals("delete")) {
                currentComponent.delete(tokenStrings[1]);
            } else if (tokenStrings[0].equals("-r")) {
                if (!currentComponent.getType().equals("Root Drive")) {
                    currentComponent.recursiveDelete(tokenStrings[1]);
                }
            } else if (tokenStrings[0].equals("mkdir")) {
                if (!currentComponent.getType().equals("Root Drive")) {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                    String formattedDateTime = currentDateTime.format(formatter);
                    Component component = new Component(tokenStrings[1], "Folder", 0,
                            directory + "\\" + tokenStrings[1],
                            formattedDateTime);
                    currentComponent.getList().add(component);
                }
            } else if (tokenStrings[0].equals("touch")) {
                if (!currentComponent.getType().equals("Root Drive")) {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                    String formattedDateTime = currentDateTime.format(formatter);
                    Component component = new Component(tokenStrings[1], "File", Integer.parseInt(tokenStrings[2]),
                            directory + "\\" + tokenStrings[1],
                            formattedDateTime);
                    currentComponent.getList().add(component);
                }
            } else if (tokenStrings[0].equals("mkdrive")) {
                if (currentComponent.getType().equals("Root Drive")) {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                    String formattedDateTime = currentDateTime.format(formatter);
                    Component component = new Component(tokenStrings[1] + ":", "Drive", 0,
                            directory + tokenStrings[1] + ":",
                            formattedDateTime);
                    currentComponent.getList().add(component);
                }
            }
        }
    }
}
