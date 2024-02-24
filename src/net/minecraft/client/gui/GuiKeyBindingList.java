/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ public class GuiKeyBindingList
/*     */   extends GuiListExtended
/*     */ {
/*     */   final GuiControls field_148191_k;
/*     */   final Minecraft mc;
/*     */   private final GuiListExtended.IGuiListEntry[] listEntries;
/*  17 */   int maxListLabelWidth = 0;
/*     */ 
/*     */   
/*     */   public GuiKeyBindingList(GuiControls controls, Minecraft mcIn) {
/*  21 */     super(mcIn, controls.width, controls.height, 63, controls.height - 32, 20);
/*  22 */     this.field_148191_k = controls;
/*  23 */     this.mc = mcIn;
/*  24 */     KeyBinding[] akeybinding = (KeyBinding[])ArrayUtils.clone((Object[])mcIn.gameSettings.keyBindings);
/*  25 */     this.listEntries = new GuiListExtended.IGuiListEntry[akeybinding.length + KeyBinding.getKeybinds().size()];
/*  26 */     Arrays.sort((Object[])akeybinding);
/*  27 */     int i = 0;
/*  28 */     String s = null;
/*     */     
/*  30 */     for (KeyBinding keybinding : akeybinding) {
/*     */       
/*  32 */       String s1 = keybinding.getKeyCategory();
/*     */       
/*  34 */       if (!s1.equals(s)) {
/*     */         
/*  36 */         s = s1;
/*  37 */         this.listEntries[i++] = new CategoryEntry(s1);
/*     */       } 
/*     */       
/*  40 */       int j = mcIn.fontRendererObj.getStringWidth(I18n.format(keybinding.getKeyDescription(), new Object[0]));
/*     */       
/*  42 */       if (j > this.maxListLabelWidth)
/*     */       {
/*  44 */         this.maxListLabelWidth = j;
/*     */       }
/*     */       
/*  47 */       this.listEntries[i++] = new KeyEntry(keybinding);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  53 */     return this.listEntries.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListExtended.IGuiListEntry getListEntry(int index) {
/*  61 */     return this.listEntries[index];
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  66 */     return super.getScrollBarX() + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  74 */     return super.getListWidth() + 32;
/*     */   }
/*     */   
/*     */   public class CategoryEntry
/*     */     implements GuiListExtended.IGuiListEntry
/*     */   {
/*     */     private final String labelText;
/*     */     private final int labelWidth;
/*     */     
/*     */     public CategoryEntry(String p_i45028_2_) {
/*  84 */       this.labelText = I18n.format(p_i45028_2_, new Object[0]);
/*  85 */       this.labelWidth = GuiKeyBindingList.this.mc.fontRendererObj.getStringWidth(this.labelText);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*  90 */       GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.labelText, GuiKeyBindingList.this.mc.currentScreen.width / 2 - this.labelWidth / 2, y + slotHeight - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */     
/*     */     public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public class KeyEntry
/*     */     implements GuiListExtended.IGuiListEntry
/*     */   {
/*     */     private final KeyBinding keybinding;
/*     */     
/*     */     private final String keyDesc;
/*     */     private final GuiButton btnChangeKeyBinding;
/*     */     private final GuiButton btnReset;
/*     */     
/*     */     KeyEntry(KeyBinding p_i45029_2_) {
/* 116 */       this.keybinding = p_i45029_2_;
/* 117 */       this.keyDesc = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
/* 118 */       this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
/* 119 */       this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/* 124 */       boolean flag = (GuiKeyBindingList.this.field_148191_k.buttonId == this.keybinding);
/* 125 */       GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.keyDesc, x + 90 - GuiKeyBindingList.this.maxListLabelWidth, y + slotHeight / 2 - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
/* 126 */       this.btnReset.xPosition = x + 190;
/* 127 */       this.btnReset.yPosition = y;
/* 128 */       this.btnReset.enabled = (this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault());
/* 129 */       this.btnReset.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
/* 130 */       this.btnChangeKeyBinding.xPosition = x + 105;
/* 131 */       this.btnChangeKeyBinding.yPosition = y;
/* 132 */       this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
/* 133 */       boolean flag1 = false;
/*     */       
/* 135 */       if (this.keybinding.getKeyCode() != 0)
/*     */       {
/* 137 */         for (KeyBinding keybinding : GuiKeyBindingList.this.mc.gameSettings.keyBindings) {
/*     */           
/* 139 */           if (keybinding != this.keybinding && keybinding.getKeyCode() == this.keybinding.getKeyCode()) {
/*     */             
/* 141 */             flag1 = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 147 */       if (flag) {
/*     */         
/* 149 */         this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
/*     */       }
/* 151 */       else if (flag1) {
/*     */         
/* 153 */         this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
/*     */       } 
/*     */       
/* 156 */       this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 161 */       if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
/*     */         
/* 163 */         GuiKeyBindingList.this.field_148191_k.buttonId = this.keybinding;
/* 164 */         return true;
/*     */       } 
/* 166 */       if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, p_148278_2_, p_148278_3_)) {
/*     */         
/* 168 */         GuiKeyBindingList.this.mc.gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
/* 169 */         KeyBinding.resetKeyBindingArrayAndHash();
/* 170 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 174 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 180 */       this.btnChangeKeyBinding.mouseReleased(x, y);
/* 181 */       this.btnReset.mouseReleased(x, y);
/*     */     }
/*     */     
/*     */     public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiKeyBindingList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */