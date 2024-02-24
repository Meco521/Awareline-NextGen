/*     */ package net.minecraft.client.settings;
/*     */ 
/*     */ import awareline.main.mod.implement.move.InvMove;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ 
/*     */ public class KeyBinding
/*     */   implements Comparable<KeyBinding>
/*     */ {
/*  15 */   private static final List<KeyBinding> keybindArray = Lists.newArrayList();
/*  16 */   private static final IntHashMap<KeyBinding> hash = new IntHashMap();
/*  17 */   private static final Set<String> keybindSet = Sets.newHashSet();
/*     */   
/*     */   private final String keyDescription;
/*     */   
/*     */   private final int keyCodeDefault;
/*     */   
/*     */   private final String keyCategory;
/*     */   
/*     */   private int keyCode;
/*     */   public boolean pressed;
/*     */   private int pressTime;
/*     */   
/*     */   public static void onTick(int keyCode) {
/*  30 */     if (keyCode != 0) {
/*  31 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  33 */       if (keybinding != null) {
/*  34 */         keybinding.pressTime++;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setKeyBindState(int keyCode, boolean pressed) {
/*  40 */     if (keyCode != 0) {
/*  41 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  43 */       if (keybinding != null) {
/*  44 */         keybinding.pressed = pressed;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unPressAllKeys() {
/*  50 */     for (KeyBinding keybinding : keybindArray) {
/*     */       
/*  52 */       if (InvMove.getInstance.isEnabled() && (Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
/*  53 */         GameSettings gameSettings = (Minecraft.getMinecraft()).gameSettings;
/*  54 */         int keyCode = keybinding.keyCode;
/*  55 */         if (keyCode == gameSettings.keyBindForward.keyCode || keyCode == gameSettings.keyBindRight.keyCode || keyCode == gameSettings.keyBindLeft.keyCode || keyCode == gameSettings.keyBindBack.keyCode)
/*     */           continue; 
/*     */       } 
/*  58 */       keybinding.unpressKey();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void resetKeyBindingArrayAndHash() {
/*  63 */     hash.clearMap();
/*     */     
/*  65 */     for (KeyBinding keybinding : keybindArray) {
/*  66 */       hash.addKey(keybinding.keyCode, keybinding);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Set<String> getKeybinds() {
/*  71 */     return keybindSet;
/*     */   }
/*     */   
/*     */   public KeyBinding(String description, int keyCode, String category) {
/*  75 */     this.keyDescription = description;
/*  76 */     this.keyCode = keyCode;
/*  77 */     this.keyCodeDefault = keyCode;
/*  78 */     this.keyCategory = category;
/*  79 */     keybindArray.add(this);
/*  80 */     hash.addKey(keyCode, this);
/*  81 */     keybindSet.add(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeyDown() {
/*  88 */     return this.pressed;
/*     */   }
/*     */   
/*     */   public String getKeyCategory() {
/*  92 */     return this.keyCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPressed() {
/* 100 */     if (this.pressTime == 0) {
/* 101 */       return false;
/*     */     }
/* 103 */     this.pressTime--;
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void unpressKey() {
/* 109 */     this.pressTime = 0;
/* 110 */     this.pressed = false;
/*     */   }
/*     */   
/*     */   public String getKeyDescription() {
/* 114 */     return this.keyDescription;
/*     */   }
/*     */   
/*     */   public int getKeyCodeDefault() {
/* 118 */     return this.keyCodeDefault;
/*     */   }
/*     */   
/*     */   public int getKeyCode() {
/* 122 */     return this.keyCode;
/*     */   }
/*     */   
/*     */   public void setKeyCode(int keyCode) {
/* 126 */     this.keyCode = keyCode;
/*     */   }
/*     */   
/*     */   public int compareTo(KeyBinding p_compareTo_1_) {
/* 130 */     int i = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));
/*     */     
/* 132 */     if (i == 0) {
/* 133 */       i = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
/*     */     }
/*     */     
/* 136 */     return i;
/*     */   }
/*     */   
/*     */   public boolean getIsKeyPressed() {
/* 140 */     return this.pressed;
/*     */   }
/*     */   
/*     */   public void setPressed(boolean b) {
/* 144 */     this.pressed = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\settings\KeyBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */