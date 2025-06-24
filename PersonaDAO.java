
package CRUD_MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    private static final String URL_MYSQL = "jdbc:mysql://db4free.net:3306/crudtestdb";
    private static final String USERNAME = "crudusuario";
    private static final String PASSWORD = "Crud2025!";

    private static Connection connection;

    public PersonaDAO() {}

    public static Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL_MYSQL, USERNAME, PASSWORD);
            } catch (SQLException esql) {
                System.out.println("" + esql.getMessage());
                return null;
            }
        }
        return connection;
    }

    public void create(Persona persona) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "INSERT INTO personas (nombre, edad) VALUES (?, ?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, persona.getNombre());
        statement.setInt(2, persona.getEdad());
        statement.executeUpdate();
    }

    public Persona read(int id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM personas WHERE id = ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Persona persona = new Persona();
            persona.setId(resultSet.getInt("id"));
            persona.setNombre(resultSet.getString("nombre"));
            persona.setEdad(resultSet.getInt("edad"));
            return persona;
        }
        return null;
    }

    public void update(Persona persona) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "UPDATE personas SET nombre = ?, edad = ? WHERE id = ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, persona.getNombre());
        statement.setInt(2, persona.getEdad());
        statement.setInt(3, persona.getId());
        statement.executeUpdate();
    }

    public void delete(int id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "DELETE FROM personas WHERE id = ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public List<Persona> getAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM personas";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Persona persona = new Persona();
            persona.setId(resultSet.getInt("id"));
            persona.setNombre(resultSet.getString("nombre"));
            persona.setEdad(resultSet.getInt("edad"));
            personas.add(persona);
        }
        return personas;
    }
}
