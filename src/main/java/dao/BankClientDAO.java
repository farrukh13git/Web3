package dao;

import model.BankClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BankClientDAO {

    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<BankClient> getAllBankClient() {
        List<BankClient> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("select * from bank_client");
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                list.add(new BankClient(result.getLong(1), result.getString(2), result.getString(3), result.getLong(4)));
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean validateClient(String name, String password) {
        boolean bool = false;
        try (PreparedStatement stmt = connection.prepareStatement("select * from bank_client where name = ?")) {
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();
            if (result.next() && result.getString(3).equals(password)) {
                bool = true;
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public void updateClientsMoney(String name, Long transactValue) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE bank_client SET money = money + ? WHERE name = ?")){
            stmt.setLong(1, transactValue);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isClientHasSum(String name, Long expectedSum) {
        try {
            return getClientByName(name).getMoney() >= expectedSum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BankClient getClientByName(String name) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from bank_client where name = ?");
        stmt.setString(1, name);
        ResultSet result = stmt.executeQuery();
        result.next();
        BankClient bankClient = new BankClient(result.getLong(1), result.getString(2), result.getString(3), result.getLong(4));
        result.close();
        stmt.close();
        return bankClient;
    }

    public boolean addClient(BankClient client) throws SQLException {
        createTable();
        List<BankClient> list = getAllBankClient();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BankClient bankClient = (BankClient) it.next();
            if (bankClient.getName().equals(client.getName())) {
                return false;
            }
        }
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO bank_client (name, password, money) VALUES (?, ?, ?)");
        stmt.setString(1, client.getName());
        stmt.setString(2, client.getPassword());
        stmt.setLong(3, client.getMoney());
        stmt.executeUpdate();
        stmt.close();
        return true;
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists bank_client (id bigint auto_increment, name varchar(256), password varchar(256), money bigint, primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS bank_client");
        stmt.close();
    }
}
