import java.util.ArrayList;
import java.util.Scanner;

abstract class Milk {
    private String name;
    private Double price;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}

class RegularMilk extends Milk {
    RegularMilk() {
        setName("Regular Milk");
        setPrice(0.0);
    }
}

class AlmondMilk extends Milk {
    AlmondMilk() {
        setName("Almond Milk");
        setPrice(60.0);
    }
}

abstract class Sweetness {
    private String name;
    private Double price;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}

class Sugar extends Sweetness {
    Sugar() {
        setName("Sugar");
        setPrice(0.0);
    }
}

class Swetner extends Sweetness {
    Swetner() {
        setName("Swetner");
        setPrice(0.0);
    }
}

abstract class Ingredients {
    private String name;
    private Double price;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}

class Coffee extends Ingredients {
    Coffee() {
        setName("Coffee");
        setPrice(0.0);
    }
}

class ChocolateSyrup extends Ingredients {
    ChocolateSyrup() {
        setName("Chocolate Syrup");
        setPrice(0.0);
    }
}

class ChocolateIceCream extends Ingredients {
    ChocolateIceCream() {
        setName("Chocolate Ice Cream");
        setPrice(0.0);
    }
}

class StrawberrySyrup extends Ingredients {
    StrawberrySyrup() {
        setName("Strawberry Syrup");
        setPrice(0.0);
    }
}

class StrawberryIceCream extends Ingredients {
    StrawberryIceCream() {
        setName("Strawberry Ice Cream");
        setPrice(0.0);
    }
}

class Vanilla extends Ingredients {
    Vanilla() {
        setName("Vanilla");
        setPrice(0.0);
    }
}

class Jello extends Ingredients {
    Jello() {
        setName("Jello");
        setPrice(0.0);
    }
}

class SugarFreeJello extends Ingredients {
    SugarFreeJello() {
        setName("Sugar Free Jello");
        setPrice(0.0);
    }
}

class Candy extends Ingredients {
    Candy() {
        setName("Candy");
        setPrice(50.0);
    }
}

class Cookies extends Ingredients {
    Cookies() {
        setName("Cookies");
        setPrice(40.0);
    }
}

abstract class Shake {
    private String name;
    private Double basePrice;
    private Double totalPrice;
    private Milk milk;
    private Sweetness sweetness;
    private ArrayList<Ingredients> baseIngredients = new ArrayList<Ingredients>();
    private ArrayList<Ingredients> additionalIngredients = new ArrayList<Ingredients>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setTotalPrice() {
        Double price = 0.0;
        price += basePrice;
        price += milk.getPrice();
        price += sweetness.getPrice();
        for (Ingredients ingredients : baseIngredients) {
            price += ingredients.getPrice();
        }
        for (Ingredients ingredients : additionalIngredients) {
            price += ingredients.getPrice();
        }
        totalPrice = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    public Milk getMilk() {
        return milk;
    }

    public void setSweetness(Sweetness sweetness) {
        this.sweetness = sweetness;
    }

    public Sweetness getSweetness() {
        return sweetness;
    }

    public void setBaseIngredients(ArrayList<Ingredients> baseIngredients) {
        this.baseIngredients = baseIngredients;
    }

    public ArrayList<Ingredients> getBaseIngredients() {
        return baseIngredients;
    }

    public void setAdditionalIngredients(ArrayList<Ingredients> additionalIngredients) {
        this.additionalIngredients = additionalIngredients;
    }

    public ArrayList<Ingredients> getAdditionalIngredients() {
        return additionalIngredients;
    }

    void printShakeDetails() {
        System.out.println("Name of the shake: " + name);
        System.out.println("Base Price: Tk " + basePrice);
        System.out.println("Milk Type: ");
        if (milk.getPrice() != 0) {
            System.out.println(milk.getName() + " (" + "Tk " + milk.getPrice() + ")");
        } else {
            System.out.println(milk.getName());
        }
        System.out.println("Base Ingredients:");
        for (Ingredients ingredients : baseIngredients) {
            System.out.println(ingredients.getName());
        }
        if (additionalIngredients.size() == 0) {
            System.out.println("not ok");
            System.out.println("This shake has no additional ingredients.");
            for (Ingredients ingredients : additionalIngredients) {
                System.out.println(ingredients.getName());
            }
        } else {
            System.out.println("Additional Ingredients:");
            for (Ingredients ingredients : additionalIngredients) {
                System.out.println(ingredients.getName() + " (" + "Tk " + ingredients.getPrice() + ")");
            }
        }
        System.out.println("Total Price: Tk " + totalPrice);
    }
}

class ChocolateShake extends Shake {
    ChocolateShake(Milk milk, ArrayList<Ingredients> additionalIngredients) {
        setName("Chocolate Shake");
        setBasePrice(230.0);
        setMilk(milk);
        setSweetness(new Sugar());
        getBaseIngredients().add(new ChocolateSyrup());
        getBaseIngredients().add(new ChocolateIceCream());
        for (Ingredients ingredients : additionalIngredients) {
            getAdditionalIngredients().add(ingredients);
        }
        setTotalPrice();
    }
}

class CoffeeShake extends Shake {
    CoffeeShake(Milk milk, ArrayList<Ingredients> additionalIngredients) {
        setName("Coffee Shake");
        setBasePrice(250.0);
        setMilk(milk);
        setSweetness(new Sugar());
        getBaseIngredients().add(new Coffee());
        getBaseIngredients().add(new Jello());
        for (Ingredients ingredients : additionalIngredients) {
            getAdditionalIngredients().add(ingredients);
        }
        setTotalPrice();
    }
}

class StrawberryShake extends Shake {
    StrawberryShake(Milk milk, ArrayList<Ingredients> additionalIngredients) {
        setName("Strawberry Shake");
        setBasePrice(200.0);
        setMilk(milk);
        setSweetness(new Sugar());
        getBaseIngredients().add(new StrawberrySyrup());
        getBaseIngredients().add(new StrawberryIceCream());
        for (Ingredients ingredients : additionalIngredients) {
            getAdditionalIngredients().add(ingredients);
        }
        setTotalPrice();
    }
}

class VanillaShake extends Shake {
    VanillaShake(Milk milk, ArrayList<Ingredients> additionalIngredients) {
        setName("Vanilla Shake");
        setBasePrice(190.0);
        setMilk(milk);
        setSweetness(new Sugar());
        getBaseIngredients().add(new Vanilla());
        getBaseIngredients().add(new Jello());
        for (Ingredients ingredients : additionalIngredients) {
            getAdditionalIngredients().add(ingredients);
        }
        setTotalPrice();
    }
}

class ZeroShake extends Shake {
    ZeroShake(Milk milk, ArrayList<Ingredients> additionalIngredients) {
        setName("Zero Shake");
        setBasePrice(240.0);
        setMilk(milk);
        setSweetness(new Swetner());
        getBaseIngredients().add(new Vanilla());
        getBaseIngredients().add(new SugarFreeJello());
        for (Ingredients ingredients : additionalIngredients) {
            getAdditionalIngredients().add(ingredients);
        }
        setTotalPrice();
    }
}

class ShakeBuilder {
    private String shakeName;
    private Milk milk;
    private ArrayList<Ingredients> additionalIngredients = new ArrayList<Ingredients>();

