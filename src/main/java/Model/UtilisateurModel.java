/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Column;
import utils.validityClass.Email;
import utils.validityClass.Nom;
import utils.validityClass.Prenom;

/**
 *
 * @author 08luc
 */
public class UtilisateurModel extends Model {

    private static final List<Column> columns;
    
    

    public UtilisateurModel() {
        super("UTILISATEUR", columns); // Set table name
    }

    public UtilisateurModel(Nom nom, Prenom prenom, Email email, Boolean isAdmin, String password) throws IllegalArgumentException {
        this();
        super.set(getColumnByEnum(TABLESENUM.NOM), nom);
        super.set(getColumnByEnum(TABLESENUM.PRENOM), prenom);
        super.set(getColumnByEnum(TABLESENUM.EMAIL), email);
        super.set(getColumnByEnum(TABLESENUM.ISADMIN), isAdmin);
        super.set(getColumnByEnum(TABLESENUM.MDP), password);
    }
    
    public UtilisateurModel(String nom, String prenom, String email, Boolean isAdmin, String password) throws IllegalArgumentException {
        this(new Nom(nom), new Prenom(prenom), new Email(email), isAdmin, password);
    }
    
    
    // Static \\
    
    public static enum TABLESENUM {
        ID,
        NOM,
        PRENOM,
        EMAIL,
        ISADMIN,
        MDP
    }

    public static String getColumnByEnum(TABLESENUM column) {
        return switch (column) {
            case ID ->
                "IDUTILISATEUR";
            case NOM ->
                "NOMUTILISATEUR";
            case PRENOM ->
                "PRENOMUTILISATEUR";
            case EMAIL ->
                "EMAILUTILISATEUR";
            case ISADMIN ->
                "ISADMIN";
            case MDP ->
                "MDPUTILISATEUR";
        };
    }

    static {
        List<Column> cols = new ArrayList<>();
        cols.add(Column.ofInt(getColumnByEnum(TABLESENUM.ID))); // Use Columns class for type safety
        cols.add(Column.of(getColumnByEnum(TABLESENUM.NOM), Nom.class)); // Specify char length for string columns
        cols.add(Column.of(getColumnByEnum(TABLESENUM.PRENOM), Prenom.class));
        cols.add(Column.of(getColumnByEnum(TABLESENUM.EMAIL), Email.class));
        cols.add(Column.ofBool(getColumnByEnum(TABLESENUM.ISADMIN)));
        cols.add(Column.ofString(getColumnByEnum(TABLESENUM.MDP)));
        columns = Collections.unmodifiableList(cols);
    }
    
    // Public \\

    @Override
    public int getId() {
        return (int) super.get(getColumnByEnum(TABLESENUM.ID));
    }
    
    
    public List<Object> getValues() {
        ArrayList<Object> values = new ArrayList<>();
        for (TABLESENUM columnEnum : TABLESENUM.values()) {
            if (columnEnum != TABLESENUM.ID) {
                Object value = super.get(getColumnByEnum(columnEnum));
                values.add(value);
            }
        }
        return values;
    }

    
    public void setId(int id) {
        super.set(getColumnByEnum(TABLESENUM.ID), id);
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
