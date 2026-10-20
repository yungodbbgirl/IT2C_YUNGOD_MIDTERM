package com.scc.dao;

import com.scc.model.ScenarioEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public boolean insertJob(ScenarioEntity entity) {
        String sql = "INSERT INTO service_jobs (customer_name, service_type, estimated_cost, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entity.getCustomerName());
            pstmt.setString(2, entity.getServiceType());
            pstmt.setDouble(3, entity.getEstimatedCost());
            pstmt.setString(4, entity.getStatus());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> getAllJobs() {
        List<ScenarioEntity> entities = new ArrayList<>();
        String sql = "SELECT id, customer_name, service_type, estimated_cost, status FROM service_jobs";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ScenarioEntity entity = new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("service_type"),
                        rs.getDouble("estimated_cost"),
                        rs.getString("status")
                );
                entities.add(entity);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching records: " + e.getMessage());
        }

        return entities;
    }
}