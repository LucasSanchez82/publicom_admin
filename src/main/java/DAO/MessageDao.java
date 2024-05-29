/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.MessageModel;
import Model.MessageModel.TABLESENUM;
import Model.UtilisateurModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Column;
import utils.QueryBuilder;
import utils.enums.Operator;
import utils.exception.EmailAlreadyExistException;
/**
 *
 * @author L.sanchez
 */
public class MessageDao extends SuperDao<MessageModel> {

    public MessageDao(MessageModel model) throws SQLException {
        super(model);
    }

    @Override
    protected MessageModel createModelInstance() {
        return new MessageModel();
    }

    @Override
    public void update(MessageModel model) throws SQLException {
        String idColumn = MessageModel.getColumnByEnum(TABLESENUM.ID_MESSAGE);
        Object idValue = super.model.get(idColumn);
        if (idValue.getClass() == int.class || idValue.getClass() == Integer.class) {
            QueryBuilder queryBuilder = new QueryBuilder();
            List<String> columnsStr = model.getColumnsStr();
            columnsStr.remove(0);

            String query = queryBuilder.where(idColumn, Operator.EQUAL, (int) idValue).update(this.model.getTable(), columnsStr.toArray(new String[0]));
            PreparedStatement statement = MysqlConnector.getConnection().prepareStatement(query);
            for (int i = 0; i < columnsStr.size(); i++) {
                statement.setObject(i + 1, model.getValues().get(i));
            }
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(MessageModel model) {
        try {
            QueryBuilder queryBuilder = new QueryBuilder();
            String columnStr = MessageModel.getColumnByEnum(TABLESENUM.ID_MESSAGE);
            String query = queryBuilder.where(columnStr, Operator.EQUAL, model.getId()).deleteFrom(super.model.getTable());
            PreparedStatement stmt = MysqlConnector.getConnection().prepareStatement(query);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verifConstraints(MessageModel model) throws EmailAlreadyExistException, SQLException {

    }
    
    public List<MessageModel> getAll(UtilisateurModel utilisateur) throws SQLException {
        List<Column> rawColumns = this.model.getColumns();
        Column[] columnsArray = new Column[rawColumns.size()];
        for (int i = 0; i < rawColumns.size(); i++) {
            if (rawColumns.get(i) instanceof Column) {
                columnsArray[i] = rawColumns.get(i);
            }
        }
        String messageColumn = MessageModel.getColumnByEnum(TABLESENUM.ID_MESSAGE);
        String getAllQuery = super.queryBuilder
                .select(columnsArray)
                .from(this.model.getTable())
                .where(messageColumn, Operator.EQUAL, utilisateur.getId())
                .getQuery();
        PreparedStatement stat = this.getConnection().prepareStatement(getAllQuery);
        ResultSet rs = stat.executeQuery();
        ArrayList<MessageModel> listModels = new ArrayList<>();

        while (rs.next()) {
            MessageModel rowModel = this.createModelInstance();
            for (Column column : this.model.getColumns()) {
                String columnName = column.getName();
                Object value = rs.getObject(columnName);
                    rowModel.set(columnName, value);
            }
            listModels.add(rowModel);
        }
        return listModels;
    }

}
