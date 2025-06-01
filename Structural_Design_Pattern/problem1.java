import java.util.Scanner;

interface SpaceshipWork {
    void login();

    void repair();

    void work();

    void logout();
}

class Crewmate implements SpaceshipWork {
    @Override
    public void login() {
        System.out.println("Welcome Crewmate!");
    }

    @Override
    public void repair() {
        System.out.println("Repairing the spaceship.");
    }

    @Override
    public void work() {
        System.out.println("Doing reasearch.");
    }

    @Override
    public void logout() {
        System.out.println("Bye Bye crewmate.");
    }
}

class Decorator implements SpaceshipWork {
    Crewmate crewmate;

    Decorator(Crewmate crewmate) {
        this.crewmate = crewmate;
    }

    @Override
    public void login() {
        crewmate.login();
    }

    @Override
    public void repair() {
        crewmate.repair();
    }

    @Override
    public void work() {
        crewmate.work();
    }

    @Override
    public void logout() {
        crewmate.logout();
    }
}

class Imposter extends Decorator {
    Imposter(Crewmate crewmate) {
        super(crewmate);
    }

    @Override
    public void login() {
        super.login();
        String redColorCode = "\u001B[31m";
        String resetColorCode = "\u001B[0m";
        String redText = "We won't tell anyone; you are an imposter.";
        System.out.println(redColorCode + redText + resetColorCode);
    }

    @Override
    public void repair() {
        super.repair();
        String redColorCode = "\u001B[31m";
        String resetColorCode = "\u001B[0m";
        String redText = "Damaging the spaceship.";
        System.out.println(redColorCode + redText + resetColorCode);
    }

    @Override
    public void work() {
        super.work();
        String redColorCode = "\u001B[31m";
        String resetColorCode = "\u001B[0m";
        String redText = "Trying to kill a crewmate.";
        System.out.println(redColorCode + redText + resetColorCode);
        redText = "Successfully killed a crewmate.";
        System.out.println(redColorCode + redText + resetColorCode);
    }

    @Override
    public void logout() {
        super.logout();
        String redColorCode = "\u001B[31m";
        String resetColorCode = "\u001B[0m";
        String redText = "See you again Comrade Imposter.";
        System.out.println(redColorCode + redText + resetColorCode);
    }
}

public class problem1 {
    public static void main(String[] args) {
        Crewmate crew1 = new Crewmate(), crew2 = new Crewmate(), crew3 = new Crewmate();
        Imposter imp1 = new Imposter(new Crewmate());
        while (true) {
            SpaceshipWork spaceshipWork = null;
            Scanner scn = new Scanner(System.in);
            String str = scn.nextLine();
            String[] tokeStrings = str.split("\\s+");
            if (tokeStrings[0].equals("login") && tokeStrings[1].equals("crew1")) {
                spaceshipWork = crew1;
            } else if (tokeStrings[0].equals("login") && tokeStrings[1].equals("crew2")) {
                spaceshipWork = crew2;
            } else if (tokeStrings[0].equals("login") && tokeStrings[1].equals("crew3")) {
                spaceshipWork = crew3;
            } else if (tokeStrings[0].equals("login") && tokeStrings[1].equals("imp1")) {
                spaceshipWork = imp1;
            }
            if (spaceshipWork != null) {
                spaceshipWork.login();
                while (true) {
                    String cmd = scn.next();
                    if (cmd.equals("repair")) {
                        spaceshipWork.repair();
                    } else if (cmd.equals("work")) {
                        spaceshipWork.work();
                    } else if (cmd.equals("logout")) {
                        spaceshipWork.logout();
                        break;
                    }
                }
            }
        }
    }
}