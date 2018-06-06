/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

import Properties.Props;
import Properties.XMLSerial;
import le.gourmet.audacieux.Salle.CategoriePlat;
import le.gourmet.audacieux.Salle.PlatPrincipal;
import le.gourmet.audacieux.Salle.Serveur;
import le.gourmet.audacieux.Salle.Dessert;
import le.gourmet.audacieux.Salle.Boisson;
import le.gourmet.audacieux.Salle.TooManyCoversException;
import le.gourmet.audacieux.Salle.Table;
import le.gourmet.audacieux.Salle.CommandePlat;
import le.gourmet.audacieux.Salle.Plat;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import network.*;


/**
 *
 * @author Alessandro Aloisio
 */
public class ApplicationSalle extends javax.swing.JFrame {

    private Serveur serveur = null;
    public Vector<Table> table = new Vector<>();
    private Plat plat = null;
    
    private Table tempTable = null;
    
    private int tableCourant = -1;
    private String tableCourantCode = null;
    private int nbTableOccupe = 0;

    private NetworkBasicServer nbs, nbsPlatsPrets;
    private NetworkBasicClient nbc;
    
    private boolean impression = false;
    
    Properties prop;
    
    /**
     * Creates new form ApplicationSalle
     */
    public ApplicationSalle() {
        
        // INIT
        prop = Props.getProperties();
        
        setTitle(prop.getProperty("nomResto"));
      
        initComponents();
        
        clock();
        
//        Props.setProperties("portServ1", "55555");
//        Props.setProperties("portServ2", "55554");
//        Props.setProperties("portServ3", "55553");
//        Props.setProperties("nomResto", "Restaurant \"Le Gourmet Audacieux\"");
//        XMLSerial.addServeur("garcia", "gardan");
        nbs = new NetworkBasicServer(Integer.parseInt(prop.getProperty("portServ2")), this.CommandeEnvoyeeCheckBox);
        nbsPlatsPrets = new NetworkBasicServer(Integer.parseInt(prop.getProperty("portServ3")), this.PlatsPretCheckBox);
        

//        try (
//                FileInputStream fis = new FileInputStream("tables.data");
//                ObjectInputStream ois = new ObjectInputStream(fis);) {
//                Vector<Table> tableTmp = (Vector<Table>) ois.readObject();
//                System.out.println(table);
//        } catch (Exception e) {
//            //e.printStackTrace();
//}
    }

    private void LoggedFrame() {
        
        loggedFrame.setTitle(prop.getProperty("nomResto") +" : "+ serveur.getLogin());
        
        if(!loggedFrame.isVisible()) {
            
            loggedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loggedFrame.setSize(950, 746);
            loggedFrame.setVisible(true);
            

            // Ajouter les tables dans tableBox
            Set<String> keys = Table.tables.keySet();
            keys.forEach((key) -> {
                tableBox.addItem(key);
            });

            // Ajouter les plats dans platsBox
            keys = PlatPrincipal.plats.keySet();
            keys.forEach((key) -> {
                platsBox.addItem(key + ": " + PlatPrincipal.plats.get(key));
            });

            // Ajouter les desserts dans dessertsBox
            keys = Dessert.plats.keySet();
            keys.forEach((key) -> {
                dessertsBox.addItem(key + ": " + Dessert.plats.get(key));
            });  
            
        }else if(tempTable != null) {
            
            boolean continuer = true;
            
            placesLabel.setText(Table.tables.get(tempTable.numTable).toString());
            
            // si table occupée
            int i = 0;
            for(Table item : table){
                // Si la table est occupée et que le serveur est le même qui a pris la commande
                if(item.numTable.equals(tempTable.numTable.toString()) && serveur.getLogin().equals(table.elementAt(i).idServeur)){
                    System.out.println("Table occupee : " + item + ", index = "+i);
                   
                    tableCourant = i;
                    tableCourantCode = item.numTable;
                      
                    continuer = false;
                    break;
                }
                i++;
            }
            
            if(continuer) {
                table.add(nbTableOccupe, new Table(tempTable.numTable.toString(), Integer.parseInt(placesLabel.getText())));
                tableCourant = nbTableOccupe;
                tableCourantCode = tempTable.numTable.toString();
                nbTableOccupe++;
                table.get(tableCourant).idServeur = serveur.getLogin();
            }

            couvertsLabel.setText(Integer.toString(table.get(tableCourant).nbCouverts));
            platsAttente.setModel(createListData(false));
            listPlats.setModel(createListData(true));
            
            tempTable = null;
             
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loggedFrame = new javax.swing.JFrame();
        jLabel4 = new javax.swing.JLabel();
        tableBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPlats = new javax.swing.JList<>();
        platsBox = new javax.swing.JComboBox<>();
        btnPlats = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        dessertsBox = new javax.swing.JComboBox<>();
        btnDesserts = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        qttPlat = new javax.swing.JTextField();
        qttDessert = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        boissonAjout = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        boissonAdd = new javax.swing.JButton();
        encaisserBtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        platsAttente = new javax.swing.JList<>();
        envoyerBtn = new javax.swing.JButton();
        CommandeEnvoyeeCheckBox = new javax.swing.JCheckBox();
        PlatsPretCheckBox = new javax.swing.JCheckBox();
        lirePlatsDispoButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        placesLabel = new javax.swing.JLabel();
        couvertsLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        serveursMenu = new javax.swing.JMenu();
        mdpMenuItem = new javax.swing.JMenuItem();
        newServMenuItem = new javax.swing.JMenuItem();
        tablesMenu = new javax.swing.JMenu();
        listeTablesMenuItem = new javax.swing.JMenuItem();
        nbClientsMenuItem = new javax.swing.JMenuItem();
        sommeAddtionsMenuItem = new javax.swing.JMenuItem();
        platsMenu = new javax.swing.JMenu();
        listePlatsMenuItem = new javax.swing.JMenuItem();
        listeDessertsMenuItem = new javax.swing.JMenuItem();
        jSeparator = new javax.swing.JPopupMenu.Separator();
        creerPlatMenuItem = new javax.swing.JMenuItem();
        supprPlatMenuItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        encaisserFrame = new javax.swing.JFrame();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        encTable = new javax.swing.JTextField();
        encCouverts = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        encAddition = new javax.swing.JTextField();
        encRadioConsulter = new javax.swing.JRadioButton();
        encRadioImprimer = new javax.swing.JRadioButton();
        encRadioEncaisser = new javax.swing.JRadioButton();
        encOkBtn = new javax.swing.JButton();
        encAnnulerBtn = new javax.swing.JButton();
        encRadioGroup = new javax.swing.ButtonGroup();
        imprimFrame = new javax.swing.JFrame();
        jLabel23 = new javax.swing.JLabel();
        loginField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jLabel4.setText("Table :");

        tableBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "?" }));
        tableBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tableBoxItemStateChanged(evt);
            }
        });

        jLabel5.setText("Nombre maximum de couverts :");

        jLabel6.setText("Nombre de couverts :");

        jLabel7.setText("Plats servis :");

        jLabel8.setText("Encodage des commandes :");

        jLabel9.setText("Plats :");

        listPlats.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "RIEN", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listPlats);

        platsBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                platsBoxActionPerformed(evt);
            }
        });

        btnPlats.setText("Commander plats");
        btnPlats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlatsMouseClicked(evt);
            }
        });

        jLabel10.setText("Desserts :");

        btnDesserts.setText("Commander desserts");
        btnDesserts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDessertsMouseClicked(evt);
            }
        });

        jLabel11.setText("Quantité :");

        jLabel12.setText("Quantité :");

        qttPlat.setText("?");

        qttDessert.setText("?");

        jLabel13.setText("Remarques :");

        jTextField5.setText("??");

        jLabel14.setText("Boissons (bar) :");

        jLabel15.setText("EUR");

        boissonAjout.setText("?");

        jLabel16.setText("Addition :");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel17.setText("NON PAYEE");
        jLabel17.setFocusable(false);

        boissonAdd.setText("Ajouter");
        boissonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boissonAddMouseClicked(evt);
            }
        });

        encaisserBtn.setText("Encaisser");
        encaisserBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encaisserBtnMouseClicked(evt);
            }
        });

        jLabel18.setText("Comandes à envoyer :");

        platsAttente.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "RIEN", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(platsAttente);

        envoyerBtn.setText("Envoyer");
        envoyerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                envoyerBtnMouseClicked(evt);
            }
        });

        CommandeEnvoyeeCheckBox.setEnabled(false);
        CommandeEnvoyeeCheckBox.setLabel("Commande envoyée");
        CommandeEnvoyeeCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CommandeEnvoyeeCheckBoxItemStateChanged(evt);
            }
        });

        PlatsPretCheckBox.setLabel("Plats prêts");

        lirePlatsDispoButton.setLabel("Lire plats disponibles");
        lirePlatsDispoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lirePlatsDispoButtonMouseClicked(evt);
            }
        });

        placesLabel.setText("?");

        couvertsLabel.setText("??");

        serveursMenu.setText("Serveurs");

        mdpMenuItem.setText("Modifier mot de passe");
        mdpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mdpMenuItemActionPerformed(evt);
            }
        });
        serveursMenu.add(mdpMenuItem);

        newServMenuItem.setText("Ajouter un nouveau serveur");
        newServMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newServMenuItemActionPerformed(evt);
            }
        });
        serveursMenu.add(newServMenuItem);

        jMenuBar1.add(serveursMenu);

        tablesMenu.setText("Tables");

        listeTablesMenuItem.setText("Liste des tables");
        listeTablesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeTablesMenuItemActionPerformed(evt);
            }
        });
        tablesMenu.add(listeTablesMenuItem);

        nbClientsMenuItem.setText("Nombre total clients");
        nbClientsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nbClientsMenuItemActionPerformed(evt);
            }
        });
        tablesMenu.add(nbClientsMenuItem);

        sommeAddtionsMenuItem.setText("Somme des additions");
        sommeAddtionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sommeAddtionsMenuItemActionPerformed(evt);
            }
        });
        tablesMenu.add(sommeAddtionsMenuItem);

        jMenuBar1.add(tablesMenu);

        platsMenu.setText("Plats");

        listePlatsMenuItem.setText("Liste plats");
        listePlatsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listePlatsMenuItemActionPerformed(evt);
            }
        });
        platsMenu.add(listePlatsMenuItem);

        listeDessertsMenuItem.setText("Liste desserts");
        listeDessertsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeDessertsMenuItemActionPerformed(evt);
            }
        });
        platsMenu.add(listeDessertsMenuItem);
        platsMenu.add(jSeparator);

        creerPlatMenuItem.setText("Créer un plat");
        creerPlatMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creerPlatMenuItemActionPerformed(evt);
            }
        });
        platsMenu.add(creerPlatMenuItem);

        supprPlatMenuItem.setText("Supprimer un plat");
        supprPlatMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprPlatMenuItemActionPerformed(evt);
            }
        });
        platsMenu.add(supprPlatMenuItem);

        jMenuBar1.add(platsMenu);

        jMenu4.setText("Paramètres");

        jMenuItem10.setText("Infos système");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Paramètres de date-heure");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Aide");

        jMenuItem12.setText("Pour débuter");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem12);
        jMenu5.add(jSeparator3);

        jMenuItem13.setText("A propos de ...");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem13);

        jMenuBar1.add(jMenu5);

        loggedFrame.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout loggedFrameLayout = new javax.swing.GroupLayout(loggedFrame.getContentPane());
        loggedFrame.getContentPane().setLayout(loggedFrameLayout);
        loggedFrameLayout.setHorizontalGroup(
            loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(loggedFrameLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(tableBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jLabel7))
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dessertsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(platsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(couvertsLabel)
                            .addComponent(placesLabel)))
                    .addComponent(jLabel8)))
            .addGroup(loggedFrameLayout.createSequentialGroup()
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel18)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loggedFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(envoyerBtn)
                        .addGap(55, 55, 55)))
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19))
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(CommandeEnvoyeeCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(PlatsPretCheckBox)
                        .addGap(47, 47, 47)
                        .addComponent(lirePlatsDispoButton))))
            .addGroup(loggedFrameLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(btnPlats)
                        .addGap(214, 214, 214)
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(loggedFrameLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5))
                            .addGroup(loggedFrameLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel16)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loggedFrameLayout.createSequentialGroup()
                                            .addGap(20, 20, 20)
                                            .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(encaisserBtn, javax.swing.GroupLayout.Alignment.TRAILING))))
                                    .addGroup(loggedFrameLayout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(loggedFrameLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(boissonAdd))
                                            .addGroup(loggedFrameLayout.createSequentialGroup()
                                                .addComponent(boissonAjout, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel15)))))
                                .addGap(40, 40, 40))
                            .addGroup(loggedFrameLayout.createSequentialGroup()
                                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(loggedFrameLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(qttPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(loggedFrameLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(qttDessert, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(btnDesserts)))
        );
        loggedFrameLayout.setVerticalGroup(
            loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loggedFrameLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(boissonAjout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boissonAdd)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17))
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tableBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(51, 51, 51)
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(placesLabel))
                        .addGap(30, 30, 30)
                        .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(couvertsLabel)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(encaisserBtn))
                .addGap(20, 20, 20)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(platsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(qttPlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPlats)
                    .addComponent(jLabel13)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(dessertsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(qttDessert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnDesserts)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(loggedFrameLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(70, 70, 70)
                        .addComponent(envoyerBtn))
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(loggedFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CommandeEnvoyeeCheckBox)
                    .addComponent(PlatsPretCheckBox)
                    .addComponent(lirePlatsDispoButton))
                .addContainerGap())
        );

        jLabel20.setText("Table :");

        jLabel21.setText("Nombre de couverts :");

        encTable.setEnabled(false);

        encCouverts.setEnabled(false);

        jLabel22.setText("A PAYER :");

        encAddition.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        encAddition.setEnabled(false);

        encRadioGroup.add(encRadioConsulter);
        encRadioConsulter.setSelected(true);
        encRadioConsulter.setText("Consulter");
        encRadioConsulter.setToolTipText("Juste pour voir");

        encRadioGroup.add(encRadioImprimer);
        encRadioImprimer.setText("Imprimer");
        encRadioImprimer.setToolTipText("Fabriquer souche");

        encRadioGroup.add(encRadioEncaisser);
        encRadioEncaisser.setText("Encaisser");
        encRadioEncaisser.setToolTipText("Enregistrer le paiement");

        encOkBtn.setText("Ok");
        encOkBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encOkBtnMouseClicked(evt);
            }
        });

        encAnnulerBtn.setText("Annuler");
        encAnnulerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encAnnulerBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout encaisserFrameLayout = new javax.swing.GroupLayout(encaisserFrame.getContentPane());
        encaisserFrame.getContentPane().setLayout(encaisserFrameLayout);
        encaisserFrameLayout.setHorizontalGroup(
            encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(encaisserFrameLayout.createSequentialGroup()
                .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(encaisserFrameLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(55, 55, 55)
                        .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(encTable, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(encCouverts)))
                    .addGroup(encaisserFrameLayout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(encRadioConsulter))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(encRadioImprimer)
                            .addComponent(encAddition, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(encRadioEncaisser)
                .addGap(136, 136, 136))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, encaisserFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(encOkBtn)
                .addGap(92, 92, 92)
                .addComponent(encAnnulerBtn)
                .addGap(236, 236, 236))
        );
        encaisserFrameLayout.setVerticalGroup(
            encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(encaisserFrameLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(encTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(encCouverts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(encAddition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encRadioConsulter)
                    .addComponent(encRadioImprimer)
                    .addComponent(encRadioEncaisser))
                .addGap(41, 41, 41)
                .addGroup(encaisserFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encAnnulerBtn)
                    .addComponent(encOkBtn))
                .addGap(53, 53, 53))
        );

        jLabel23.setText("Impression en cours ....");

        javax.swing.GroupLayout imprimFrameLayout = new javax.swing.GroupLayout(imprimFrame.getContentPane());
        imprimFrame.getContentPane().setLayout(imprimFrameLayout);
        imprimFrameLayout.setHorizontalGroup(
            imprimFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imprimFrameLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel23)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        imprimFrameLayout.setVerticalGroup(
            imprimFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imprimFrameLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel23)
                .addContainerGap(175, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Serveur ?");

        jLabel2.setText("Mot de passe ?");

        loginBtn.setText("OK");
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginBtnMouseClicked(evt);
            }
        });

        cancelBtn.setText("Annuler");
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(loginBtn)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(passwordField)
                                .addComponent(loginField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cancelBtn)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel3)))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel3)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void clock(){
        
        Thread clock = new Thread()
        {
            public void run()
            {
                try {
                    for(;;){
                        // GET DATE
                        Date date = Calendar.getInstance().getTime();  
                        DateFormat dateFormat = new SimpleDateFormat("EEEE yyyy/mm/dd hh:mm:ss");  
                        String strDate = dateFormat.format(date);   

                        jLabel3.setText(strDate);

                        sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        clock.start();
    }
    
    public void imprimer(){
        
        Thread clock = new Thread()
        {
            public void run()
            {
                try {
                    
                    imprimFrame.setSize(950, 746);
                    imprimFrame.setVisible(true);     
                    impression = true;
                    sleep(1000*10);
                    impression = false;
                    imprimFrame.setVisible(false); 

                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        clock.start();
    }
    

    private void loginBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnMouseClicked
            
        serveur = Serveur.authenticate(loginField.getText(), passwordField.getText());
         
        if(serveur != null)
        {   
            System.out.println("Afficher une nouvelle fenêtre pour "+ serveur.getLogin());
            
            this.setVisible(false);
            
            LoggedFrame();
        }
        
    }//GEN-LAST:event_loginBtnMouseClicked

    // On efface les entrées des JTEXTFIELD si on annule
    private void cancelBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelBtnMouseClicked
        Component[] child = getContentPane().getComponents();
        
        for(int i = 0; i < child.length;i++){
            if(child[i] instanceof JTextField){
                ((JTextField)child[i]).setText("");
            }
        }
    }//GEN-LAST:event_cancelBtnMouseClicked

    private void tableBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tableBoxItemStateChanged
        
        boolean continuer = true;

        // afficher le nombre de places dispo de la table
        if(evt.getStateChange() == 1){
            
            // Demander si on change de serveur
            if(tableCourant >= 0) {
            
                final JTextField userNameField = new JTextField(10);
                
                userNameField.setText(serveur.getLogin());
                userNameField.enable(false);
                        
                JPanel pane = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
                        new Insets(2, 2, 2, 2), 0, 0);
                pane.add(new JLabel("Serveur en cours :"), gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.EAST;
                pane.add(userNameField, gbc);
                
                int reply = JOptionPane.showConfirmDialog(null, pane, "Restaurant \"Le gourmet audacieux\"", 
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (reply == JOptionPane.OK_OPTION) {
                    // On continue avec 
                    String userName = userNameField.getText();
                }else{
                    continuer = false;
                    loginField.setText("");
                    passwordField.setText("");
                     
                    this.setVisible(true);
                    
                    tempTable = new Table(evt.getItem().toString(), Integer.parseInt(placesLabel.getText()));
                }

            }
            
            // si table occupée
            int i = 0;
            for(Table item : table){
                // Si la table est occupée et que le serveur est le même qui a pris la commande
                if(item.numTable.equals(evt.getItem()) && serveur.getLogin().equals(table.elementAt(i).idServeur)){
                    System.out.println("Table occupee : " + item + ", index = "+i);
                    
                    continuer = false;

                    tableCourant = i;
                    tableCourantCode = item.numTable;
                    
                    couvertsLabel.setText(Integer.toString(table.get(tableCourant).nbCouverts));
                    platsAttente.setModel(createListData(false));
                    listPlats.setModel(createListData(true));
                    
                    break;
                }
                i++;
            }
            
            // si table disponible
            if(Table.tables.containsKey(evt.getItem()) && continuer) {
                
                placesLabel.setText(Table.tables.get((String)evt.getItem()).toString());
                
                table.add(nbTableOccupe, new Table(evt.getItem().toString(), Integer.parseInt(placesLabel.getText())));
                tableCourant = nbTableOccupe;
                tableCourantCode = (String)evt.getItem().toString();
                nbTableOccupe++;
                
                table.get(tableCourant).idServeur = serveur.getLogin();
                
                couvertsLabel.setText(Integer.toString(table.get(tableCourant).nbCouverts));
                platsAttente.setModel(createListData(false));
                listPlats.setModel(createListData(true));
                
            }
        }
    }//GEN-LAST:event_tableBoxItemStateChanged

    private DefaultListModel createListData(boolean servis){
        
        DefaultListModel model = new DefaultListModel();
        
        // !! récupérer seulement les siens
           
        if(table.get(tableCourant).idServeur.equals(serveur.getLogin())) {
            
            for(CommandePlat item : table.get(tableCourant).plats){

                String text = "";

                if(item.plat.servis == servis){

                    if(item.plat.getCategorie().equals(CategoriePlat.BOISSON)) {
                        text += item.plat.toString();
                    }else{
                        text += item.quantite+" "+item.plat.code+": "+item.plat.toString();
                        // Servis == true
                        if(servis)
                            text += " (c)";
                    }
 
                }

                model.addElement(text);
            }
        }else{
            System.out.println("Vous devez choisir une table");
        }
        
        return model;
    }
    
    private void btnPlatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlatsMouseClicked
        
        int nbCouverts = 0;
        int continuer = 0;
        
        if(table.get(tableCourant) != null){
            // quantite
            if(qttPlat.getText().matches("^[0-9]+$")) {
                
                nbCouverts = Integer.parseInt(qttPlat.getText());
                
                // dialogue si plus grand que nb places
                try {
                    if(table.get(tableCourant).nbCouverts+nbCouverts > table.get(tableCourant).nbPlaces)
                        throw new TooManyCoversException();
                } catch (TooManyCoversException ex) {
                    continuer = ex.n;
                }
                
                if(nbCouverts > 0 && continuer == 0) { 

                    try {
                        FileOutputStream fos = new FileOutputStream("tables.data");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);

                        oos.writeObject(table);
                        oos.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //get code
                    plat = new PlatPrincipal(platsBox.getSelectedItem().toString().split(":")[0]);
                    
                    table.get(tableCourant).plats.add(new CommandePlat(plat, nbCouverts));
                    
                    // Inc nb couverts label
                    table.get(tableCourant).nbCouverts += nbCouverts;
                    couvertsLabel.setText(Integer.toString(table.get(tableCourant).nbCouverts));
                    
                    // Ajout dans la liste en attente
                    platsAttente.setModel(createListData(false));
                    
                    table.get(tableCourant).addition+=(plat.getPrix()*nbCouverts);
                    
                    
                }else{
                    System.out.println("Commande annuler");
                }
            }
            else{
                System.out.println("Vous devez introduire une quantité");
            }
            
        }else{
            System.out.println("Vous devez choisir une table");
        }
        
    }//GEN-LAST:event_btnPlatsMouseClicked

    private void btnDessertsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDessertsMouseClicked
        int nbCouverts = 0;
        
        if(table.get(tableCourant) != null){
            // quantite
            if(qttDessert.getText().matches("^[0-9]+$")) {
                
                nbCouverts = Integer.parseInt(qttDessert.getText());
                
                if(nbCouverts > 0) { 
                    //get code
                    plat = new Dessert(dessertsBox.getSelectedItem().toString().split(":")[0]);
                    
                    table.get(tableCourant).plats.add(new CommandePlat(plat, nbCouverts));
                     
                    // Ajout dans la liste en attente
                    platsAttente.setModel(createListData(false));
                    
                    table.get(tableCourant).addition+=(plat.getPrix()*nbCouverts);
                }else{
                    System.out.println("Quantité inférieur à min 1");
                }
            }
            else{
                System.out.println("Vous devez introduire une quantité");
            }
            
        }else{
            System.out.println("Vous devez choisir une table");
        }
    }//GEN-LAST:event_btnDessertsMouseClicked

    private void envoyerBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_envoyerBtnMouseClicked
        String platsEnvoi = new String();
        //Gérer le cuisine/salle
        // pour le moment mettre tous les plats en mode servis
        for(CommandePlat item : table.get(tableCourant).plats){
 
            if(item.plat.servis == false){
                
                platsEnvoi += tableCourantCode+'&'+item.toString()+';';
                
                item.plat.servis = true;
            }
        }
        
        platsAttente.setModel(createListData(false));
        listPlats.setModel(createListData(true));
        
        System.out.println(table.get(tableCourant).addition);
        
        if(nbc == null)
        {
            nbc = new NetworkBasicClient("localhost", 55555);
            // Attente le temps du lancement du client
            try        
            {
                Thread.sleep(100);
            } 
            catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
        }
        try
        {
            nbc.sendStringWithoutWaiting(platsEnvoi);
        }
        catch(Exception e)
        {
            System.out.println("Erreur de l'envoi vers la cuisine, verifiez l'état du serveur de la cuisine!");
        }
            
    }//GEN-LAST:event_envoyerBtnMouseClicked

    private void boissonAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boissonAddMouseClicked
        if(tableCourant >= 0 && table.get(tableCourant) != null){
            double prix = Double.parseDouble(boissonAjout.getText());
            
            plat = new Boisson(prix, true);
            table.get(tableCourant).plats.add(new CommandePlat(plat, 1));
            
            listPlats.setModel(createListData(true));
            
            table.get(tableCourant).addition+=(plat.getPrix());
        }else{
            System.out.println("Vous devez choisir une table");
        }
    }//GEN-LAST:event_boissonAddMouseClicked

    private void encaisserBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encaisserBtnMouseClicked
        
        encaisserFrame.setTitle("Restaurant \"Le gourmet audacieux\"");
        encaisserFrame.setSize(715, 443);
        encaisserFrame.setVisible(true);
        
        encTable.setText(table.get(tableCourant).numTable);
        encCouverts.setText(Integer.toString(table.get(tableCourant).nbCouverts));
        encAddition.setText(Double.toString(table.get(tableCourant).addition));
        
    }//GEN-LAST:event_encaisserBtnMouseClicked

    private void encAnnulerBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encAnnulerBtnMouseClicked
        encaisserFrame.setVisible(false);
    }//GEN-LAST:event_encAnnulerBtnMouseClicked

    private void encOkBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encOkBtnMouseClicked
        
        if(encRadioConsulter.isSelected()){
            System.out.println("Consulter");
        }else if(encRadioImprimer.isSelected()){
            imprimer();
  
            System.out.println("Imprimer");
        }else if(encRadioEncaisser.isSelected()){
            System.out.println("Encaisser");
        }
        
    }//GEN-LAST:event_encOkBtnMouseClicked

    private void CommandeEnvoyeeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CommandeEnvoyeeCheckBoxItemStateChanged
        // quand la commande est recu par le cuisto
        if(this.CommandeEnvoyeeCheckBox.isSelected())
        {
            JOptionPane.showMessageDialog(this,
                "Commande reçue !", "Réponse de la cuisine", JOptionPane.INFORMATION_MESSAGE);
            this.CommandeEnvoyeeCheckBox.setSelected(false);
        }
    }//GEN-LAST:event_CommandeEnvoyeeCheckBoxItemStateChanged

    private void lirePlatsDispoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lirePlatsDispoButtonMouseClicked
        // TODO add your handling code here:
        if(this.PlatsPretCheckBox.isSelected())
        {
            String[] str = nbsPlatsPrets.getMessage().split(";");

            String msg = "";
            
            for(int i = 0; i < str.length; i++)
            {
                String[] commande = str[i].split(" ");
                    
                msg += commande[0] + " ";
                
                if(commande[1].contains("D_"))
                    msg += ((Dessert)Dessert.plats.get(commande[1])).getLibelle();  
                else
                    msg += ((PlatPrincipal)PlatPrincipal.plats.get(commande[1])).getLibelle();
                
                msg += " !\n";
            }
            
            JOptionPane.showMessageDialog(this, msg, "Message de la cuisine", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_lirePlatsDispoButtonMouseClicked

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "OS nom : "+System.getProperty("os.name")+ System.lineSeparator()+ "Current dir : "+System.getProperty("user.dir")+ System.lineSeparator(), "A propos de cette application", JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void mdpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mdpMenuItemActionPerformed
        // TODO add your handling code here:

        final JTextField userNameField = new JTextField(10);
           
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
                new Insets(2, 2, 2, 2), 0, 0);
        pane.add(new JLabel("Modification du mdp : "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(userNameField, gbc);

        int reply = JOptionPane.showConfirmDialog(this, pane, "Restaurant \"Le gourmet audacieux\"", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (reply == JOptionPane.OK_OPTION) {
            // On continue avec 
            String userName = userNameField.getText();
            Props.setProperties(serveur.getLogin(), userName);
        }
    }//GEN-LAST:event_mdpMenuItemActionPerformed

    private void newServMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newServMenuItemActionPerformed
        // TODO add your handling code here:
        final JTextField userNameField = new JTextField(10);
        final JTextField pwdField = new JTextField(10);
        
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
                        new Insets(2, 2, 2, 2), 0, 0);
        
        
        pane.add(new JLabel("Nom du serveur : "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(userNameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        pane.add(new JLabel("Mot de passe : "), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(pwdField, gbc);
        
        int reply = JOptionPane.showConfirmDialog(this, pane, "Restaurant \"Le gourmet audacieux\"", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (reply == JOptionPane.OK_OPTION) {
            // On continue avec 
            String userName = userNameField.getText();
            String password = pwdField.getText();
            XMLSerial.addServeur(userName, password);
        }
    }//GEN-LAST:event_newServMenuItemActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Cette application a été développée dans une ambiance joyeuse et studieuse. Le résultat devrait vous séduire..."
                + "Daniel Garcia Lecloux et Alessandro Aloisio", "A propos de cette application", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "", "Pour débuter", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void listePlatsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listePlatsMenuItemActionPerformed
        // TODO add your handling code here:
        String str = new String();

        PlatPrincipal[] listePlats = PlatPrincipal.plats.values().toArray(new PlatPrincipal[PlatPrincipal.plats.size()]);
        for(int i=0; i<listePlats.length; i++)
        {

            str += listePlats[i].getLibelle() + " " + listePlats[i].prix + System.lineSeparator();
            
        }
        JOptionPane.showMessageDialog(this, str, "Liste des plats", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_listePlatsMenuItemActionPerformed

    private void listeDessertsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listeDessertsMenuItemActionPerformed
        // TODO add your handling code here:
        String str = new String();

        Dessert[] listePlats = Dessert.plats.values().toArray(new Dessert[Dessert.plats.size()]);
        for(int i=0; i<listePlats.length; i++)
        {

            str += listePlats[i].getLibelle() + " " + listePlats[i].prix + System.lineSeparator();
            
        }
        JOptionPane.showMessageDialog(this, str, "Liste des plats", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_listeDessertsMenuItemActionPerformed

    private void creerPlatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creerPlatMenuItemActionPerformed
        FileOutputStream fos = null;
        
        final JTextField codePlat = new JTextField(10);
        final JTextField libellePlat = new JTextField(20);
        final JTextField prixPlat = new JTextField(20);
        
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
                        new Insets(2, 2, 2, 2), 0, 0);
        
        
        pane.add(new JLabel("Code : "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(codePlat, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        pane.add(new JLabel("Libelle : "), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(libellePlat, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        
        pane.add(new JLabel("Prix : "), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(prixPlat, gbc);
        
        int reply = JOptionPane.showConfirmDialog(this, pane, "Restaurant \"Le gourmet audacieux\"", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (reply == JOptionPane.OK_OPTION) {  
            try {
                // TODO add your handling code here:
                String strAjout = new String();
                strAjout += codePlat.getText() + " & " + libellePlat.getText() + " & " + prixPlat.getText() + System.lineSeparator();
                fos = new FileOutputStream("plats.txt", true);
                fos.write(strAjout.getBytes());
                PlatPrincipal.plats.put(codePlat.getText(), new PlatPrincipal(libellePlat.getText(), Double.parseDouble(prixPlat.getText())));

                Set<String> keys = PlatPrincipal.plats.keySet();
                platsBox.removeAllItems();
                keys.forEach((key) -> {
                    platsBox.addItem(key + ": " + PlatPrincipal.plats.get(key));
                });
                // 
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_creerPlatMenuItemActionPerformed

    private void listeTablesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listeTablesMenuItemActionPerformed
        // TODO add your handling code here:
        String str = new String();

        for(int i=0; i<table.size();i++)
        {
            str += "Num table : " + table.elementAt(i).numTable + ", Serveur : " + table.elementAt(i).idServeur +System.lineSeparator();
        }
        
        JOptionPane.showMessageDialog(this, str, "Liste des tables", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_listeTablesMenuItemActionPerformed

    private void nbClientsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nbClientsMenuItemActionPerformed
        
        String str = new String();
        int nbClients = 0;
        
        for(int i=0; i<table.size();i++)
        {
            for(int j = 0;j<table.elementAt(i).plats.size();j++){
                nbClients += table.elementAt(i).plats.elementAt(j).quantite;
            }
        }
        
        str += "Il y a environ " + nbClients + " clients.";
        
        JOptionPane.showMessageDialog(this, str, "Nb clients", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_nbClientsMenuItemActionPerformed

    private void sommeAddtionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sommeAddtionsMenuItemActionPerformed
        // TODO add your handling code here:
        String str = new String();
        double additionTotal = 0;
        
        for(int i=0; i<table.size();i++)
        {
            additionTotal += table.elementAt(i).addition;
        }
        
        str += "L'addition total s'élève à "+additionTotal;
        
        JOptionPane.showMessageDialog(this, str, "Additions", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_sommeAddtionsMenuItemActionPerformed

    private void supprPlatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprPlatMenuItemActionPerformed
        
        
        final JTextField code = new JTextField(10);
           
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 
                new Insets(2, 2, 2, 2), 0, 0);
        pane.add(new JLabel("Code du plat : "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        pane.add(code, gbc);

        int reply = JOptionPane.showConfirmDialog(this, pane, "Suppression plat", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (reply == JOptionPane.OK_OPTION) {
            // On continue avec 
        
            FileWriter fileWritter = null;
            try {
                // TODO add your handling code here:

                String oldContent = "";
                BufferedReader reader = new BufferedReader(new FileReader("plats.txt"));
                String line = reader.readLine();
                while (line != null)
                {
                    System.out.println(code.getText());
                    if(!line.contains(code.getText()))
                    {
                        oldContent = oldContent + line + System.lineSeparator();
                        
                        
                    }
                    line = reader.readLine();
                }  
                System.out.println(oldContent);
                
                fileWritter = new FileWriter("plats.txt");
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(oldContent);
                reader.close();
                bufferWritter.close();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fileWritter.close();
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_supprPlatMenuItemActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void platsBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_platsBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_platsBoxActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplicationSalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationSalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationSalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationSalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApplicationSalle().setVisible(true);    
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CommandeEnvoyeeCheckBox;
    private javax.swing.JCheckBox PlatsPretCheckBox;
    private javax.swing.JButton boissonAdd;
    private javax.swing.JTextField boissonAjout;
    private javax.swing.JButton btnDesserts;
    private javax.swing.JButton btnPlats;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel couvertsLabel;
    private javax.swing.JMenuItem creerPlatMenuItem;
    private javax.swing.JComboBox<String> dessertsBox;
    private javax.swing.JTextField encAddition;
    private javax.swing.JButton encAnnulerBtn;
    private javax.swing.JTextField encCouverts;
    private javax.swing.JButton encOkBtn;
    private javax.swing.JRadioButton encRadioConsulter;
    private javax.swing.JRadioButton encRadioEncaisser;
    private javax.swing.ButtonGroup encRadioGroup;
    private javax.swing.JRadioButton encRadioImprimer;
    private javax.swing.JTextField encTable;
    private javax.swing.JButton encaisserBtn;
    private javax.swing.JFrame encaisserFrame;
    private javax.swing.JButton envoyerBtn;
    private javax.swing.JFrame imprimFrame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton lirePlatsDispoButton;
    private javax.swing.JList<String> listPlats;
    private javax.swing.JMenuItem listeDessertsMenuItem;
    private javax.swing.JMenuItem listePlatsMenuItem;
    private javax.swing.JMenuItem listeTablesMenuItem;
    private javax.swing.JFrame loggedFrame;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField loginField;
    private javax.swing.JMenuItem mdpMenuItem;
    private javax.swing.JMenuItem nbClientsMenuItem;
    private javax.swing.JMenuItem newServMenuItem;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel placesLabel;
    private javax.swing.JList<String> platsAttente;
    private javax.swing.JComboBox<String> platsBox;
    private javax.swing.JMenu platsMenu;
    private javax.swing.JTextField qttDessert;
    private javax.swing.JTextField qttPlat;
    private javax.swing.JMenu serveursMenu;
    private javax.swing.JMenuItem sommeAddtionsMenuItem;
    private javax.swing.JMenuItem supprPlatMenuItem;
    private javax.swing.JComboBox<String> tableBox;
    private javax.swing.JMenu tablesMenu;
    // End of variables declaration//GEN-END:variables

}
