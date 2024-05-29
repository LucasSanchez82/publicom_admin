/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author l.sanchez
 */
import DAO.MysqlConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.enums.Operator;
import utils.enums.OrderBy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 * template
 */
/**
 *
 * @author l.sanchez
 */
public class QueryBuilder {

    private String selectStr;
    private String fromStr;
    private String orderByStr;
    private String orderBy;
    private String limitStr;
    private ArrayList<WhereClause> whereList;
    private Connection connection;

    public QueryBuilder() throws SQLException {
        this.whereList = new ArrayList<>();
        this.connection = MysqlConnector.getConnection();
    }

    public QueryBuilder select(Column... column) {
        String[] selectArray = new String[column.length];
        for (int i = 0; i < column.length; i++) {
            selectArray[i] = column[i].getName();
        }
        this.selectStr = String.join(", ", selectArray);
        return this;
    }

    public QueryBuilder from(String from) {
        this.fromStr = from;
        return this;
    }

    private WhereClause createWhereClause(String key, Operator operator, Object value) {
        String opSymbol = switch (operator) {
            case EQUAL ->
                "=";
            case NOT_EQUAL ->
                "!=";
            case GREATER_THAN ->
                ">";
            case LESS_THAN ->
                "<";
            case GREATER_THAN_OR_EQUAL ->
                ">=";
            case LESS_THAN_OR_EQUAL ->
                "<=";
        };
        return new WhereClause(key, opSymbol, value);
    }

    public QueryBuilder where(String key, Operator operator, String value) {
        whereList.add(createWhereClause(key, operator, value));
        return this;
    }

    public QueryBuilder where(String key, Operator operator, int value) {
        whereList.add(createWhereClause(key, operator, value));
        return this;
    }

    public QueryBuilder orderBy(String column, OrderBy order) {
        String orderOp = switch (order) {
            case ASC ->
                "asc";
            case DESC ->
                "desc";
        };
        this.orderByStr = String.format("%s %s", column, orderOp);
        return this;
    }

    public QueryBuilder limit(int limit) {
        this.limitStr = String.format("LIMIT %d", limit);
        return this;
    }

    public String getQuery() {
        String query = String.format("SELECT %s FROM %s\n", this.selectStr, this.fromStr);
        if (!whereList.isEmpty()) {
            query += "WHERE ";
            for (int i = 0; i < whereList.size(); i++) {
                var instance = whereList.get(i);
                String andClause = i > 0 ? "\nAND " : "";
                String value;
                if (instance.getValue() instanceof String) {
                    value = String.format("'%s'", instance.getValue());
                } else {
                    value = instance.getValue().toString();
                }
                query += String.format("%s%s %s %s", andClause, instance.getKey(), instance.getOperator(), value);
            }
            query += "\n";
        }
        if (orderByStr != null) {
            query += String.format("ORDER BY %s\n", orderByStr);
        }
        if (limitStr != null) {
            query += limitStr;
        }
        query += ";";
        System.out.println(query);
        return query;
    }

    public int executeInsert(String table, List<String> columns, List<?> values) throws SQLException, IllegalArgumentException {
        if (columns.size() == values.size()) {
            String insertSql = String.format("INSERT INTO %s (%s) VALUES (", table, String.join(", ", columns));
            for (int i = 0; i < columns.size(); i++) {
                insertSql += "?";
                if (i < columns.size() - 1) {
                    insertSql += ",";
                }
            }
            insertSql += ");";

            PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            statement.executeUpdate();
            int generatedId = -1;
            var generatedKeysResultSet = statement.getGeneratedKeys();
            if (generatedKeysResultSet.next()) {
                generatedId = generatedKeysResultSet.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated key");
            }
            statement.close();
            return generatedId;
        } else {
            throw new IllegalArgumentException("il doit y avoir autant de columns que de values : \ncolumns : \n" + columns + "\nvalues" + values);
        }
    }

    public String update(String table, String... updateColumns) throws SQLException {
        String updateSql = String.format("UPDATE %s SET ", table);
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < updateColumns.length; i++) {
            updateSql += updateColumns[i] + "=?";
            if (i < updateColumns.length - 1) {
                updateSql += ",";
            }
        }

        if (whereList.isEmpty()) {
            throw new IllegalArgumentException("Update requires a WHERE clause");
        }

        updateSql += "\nWHERE ";
        for (int i = 0; i < whereList.size(); i++) {
            var instance = whereList.get(i);
            String andClause = i > 0 ? "\nAND " : "";
            String value;
            if (instance.getValue() instanceof String) {
                value = String.format("'%s'", instance.getValue());
            } else {
                value = instance.getValue().toString();
            }
            updateSql += String.format("%s%s %s %s", andClause, instance.getKey(), instance.getOperator(), value);
        }

        return updateSql;
    }

    public String deleteFrom(String table) throws SQLException {
        String deleteSql = String.format("DELETE FROM %s", table);

        if (!whereList.isEmpty()) {
            deleteSql += "\nWHERE ";
            for (int i = 0; i < whereList.size(); i++) {
                var instance = whereList.get(i);
                String andClause = i > 0 ? "\nAND " : "";
                String value;
                if (instance.getValue() instanceof String) {
                    value = String.format("'%s'", instance.getValue());
                } else {
                    value = instance.getValue().toString();
                }
                deleteSql += String.format("%s%s %s %s", andClause, instance.getKey(), instance.getOperator(), value);
            }
            return deleteSql + ";";
        }else throw new IllegalArgumentException("Il doit y avoir une clause where pour utiliser delete");
    }
}
