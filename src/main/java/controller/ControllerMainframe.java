/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.UtilisateurDao;
import Model.UtilisateurModel;
import Vue.MainFrame;
import Vue.TableauMessages;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import utils.CheckedValue;
import utils.Crypt;
import utils.exception.EmailAlreadyExistException;
import utils.validityClass.Email;
import utils.validityClass.Nom;
import utils.validityClass.Prenom;
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
    
    public void addUser(JTextField tfPrenom, JTextField tfNom, JTextField tfMail, JTextField tfMotDePasse) {
        try {

            Prenom prenom = new Prenom(tfPrenom.getText());
            Nom nom = new Nom(tfNom.getText());
            Email mail = new Email(tfMail.getText());
            String motDePasse = tfMotDePasse.getText();
            Crypt chiffrement = new Crypt();

            var checkedPassword = chiffrement.checkPassword(motDePasse);
            if (checkedPassword.isValid()) {
                String password = chiffrement.hash(motDePasse);
                UtilisateurModel utilisateur = new UtilisateurModel(nom, prenom, mail, false, password);
                UtilisateurDao utilisateurDao;
                try {
                    utilisateurDao = new UtilisateurDao(utilisateur);
                    UtilisateurModel createdUtilisateur = utilisateurDao.insert(utilisateur);
                    this.tableModel.addUtilisateur(utilisateur);
                    
                    //vider les champs de texte
                    tfPrenom.setText("");
                    tfNom.setText("");
                    tfMail.setText("");
                    tfMotDePasse.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this.mainFrame, "Erreur Serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch(EmailAlreadyExistException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this.mainFrame, "Email existe deja", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.mainFrame, "Erreur Inconnu", "Erreur", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Mainframe.btnAddUserActionPerformed() : " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this.mainFrame, checkedPassword.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this.mainFrame, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this.mainFrame, "Erreur inconnu", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void delUser(JTable tableUser) {
        int[] selectedRows = tableUser.getSelectedRows();

        /*
         * Boucle inverse pour supprimer les lignes du tableau par le bas Sinon
         * des exceptions apparaissent. Pourquoi : tableau de taille 4. je
         * recupere la valeur 0 pour la supprimer. je la supprime (tableau
         * taille 3) je recupere la valeur 1 pour la supprimer. je la supprime
         * (tableau taille 2) je recupere la valeur 2 pour la supprimer. je la
         * supprime impossible puisque la derniere valeur est en position 1
         */
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            System.out.println("i : " + i);
            int selectedRow = selectedRows[i];
            UtilisateurModel selectedUser = this.tableModel.getRow(selectedRow);
            try {
                UtilisateurDao utilisateurDao = new UtilisateurDao(selectedUser);
                utilisateurDao.delete(selectedUser);
                this.tableModel.removeUtilisateur(selectedUser);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this.mainFrame, "Erreur interne lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void updateUser(JTable tableUser) {
        int[] selectedRows = tableUser.getSelectedRows();
        if (selectedRows.length == 1) {
            int selectedRow = selectedRows[0];
            UtilisateurModel utilisateur = this.tableModel.getRow(selectedRow);
            String newPass = JOptionPane.showInputDialog(this.mainFrame, "Nouveau Mot de passe", "Nouveau Mot de passe", JOptionPane.QUESTION_MESSAGE);
            Crypt chiffrement = new Crypt();
            CheckedValue checkedPassword = chiffrement.checkPassword(newPass);
            if (checkedPassword.isValid()) {
                String hashedPassword = chiffrement.hash(newPass);
                utilisateur.set(UtilisateurModel.TABLESENUM.MDP, hashedPassword);
                UtilisateurDao utilisateurDao;
                try {
                    utilisateurDao = new UtilisateurDao(utilisateur);
                    utilisateurDao.update(utilisateur);
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this.mainFrame, "Erreur Serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.mainFrame, "Erreur Inconnu", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this.mainFrame, checkedPassword.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, "une et une seule ligne doit etre selectionne", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showMessages(JTable tableUser) {
        int[] selectedRows = tableUser.getSelectedRows();
        if (selectedRows.length == 1) {
            int selectedRow = selectedRows[0];
            UtilisateurModel utilisateur = this.tableModel.getRow(selectedRow);
            TableauMessages tableauMessages = new TableauMessages(utilisateur);
            tableauMessages.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, "une et une seule ligne doit etre selectionne", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
