/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.UtilisateurDao;
import Model.UtilisateurModel;
import Vue.MainFrame;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 08luc
 */
public class ControllerMainframe {

    private MainFrame mainFrame;
    private TableModelUtilisateur tableModel;

    public ControllerMainframe(MainFrame mainFrame) {
        this.tableModel = new TableModelUtilisateur();
        this.mainFrame = mainFrame;
        this.tableModel = tableModel;
        this.setAllUtilisateur();

    }

    private void setAllUtilisateur() {
        UtilisateurModel utilisateurModel = new UtilisateurModel();
        try {
            tableModel.cleanUtilisateurs(); // vider le tabeleau
            UtilisateurDao utilisateurDao = new UtilisateurDao(utilisateurModel);
            List<UtilisateurModel> allUtilisateurs = utilisateurDao.getAll();
            for (UtilisateurModel utilisateur : allUtilisateurs) { // ajouter chaque utilisateur de bdd au tableau
                this.tableModel.addUtilisateur(utilisateur);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public TableModelUtilisateur getTableModelUtilisateur() {
        return this.tableModel;
    }
}
