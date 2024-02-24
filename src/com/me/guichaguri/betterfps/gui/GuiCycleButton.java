/*    */ package com.me.guichaguri.betterfps.gui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiCycleButton
/*    */   extends GuiButton
/*    */ {
/*    */   private final String title;
/* 15 */   protected int key = 0;
/*    */   protected List<? extends Object> keys;
/*    */   protected HashMap<? extends Object, String> values;
/*    */   private final String[] helpLines;
/*    */   
/*    */   public <T> GuiCycleButton(int buttonId, String title, HashMap<T, String> values, T defaultValue, String[] helpLines) {
/* 21 */     super(buttonId, 0, 0, title);
/* 22 */     this.title = title;
/* 23 */     this.keys = new ArrayList(values.keySet());
/* 24 */     this.helpLines = helpLines;
/* 25 */     for (int i = 0; i < this.keys.size(); i++) {
/* 26 */       if (defaultValue.equals(this.keys.get(i))) {
/* 27 */         this.key = i;
/*    */         break;
/*    */       } 
/*    */     } 
/* 31 */     this.values = (HashMap)values;
/* 32 */     updateTitle();
/*    */   }
/*    */ 
/*    */   
/*    */   public void actionPerformed() {
/* 37 */     if (Keyboard.isKeyDown(42) && shiftClick())
/* 38 */       return;  this.key++;
/* 39 */     if (this.key >= this.keys.size()) this.key = 0; 
/* 40 */     updateTitle();
/*    */   }
/*    */   
/*    */   public boolean shiftClick() {
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   protected void updateTitle() {
/* 48 */     this.displayString = this.title + ": " + (String)this.values.get(this.keys.get(this.key));
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getSelectedValue() {
/* 53 */     return (T)this.keys.get(this.key);
/*    */   }
/*    */   
/*    */   public String[] getHelpText() {
/* 57 */     return this.helpLines;
/*    */   }
/*    */   
/*    */   public static class GuiBooleanButton extends GuiCycleButton {
/* 61 */     private static final HashMap<Boolean, String> booleanValues = new HashMap<>();
/*    */     
/*    */     static {
/* 64 */       booleanValues.put(Boolean.valueOf(true), "On");
/* 65 */       booleanValues.put(Boolean.valueOf(false), "Off");
/*    */     }
/*    */     
/*    */     public GuiBooleanButton(int buttonId, String title, boolean defaultValue, String[] helpLines) {
/* 69 */       super(buttonId, title, (HashMap)booleanValues, (T)Boolean.valueOf(defaultValue), helpLines);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\gui\GuiCycleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */