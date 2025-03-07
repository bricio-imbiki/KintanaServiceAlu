/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Panel;

import Chart.BarreChart;
import Chart.LineChart;
import Components.RoundedPanel;
import ConnexionDB.DatabaseUtils;
import Font.GoogleMaterialDesignIcons;
import Font.IconFontSwing;
import Models.ModelCard;
import Models.ModelChart;
import Models.ModelClient;
import Models.ModelCommande;
import Models.ModelDevis;
import Models.ModelFacture;
import Views.Dashboard;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.EmptyBorder;
public class HomePanel extends RoundedPanel {


    private JPanel contentPanel, topPanel, headerPanel, mainPanel, CardPanel, mainContentPanel;
    private Card ClientsCard, DevisCard, CommandeCard, FactureCard;
private boolean isFirstOpen = true;
    private BarreChart chart;
    private CustomPanel FavoritePanel;
  
    private CustomPanel PanelChart;
    private CustomPanel LineChart;
    private LineChart line;

    public class CustomPanel extends JPanel {

        private final int cornerRadius;
        private final Color backgroundColor;

        public CustomPanel(int cornerRadius, Color backgroundColor) {
            this.cornerRadius = cornerRadius;
            this.backgroundColor = backgroundColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(backgroundColor);
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            g2d.dispose();
        }
    }
 public static final String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
   
    public HomePanel() {
        super(20);
        customInitComponents();
    
    }

    private void customInitComponents()  {


        // Créer le panneau principal qui contient tout
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(25,25,25));
        // Initialiser le topPanel et le headerPanel
        topPanel = new JPanel();
        ///headerPanel = new JPanel();
        //headerPanel.setOpaque(false);

       // Configuration du headerPanel (en-tête)
       //  headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
       // JLabel headerLabel = new JLabel("Tableau de bord");
       // headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
       // headerPanel.add(headerLabel);
        // Ajouter headerPanel au topPanel
        topPanel.setLayout(new BorderLayout());
       // topPanel.add(headerPanel, BorderLayout.NORTH);
         topPanel.setOpaque(false);
        // Initialiser le panneau pour contenir les cartes
        CardPanel = new JPanel();
        CardPanel.setOpaque(false);
        CardPanel.setLayout(new GridLayout(1, 5, 10, 0)); // 1 ligne avec 5 colonnes

        // Initialiser et configurer les cartes
        ClientsCard = new Card();
        DevisCard = new Card();
        CommandeCard = new Card();
        FactureCard = new Card();
        contentPanel = new JPanel();

        mainContentPanel = new JPanel();
        
        // Modification des couleurs ici
        LineChart = new CustomPanel(30, new Color(62, 66, 68)); // Rounded corners with light blue (bleu clair)
        PanelChart = new CustomPanel(30,new Color(62, 66, 68));
        //  //new Color(62, 66, 68));
        //RecentDevisPanel = new CustomPanel(30, new Color(51, 51, 51)); // Rounded corners with light blue
        // Configuration du mainContentPanel et contentPanel
        mainContentPanel.setLayout(new BorderLayout());
        contentPanel.setLayout(new GridLayout(1, 2, 20, 5));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 4, 5));
        mainContentPanel.setOpaque(false);
        contentPanel.setOpaque(false);
        // FavoritePanel.setLayout(new BorderLayout());
         PanelChart.setLayout(new BorderLayout());
          LineChart.setLayout(new BorderLayout()); 
        // Initialize the chart here
          chart = new BarreChart();
          line = new LineChart();
         //chart.setPreferredSize(new Dimension(2000, 500));

        // Ajout des panels de contenu au contentPanel
        contentPanel.add( LineChart);
        contentPanel.add( PanelChart);
        // Obtient la liste des devis, commandes, factures et clients depuis la base de données
       // Création de maps pour compter les devis, commandes, factures, et clients par mois
        Map<String, int[]> statsPerMonth = new HashMap<>();

        // Initialisation des données pour chaque mois avec des valeurs à 0
        for (String month : months) {
            statsPerMonth.put(month, new int[]{0, 0, 0, 0}); // Index 0 -> Devis, 1 -> Commandes, 2 -> Factures, 3 -> Clients
        }
 
        // Récupération des devis depuis la base de données
      
        List<ModelClient> clientList = DatabaseUtils.getAllClients();
        List<ModelDevis> devisList = DatabaseUtils.getAllDevis(); 
        List<ModelCommande> commandeList = DatabaseUtils.getAllCommande();
        List<ModelFacture> factureList = DatabaseUtils.getAllFacture();
       // Parcours des listes pour compter les entités par mois
       for (ModelClient client : clientList) {
            Date dateClient = client.getDateCreation();  // Suppose que ModelClient a une méthode getDateCreation()
            String monthName = getMonthNameFromDate(dateClient);
            int[] monthStats = statsPerMonth.get(monthName);
            monthStats[0]++;  // Incrémenter le nombre de clients
            statsPerMonth.put(monthName, monthStats);
        }
        for (ModelDevis devis : devisList) {
            Date dateDevis = devis.getDateDevis();  // Suppose que ModelDevis a une méthode getDateDevis()
            String monthName = getMonthNameFromDate(dateDevis);
            int[] monthStats = statsPerMonth.get(monthName);
            monthStats[1]++;  // Incrémenter le nombre de devis
            statsPerMonth.put(monthName, monthStats);
        }

        for (ModelCommande commande : commandeList) {
            Date dateCommande = commande.getDateCommande();  // Suppose que ModelCommande a une méthode getDateCommande()
            String monthName = getMonthNameFromDate(dateCommande);
            int[] monthStats = statsPerMonth.get(monthName);
            monthStats[2]++;  // Incrémenter le nombre de commandes
            statsPerMonth.put(monthName, monthStats);
        }

        for (ModelFacture facture : factureList) {
            Date dateFacture = facture.getDateFacture();  // Suppose que ModelFacture a une méthode getDateFacture()
            String monthName = getMonthNameFromDate(dateFacture);
            int[] monthStats = statsPerMonth.get(monthName);
            monthStats[3]++;  // Incrémenter le nombre de factures
            statsPerMonth.put(monthName, monthStats);
        }

        //Ajout des légendes pour le graphique
        
        chart.addLegend("Clients", new Color(0, 108, 247), new Color(12, 84, 175));
        chart.addLegend("Devis", new Color(255, 212, 99), new Color(194, 85, 1));
        chart.addLegend("Commandes", new Color(137, 93, 56), new Color(186, 37, 37));
        chart.addLegend("Factures", new Color(60, 195, 0), new Color(123, 150, 23));
        
        
         line.addLegend("Clients", new Color(0, 108, 247), new Color(12, 84, 175));
         line.addLegend("Devis", new Color(255, 212, 99), new Color(194, 85, 1));
         line.addLegend("Commandes", new Color(137, 93, 56), new Color(186, 37, 37));
         line.addLegend("Factures", new Color(60, 195, 0), new Color(123, 150, 23));


         //Ajout dynamique des données au graphique pour chaque mois
        for (String month : months) {
            int[] stats = statsPerMonth.getOrDefault(month, new int[]{0, 0, 0, 0});
            chart.addData(new ModelChart(month, new double[]{stats[0], stats[1], stats[2], stats[3]}));  // Devis, Commandes, Factures, Clients
        }
          for (String month : months) {
            int[] stats = statsPerMonth.getOrDefault(month, new int[]{0, 0, 0, 0});
            line.addData(new ModelChart(month, new double[]{stats[0], stats[1], stats[2], stats[3]}));  // Devis, Commandes, Factures, Clients
        }

