package grup.domain;

import java.time.LocalDateTime;

public class Transaction {

    private Integer id;
    private String numeUtilizator;
    private String coinName;
    private String type;
    private Integer price;
    private LocalDateTime timestamp;


    public Transaction(Integer id, String numeUtilizator, String coinName, String type, Integer price, LocalDateTime timestamp) {
        this.id = id;
        this.numeUtilizator = numeUtilizator;
        this.coinName = coinName;
        this.type = type;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
