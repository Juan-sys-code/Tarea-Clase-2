package bdd;

import java.sql.*;

public class Dietas {
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Dietas (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "empleado VARCHAR(50), " +
                "departamento VARCHAR(50), " +
                "cantidad DECIMAL(10, 2), " +
                "concepto VARCHAR(100))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Tabla Dietas creada o ya existente.");
        }
    }

    private static void insertDietas(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO Dietas (empleado, departamento, cantidad, concepto) VALUES " +
                "(?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            connection.setAutoCommit(false);

            Object[][] dietas = {
                    {"Juan Pérez", "Informática", 45.50, "Almuerzo de trabajo"},
                    {"Ana López", "Ventas", 20.00, "Taxi"},
                    {"Carlos Sánchez", "Recursos Humanos", 25.75, "Cena de negocios"},
                    {"María Fernández", "Informática", 60.00, "Viaje de negocios"},
                    {"Lucía Gómez", "Ventas", 15.00, "Desayuno de trabajo"},
                    {"Pedro Rodríguez", "Recursos Humanos", 35.20, "Almuerzo de trabajo"},
                    {"Sofía Martínez", "Informática", 25.50, "Desplazamiento"},
                    {"Miguel Torres", "Ventas", 55.00, "Cena de negocios"},
                    {"Laura Díaz", "Recursos Humanos", 42.30, "Alojamiento"},
                    {"Luis García", "Informática", 70.00, "Curso de formación"}
            };

            for (Object[] dieta : dietas) {
                preparedStatement.setString(1, (String) dieta[0]);
                preparedStatement.setString(2, (String) dieta[1]);
                preparedStatement.setDouble(3, (Double) dieta[2]);
                preparedStatement.setString(4, (String) dieta[3]);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Dietas insertadas correctamente.");
        }
    }

    private static void queryDietasInformatica(Connection connection) throws SQLException {
        String querySQL = "SELECT * FROM Dietas WHERE departamento = 'Informática' AND cantidad > 30.00";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querySQL)) {

            System.out.println("Dietas del departamento de Informática mayores de 30 €:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String empleado = resultSet.getString("empleado");
                String departamento = resultSet.getString("departamento");
                double cantidad = resultSet.getDouble("cantidad");
                String concepto = resultSet.getString("concepto");

                System.out.printf("ID: %d, Empleado: %s, Departamento: %s, Cantidad: %.2f, Concepto: %s%n",
                        id, empleado, departamento, cantidad, concepto);
            }
        }
    }

    private static void updateDietasVentas(Connection connection) throws SQLException {
        String updateSQL = "UPDATE Dietas SET cantidad = cantidad * 1.10 WHERE departamento = 'Ventas'";

        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(updateSQL);
            System.out.printf("Dietas del departamento de Ventas incrementadas en un 10%%. Filas afectadas: %d%n", rowsAffected);
        }
    }
}

