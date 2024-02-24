/*    */ package com.me.guichaguri.betterfps.installer;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ public class VersionSelector extends JDialog implements ActionListener {
/* 14 */   private static VersionSelector INSTANCE = null;
/*    */   
/*    */   public static void open(Component c, File mcDir, List<String> versionNames) {
/* 17 */     if (INSTANCE != null) INSTANCE.setVisible(false); 
/* 18 */     INSTANCE = new VersionSelector(mcDir, versionNames);
/* 19 */     INSTANCE.setLocationRelativeTo(c);
/* 20 */     INSTANCE.setVisible(true);
/*    */   }
/*    */   
/* 23 */   private final String INSTALL = "install";
/*    */   
/*    */   private final File mcDir;
/*    */   
/*    */   private final JComboBox version;
/*    */ 
/*    */   
/*    */   private VersionSelector(File mcDir, List<String> versionNames) {
/* 31 */     this.mcDir = mcDir;
/*    */     
/* 33 */     setTitle("Select a Version");
/* 34 */     setLayout(new GridBagLayout());
/* 35 */     GridBagConstraints c = new GridBagConstraints();
/* 36 */     c.insets = new Insets(5, 5, 5, 5);
/* 37 */     c.fill = 2;
/* 38 */     c.gridx = 0;
/*    */     
/* 40 */     this.version = new JComboBox();
/* 41 */     for (String v : versionNames) {
/* 42 */       this.version.addItem(v);
/*    */     }
/* 44 */     add(this.version, c);
/*    */     
/* 46 */     JButton install = new JButton("Install");
/* 47 */     install.setActionCommand("install");
/* 48 */     install.addActionListener(this);
/* 49 */     add(install, c);
/*    */     
/* 51 */     Dimension d = getPreferredSize();
/* 52 */     setSize(new Dimension(d.width + 50, d.height + 50));
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionPerformed(ActionEvent event) {
/* 57 */     String cmd = event.getActionCommand();
/* 58 */     if (cmd.equals("install")) {
/* 59 */       String ver = this.version.getSelectedItem().toString();
/* 60 */       if (ver.toLowerCase().contains("forge")) {
/* 61 */         String[] options = { "Yes, I don't care", "No, I'll do it correctly", "What?!" };
/* 62 */         int r = JOptionPane.showOptionDialog(this, "Looks like you're using Forge.\nYou just need to drop the BetterFps jar file in the mods folder.\nDo you want to continue anyway?", "Forge Version", 1, 3, null, (Object[])options, options[1]);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 68 */         if (r == 1) {
/* 69 */           setVisible(false); return;
/*    */         } 
/* 71 */         if (r == 2 || r == -1) {
/*    */           return;
/*    */         }
/*    */       } 
/*    */       try {
/* 76 */         InstanceInstaller.install(this.mcDir, ver);
/* 77 */         setVisible(false);
/* 78 */         JOptionPane.showMessageDialog(this, "BetterFps was successfully installed!", "Done!", 1);
/*    */       }
/* 80 */       catch (Exception ex) {
/* 81 */         JOptionPane.showMessageDialog(this, "An error has ocurred: " + ex.getClass().getSimpleName() + "\nTry choosing another version", "Oops!", 0);
/*    */         
/* 83 */         ex.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVisible(boolean visible) {
/* 90 */     super.setVisible(visible);
/* 91 */     if (!visible)
/* 92 */       INSTANCE = null; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\VersionSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */