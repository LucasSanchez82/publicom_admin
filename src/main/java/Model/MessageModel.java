/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Column;

/**
 *
 * @author 08luc
 */
public class MessageModel extends Model {

    private static final List<Column> columns;
    
    

    public MessageModel() {
        super("MESSAGE", columns); // Set table name
    }

    public MessageModel(UtilisateurModel utilisateur, String titre, String contenu, boolean enLigne) throws IllegalArgumentException {
        this();
        super.set(getColumnByEnum(TABLESENUM.ID_UTILISATEUR), utilisateur.getId());
        super.set(getColumnByEnum(TABLESENUM.TITRE), titre);
        super.set(getColumnByEnum(TABLESENUM.CONTENU), contenu);
        super.set(getColumnByEnum(TABLESENUM.EN_LIGNE), enLigne);
    }
    
    
    // Static \\
    
    public static enum TABLESENUM {
        ID_MESSAGE,
        ID_UTILISATEUR,
        TITRE,
        CONTENU,
        EN_LIGNE,
    }

    public static String getColumnByEnum(TABLESENUM column) {
        return switch (column) {
            case ID_MESSAGE ->
                "IDMESSAGE";
            case ID_UTILISATEUR ->
                "IDUTILISATEUR";
            case TITRE ->
                "TITREMESSAGE";
            case CONTENU ->
                "CONTENUMESSAGE";
            case EN_LIGNE ->
                "ENLIGNE";
        };
    }

    static {
        List<Column> cols = new ArrayList<>();
        cols.add(Column.ofInt(getColumnByEnum(TABLESENUM.ID_MESSAGE))); // Use Columns class for type safety
        cols.add(Column.ofInt(getColumnByEnum(TABLESENUM.ID_UTILISATEUR)));
        cols.add(Column.ofBool(getColumnByEnum(TABLESENUM.EN_LIGNE)));
        cols.add(Column.ofString(getColumnByEnum(TABLESENUM.TITRE)));
        cols.add(Column.ofString(getColumnByEnum(TABLESENUM.CONTENU)));
        
        columns = Collections.unmodifiableList(cols);
    }
    
    // Public \\

    @Override
    public int getId() {
        return (int) super.get(getColumnByEnum(TABLESENUM.ID_MESSAGE));
    }
    
    
    public List<Object> getValues() {
        ArrayList<Object> values = new ArrayList<>();
        for (TABLESENUM columnEnum : TABLESENUM.values()) {
            if (columnEnum != TABLESENUM.ID_MESSAGE) {
                Object value = super.get(getColumnByEnum(columnEnum));
                values.add(value);
            }
        }
        return values;
    }

    
    public void setId(int id) {
        super.set(getColumnByEnum(TABLESENUM.ID_MESSAGE), id);
    }
    
    public void set(TABLESENUM tablesenum, Object object) {
        super.set(getColumnByEnum(tablesenum), object);
    }

    // Add getter and setter methods for each field (optional)
    // Example getter
//    public int getIdUtilisateur() {
//        UtilisateurModel.columns
//    }
}
