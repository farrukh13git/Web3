package model;


import java.util.Objects;

public class BankClient {
    private long id;
    private String name;
    private String password;
    private Long money;

    public BankClient(String name, String password, Long money) {
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public BankClient(long id, String name, String password, Long money) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Long getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankClient that = (BankClient) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getMoney(), that.getMoney());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getMoney());
    }
}