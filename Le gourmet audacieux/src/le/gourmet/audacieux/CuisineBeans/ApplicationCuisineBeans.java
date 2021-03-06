package le.gourmet.audacieux.CuisineBeans;

import Properties.Props;
import StringSlicer.*;
import java.beans.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import le.gourmet.audacieux.CuisineBeans.DishReadyBean;
import le.gourmet.audacieux.CuisineBeans.GetRecipeBean;
import le.gourmet.audacieux.CuisineBeans.IngredientsEvent;
import le.gourmet.audacieux.CuisineBeans.PlatAPreparer;
import network.NetworkBasicClient;
import network.NetworkBasicServer;

/**
 *
 * @author Alessandro Aloisio
 */
public class ApplicationCuisineBeans extends javax.swing.JFrame implements TimeComputingBean {

    
    NetworkBasicServer nbs;
    NetworkBasicClient nbc, nbcPlatPret;
    
    DefaultTableModel modeljTablePrepa;
    DefaultTableModel modelCommande = new DefaultTableModel();
    Properties prop;
    
    TimeComputingBean tm;
    
    /**
     * Creates new form ApplicationCuisine
     */
    
    public ApplicationCuisineBeans() {
        initComponents();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(485, 450);
        
        prop = Props.getProperties();
        
        nbs = new NetworkBasicServer(Integer.parseInt(prop.getProperty("portServ1")), this.CmdRecueCheckBox);

        this.jTableCommande.setEnabled(false);
        this.modeljTablePrepa = new DefaultTableModel(){
            private static final long serialVersionUID = 1L;  
            @Override
            public Class<?> getColumnClass(int column) {
                    switch (column) {  
                        case 0:
                            return String.class;
                        case 1:
                            return String.class;
                        case 2:
                            return String.class;
                        case 3:
                            return String.class; 
                        case 4:
                            return Boolean.class;   
                        case 5:
                            return Boolean.class;
                        case 6:
                            return Boolean.class;
                        default:
                            return Boolean.class;
                    }  
            }
            public boolean isCellEditable(int row, int column)
            {
                if(column < 4)
                    return false;//This causes all cells to be not editable
                
                return true;
            }
            
        };
        
        modeljTablePrepa.addTableModelListener(new TableModelListener() 
        {
            public void tableChanged(TableModelEvent e) 
            {
                //Serialisation changement d'etat checkbox
                if(e.getColumn() >= 4)
                {
                    if(e.getColumn() == 4)
                    {
                        System.out.println(modeljTablePrepa.getValueAt(e.getLastRow(), 2));
                        System.out.println(modeljTablePrepa.getValueAt(e.getLastRow(), 4));
                        
                        if((Boolean) modeljTablePrepa.getValueAt(e.getLastRow(), 4) == true)
                        {
                            GetRecipeBean recipe = new GetRecipeBean(modeljTablePrepa.getValueAt(e.getLastRow(), 1).toString(), Integer.parseInt(modeljTablePrepa.getValueAt(e.getLastRow(), 0).toString()));
                            
                            recipe.addRecipeListener(ApplicationCuisineBeans.this);
                            recipe.init();
                            recipe.run();
                        }
   
                    }
                 
                    serializeModelObject();
                }

            }
        });
        modeljTablePrepa.addColumn("Quantité");
        modeljTablePrepa.addColumn("Plat");
        modeljTablePrepa.addColumn("Table");
        modeljTablePrepa.addColumn("Heure arrivée");
        modeljTablePrepa.addColumn("En préparation");
        modeljTablePrepa.addColumn("A enlever");
        modeljTablePrepa.addColumn("Enlevé");
        
        modelCommande.addColumn("Quantité");
        modelCommande.addColumn("Plat");
        modelCommande.addColumn("Table");
        modelCommande.addColumn("Heure");
        
        deserializeModelObject();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        showCommande = new javax.swing.JButton();
        commandeRecueButton = new javax.swing.JButton();
        CmdRecueCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabelCommande = new javax.swing.JLabel();
        platEnleverButton = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePrep = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCommande = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        showCommande.setText("Voir commande");
        showCommande.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCommandeMouseClicked(evt);
            }
        });

        commandeRecueButton.setText("Commande reçue !");
        commandeRecueButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commandeRecueButtonMouseClicked(evt);
            }
        });

        CmdRecueCheckBox.setText("Commande reçue");

        jLabel1.setText("Liste des plats de la commande :");

        jLabel2.setText("Plats en préparation :");

        jLabelCommande.setText(">>");

        platEnleverButton.setText("Prévenir plats à enlever");
        platEnleverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                platEnleverButtonMouseClicked(evt);
            }
        });

        jTablePrep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTablePrep);

        jTableCommande.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTableCommande);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(122, 122, 122)
                        .addComponent(commandeRecueButton))
                    .addComponent(jLabelCommande)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(platEnleverButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CmdRecueCheckBox)
                        .addGap(44, 44, 44)
                        .addComponent(showCommande))
                    .addComponent(jSeparator1)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showCommande)
                    .addComponent(CmdRecueCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelCommande)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(commandeRecueButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(platEnleverButton)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>                        

    private void serializeModelObject() {
        
        try {
            FileOutputStream fos = new FileOutputStream("cuisineCommande.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(modelCommande.getDataVector());
            oos.close();
            
            fos = new FileOutputStream("cuisinePrepa.data");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(modeljTablePrepa.getDataVector());
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deserializeModelObject() {
        try {
            /////////// Deserialisation des commandes
            FileInputStream fis = new FileInputStream("cuisineCommande.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            Vector columnIdentifiers = new Vector();
            for(int i=0; i<modelCommande.getColumnCount(); i++)
                columnIdentifiers.add(modelCommande.getColumnName(i));
            
            modelCommande.setDataVector((Vector)ois.readObject(), columnIdentifiers);

            jTableCommande.setModel(modelCommande);

            /////////// Deserialisation des preparations
            fis = new FileInputStream("cuisinePrepa.data");
            ois = new ObjectInputStream(fis);
            
            columnIdentifiers = new Vector();
            for(int i=0; i<modeljTablePrepa.getColumnCount(); i++)
                columnIdentifiers.add(modeljTablePrepa.getColumnName(i));
            
            modeljTablePrepa.setDataVector((Vector)ois.readObject(), columnIdentifiers);
            jTablePrep.setModel(modeljTablePrepa);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //catch (ClassNotFoundException ex) {
//            Logger.getLogger(ApplicationCuisine.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    private void showCommandeMouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
        String[] donneesCommande = new String[4];
        String msg = new String();
        msg = nbs.getMessage();
        
        this.jLabelCommande.setText(">> " + msg);
        
        StringSlicer ss = new StringSlicer(msg, ";");
        ss.getComponents(true);
        
        Vector<String> hashSet = ss.listComponents();

        for(String s: hashSet)
        {
            String[] tmp = s.split("&");
            
            donneesCommande[0] = tmp[0];
            
            tmp = tmp[1].split(":");
            
            donneesCommande[1] = tmp[0];
            donneesCommande[2] = tmp[1];
            donneesCommande[3] = tmp[2];
            
            SimpleDateFormat time_formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String current_time_str = time_formatter.format(System.currentTimeMillis());
            
            //System.out.println(donneesCommande[0] + donneesCommande[1] + donneesCommande[2] + donneesCommande[3]);
            modelCommande.addRow(new Object[] { donneesCommande[1] , donneesCommande[2] , donneesCommande[0], current_time_str });
            modeljTablePrepa.addRow(new Object[] { donneesCommande[1] , donneesCommande[2] , donneesCommande[0], current_time_str.split(" ")[1], false, false, false });
        }
        serializeModelObject();
        this.jTableCommande.setModel(modelCommande);
        this.jTablePrep.setModel(modeljTablePrepa);
        
    }                                         

    private void commandeRecueButtonMouseClicked(java.awt.event.MouseEvent evt) {                                                 
        // TODO add your handling code here:
        
        for(int i=0; i<jTablePrep.getRowCount(); i++)
        {
            if ((Boolean)jTablePrep.getValueAt(i, 4) == true)
            {
                if(nbc == null)
                {
                    nbc = new NetworkBasicClient("localhost", 55554);

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
                // Envoi etat commandeRecue vers la salle
                try
                {
                    nbc.sendStringWithoutWaiting("");
                }
                catch(Exception e)
                {
                    System.out.println("Erreur de l'envoi vers la salle, verifiez l'état du serveur de la salle!");
                }
                i = jTablePrep.getRowCount();
            }
        }
    }                                                

    private void platEnleverButtonMouseClicked(java.awt.event.MouseEvent evt) {                                               
        // TODO add your handling code here:
        String str = new String();
        for(int i=0; i<jTablePrep.getRowCount(); i++)
        {
            if ((Boolean)jTablePrep.getValueAt(i, 5) == true && (Boolean)jTablePrep.getValueAt(i, 6) == false)
            {
                str += (String)jTablePrep.getValueAt(i, 0) + " " + (String)jTablePrep.getValueAt(i, 1) + ";";
            }
        }
        if(nbcPlatPret == null)
        {
            nbcPlatPret = new NetworkBasicClient("localhost", 55553);

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
        // Envoi etat commandeRecue vers la salle
        try
        {
            nbcPlatPret.sendStringWithoutWaiting(str);
        }
        catch(Exception e)
        {
            System.out.println("Erreur de l'envoi vers la salle, verifiez l'état du serveur de la salle!");
        }
    }                                              

    public void ingredientsReceived(IngredientsEvent e)
    {
        System.out.println("+++++++++ !!!!! "+ e.toString() + "!!!!");
        String[] plat = e.getIngredients();
        String[] ingr = plat[1].split(",");
        
        PlatAPreparer infosPlat = new PlatAPreparer(plat[0], ingr.length / 3 * 10);
        
        DishReadyBean dishReady = new DishReadyBean(infosPlat);

    }
    
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
            java.util.logging.Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApplicationCuisineBeans().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox CmdRecueCheckBox;
    private javax.swing.JButton commandeRecueButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCommande;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableCommande;
    private javax.swing.JTable jTablePrep;
    private javax.swing.JToggleButton platEnleverButton;
    private javax.swing.JButton showCommande;
    // End of variables declaration                   
}

