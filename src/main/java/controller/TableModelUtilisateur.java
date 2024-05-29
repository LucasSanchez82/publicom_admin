package controller;

import DAO.UtilisateurDao;
import Model.UtilisateurModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Column;

public class TableModelUtilisateur extends SuperTableModel<UtilisateurModel> {

    private final List<String> columnNamesWithHiddens;

    public TableModelUtilisateur() {
        super();
        this.columnNamesWithHiddens = new ArrayList<>((new UtilisateurModel()).getColumnsStr());
        var columnNamesTemp = new ArrayList<>(columnNamesWithHiddens);

        int mdpIndex = this.columnNamesWithHiddens.indexOf(UtilisateurModel.getColumnByEnum(UtilisateurModel.TABLESENUM.MDP));
        int idIndex = this.columnNamesWithHiddens.indexOf(UtilisateurModel.getColumnByEnum(UtilisateurModel.TABLESENUM.ID));

        columnNamesTemp.remove(mdpIndex);
        columnNamesTemp.remove(idIndex);
        this.columnNames = new ArrayList<>(columnNamesTemp);
    }

    public void addUtilisateur(UtilisateurModel utilisateur) {
        addItem(utilisateur);
    }

    public void removeUtilisateur(UtilisateurModel utilisateur) {
        removeItem(utilisateur);
    }

    public UtilisateurModel getUtilisateur(int rowIndex) {
        return getRow(rowIndex);
    }

    public void cleanUtilisateurs() {
        cleanItems();
    }

    @Override
    protected Object getItemValue(UtilisateurModel item, int columnIndex) {
        return item.get(columnNames.get(columnIndex));
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int realColumn = columnIndexVisibleToHidden(columnIndex);
        UtilisateurModel utilisateurModel = new UtilisateurModel();
        if ((utilisateurModel.getColumns().size() > realColumn) && (columnIndex >= 0)) {
            return utilisateurModel.getColumns().get(realColumn).getType();
        } else {
            return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        int hiddenColumnName = columnIndexVisibleToHidden(columnIndex);
        UtilisateurModel user = this.items.get(rowIndex);
        String columnName = user.getColumns().get(hiddenColumnName).getName();
        for (Column column : user.getColumns()) {
            boolean isGoodColumn = column.getName().equals(columnName);
            boolean isGoodType = column.getType().isInstance(aValue);
            if (isGoodColumn && !isGoodType) {
                throw new IllegalArgumentException("Type de valeur incorrect pour la colonne " + columnName);
            } else if (isGoodColumn) {
                user.set(columnName, aValue);
                fireTableCellUpdated(rowIndex, columnIndex);
                try {
                    UtilisateurDao utilisateurDao = new UtilisateurDao(user);
                    utilisateurDao.update(user);
                } catch (SQLException ex) {
                    Logger.getLogger(TableModelUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
    }

    private int columnIndexVisibleToHidden(int columnIndex) {
        String visibleColumnName = this.columnNames.get(columnIndex);
        return this.columnNamesWithHiddens.indexOf(visibleColumnName);
    }
}