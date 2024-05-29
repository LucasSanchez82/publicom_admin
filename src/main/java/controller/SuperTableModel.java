package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Classe de modèle de table générique pour réutiliser la logique commune à différents modèles de table.
 */
public abstract class SuperTableModel<T> extends AbstractTableModel {
    protected List<T> items;
    
    protected List<String> columnNames;
        public SuperTableModel() {
        this.items = new ArrayList<>();
        this.columnNames = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
        fireTableDataChanged();
    }

    public void removeItem(T item) {
        items.remove(item);
        fireTableDataChanged();
    }

    public T getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
    
    
    public T getRow(int index) {
        System.out.println(getItem(index));
        return getItem(index);
    }

    public void cleanItems() {
        items.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnNames.size()) {
            return columnNames.get(columnIndex);
        } else {
            return "Column " + columnIndex;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < items.size()) {
            return getItemValue(items.get(rowIndex), columnIndex);
        }
        return null;
    }

    /**
     * Méthode abstraite pour obtenir la valeur de l'élément à un indice de colonne spécifique.
     * @param item L'élément de la liste.
     * @param columnIndex L'indice de la colonne.
     * @return La valeur de l'élément à l'indice de colonne donné.
     */
    protected abstract Object getItemValue(T item, int columnIndex);
}