package controller;

import java.util.ArrayList;
import java.util.List;

import Model.MessageModel;

/**
 * TableModelMessages étend SuperTableModel pour gérer spécifiquement la visualisation des messages.
 */
public class TableModelMessage extends SuperTableModel<MessageModel> {

    private final List<String> columnNamesWithHiddens;

    public TableModelMessage(List<MessageModel> messages) {
        super();
        this.items = messages; // Initialisation avec une liste de messages existante
        this.columnNamesWithHiddens = new ArrayList<>((new MessageModel()).getColumnsStr());
        this.columnNames = new ArrayList<>(columnNamesWithHiddens);
    }

    @Override
    protected Object getItemValue(MessageModel item, int columnIndex) {
        // Supposons que MessageModel a une méthode get qui prend un nom de colonne et retourne la valeur
        return item.get(columnNames.get(columnIndex));
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Rendre toutes les cellules non éditables puisque la table est destinée uniquement à la visualisation
        return false;
    }

    // Vous pouvez ajouter d'autres méthodes si nécessaire pour la gestion de la visualisation
}