/*     */ package com.me.theresa.fontRenderer.font.util;
/*     */ import com.me.theresa.fontRenderer.font.effect.ConfigurableEffect;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GridBagConstraints;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextArea;
/*     */ 
/*     */ public class EffectUtil {
/*  16 */   private static final BufferedImage scratchImage = new BufferedImage(256, 256, 2);
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage getScratchImage() {
/*  21 */     Graphics2D g = (Graphics2D)scratchImage.getGraphics();
/*  22 */     g.setComposite(AlphaComposite.Clear);
/*  23 */     g.fillRect(0, 0, 256, 256);
/*  24 */     g.setComposite(AlphaComposite.SrcOver);
/*  25 */     g.setColor(Color.white);
/*  26 */     return scratchImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value colorValue(String name, Color currentValue) {
/*  31 */     return new DefaultValue(name, toString(currentValue)) {
/*     */         public void showDialog() {
/*  33 */           Color newColor = JColorChooser.showDialog(null, "Choose a color", EffectUtil.fromString(this.value));
/*  34 */           if (newColor != null) this.value = EffectUtil.toString(newColor); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/*  38 */           return EffectUtil.fromString(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value intValue(String name, final int currentValue, final String description) {
/*  45 */     return new DefaultValue(name, String.valueOf(currentValue)) {
/*     */         public void showDialog() {
/*  47 */           JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, -32768, 32767, 1));
/*  48 */           if (showValueDialog(spinner, description)) this.value = String.valueOf(spinner.getValue()); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/*  52 */           return Integer.valueOf(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value floatValue(String name, final float currentValue, final float min, final float max, final String description) {
/*  60 */     return new DefaultValue(name, String.valueOf(currentValue)) {
/*     */         public void showDialog() {
/*  62 */           JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, min, max, 0.10000000149011612D));
/*  63 */           if (showValueDialog(spinner, description))
/*  64 */             this.value = String.valueOf(((Double)spinner.getValue()).floatValue()); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/*  68 */           return Float.valueOf(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value booleanValue(String name, final boolean currentValue, final String description) {
/*  75 */     return new DefaultValue(name, String.valueOf(currentValue)) {
/*     */         public void showDialog() {
/*  77 */           JCheckBox checkBox = new JCheckBox();
/*  78 */           checkBox.setSelected(currentValue);
/*  79 */           if (showValueDialog(checkBox, description)) this.value = String.valueOf(checkBox.isSelected()); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/*  83 */           return Boolean.valueOf(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value optionValue(String name, final String currentValue, final String[][] options, final String description) {
/*  90 */     return new DefaultValue(name, currentValue) {
/*     */         public void showDialog() {
/*  92 */           int selectedIndex = -1;
/*  93 */           DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
/*  94 */           for (int i = 0; i < options.length; i++) {
/*  95 */             model.addElement(options[i][0]);
/*  96 */             if (getValue(i).equals(currentValue)) selectedIndex = i; 
/*     */           } 
/*  98 */           JComboBox<String> comboBox = new JComboBox<>(model);
/*  99 */           comboBox.setSelectedIndex(selectedIndex);
/* 100 */           if (showValueDialog(comboBox, description)) this.value = getValue(comboBox.getSelectedIndex()); 
/*     */         }
/*     */         
/*     */         private String getValue(int i) {
/* 104 */           if ((options[i]).length == 1) return options[i][0]; 
/* 105 */           return options[i][1];
/*     */         }
/*     */         
/*     */         public String toString() {
/* 109 */           for (int i = 0; i < options.length; i++) {
/* 110 */             if (getValue(i).equals(this.value)) return options[i][0]; 
/* 111 */           }  return "";
/*     */         }
/*     */         
/*     */         public Object getObject() {
/* 115 */           return this.value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(Color color) {
/* 122 */     if (color == null) throw new IllegalArgumentException("color cannot be null."); 
/* 123 */     String r = Integer.toHexString(color.getRed());
/* 124 */     if (r.length() == 1) r = "0" + r; 
/* 125 */     String g = Integer.toHexString(color.getGreen());
/* 126 */     if (g.length() == 1) g = "0" + g; 
/* 127 */     String b = Integer.toHexString(color.getBlue());
/* 128 */     if (b.length() == 1) b = "0" + b; 
/* 129 */     return r + g + b;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color fromString(String rgb) {
/* 134 */     if (rgb == null || rgb.length() != 6) return Color.white; 
/* 135 */     return new Color(Integer.parseInt(rgb.substring(0, 2), 16), Integer.parseInt(rgb.substring(2, 4), 16), Integer.parseInt(rgb
/* 136 */           .substring(4, 6), 16));
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class DefaultValue
/*     */     implements ConfigurableEffect.Value
/*     */   {
/*     */     String value;
/*     */     
/*     */     String name;
/*     */     
/*     */     public DefaultValue(String name, String value) {
/* 148 */       this.value = value;
/* 149 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setString(String value) {
/* 154 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getString() {
/* 159 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 164 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 169 */       if (this.value == null) {
/* 170 */         return "";
/*     */       }
/* 172 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean showValueDialog(final JComponent component, String description) {
/* 177 */       EffectUtil.ValueDialog dialog = new EffectUtil.ValueDialog(component, this.name, description);
/* 178 */       dialog.setTitle(this.name);
/* 179 */       dialog.setLocationRelativeTo(null);
/* 180 */       EventQueue.invokeLater(new Runnable() {
/*     */             public void run() {
/* 182 */               JComponent focusComponent = component;
/* 183 */               if (focusComponent instanceof JSpinner)
/* 184 */                 focusComponent = ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField(); 
/* 185 */               focusComponent.requestFocusInWindow();
/*     */             }
/*     */           });
/* 188 */       dialog.setVisible(true);
/* 189 */       return dialog.okPressed;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ValueDialog
/*     */     extends JDialog
/*     */   {
/*     */     public boolean okPressed = false;
/*     */     
/*     */     public ValueDialog(JComponent component, String name, String description) {
/* 199 */       setDefaultCloseOperation(2);
/* 200 */       setLayout(new GridBagLayout());
/* 201 */       setModal(true);
/*     */       
/* 203 */       if (component instanceof JSpinner) {
/* 204 */         ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField().setColumns(4);
/*     */       }
/* 206 */       JPanel descriptionPanel = new JPanel();
/* 207 */       descriptionPanel.setLayout(new GridBagLayout());
/* 208 */       getContentPane().add(descriptionPanel, new GridBagConstraints(0, 0, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */       
/* 212 */       descriptionPanel.setBackground(Color.white);
/* 213 */       descriptionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
/*     */       
/* 215 */       JTextArea descriptionText = new JTextArea(description);
/* 216 */       descriptionPanel.add(descriptionText, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
/*     */       
/* 218 */       descriptionText.setWrapStyleWord(true);
/* 219 */       descriptionText.setLineWrap(true);
/* 220 */       descriptionText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 221 */       descriptionText.setEditable(false);
/*     */ 
/*     */       
/* 224 */       JPanel panel = new JPanel();
/* 225 */       getContentPane().add(panel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 0, new Insets(5, 5, 0, 5), 0, 0));
/*     */ 
/*     */ 
/*     */       
/* 229 */       panel.add(new JLabel(name + ":"));
/* 230 */       panel.add(component);
/*     */       
/* 232 */       JPanel buttonPanel = new JPanel();
/* 233 */       getContentPane().add(buttonPanel, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       JButton okButton = new JButton("OK");
/* 239 */       buttonPanel.add(okButton);
/* 240 */       okButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent evt) {
/* 242 */               EffectUtil.ValueDialog.this.okPressed = true;
/* 243 */               EffectUtil.ValueDialog.this.setVisible(false);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 248 */       JButton cancelButton = new JButton("Cancel");
/* 249 */       buttonPanel.add(cancelButton);
/* 250 */       cancelButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent evt) {
/* 252 */               EffectUtil.ValueDialog.this.setVisible(false);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 257 */       setSize(new Dimension(320, 175));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\fon\\util\EffectUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */