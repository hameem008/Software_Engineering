import java.util.ArrayList;

class Admin {
    Admin(){
    }
    void changePrice(String command, String stockName, Double amount, ArrayList<Stock> stockList) {
        Stock stock = null;
        for (Stock s : stockList) {
            if (stockName.equals(s.getName())) {
                stock = s;
                break;
            }
        }
        if (stock != null) {
            if (command.equals("I")) {
                stock.setPrice(stock.getPrice() + amount);
            } else if (command.equals("D")) {
                stock.setPrice(stock.getPrice() - amount);
            }
        }
    }
    void changeInCount(String stockName, int stockCount, ArrayList<Stock> stockList) {
        Stock stock = null;
        for (Stock s : stockList) {
            if (stockName.equals(s.getName())) {
                stock = s;
                break;
            }
        }
        if (stock != null) {
            stock.setCount(stockCount);
        }
    }
}