/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.IShaderPack;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ class GuiSlotShaders extends GuiSlot {
/*     */   private ArrayList shaderslist;
/*     */   private int selectedIndex;
/*  22 */   private long lastClickedCached = 0L;
/*     */   
/*     */   final GuiShaders shadersGui;
/*     */   
/*     */   public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
/*  27 */     super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
/*  28 */     this.shadersGui = par1GuiShaders;
/*  29 */     updateList();
/*  30 */     this.amountScrolled = 0.0F;
/*  31 */     int i = this.selectedIndex * slotHeight;
/*  32 */     int j = (bottom - top) / 2;
/*     */     
/*  34 */     if (i > j)
/*     */     {
/*  36 */       scrollBy(i - j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  45 */     return this.width - 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateList() {
/*  50 */     this.shaderslist = Shaders.listOfShaders();
/*  51 */     this.selectedIndex = 0;
/*  52 */     int i = 0;
/*     */     
/*  54 */     for (int j = this.shaderslist.size(); i < j; i++) {
/*     */       
/*  56 */       if (((String)this.shaderslist.get(i)).equals(Shaders.currentShaderName)) {
/*     */         
/*  58 */         this.selectedIndex = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  66 */     return this.shaderslist.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int index, boolean doubleClicked, int mouseX, int mouseY) {
/*  74 */     if (index != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
/*     */       
/*  76 */       String s = this.shaderslist.get(index);
/*  77 */       IShaderPack ishaderpack = Shaders.getShaderPack(s);
/*     */       
/*  79 */       if (checkCompatible(ishaderpack, index))
/*     */       {
/*  81 */         selectIndex(index);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void selectIndex(int index) {
/*  88 */     this.selectedIndex = index;
/*  89 */     this.lastClickedCached = this.lastClicked;
/*  90 */     Shaders.setShaderPack(this.shaderslist.get(index));
/*  91 */     Shaders.uninit();
/*  92 */     this.shadersGui.updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkCompatible(IShaderPack sp, final int index) {
/*  97 */     if (sp == null)
/*     */     {
/*  99 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 103 */     InputStream inputstream = sp.getResourceAsStream("/shaders/shaders.properties");
/* 104 */     Properties properties = ResUtils.readProperties(inputstream, "Shaders");
/*     */     
/* 106 */     if (properties == null)
/*     */     {
/* 108 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 112 */     String s = "version.1.8.9";
/* 113 */     String s1 = properties.getProperty(s);
/*     */     
/* 115 */     if (s1 == null)
/*     */     {
/* 117 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 121 */     s1 = s1.trim();
/* 122 */     String s2 = "M6_pre1";
/* 123 */     int i = Config.compareRelease(s2, s1);
/*     */     
/* 125 */     if (i >= 0)
/*     */     {
/* 127 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 131 */     String s3 = ("HD_U_" + s1).replace('_', ' ');
/* 132 */     String s4 = I18n.format("of.message.shaders.nv1", new Object[] { s3 });
/* 133 */     String s5 = I18n.format("of.message.shaders.nv2", new Object[0]);
/* 134 */     GuiYesNoCallback guiyesnocallback = new GuiYesNoCallback()
/*     */       {
/*     */         public void confirmClicked(boolean result, int id)
/*     */         {
/* 138 */           if (result)
/*     */           {
/* 140 */             GuiSlotShaders.this.selectIndex(index);
/*     */           }
/*     */           
/* 143 */           GuiSlotShaders.this.mc.displayGuiScreen((GuiScreen)GuiSlotShaders.this.shadersGui);
/*     */         }
/*     */       };
/* 146 */     GuiYesNo guiyesno = new GuiYesNo(guiyesnocallback, s4, s5, 0);
/* 147 */     this.mc.displayGuiScreen((GuiScreen)guiyesno);
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int index) {
/* 160 */     return (index == this.selectedIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 165 */     return this.width - 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 173 */     return getSize() * 18;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBackground() {}
/*     */ 
/*     */   
/*     */   protected void drawSlot(int index, int posX, int posY, int contentY, int mouseX, int mouseY) {
/* 182 */     String s = this.shaderslist.get(index);
/*     */     
/* 184 */     if (s.equals("OFF")) {
/*     */       
/* 186 */       s = Lang.get("of.options.shaders.packNone");
/*     */     }
/* 188 */     else if (s.equals("(internal)")) {
/*     */       
/* 190 */       s = Lang.get("of.options.shaders.packDefault");
/*     */     } 
/* 192 */     FontLoader.PF16.drawCenteredStringWithShadow(s, this.width / 2.0F, (posY + 4), 14737632);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectedIndex() {
/* 198 */     return this.selectedIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\gui\GuiSlotShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */