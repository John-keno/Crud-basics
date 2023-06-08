package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {

    private final String JDBCUrl = "jdbc:sqlite:src/database/store.db";
    private final String JDBCClass = "org.sqlite.JDBC";
    public ProductDAO() {
    }

    private Connection getDBConnection() throws ClassNotFoundException{
        Connection connection;

        try {
            Class.forName(JDBCClass);
            connection = DriverManager.getConnection(JDBCUrl);
        } catch (SQLException e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            connection = null;
        }

        return connection;
    }

    public List<Product> getAllProducts(){
        String query = "select * from product";

        List<Product> products = new ArrayList<>();

        try {
            Connection connection = getDBConnection();
            PreparedStatement queryStatement = connection.prepareStatement(query);
            ResultSet result = queryStatement.executeQuery();

            while (result.next()){
                Product product  = new Product();
                product.setId(result.getInt("id"));
                product.setProductName(result.getString("productName"));
                product.setSKU(result.getString("SKU"));
                product.setDescription(result.getString("description"));
                product.setCategory(result.getString("category"));
                product.setPrice(result.getInt("price"));
                products.add(product);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            products = null;
        }

        return products;
    }

    public Product getProduct(int id){
        String query = "select * from Product where id = ?";
        Product product = new Product();

        try {
            Connection connection = getDBConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            product.setId(result.getInt("id"));
            product.setProductName(result.getString("productName"));
            product.setSKU(result.getString("SKU"));
            product.setDescription(result.getString("description"));
            product.setCategory(result.getString("category"));
            product.setPrice(result.getInt("price"));

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            product = null;
            System.out.println("Product not found");
        }
        return product;
    }

    public Boolean updateProduct(Product product){
        String query = "update Product set productName = ?, SKU = ?, description = ?, category = ?, price = ? where id = ?";
        boolean successful;

        try{
            Connection connection = getDBConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getSKU());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getCategory());
            statement.setInt(5, product.getPrice());
            statement.setInt(6, product.getId());
            successful = statement.executeUpdate() > 0;
            connection.close();
        } catch (ClassNotFoundException | SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            successful = false;
        }

        return successful;
    }

    public Boolean deleteProduct(int id){
        String query = "delete from Product where id = ?";
        boolean successful;
        try{
            Connection connection = getDBConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            successful = statement.executeUpdate() > 0;
            connection.close();
        } catch (ClassNotFoundException | SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            successful = false;
        }

        return successful;
    }

    public Boolean addProduct(Product product){
        String query = "insert into Product(productName, SKU, description, category, price) values (?, ?, ?, ?, ?)";
        boolean successful;

        try{
            Connection connection = getDBConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getSKU());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getCategory());
            statement.setInt(5, product.getPrice());
            successful = statement.executeUpdate() > 0;
            connection.close();
        } catch (ClassNotFoundException | SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            System.out.println(e.getLocalizedMessage());
            successful = false;
        }

        return successful;
    }
}
