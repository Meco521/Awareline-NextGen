/*     */ package com.me.guichaguri.betterfps.installer;
/*     */ import com.me.guichaguri.betterfps.BetterFpsHelper;
/*     */ import com.me.guichaguri.betterfps.math.JavaMath;
/*     */ import com.me.guichaguri.betterfps.math.TaylorMath;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class AlgorithmTester extends JFrame implements ActionListener {
/*  20 */   private static final Class[] algorithms = new Class[] { JavaMath.class, VanillaMath.class, TaylorMath.class, LibGDXMath.class, RivensMath.class, RivensFullMath.class, RivensHalfMath.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashMap<String, Long> testAlgorithms() {
/*  27 */     HashMap<String, Long> results = new HashMap<>();
/*  28 */     for (Class algorithm : algorithms) {
/*     */       try {
/*  30 */         Method sin = algorithm.getDeclaredMethod("sin", new Class[] { float.class });
/*  31 */         Method cos = algorithm.getDeclaredMethod("cos", new Class[] { float.class });
/*     */         
/*  33 */         long startTime = System.nanoTime();
/*  34 */         for (int i = 0; i < 360000; i++) {
/*  35 */           float angle = i / 1000.0F;
/*  36 */           sin.invoke(null, new Object[] { Float.valueOf(angle) });
/*  37 */           cos.invoke(null, new Object[] { Float.valueOf(angle) });
/*     */         } 
/*  39 */         long endTime = System.nanoTime();
/*     */         
/*  41 */         String name = algorithm.getSimpleName();
/*  42 */         for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)BetterFpsHelper.helpers.entrySet()) {
/*  43 */           if (((String)e.getValue()).equals(name)) {
/*  44 */             name = e.getKey();
/*     */             break;
/*     */           } 
/*     */         } 
/*  48 */         results.put(name, Long.valueOf(endTime - startTime));
/*  49 */       } catch (Exception ex) {
/*  50 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*  53 */     return results;
/*     */   }
/*     */   
/*  56 */   private static AlgorithmTester INSTANCE = null;
/*     */   
/*     */   public static void open(Component c, File mcDir, String calcAction, ActionListener listener) {
/*  59 */     if (INSTANCE != null) INSTANCE.setVisible(false); 
/*  60 */     INSTANCE = new AlgorithmTester(mcDir, testAlgorithms(), calcAction, listener);
/*  61 */     INSTANCE.setLocationRelativeTo(c);
/*  62 */     INSTANCE.setVisible(true);
/*  63 */     INSTANCE.requestFocusInWindow();
/*     */   }
/*     */   
/*  66 */   private final String CHANGE_ALGORITHM = "tester_change_algorithm";
/*  67 */   private final String CLOSE_TESTER = "close_tester";
/*     */   
/*  69 */   private final String DESCRIPTION = "<html><center>We recommend testing a few times<br>to confirm which is the best algorithm<br><small>[Notice that this tester is still in development]</small></center></html>";
/*     */ 
/*     */   
/*     */   private final File mcDir;
/*     */   
/*  74 */   private String bestAlgorithm = null;
/*  75 */   private String bestAlgorithmName = null;
/*     */   
/*     */   public AlgorithmTester(final File mcDir, HashMap<String, Long> results, String CALC_ALGORITHM, ActionListener calcAction) {
/*  78 */     this.mcDir = mcDir;
/*     */     
/*  80 */     setTitle("Test Results");
/*  81 */     setLayout(new GridBagLayout());
/*  82 */     GridBagConstraints c = new GridBagConstraints();
/*  83 */     c.insets = new Insets(5, 5, 5, 5);
/*  84 */     c.fill = 2;
/*  85 */     c.gridx = 0;
/*  86 */     add(new JLabel("<html><center>We recommend testing a few times<br>to confirm which is the best algorithm<br><small>[Notice that this tester is still in development]</small></center></html>", 0), c);
/*  87 */     JPanel resultsPanel = new JPanel();
/*  88 */     resultsPanel.setLayout(new GridLayout(0, 2, 5, 0));
/*     */     
/*  90 */     long bestAlgorithmTime = 0L;
/*  91 */     for (Map.Entry<String, Long> e : results.entrySet()) {
/*  92 */       String algorithm = e.getKey();
/*  93 */       long v = ((Long)e.getValue()).longValue();
/*  94 */       String displayName = algorithm;
/*  95 */       if (BetterFpsHelper.displayHelpers.containsKey(algorithm)) {
/*  96 */         displayName = (String)BetterFpsHelper.displayHelpers.get(algorithm);
/*     */       }
/*  98 */       if (v < bestAlgorithmTime || this.bestAlgorithm == null) {
/*  99 */         bestAlgorithmTime = v;
/* 100 */         this.bestAlgorithm = algorithm;
/* 101 */         this.bestAlgorithmName = displayName;
/*     */       } 
/* 103 */       resultsPanel.add(new JLabel(displayName, 4));
/* 104 */       resultsPanel.add(new JLabel((Math.round((float)v / 1000000.0F * 100.0F) / 100.0F) + "ms", 2));
/*     */     } 
/* 106 */     add(resultsPanel, c);
/* 107 */     JButton changeAlgorithm = new JButton("Change Algorithm to " + this.bestAlgorithmName);
/* 108 */     changeAlgorithm.setToolTipText("This will choose the best algorithm and change the config file for you.");
/* 109 */     changeAlgorithm.setActionCommand("tester_change_algorithm");
/* 110 */     changeAlgorithm.addActionListener(this);
/* 111 */     add(changeAlgorithm, c);
/* 112 */     JButton calcAgain = new JButton("Test Again");
/* 113 */     if (CALC_ALGORITHM == null || calcAction == null) {
/* 114 */       calcAgain.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 117 */               AlgorithmTester.open((Component)null, mcDir, (String)null, (ActionListener)null);
/*     */             }
/*     */           });
/* 120 */       setDefaultCloseOperation(3);
/*     */     } else {
/* 122 */       calcAgain.setActionCommand(CALC_ALGORITHM);
/* 123 */       calcAgain.addActionListener(calcAction);
/*     */     } 
/* 125 */     add(calcAgain, c);
/* 126 */     JButton close = new JButton("Close");
/* 127 */     close.setActionCommand("close_tester");
/* 128 */     close.addActionListener(this);
/* 129 */     add(close, c);
/* 130 */     Dimension d = getPreferredSize();
/* 131 */     setSize(new Dimension(d.width + 50, d.height + 50));
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 136 */     String cmd = event.getActionCommand();
/* 137 */     if (cmd.equals("close_tester")) {
/* 138 */       setVisible(false);
/* 139 */       if (getDefaultCloseOperation() == 3) System.exit(0); 
/* 140 */     } else if (cmd.equals("tester_change_algorithm")) {
/* 141 */       if (!this.mcDir.exists() || !this.mcDir.isDirectory()) {
/* 142 */         JOptionPane.showMessageDialog(this, "The install location is invalid.", "Oops!", 2);
/*     */         
/*     */         return;
/*     */       } 
/* 146 */       BetterFpsHelper.MCDIR = this.mcDir;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       setVisible(false);
/* 152 */       System.out.println(this.bestAlgorithm);
/* 153 */       JOptionPane.showMessageDialog(this, "The algorithm was set to " + this.bestAlgorithmName + ".\n\nNote: If the game is started, you have to restart it to take effect", "Done!", 1);
/*     */ 
/*     */ 
/*     */       
/* 157 */       if (getDefaultCloseOperation() == 3) System.exit(0);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 163 */     super.setVisible(visible);
/* 164 */     if (!visible) {
/* 165 */       INSTANCE = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class VanillaMath
/*     */   {
/* 171 */     private static final float[] SIN_TABLE = new float[65536];
/*     */     
/*     */     static {
/* 174 */       for (int i = 0; i < 65536; i++) {
/* 175 */         SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0D / 65536.0D);
/*     */       }
/*     */     }
/*     */     
/*     */     public static float sin(float val) {
/* 180 */       return SIN_TABLE[(int)(val * 10430.378F) & 0xFFFF];
/*     */     }
/*     */     
/*     */     public static float cos(float val) {
/* 184 */       return SIN_TABLE[(int)(val * 10430.378F + 16384.0F) & 0xFFFF];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\AlgorithmTester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */