package bdd;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            cn = conexion.conectar();
            st = cn.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}