// Lancer le graphique
        chart.start();
        line.start();
    // Set the chart to show current month
          // Ajustement de la barre de défilement uniquement lors de la première ouverture

  
       PanelChart.add(chart, BorderLayout.CENTER);
       LineChart.add(line, BorderLayout.CENTER);
        // Ajout du contentPanel au mainContentPanel
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);
        List<ModelClient> clients = DatabaseUtils.getAllClients();  // Remplacer par le bon appel à votre classe
        int clientCount = clients.size();  // Obtenir le nombre de clients

        ClientsCard.setBackground(new Color(10, 30, 214));
        ClientsCard.setColorGradient(new Color(72, 111, 252));
        Icon clientIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 80, new Color(255, 255, 255, 100), new Color(255, 255, 255, 15));
        ClientsCard.setData(new ModelCard(clientIcon, "Clients", String.valueOf(clientCount)));  // Affiche le nombre réel
        ClientsCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                openClientPanel();
            }
        });

        // Utiliser la méthode getAllDevis pour obtenir la liste de devis
        int devisCount = devisList.size();

        DevisCard.setBackground(new Color(194, 85, 1));
        DevisCard.setColorGradient(new Color(255, 212, 99));

        DevisCard.setData(new ModelCard(new ImageIcon(getClass().getResource("/Icon/tape measure.png")), "Devis", String.valueOf(devisCount)));
        DevisCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                openDevisPanel();
            }
        });

        int commandeListCount = commandeList.size();
        CommandeCard.setBackground(new Color(216, 75, 55));
        CommandeCard.setColorGradient(new Color(137, 93, 56));
        CommandeCard.setData(new ModelCard(new ImageIcon(getClass().getResource("/Icon/icons8-order-80 (5).png")), "Commandes", String.valueOf(commandeListCount)));
        CommandeCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                openCommandePanel();
            }
        });

        int factureListCount = factureList.size();
        FactureCard.setBackground(new Color(60, 195, 0));
        FactureCard.setColorGradient(new Color(123, 150, 23));
        FactureCard.setData(new ModelCard(new ImageIcon(getClass().getResource("/Icon/icons8-receipt-64.png")), "Factures", String.valueOf(factureListCount)));
        FactureCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                openFacturePanel();
            }
        });
        // Ajouter les cartes au CardPanel
        CardPanel.add(ClientsCard);
        CardPanel.add(DevisCard);
        CardPanel.add(CommandeCard);
        CardPanel.add(FactureCard);

        // Ajouter CardPanel au topPanel
        topPanel.add(CardPanel, BorderLayout.CENTER);

        // Ajouter le topPanel au mainPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        // Ajouter le mainPanel à ce HomePanel
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

    }
// Fonction utilitaire pour obtenir le mois d'un objet Date
private String getMonthNameFromDate(Date date) {
    if (date == null) {
        throw new IllegalArgumentException("The date provided must not be null.");
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int monthNumber = calendar.get(Calendar.MONTH);

    // Assuming 'months' is an array holding the names of months in French
    return months[monthNumber];
}
    // Méthode pour ouvrir le ClientPanel
    private void openClientPanel() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showClientPanel();
            dashboard.selectMenuIndex(1,1);
        }
    }

    private void openDevisPanel() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showDevisPanel();
            dashboard.selectMenuIndex(2,1);
        }
    }

    private void openCommandePanel() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showCommandePanel();
            dashboard.selectMenuIndex(3,1);
        }
    }

    private void openFacturePanel() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showFacturePanel();
            dashboard.selectMenuIndex(4,1);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