    public void setShakeName(String shakeName) {
        this.shakeName = shakeName;
    }

    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    void addIngredients(Ingredients ingredients) {
        additionalIngredients.add(ingredients);
    }

    Shake buildShake() {
        if (shakeName.equalsIgnoreCase("Chocolate Shake")) {
            return new ChocolateShake(milk, additionalIngredients);
        } else if (shakeName.equalsIgnoreCase("Coffee Shake")) {
            return new CoffeeShake(milk, additionalIngredients);
        } else if (shakeName.equalsIgnoreCase("Strawberry Shake")) {
            return new StrawberryShake(milk, additionalIngredients);
        } else if (shakeName.equalsIgnoreCase("Vanilla Shake")) {
            return new VanillaShake(milk, additionalIngredients);
        } else if (shakeName.equalsIgnoreCase("Zero Shake")) {
            return new ZeroShake(milk, additionalIngredients);
        } else {
            return null;
        }
    }

    void builderInitializer() {
        shakeName = "";
        milk = null;
        additionalIngredients.clear();
    }
}

public class Main {
    public static void main(String[] args) {
        while (true) {
            Double totalCost = 0.0;
            ArrayList<Shake> orderShakes = new ArrayList<Shake>();
            ShakeBuilder shakeBuilder = new ShakeBuilder();
            Scanner scn = new Scanner(System.in);
            System.out.println("Do you want to place an order?");
            System.out.println("Press o for placing an order.");
            String cmd = scn.nextLine();
            if (cmd.equalsIgnoreCase("o")) {
                while (true) {
                    System.out.println("Type the name of the shake you wnat.");
                    System.out.println("1. Chocolate Shake");
                    System.out.println("2. Coffee Shake");
                    System.out.println("3. Strawberry Shake");
                    System.out.println("4. Vanilla Shake");
                    System.out.println("5. Zero Shake");
                    String shakeName = scn.nextLine();
                    shakeBuilder.setShakeName(shakeName);
                    System.out.println("Type the type of the milk you want.");
                    System.out.println("1. Regular Milk");
                    System.out.println("2. Almond MIlk (Lactose-free Milk)");
                    String milkType = scn.nextLine();
                    if (milkType.equalsIgnoreCase("Almond Milk")) {
                        shakeBuilder.setMilk(new AlmondMilk());
                    } else if (milkType.equalsIgnoreCase("Regular Milk")) {
                        shakeBuilder.setMilk(new RegularMilk());
                    }
                    System.out.println("Do you want candy on the top of your shake?");
                    String isCandy = scn.nextLine();
                    if (isCandy.equalsIgnoreCase("Yes")) {
                        shakeBuilder.addIngredients(new Candy());
                    }
                    System.out.println("Do you want cookies on the top of your shake?");
                    String isCookies = scn.nextLine();
                    if (isCookies.equalsIgnoreCase("Yes")) {
                        shakeBuilder.addIngredients(new Cookies());
                    }
                    Shake shake = shakeBuilder.buildShake();
                    if (shake == null) {
                        System.out.println("We don't have this shake.");
                    } else {
                        System.out.println("Order accepted.");
                        totalCost += shake.getTotalPrice();
                        orderShakes.add(shake);
                        shakeBuilder.builderInitializer();
                    }
                    System.out.println("Do you want another shake?");
                    System.out.println("Type e for exiting ");
                    System.out.println("Type anything for another shake.");
                    String discision = scn.nextLine();
                    if (discision.equals("e")) {
                        System.out.println();
                        break;
                    }
                }
            }
            if (totalCost > 0) {
                for (Shake shake : orderShakes) {
                    shake.printShakeDetails();
                    System.out.println();
                }
                System.out.println("Total cost for this order: Tk " + totalCost);
            }

        }
    }

}