/*     */ package com.me.guichaguri.betterfps.installer;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ public class BetterFpsInstaller extends JFrame implements ActionListener {
/*     */   public static void main(String[] args) {
/*  17 */     String tester = System.getProperty("tester", null);
/*  18 */     if (tester != null) {
/*  19 */       JFrame load = new JFrame();
/*  20 */       load.add(new JLabel("Testing each algorithm..."));
/*  21 */       load.setSize(250, 100);
/*  22 */       load.setLocationRelativeTo((Component)null);
/*  23 */       load.setVisible(true);
/*  24 */       load.requestFocusInWindow();
/*  25 */       AlgorithmTester.open(null, new File(tester), null, null);
/*  26 */       load.setVisible(false);
/*     */       return;
/*     */     } 
/*  29 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/*  33 */               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  34 */             } catch (Exception exception) {}
/*     */ 
/*     */             
/*  37 */             BetterFpsInstaller installer = new BetterFpsInstaller();
/*  38 */             installer.setVisible(true);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*  44 */   private final String installerDesc = "<html>This is the installer for <strong>BetterFps</strong><br>If you are using Forge, you just need to drop this file in the mods folder<br>It's recommended closing the Minecraft Launcher before installing.</html>";
/*     */ 
/*     */   
/*  47 */   private final String modUrl = "http://minecraft.curseforge.com/mc-mods/229876-betterfps";
/*     */ 
/*     */   
/*  50 */   private final String INSTALL = "install";
/*  51 */   private final String PAGE = "page";
/*  52 */   private final String CALC_ALGORITHM = "calc_algorithm";
/*  53 */   private final String CHANGE_FILE = "change_file";
/*     */   
/*     */   private final JTextField installLocation;
/*     */   
/*     */   private final JFileChooser fc;
/*     */   
/*  59 */   private final JDialog versionDialog = null;
/*     */   
/*  61 */   private final JComboBox versionComboBox = null;
/*     */   
/*     */   public BetterFpsInstaller() {
/*  64 */     setTitle("BetterFps Installer");
/*  65 */     setLayout(new GridBagLayout());
/*     */     
/*  67 */     GridBagConstraints c = new GridBagConstraints();
/*  68 */     c.fill = 2;
/*  69 */     c.gridx = 0;
/*  70 */     c.gridy = 0;
/*  71 */     c.ipadx = 5;
/*  72 */     c.ipady = 5;
/*  73 */     c.insets = new Insets(5, 5, 5, 5);
/*     */     
/*  75 */     JLabel title = new JLabel("BetterFps Installer");
/*  76 */     title.setFont(title.getFont().deriveFont(32.0F));
/*  77 */     add(title, c);
/*     */     
/*  79 */     c.gridy = 1;
/*  80 */     JLabel desc = new JLabel("<html>This is the installer for <strong>BetterFps</strong><br>If you are using Forge, you just need to drop this file in the mods folder<br>It's recommended closing the Minecraft Launcher before installing.</html>");
/*  81 */     add(desc, c);
/*     */     
/*  83 */     c.gridy = 2;
/*  84 */     c.fill = 2;
/*  85 */     this.installLocation = new JTextField(12);
/*  86 */     this.installLocation.setText(InstanceInstaller.getSuggestedMinecraftFolder().getAbsolutePath());
/*  87 */     add(this.installLocation, c);
/*     */     
/*  89 */     c.gridx = 1;
/*  90 */     c.fill = 0;
/*  91 */     JButton choose = new JButton("...");
/*  92 */     choose.setActionCommand("change_file");
/*  93 */     choose.addActionListener(this);
/*  94 */     add(choose, c);
/*     */     
/*  96 */     this.fc = new JFileChooser();
/*  97 */     this.fc.setFileSelectionMode(1);
/*  98 */     this.fc.setDialogTitle("Select the Minecraft Installation folder (.minecraft)");
/*     */     
/* 100 */     c.fill = 2;
/* 101 */     c.gridx = 0;
/* 102 */     c.gridy = 3;
/* 103 */     JButton install = new JButton("Install");
/* 104 */     install.setActionCommand("install");
/* 105 */     install.addActionListener(this);
/* 106 */     add(install, c);
/*     */     
/* 108 */     c.gridy = 4;
/* 109 */     JButton testAlgorithms = new JButton("Test Algorithms");
/* 110 */     testAlgorithms.setToolTipText("Test all algorithm to see which is faster");
/* 111 */     testAlgorithms.setActionCommand("calc_algorithm");
/* 112 */     testAlgorithms.addActionListener(this);
/* 113 */     add(testAlgorithms, c);
/*     */     
/* 115 */     c.gridy = 5;
/* 116 */     JButton page = new JButton("Official Page");
/* 117 */     page.setActionCommand("page");
/* 118 */     page.addActionListener(this);
/* 119 */     add(page, c);
/*     */     
/* 121 */     setSize(450, 325);
/* 122 */     setResizable(false);
/* 123 */     setDefaultCloseOperation(3);
/* 124 */     setLocationRelativeTo((Component)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 129 */     String cmd = event.getActionCommand();
/* 130 */     if (cmd.equals("install")) {
/* 131 */       File file = new File(this.installLocation.getText());
/* 132 */       if (!file.exists() || !file.isDirectory()) {
/* 133 */         JOptionPane.showMessageDialog(this, "The install location is invalid.", "Oops!", 2);
/*     */         
/*     */         return;
/*     */       } 
/* 137 */       List<String> versions = InstanceInstaller.getVersions(file);
/* 138 */       VersionSelector.open(this, file, versions);
/* 139 */     } else if (cmd.equals("page")) {
/*     */       try {
/* 141 */         Desktop.getDesktop().browse(new URI("http://minecraft.curseforge.com/mc-mods/229876-betterfps"));
/* 142 */       } catch (Exception ex) {
/* 143 */         JOptionPane.showMessageDialog(this, "http://minecraft.curseforge.com/mc-mods/229876-betterfps", "URL", 1);
/*     */       } 
/* 145 */     } else if (cmd.equals("change_file")) {
/* 146 */       int val = this.fc.showDialog(this, "Select");
/* 147 */       if (val == 0) {
/* 148 */         this.installLocation.setText(this.fc.getSelectedFile().getAbsolutePath());
/*     */       }
/* 150 */     } else if (cmd.equals("calc_algorithm")) {
/* 151 */       File file = new File(this.installLocation.getText());
/* 152 */       AlgorithmTester.open(this, file, "calc_algorithm", this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\BetterFpsInstaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */