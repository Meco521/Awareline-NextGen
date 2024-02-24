/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ 
/*     */ public class GuiPageButtonList
/*     */   extends GuiListExtended
/*     */ {
/*  14 */   private final List<GuiEntry> field_178074_u = Lists.newArrayList();
/*  15 */   private final IntHashMap<Gui> field_178073_v = new IntHashMap();
/*  16 */   private final List<GuiTextField> field_178072_w = Lists.newArrayList();
/*     */   
/*     */   private final GuiListEntry[][] field_178078_x;
/*     */   private int field_178077_y;
/*     */   private final GuiResponder field_178076_z;
/*     */   private Gui field_178075_A;
/*     */   
/*     */   public GuiPageButtonList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn, GuiResponder p_i45536_7_, GuiListEntry[]... p_i45536_8_) {
/*  24 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  25 */     this.field_178076_z = p_i45536_7_;
/*  26 */     this.field_178078_x = p_i45536_8_;
/*  27 */     this.field_148163_i = false;
/*  28 */     func_178069_s();
/*  29 */     func_178055_t();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178069_s() {
/*  34 */     for (GuiListEntry[] aguipagebuttonlist$guilistentry : this.field_178078_x) {
/*     */       
/*  36 */       for (int i = 0; i < aguipagebuttonlist$guilistentry.length; i += 2) {
/*     */         
/*  38 */         GuiListEntry guipagebuttonlist$guilistentry = aguipagebuttonlist$guilistentry[i];
/*  39 */         GuiListEntry guipagebuttonlist$guilistentry1 = (i < aguipagebuttonlist$guilistentry.length - 1) ? aguipagebuttonlist$guilistentry[i + 1] : null;
/*  40 */         Gui gui = func_178058_a(guipagebuttonlist$guilistentry, 0, (guipagebuttonlist$guilistentry1 == null));
/*  41 */         Gui gui1 = func_178058_a(guipagebuttonlist$guilistentry1, 160, (guipagebuttonlist$guilistentry == null));
/*  42 */         GuiEntry guipagebuttonlist$guientry = new GuiEntry(gui, gui1);
/*  43 */         this.field_178074_u.add(guipagebuttonlist$guientry);
/*     */         
/*  45 */         if (guipagebuttonlist$guilistentry != null && gui != null) {
/*     */           
/*  47 */           this.field_178073_v.addKey(guipagebuttonlist$guilistentry.func_178935_b(), gui);
/*     */           
/*  49 */           if (gui instanceof GuiTextField)
/*     */           {
/*  51 */             this.field_178072_w.add((GuiTextField)gui);
/*     */           }
/*     */         } 
/*     */         
/*  55 */         if (guipagebuttonlist$guilistentry1 != null && gui1 != null) {
/*     */           
/*  57 */           this.field_178073_v.addKey(guipagebuttonlist$guilistentry1.func_178935_b(), gui1);
/*     */           
/*  59 */           if (gui1 instanceof GuiTextField)
/*     */           {
/*  61 */             this.field_178072_w.add((GuiTextField)gui1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178055_t() {
/*  70 */     this.field_178074_u.clear();
/*     */     
/*  72 */     for (int i = 0; i < (this.field_178078_x[this.field_178077_y]).length; i += 2) {
/*     */       
/*  74 */       GuiListEntry guipagebuttonlist$guilistentry = this.field_178078_x[this.field_178077_y][i];
/*  75 */       GuiListEntry guipagebuttonlist$guilistentry1 = (i < (this.field_178078_x[this.field_178077_y]).length - 1) ? this.field_178078_x[this.field_178077_y][i + 1] : null;
/*  76 */       Gui gui = (Gui)this.field_178073_v.lookup(guipagebuttonlist$guilistentry.func_178935_b());
/*  77 */       Gui gui1 = (guipagebuttonlist$guilistentry1 != null) ? (Gui)this.field_178073_v.lookup(guipagebuttonlist$guilistentry1.func_178935_b()) : null;
/*  78 */       GuiEntry guipagebuttonlist$guientry = new GuiEntry(gui, gui1);
/*  79 */       this.field_178074_u.add(guipagebuttonlist$guientry);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181156_c(int p_181156_1_) {
/*  85 */     if (p_181156_1_ != this.field_178077_y) {
/*     */       
/*  87 */       int i = this.field_178077_y;
/*  88 */       this.field_178077_y = p_181156_1_;
/*  89 */       func_178055_t();
/*  90 */       func_178060_e(i, p_181156_1_);
/*  91 */       this.amountScrolled = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_178059_e() {
/*  97 */     return this.field_178077_y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_178057_f() {
/* 102 */     return this.field_178078_x.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public Gui func_178056_g() {
/* 107 */     return this.field_178075_A;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178071_h() {
/* 112 */     if (this.field_178077_y > 0)
/*     */     {
/* 114 */       func_181156_c(this.field_178077_y - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178064_i() {
/* 120 */     if (this.field_178077_y < this.field_178078_x.length - 1)
/*     */     {
/* 122 */       func_181156_c(this.field_178077_y + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Gui func_178061_c(int p_178061_1_) {
/* 128 */     return (Gui)this.field_178073_v.lookup(p_178061_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178060_e(int p_178060_1_, int p_178060_2_) {
/* 133 */     for (GuiListEntry guipagebuttonlist$guilistentry : this.field_178078_x[p_178060_1_]) {
/*     */       
/* 135 */       if (guipagebuttonlist$guilistentry != null)
/*     */       {
/* 137 */         func_178066_a((Gui)this.field_178073_v.lookup(guipagebuttonlist$guilistentry.func_178935_b()), false);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     for (GuiListEntry guipagebuttonlist$guilistentry1 : this.field_178078_x[p_178060_2_]) {
/*     */       
/* 143 */       if (guipagebuttonlist$guilistentry1 != null)
/*     */       {
/* 145 */         func_178066_a((Gui)this.field_178073_v.lookup(guipagebuttonlist$guilistentry1.func_178935_b()), true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178066_a(Gui p_178066_1_, boolean p_178066_2_) {
/* 152 */     if (p_178066_1_ instanceof GuiButton) {
/*     */       
/* 154 */       ((GuiButton)p_178066_1_).visible = p_178066_2_;
/*     */     }
/* 156 */     else if (p_178066_1_ instanceof GuiTextField) {
/*     */       
/* 158 */       ((GuiTextField)p_178066_1_).setVisible(p_178066_2_);
/*     */     }
/* 160 */     else if (p_178066_1_ instanceof GuiLabel) {
/*     */       
/* 162 */       ((GuiLabel)p_178066_1_).visible = p_178066_2_;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Gui func_178058_a(GuiListEntry p_178058_1_, int p_178058_2_, boolean p_178058_3_) {
/* 168 */     return (p_178058_1_ instanceof GuiSlideEntry) ? func_178067_a(this.width / 2 - 155 + p_178058_2_, 0, (GuiSlideEntry)p_178058_1_) : ((p_178058_1_ instanceof GuiButtonEntry) ? func_178065_a(this.width / 2 - 155 + p_178058_2_, 0, (GuiButtonEntry)p_178058_1_) : ((p_178058_1_ instanceof EditBoxEntry) ? func_178068_a(this.width / 2 - 155 + p_178058_2_, 0, (EditBoxEntry)p_178058_1_) : ((p_178058_1_ instanceof GuiLabelEntry) ? func_178063_a(this.width / 2 - 155 + p_178058_2_, 0, (GuiLabelEntry)p_178058_1_, p_178058_3_) : null)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181155_a(boolean p_181155_1_) {
/* 173 */     for (GuiEntry guipagebuttonlist$guientry : this.field_178074_u) {
/*     */       
/* 175 */       if (guipagebuttonlist$guientry.field_178029_b instanceof GuiButton)
/*     */       {
/* 177 */         ((GuiButton)guipagebuttonlist$guientry.field_178029_b).enabled = p_181155_1_;
/*     */       }
/*     */       
/* 180 */       if (guipagebuttonlist$guientry.field_178030_c instanceof GuiButton)
/*     */       {
/* 182 */         ((GuiButton)guipagebuttonlist$guientry.field_178030_c).enabled = p_181155_1_;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
/* 189 */     boolean flag = super.mouseClicked(mouseX, mouseY, mouseEvent);
/* 190 */     int i = getSlotIndexFromScreenCoords(mouseX, mouseY);
/*     */     
/* 192 */     if (i >= 0) {
/*     */       
/* 194 */       GuiEntry guipagebuttonlist$guientry = getListEntry(i);
/*     */       
/* 196 */       if (this.field_178075_A != guipagebuttonlist$guientry.field_178028_d && this.field_178075_A != null && this.field_178075_A instanceof GuiTextField)
/*     */       {
/* 198 */         ((GuiTextField)this.field_178075_A).setFocused(false);
/*     */       }
/*     */       
/* 201 */       this.field_178075_A = guipagebuttonlist$guientry.field_178028_d;
/*     */     } 
/*     */     
/* 204 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiSlider func_178067_a(int p_178067_1_, int p_178067_2_, GuiSlideEntry p_178067_3_) {
/* 209 */     GuiSlider guislider = new GuiSlider(this.field_178076_z, p_178067_3_.func_178935_b(), p_178067_1_, p_178067_2_, p_178067_3_.func_178936_c(), p_178067_3_.func_178943_e(), p_178067_3_.func_178944_f(), p_178067_3_.func_178942_g(), p_178067_3_.func_178945_a());
/* 210 */     guislider.visible = p_178067_3_.func_178934_d();
/* 211 */     return guislider;
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiListButton func_178065_a(int p_178065_1_, int p_178065_2_, GuiButtonEntry p_178065_3_) {
/* 216 */     GuiListButton guilistbutton = new GuiListButton(this.field_178076_z, p_178065_3_.func_178935_b(), p_178065_1_, p_178065_2_, p_178065_3_.func_178936_c(), p_178065_3_.func_178940_a());
/* 217 */     guilistbutton.visible = p_178065_3_.func_178934_d();
/* 218 */     return guilistbutton;
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiTextField func_178068_a(int p_178068_1_, int p_178068_2_, EditBoxEntry p_178068_3_) {
/* 223 */     GuiTextField guitextfield = new GuiTextField(p_178068_3_.func_178935_b(), this.mc.fontRendererObj, p_178068_1_, p_178068_2_, 150, 20);
/* 224 */     guitextfield.setText(p_178068_3_.func_178936_c());
/* 225 */     guitextfield.func_175207_a(this.field_178076_z);
/* 226 */     guitextfield.setVisible(p_178068_3_.func_178934_d());
/* 227 */     guitextfield.setValidator(p_178068_3_.func_178950_a());
/* 228 */     return guitextfield;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiLabel func_178063_a(int p_178063_1_, int p_178063_2_, GuiLabelEntry p_178063_3_, boolean p_178063_4_) {
/*     */     GuiLabel guilabel;
/* 235 */     if (p_178063_4_) {
/*     */       
/* 237 */       guilabel = new GuiLabel(this.mc.fontRendererObj, p_178063_3_.func_178935_b(), p_178063_1_, p_178063_2_, this.width - (p_178063_1_ << 1), 20, -1);
/*     */     }
/*     */     else {
/*     */       
/* 241 */       guilabel = new GuiLabel(this.mc.fontRendererObj, p_178063_3_.func_178935_b(), p_178063_1_, p_178063_2_, 150, 20, -1);
/*     */     } 
/*     */     
/* 244 */     guilabel.visible = p_178063_3_.func_178934_d();
/* 245 */     guilabel.func_175202_a(p_178063_3_.func_178936_c());
/* 246 */     guilabel.setCentered();
/* 247 */     return guilabel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_178062_a(char p_178062_1_, int p_178062_2_) {
/* 252 */     if (this.field_178075_A instanceof GuiTextField) {
/*     */       
/* 254 */       GuiTextField guitextfield = (GuiTextField)this.field_178075_A;
/*     */       
/* 256 */       if (!GuiScreen.isKeyComboCtrlV(p_178062_2_)) {
/*     */         
/* 258 */         if (p_178062_2_ == 15) {
/*     */           
/* 260 */           guitextfield.setFocused(false);
/* 261 */           int k = this.field_178072_w.indexOf(this.field_178075_A);
/*     */           
/* 263 */           if (GuiScreen.isShiftKeyDown()) {
/*     */             
/* 265 */             if (k == 0)
/*     */             {
/* 267 */               k = this.field_178072_w.size() - 1;
/*     */             }
/*     */             else
/*     */             {
/* 271 */               k--;
/*     */             }
/*     */           
/* 274 */           } else if (k == this.field_178072_w.size() - 1) {
/*     */             
/* 276 */             k = 0;
/*     */           }
/*     */           else {
/*     */             
/* 280 */             k++;
/*     */           } 
/*     */           
/* 283 */           this.field_178075_A = this.field_178072_w.get(k);
/* 284 */           guitextfield = (GuiTextField)this.field_178075_A;
/* 285 */           guitextfield.setFocused(true);
/* 286 */           float l = guitextfield.yPosition + this.slotHeight;
/* 287 */           float i1 = guitextfield.yPosition;
/*     */           
/* 289 */           if (l > this.bottom)
/*     */           {
/* 291 */             this.amountScrolled += l - this.bottom;
/*     */           }
/* 293 */           else if (i1 < this.top)
/*     */           {
/* 295 */             this.amountScrolled = i1;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 300 */           guitextfield.textboxKeyTyped(p_178062_1_, p_178062_2_);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 305 */         String s = GuiScreen.getClipboardString();
/* 306 */         String[] astring = s.split(";");
/* 307 */         int i = this.field_178072_w.indexOf(this.field_178075_A);
/* 308 */         int j = i;
/*     */         
/* 310 */         for (String s1 : astring) {
/*     */           
/* 312 */           ((GuiTextField)this.field_178072_w.get(j)).setText(s1);
/*     */           
/* 314 */           if (j == this.field_178072_w.size() - 1) {
/*     */             
/* 316 */             j = 0;
/*     */           }
/*     */           else {
/*     */             
/* 320 */             j++;
/*     */           } 
/*     */           
/* 323 */           if (j == i) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEntry getListEntry(int index) {
/* 337 */     return this.field_178074_u.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 342 */     return this.field_178074_u.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 350 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 355 */     return super.getScrollBarX() + 32;
/*     */   }
/*     */   
/*     */   public static class EditBoxEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     private final Predicate<String> field_178951_a;
/*     */     
/*     */     public EditBoxEntry(int p_i45534_1_, String p_i45534_2_, boolean p_i45534_3_, Predicate<String> p_i45534_4_) {
/* 364 */       super(p_i45534_1_, p_i45534_2_, p_i45534_3_);
/* 365 */       this.field_178951_a = (Predicate<String>)Objects.firstNonNull(p_i45534_4_, Predicates.alwaysTrue());
/*     */     }
/*     */ 
/*     */     
/*     */     public Predicate<String> func_178950_a() {
/* 370 */       return this.field_178951_a;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiButtonEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     private final boolean field_178941_a;
/*     */     
/*     */     public GuiButtonEntry(int p_i45535_1_, String p_i45535_2_, boolean p_i45535_3_, boolean p_i45535_4_) {
/* 380 */       super(p_i45535_1_, p_i45535_2_, p_i45535_3_);
/* 381 */       this.field_178941_a = p_i45535_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_178940_a() {
/* 386 */       return this.field_178941_a;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiEntry
/*     */     implements GuiListExtended.IGuiListEntry {
/* 392 */     private final Minecraft field_178031_a = Minecraft.getMinecraft();
/*     */     
/*     */     final Gui field_178029_b;
/*     */     final Gui field_178030_c;
/*     */     Gui field_178028_d;
/*     */     
/*     */     public GuiEntry(Gui p_i45533_1_, Gui p_i45533_2_) {
/* 399 */       this.field_178029_b = p_i45533_1_;
/* 400 */       this.field_178030_c = p_i45533_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Gui func_178022_a() {
/* 405 */       return this.field_178029_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public Gui func_178021_b() {
/* 410 */       return this.field_178030_c;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/* 415 */       func_178017_a(this.field_178029_b, y, mouseX, mouseY, false);
/* 416 */       func_178017_a(this.field_178030_c, y, mouseX, mouseY, false);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178017_a(Gui p_178017_1_, int p_178017_2_, int p_178017_3_, int p_178017_4_, boolean p_178017_5_) {
/* 421 */       if (p_178017_1_ != null)
/*     */       {
/* 423 */         if (p_178017_1_ instanceof GuiButton) {
/*     */           
/* 425 */           func_178024_a((GuiButton)p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
/*     */         }
/* 427 */         else if (p_178017_1_ instanceof GuiTextField) {
/*     */           
/* 429 */           func_178027_a((GuiTextField)p_178017_1_, p_178017_2_, p_178017_5_);
/*     */         }
/* 431 */         else if (p_178017_1_ instanceof GuiLabel) {
/*     */           
/* 433 */           func_178025_a((GuiLabel)p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178024_a(GuiButton p_178024_1_, int p_178024_2_, int p_178024_3_, int p_178024_4_, boolean p_178024_5_) {
/* 440 */       p_178024_1_.yPosition = p_178024_2_;
/*     */       
/* 442 */       if (!p_178024_5_)
/*     */       {
/* 444 */         p_178024_1_.drawButton(this.field_178031_a, p_178024_3_, p_178024_4_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178027_a(GuiTextField p_178027_1_, int p_178027_2_, boolean p_178027_3_) {
/* 450 */       p_178027_1_.yPosition = p_178027_2_;
/*     */       
/* 452 */       if (!p_178027_3_)
/*     */       {
/* 454 */         p_178027_1_.drawTextBox();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178025_a(GuiLabel p_178025_1_, int p_178025_2_, int p_178025_3_, int p_178025_4_, boolean p_178025_5_) {
/* 460 */       p_178025_1_.field_146174_h = p_178025_2_;
/*     */       
/* 462 */       if (!p_178025_5_)
/*     */       {
/* 464 */         p_178025_1_.drawLabel(this.field_178031_a, p_178025_3_, p_178025_4_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
/* 470 */       func_178017_a(this.field_178029_b, p_178011_3_, 0, 0, true);
/* 471 */       func_178017_a(this.field_178030_c, p_178011_3_, 0, 0, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 476 */       boolean flag = func_178026_a(this.field_178029_b, p_148278_2_, p_148278_3_, p_148278_4_);
/* 477 */       boolean flag1 = func_178026_a(this.field_178030_c, p_148278_2_, p_148278_3_, p_148278_4_);
/* 478 */       return (flag || flag1);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_178026_a(Gui p_178026_1_, int p_178026_2_, int p_178026_3_, int p_178026_4_) {
/* 483 */       if (p_178026_1_ == null)
/*     */       {
/* 485 */         return false;
/*     */       }
/* 487 */       if (p_178026_1_ instanceof GuiButton)
/*     */       {
/* 489 */         return func_178023_a((GuiButton)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
/*     */       }
/*     */ 
/*     */       
/* 493 */       if (p_178026_1_ instanceof GuiTextField)
/*     */       {
/* 495 */         func_178018_a((GuiTextField)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
/*     */       }
/*     */       
/* 498 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean func_178023_a(GuiButton p_178023_1_, int p_178023_2_, int p_178023_3_, int p_178023_4_) {
/* 504 */       boolean flag = p_178023_1_.mousePressed(this.field_178031_a, p_178023_2_, p_178023_3_);
/*     */       
/* 506 */       if (flag)
/*     */       {
/* 508 */         this.field_178028_d = p_178023_1_;
/*     */       }
/*     */       
/* 511 */       return flag;
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178018_a(GuiTextField p_178018_1_, int p_178018_2_, int p_178018_3_, int p_178018_4_) {
/* 516 */       p_178018_1_.mouseClicked(p_178018_2_, p_178018_3_, p_178018_4_);
/*     */       
/* 518 */       if (p_178018_1_.isFocused())
/*     */       {
/* 520 */         this.field_178028_d = p_178018_1_;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 526 */       func_178016_b(this.field_178029_b, x, y, mouseEvent);
/* 527 */       func_178016_b(this.field_178030_c, x, y, mouseEvent);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178016_b(Gui p_178016_1_, int p_178016_2_, int p_178016_3_, int p_178016_4_) {
/* 532 */       if (p_178016_1_ != null)
/*     */       {
/* 534 */         if (p_178016_1_ instanceof GuiButton)
/*     */         {
/* 536 */           func_178019_b((GuiButton)p_178016_1_, p_178016_2_, p_178016_3_, p_178016_4_);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_178019_b(GuiButton p_178019_1_, int p_178019_2_, int p_178019_3_, int p_178019_4_) {
/* 543 */       p_178019_1_.mouseReleased(p_178019_2_, p_178019_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GuiLabelEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     public GuiLabelEntry(int p_i45532_1_, String p_i45532_2_, boolean p_i45532_3_) {
/* 551 */       super(p_i45532_1_, p_i45532_2_, p_i45532_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GuiListEntry
/*     */   {
/*     */     private final int field_178939_a;
/*     */     private final String field_178937_b;
/*     */     private final boolean field_178938_c;
/*     */     
/*     */     public GuiListEntry(int p_i45531_1_, String p_i45531_2_, boolean p_i45531_3_) {
/* 563 */       this.field_178939_a = p_i45531_1_;
/* 564 */       this.field_178937_b = p_i45531_2_;
/* 565 */       this.field_178938_c = p_i45531_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_178935_b() {
/* 570 */       return this.field_178939_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public String func_178936_c() {
/* 575 */       return this.field_178937_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_178934_d() {
/* 580 */       return this.field_178938_c;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface GuiResponder
/*     */   {
/*     */     void func_175321_a(int param1Int, boolean param1Boolean);
/*     */     
/*     */     void onTick(int param1Int, float param1Float);
/*     */     
/*     */     void func_175319_a(int param1Int, String param1String);
/*     */   }
/*     */   
/*     */   public static class GuiSlideEntry
/*     */     extends GuiListEntry
/*     */   {
/*     */     private final GuiSlider.FormatHelper field_178949_a;
/*     */     private final float field_178947_b;
/*     */     private final float field_178948_c;
/*     */     private final float field_178946_d;
/*     */     
/*     */     public GuiSlideEntry(int p_i45530_1_, String p_i45530_2_, boolean p_i45530_3_, GuiSlider.FormatHelper p_i45530_4_, float p_i45530_5_, float p_i45530_6_, float p_i45530_7_) {
/* 602 */       super(p_i45530_1_, p_i45530_2_, p_i45530_3_);
/* 603 */       this.field_178949_a = p_i45530_4_;
/* 604 */       this.field_178947_b = p_i45530_5_;
/* 605 */       this.field_178948_c = p_i45530_6_;
/* 606 */       this.field_178946_d = p_i45530_7_;
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiSlider.FormatHelper func_178945_a() {
/* 611 */       return this.field_178949_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public float func_178943_e() {
/* 616 */       return this.field_178947_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public float func_178944_f() {
/* 621 */       return this.field_178948_c;
/*     */     }
/*     */ 
/*     */     
/*     */     public float func_178942_g() {
/* 626 */       return this.field_178946_d;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiPageButtonList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */