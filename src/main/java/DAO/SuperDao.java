/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Model;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Column;
import utils.QueryBuilder;

/**
 *
 * @author 08luc
 */
public abstract class SuperDao<T extends Model> {

    protected T model;
    private Connection connection;
    protected QueryBuilder queryBuilder;
    private Component component;

    protected SuperDao(T model) throws SQLException {
        this.connection = MysqlConnector.getConnection();
        this.model = model;
        this.queryBuilder = new QueryBuilder();
    }

    public Connection getConnection() {
        return connection;
    }

    public List<T> getAll() throws SQLException {
        List<Column> rawColumns = this.model.getColumns();
        Column[] columnsArray = new Column[rawColumns.size()];
        for (int i = 0; i < rawColumns.size(); i++) {
            if (rawColumns.get(i) instanceof Column) {
                columnsArray[i] = rawColumns.get(i);
            }
        }
        String getAllQuery = this.queryBuilder
                .select(columnsArray)
                .from(this.model.getTable())
                .getQuery();
        PreparedStatement stat = this.getConnection().prepareStatement(getAllQuery);
        ResultSet rs = stat.executeQuery();
        ArrayList<T> listModels = new ArrayList<>();

        while (rs.next()) {
            T rowModel = this.createModelInstance();
            for (Column column : this.model.getColumns()) {
                String columnName = column.getName();
                Object value = rs.getObject(columnName);
                    rowModel.set(columnName, value);
            }
            listModels.add(rowModel);
        }
        return listModels;
    }

    public T insert(T model) throws SQLException, IllegalArgumentException {
        this.verifConstraints(model);
        List<String> columns = model.getColumnsStr().subList(1, model.getColumnsStr().size());
        List<Object> values = model.getValues();
        int generatedId = this.queryBuilder.executeInsert(model.getTable(), columns, values);
        model.setId(generatedId);
        return model;
    }

    abstract public void delete(T model);

    abstract public void update(T model) throws SQLException;

    public T get() {
        System.out.println("get");
        return null;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
    
    
    protected abstract T createModelInstance();
    public abstract void verifConstraints(T model) throws IllegalArgumentException, SQLException;
    
}
