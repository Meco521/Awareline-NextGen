/*     */ package net.minecraft.client.gui.achievement;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.IProgressMeter;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatCrafting;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiStats extends GuiScreen implements IProgressMeter {
/*  34 */   protected String screenTitle = "Select world";
/*     */   
/*     */   protected GuiScreen parentScreen;
/*     */   
/*     */   private StatsGeneral generalStats;
/*     */   private StatsItem itemStats;
/*     */   private StatsBlock blockStats;
/*     */   private StatsMobsList mobStats;
/*     */   StatFileWriter field_146546_t;
/*     */   private GuiSlot displaySlot;
/*     */   private boolean doesGuiPauseGame = true;
/*     */   
/*     */   public GuiStats(GuiScreen p_i1071_1_, StatFileWriter p_i1071_2_) {
/*  47 */     this.parentScreen = p_i1071_1_;
/*  48 */     this.field_146546_t = p_i1071_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  57 */     this.screenTitle = I18n.format("gui.stats", new Object[0]);
/*  58 */     this.doesGuiPauseGame = true;
/*  59 */     this.mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  67 */     super.handleMouseInput();
/*     */     
/*  69 */     if (this.displaySlot != null)
/*     */     {
/*  71 */       this.displaySlot.handleMouseInput();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175366_f() {
/*  77 */     this.generalStats = new StatsGeneral(this.mc);
/*  78 */     this.generalStats.registerScrollButtons(1, 1);
/*  79 */     this.itemStats = new StatsItem(this.mc);
/*  80 */     this.itemStats.registerScrollButtons(1, 1);
/*  81 */     this.blockStats = new StatsBlock(this.mc);
/*  82 */     this.blockStats.registerScrollButtons(1, 1);
/*  83 */     this.mobStats = new StatsMobsList(this.mc);
/*  84 */     this.mobStats.registerScrollButtons(1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void createButtons() {
/*  89 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
/*  90 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 160, this.height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
/*     */     GuiButton guibutton;
/*  92 */     this.buttonList.add(guibutton = new GuiButton(2, this.width / 2 - 80, this.height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
/*     */     GuiButton guibutton1;
/*  94 */     this.buttonList.add(guibutton1 = new GuiButton(3, this.width / 2, this.height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
/*     */     GuiButton guibutton2;
/*  96 */     this.buttonList.add(guibutton2 = new GuiButton(4, this.width / 2 + 80, this.height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
/*     */     
/*  98 */     if (this.blockStats.getSize() == 0)
/*     */     {
/* 100 */       guibutton.enabled = false;
/*     */     }
/*     */     
/* 103 */     if (this.itemStats.getSize() == 0)
/*     */     {
/* 105 */       guibutton1.enabled = false;
/*     */     }
/*     */     
/* 108 */     if (this.mobStats.getSize() == 0)
/*     */     {
/* 110 */       guibutton2.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 118 */     if (button.enabled)
/*     */     {
/* 120 */       if (button.id == 0) {
/*     */         
/* 122 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 124 */       else if (button.id == 1) {
/*     */         
/* 126 */         this.displaySlot = this.generalStats;
/*     */       }
/* 128 */       else if (button.id == 3) {
/*     */         
/* 130 */         this.displaySlot = this.itemStats;
/*     */       }
/* 132 */       else if (button.id == 2) {
/*     */         
/* 134 */         this.displaySlot = this.blockStats;
/*     */       }
/* 136 */       else if (button.id == 4) {
/*     */         
/* 138 */         this.displaySlot = this.mobStats;
/*     */       }
/*     */       else {
/*     */         
/* 142 */         this.displaySlot.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 152 */     if (this.doesGuiPauseGame) {
/*     */       
/* 154 */       drawDefaultBackground();
/* 155 */       drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
/* 156 */       drawCenteredString(this.fontRendererObj, lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length)], this.width / 2, this.height / 2 + (this.fontRendererObj.FONT_HEIGHT << 1), 16777215);
/*     */     }
/*     */     else {
/*     */       
/* 160 */       this.displaySlot.drawScreen(mouseX, mouseY, partialTicks);
/* 161 */       drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
/* 162 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doneLoading() {
/* 168 */     if (this.doesGuiPauseGame) {
/*     */       
/* 170 */       func_175366_f();
/* 171 */       createButtons();
/* 172 */       this.displaySlot = this.generalStats;
/* 173 */       this.doesGuiPauseGame = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 182 */     return !this.doesGuiPauseGame;
/*     */   }
/*     */ 
/*     */   
/*     */   void drawStatsScreen(int p_146521_1_, int p_146521_2_, Item p_146521_3_) {
/* 187 */     drawButtonBackground(p_146521_1_ + 1, p_146521_2_ + 1);
/* 188 */     GlStateManager.enableRescaleNormal();
/* 189 */     RenderHelper.enableGUIStandardItemLighting();
/* 190 */     this.itemRender.renderItemIntoGUI(new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2);
/* 191 */     RenderHelper.disableStandardItemLighting();
/* 192 */     GlStateManager.disableRescaleNormal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawButtonBackground(int p_146531_1_, int p_146531_2_) {
/* 200 */     drawSprite(p_146531_1_, p_146531_2_, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void drawSprite(int p_146527_1_, int p_146527_2_, int p_146527_3_, int p_146527_4_) {
/* 208 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 209 */     this.mc.getTextureManager().bindTexture(statIcons);
/* 210 */     float f = 0.0078125F;
/* 211 */     float f1 = 0.0078125F;
/* 212 */     int i = 18;
/* 213 */     int j = 18;
/* 214 */     Tessellator tessellator = Tessellator.getInstance();
/* 215 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 216 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 217 */     worldrenderer.pos(p_146527_1_, (p_146527_2_ + 18), this.zLevel).tex((p_146527_3_ * 0.0078125F), ((p_146527_4_ + 18) * 0.0078125F)).endVertex();
/* 218 */     worldrenderer.pos((p_146527_1_ + 18), (p_146527_2_ + 18), this.zLevel).tex(((p_146527_3_ + 18) * 0.0078125F), ((p_146527_4_ + 18) * 0.0078125F)).endVertex();
/* 219 */     worldrenderer.pos((p_146527_1_ + 18), p_146527_2_, this.zLevel).tex(((p_146527_3_ + 18) * 0.0078125F), (p_146527_4_ * 0.0078125F)).endVertex();
/* 220 */     worldrenderer.pos(p_146527_1_, p_146527_2_, this.zLevel).tex((p_146527_3_ * 0.0078125F), (p_146527_4_ * 0.0078125F)).endVertex();
/* 221 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   abstract class Stats
/*     */     extends GuiSlot {
/* 226 */     protected int field_148218_l = -1;
/*     */     protected List<StatCrafting> statsHolder;
/*     */     protected Comparator<StatCrafting> statSorter;
/* 229 */     protected int field_148217_o = -1;
/*     */     
/*     */     protected int field_148215_p;
/*     */     
/*     */     protected Stats(Minecraft mcIn) {
/* 234 */       super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 20);
/* 235 */       setShowSelectionBox(false);
/* 236 */       setHasListHeader(true, 20);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 245 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 250 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
/* 255 */       if (!Mouse.isButtonDown(0))
/*     */       {
/* 257 */         this.field_148218_l = -1;
/*     */       }
/*     */       
/* 260 */       if (this.field_148218_l == 0) {
/*     */         
/* 262 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 266 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
/*     */       } 
/*     */       
/* 269 */       if (this.field_148218_l == 1) {
/*     */         
/* 271 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 275 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
/*     */       } 
/*     */       
/* 278 */       if (this.field_148218_l == 2) {
/*     */         
/* 280 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
/*     */       }
/*     */       else {
/*     */         
/* 284 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
/*     */       } 
/*     */       
/* 287 */       if (this.field_148217_o != -1) {
/*     */         
/* 289 */         int i = 79;
/* 290 */         int j = 18;
/*     */         
/* 292 */         if (this.field_148217_o == 1) {
/*     */           
/* 294 */           i = 129;
/*     */         }
/* 296 */         else if (this.field_148217_o == 2) {
/*     */           
/* 298 */           i = 179;
/*     */         } 
/*     */         
/* 301 */         if (this.field_148215_p == 1)
/*     */         {
/* 303 */           j = 36;
/*     */         }
/*     */         
/* 306 */         GuiStats.this.drawSprite(p_148129_1_ + i, p_148129_2_ + 1, j, 0);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_148132_a(int p_148132_1_, int p_148132_2_) {
/* 312 */       this.field_148218_l = -1;
/*     */       
/* 314 */       if (p_148132_1_ >= 79 && p_148132_1_ < 115) {
/*     */         
/* 316 */         this.field_148218_l = 0;
/*     */       }
/* 318 */       else if (p_148132_1_ >= 129 && p_148132_1_ < 165) {
/*     */         
/* 320 */         this.field_148218_l = 1;
/*     */       }
/* 322 */       else if (p_148132_1_ >= 179 && p_148132_1_ < 215) {
/*     */         
/* 324 */         this.field_148218_l = 2;
/*     */       } 
/*     */       
/* 327 */       if (this.field_148218_l >= 0) {
/*     */         
/* 329 */         func_148212_h(this.field_148218_l);
/* 330 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected final int getSize() {
/* 336 */       return this.statsHolder.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected final StatCrafting func_148211_c(int p_148211_1_) {
/* 341 */       return this.statsHolder.get(p_148211_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract String func_148210_b(int param1Int);
/*     */     
/*     */     protected void func_148209_a(StatBase p_148209_1_, int p_148209_2_, int p_148209_3_, boolean p_148209_4_) {
/* 348 */       if (p_148209_1_ != null) {
/*     */         
/* 350 */         String s = p_148209_1_.format(GuiStats.this.field_146546_t.readStat(p_148209_1_));
/* 351 */         GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(s), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
/*     */       }
/*     */       else {
/*     */         
/* 355 */         String s1 = "-";
/* 356 */         GuiStats.this.drawString(GuiStats.this.fontRendererObj, s1, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(s1), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_148142_b(int p_148142_1_, int p_148142_2_) {
/* 362 */       if (p_148142_2_ >= this.top && p_148142_2_ <= this.bottom) {
/*     */         
/* 364 */         int i = getSlotIndexFromScreenCoords(p_148142_1_, p_148142_2_);
/* 365 */         int j = this.width / 2 - 92 - 16;
/*     */         
/* 367 */         if (i >= 0) {
/*     */           
/* 369 */           if (p_148142_1_ < j + 40 || p_148142_1_ > j + 40 + 20) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 374 */           StatCrafting statcrafting = func_148211_c(i);
/* 375 */           func_148213_a(statcrafting, p_148142_1_, p_148142_2_);
/*     */         }
/*     */         else {
/*     */           
/* 379 */           String s = "";
/*     */           
/* 381 */           if (p_148142_1_ >= j + 115 - 18 && p_148142_1_ <= j + 115) {
/*     */             
/* 383 */             s = func_148210_b(0);
/*     */           }
/* 385 */           else if (p_148142_1_ >= j + 165 - 18 && p_148142_1_ <= j + 165) {
/*     */             
/* 387 */             s = func_148210_b(1);
/*     */           }
/*     */           else {
/*     */             
/* 391 */             if (p_148142_1_ < j + 215 - 18 || p_148142_1_ > j + 215) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/* 396 */             s = func_148210_b(2);
/*     */           } 
/*     */           
/* 399 */           s = I18n.format(s, new Object[0]).trim();
/*     */           
/* 401 */           if (!s.isEmpty()) {
/*     */             
/* 403 */             int k = p_148142_1_ + 12;
/* 404 */             int l = p_148142_2_ - 12;
/* 405 */             int i1 = GuiStats.this.fontRendererObj.getStringWidth(s);
/* 406 */             GuiStats.this.drawGradientRect(k - 3, l - 3, k + i1 + 3, l + 8 + 3, -1073741824, -1073741824);
/* 407 */             GuiStats.this.fontRendererObj.drawStringWithShadow(s, k, l, -1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_148213_a(StatCrafting p_148213_1_, int p_148213_2_, int p_148213_3_) {
/* 415 */       if (p_148213_1_ != null) {
/*     */         
/* 417 */         Item item = p_148213_1_.func_150959_a();
/* 418 */         ItemStack itemstack = new ItemStack(item);
/* 419 */         String s = itemstack.getUnlocalizedName();
/* 420 */         String s1 = I18n.format(s + ".name", new Object[0]).trim();
/*     */         
/* 422 */         if (!s1.isEmpty()) {
/*     */           
/* 424 */           int i = p_148213_2_ + 12;
/* 425 */           int j = p_148213_3_ - 12;
/* 426 */           int k = GuiStats.this.fontRendererObj.getStringWidth(s1);
/* 427 */           GuiStats.this.drawGradientRect(i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
/* 428 */           GuiStats.this.fontRendererObj.drawStringWithShadow(s1, i, j, -1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_148212_h(int p_148212_1_) {
/* 435 */       if (p_148212_1_ != this.field_148217_o) {
/*     */         
/* 437 */         this.field_148217_o = p_148212_1_;
/* 438 */         this.field_148215_p = -1;
/*     */       }
/* 440 */       else if (this.field_148215_p == -1) {
/*     */         
/* 442 */         this.field_148215_p = 1;
/*     */       }
/*     */       else {
/*     */         
/* 446 */         this.field_148217_o = -1;
/* 447 */         this.field_148215_p = 0;
/*     */       } 
/*     */       
/* 450 */       this.statsHolder.sort(this.statSorter);
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsBlock
/*     */     extends Stats
/*     */   {
/*     */     public StatsBlock(Minecraft mcIn) {
/* 458 */       super(mcIn);
/* 459 */       this.statsHolder = Lists.newArrayList();
/*     */       
/* 461 */       for (StatCrafting statcrafting : StatList.objectMineStats) {
/*     */         
/* 463 */         boolean flag = false;
/* 464 */         int i = Item.getIdFromItem(statcrafting.func_150959_a());
/*     */         
/* 466 */         if (GuiStats.this.field_146546_t.readStat((StatBase)statcrafting) > 0) {
/*     */           
/* 468 */           flag = true;
/*     */         }
/* 470 */         else if (StatList.objectUseStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectUseStats[i]) > 0) {
/*     */           
/* 472 */           flag = true;
/*     */         }
/* 474 */         else if (StatList.objectCraftStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectCraftStats[i]) > 0) {
/*     */           
/* 476 */           flag = true;
/*     */         } 
/*     */         
/* 479 */         if (flag)
/*     */         {
/* 481 */           this.statsHolder.add(statcrafting);
/*     */         }
/*     */       } 
/*     */       
/* 485 */       this.statSorter = new Comparator<StatCrafting>()
/*     */         {
/*     */           public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_)
/*     */           {
/* 489 */             int j = Item.getIdFromItem(p_compare_1_.func_150959_a());
/* 490 */             int k = Item.getIdFromItem(p_compare_2_.func_150959_a());
/* 491 */             StatBase statbase = null;
/* 492 */             StatBase statbase1 = null;
/*     */             
/* 494 */             if (GuiStats.StatsBlock.this.field_148217_o == 2) {
/*     */               
/* 496 */               statbase = StatList.mineBlockStatArray[j];
/* 497 */               statbase1 = StatList.mineBlockStatArray[k];
/*     */             }
/* 499 */             else if (GuiStats.StatsBlock.this.field_148217_o == 0) {
/*     */               
/* 501 */               statbase = StatList.objectCraftStats[j];
/* 502 */               statbase1 = StatList.objectCraftStats[k];
/*     */             }
/* 504 */             else if (GuiStats.StatsBlock.this.field_148217_o == 1) {
/*     */               
/* 506 */               statbase = StatList.objectUseStats[j];
/* 507 */               statbase1 = StatList.objectUseStats[k];
/*     */             } 
/*     */             
/* 510 */             if (statbase != null || statbase1 != null) {
/*     */               
/* 512 */               if (statbase == null)
/*     */               {
/* 514 */                 return 1;
/*     */               }
/*     */               
/* 517 */               if (statbase1 == null)
/*     */               {
/* 519 */                 return -1;
/*     */               }
/*     */               
/* 522 */               int l = GuiStats.this.field_146546_t.readStat(statbase);
/* 523 */               int i1 = GuiStats.this.field_146546_t.readStat(statbase1);
/*     */               
/* 525 */               if (l != i1)
/*     */               {
/* 527 */                 return (l - i1) * GuiStats.StatsBlock.this.field_148215_p;
/*     */               }
/*     */             } 
/*     */             
/* 531 */             return j - k;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
/* 538 */       super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
/*     */       
/* 540 */       if (this.field_148218_l == 0) {
/*     */         
/* 542 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
/*     */       }
/*     */       else {
/*     */         
/* 546 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
/*     */       } 
/*     */       
/* 549 */       if (this.field_148218_l == 1) {
/*     */         
/* 551 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
/*     */       }
/*     */       else {
/*     */         
/* 555 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
/*     */       } 
/*     */       
/* 558 */       if (this.field_148218_l == 2) {
/*     */         
/* 560 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
/*     */       }
/*     */       else {
/*     */         
/* 564 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 570 */       StatCrafting statcrafting = func_148211_c(entryID);
/* 571 */       Item item = statcrafting.func_150959_a();
/* 572 */       GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, item);
/* 573 */       int i = Item.getIdFromItem(item);
/* 574 */       func_148209_a(StatList.objectCraftStats[i], p_180791_2_ + 115, p_180791_3_, (entryID % 2 == 0));
/* 575 */       func_148209_a(StatList.objectUseStats[i], p_180791_2_ + 165, p_180791_3_, (entryID % 2 == 0));
/* 576 */       func_148209_a((StatBase)statcrafting, p_180791_2_ + 215, p_180791_3_, (entryID % 2 == 0));
/*     */     }
/*     */ 
/*     */     
/*     */     protected String func_148210_b(int p_148210_1_) {
/* 581 */       return (p_148210_1_ == 0) ? "stat.crafted" : ((p_148210_1_ == 1) ? "stat.used" : "stat.mined");
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsGeneral
/*     */     extends GuiSlot
/*     */   {
/*     */     public StatsGeneral(Minecraft mcIn) {
/* 589 */       super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 10);
/* 590 */       setShowSelectionBox(false);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 595 */       return StatList.generalStats.size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 604 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 609 */       return getSize() * 10;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 614 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 619 */       StatBase statbase = StatList.generalStats.get(entryID);
/* 620 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, statbase.getStatName().getUnformattedText(), p_180791_2_ + 2, p_180791_3_ + 1, (entryID % 2 == 0) ? 16777215 : 9474192);
/* 621 */       String s = statbase.format(GuiStats.this.field_146546_t.readStat(statbase));
/* 622 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_180791_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(s), p_180791_3_ + 1, (entryID % 2 == 0) ? 16777215 : 9474192);
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsItem
/*     */     extends Stats
/*     */   {
/*     */     public StatsItem(Minecraft mcIn) {
/* 630 */       super(mcIn);
/* 631 */       this.statsHolder = Lists.newArrayList();
/*     */       
/* 633 */       for (StatCrafting statcrafting : StatList.itemStats) {
/*     */         
/* 635 */         boolean flag = false;
/* 636 */         int i = Item.getIdFromItem(statcrafting.func_150959_a());
/*     */         
/* 638 */         if (GuiStats.this.field_146546_t.readStat((StatBase)statcrafting) > 0) {
/*     */           
/* 640 */           flag = true;
/*     */         }
/* 642 */         else if (StatList.objectBreakStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectBreakStats[i]) > 0) {
/*     */           
/* 644 */           flag = true;
/*     */         }
/* 646 */         else if (StatList.objectCraftStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectCraftStats[i]) > 0) {
/*     */           
/* 648 */           flag = true;
/*     */         } 
/*     */         
/* 651 */         if (flag)
/*     */         {
/* 653 */           this.statsHolder.add(statcrafting);
/*     */         }
/*     */       } 
/*     */       
/* 657 */       this.statSorter = new Comparator<StatCrafting>()
/*     */         {
/*     */           public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_)
/*     */           {
/* 661 */             int j = Item.getIdFromItem(p_compare_1_.func_150959_a());
/* 662 */             int k = Item.getIdFromItem(p_compare_2_.func_150959_a());
/* 663 */             StatBase statbase = null;
/* 664 */             StatBase statbase1 = null;
/*     */             
/* 666 */             if (GuiStats.StatsItem.this.field_148217_o == 0) {
/*     */               
/* 668 */               statbase = StatList.objectBreakStats[j];
/* 669 */               statbase1 = StatList.objectBreakStats[k];
/*     */             }
/* 671 */             else if (GuiStats.StatsItem.this.field_148217_o == 1) {
/*     */               
/* 673 */               statbase = StatList.objectCraftStats[j];
/* 674 */               statbase1 = StatList.objectCraftStats[k];
/*     */             }
/* 676 */             else if (GuiStats.StatsItem.this.field_148217_o == 2) {
/*     */               
/* 678 */               statbase = StatList.objectUseStats[j];
/* 679 */               statbase1 = StatList.objectUseStats[k];
/*     */             } 
/*     */             
/* 682 */             if (statbase != null || statbase1 != null) {
/*     */               
/* 684 */               if (statbase == null)
/*     */               {
/* 686 */                 return 1;
/*     */               }
/*     */               
/* 689 */               if (statbase1 == null)
/*     */               {
/* 691 */                 return -1;
/*     */               }
/*     */               
/* 694 */               int l = GuiStats.this.field_146546_t.readStat(statbase);
/* 695 */               int i1 = GuiStats.this.field_146546_t.readStat(statbase1);
/*     */               
/* 697 */               if (l != i1)
/*     */               {
/* 699 */                 return (l - i1) * GuiStats.StatsItem.this.field_148215_p;
/*     */               }
/*     */             } 
/*     */             
/* 703 */             return j - k;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
/* 710 */       super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
/*     */       
/* 712 */       if (this.field_148218_l == 0) {
/*     */         
/* 714 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
/*     */       }
/*     */       else {
/*     */         
/* 718 */         GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
/*     */       } 
/*     */       
/* 721 */       if (this.field_148218_l == 1) {
/*     */         
/* 723 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
/*     */       }
/*     */       else {
/*     */         
/* 727 */         GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
/*     */       } 
/*     */       
/* 730 */       if (this.field_148218_l == 2) {
/*     */         
/* 732 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
/*     */       }
/*     */       else {
/*     */         
/* 736 */         GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 742 */       StatCrafting statcrafting = func_148211_c(entryID);
/* 743 */       Item item = statcrafting.func_150959_a();
/* 744 */       GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, item);
/* 745 */       int i = Item.getIdFromItem(item);
/* 746 */       func_148209_a(StatList.objectBreakStats[i], p_180791_2_ + 115, p_180791_3_, (entryID % 2 == 0));
/* 747 */       func_148209_a(StatList.objectCraftStats[i], p_180791_2_ + 165, p_180791_3_, (entryID % 2 == 0));
/* 748 */       func_148209_a((StatBase)statcrafting, p_180791_2_ + 215, p_180791_3_, (entryID % 2 == 0));
/*     */     }
/*     */ 
/*     */     
/*     */     protected String func_148210_b(int p_148210_1_) {
/* 753 */       return (p_148210_1_ == 1) ? "stat.crafted" : ((p_148210_1_ == 2) ? "stat.used" : "stat.depleted");
/*     */     }
/*     */   }
/*     */   
/*     */   class StatsMobsList
/*     */     extends GuiSlot {
/* 759 */     private final List<EntityList.EntityEggInfo> field_148222_l = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public StatsMobsList(Minecraft mcIn) {
/* 763 */       super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, GuiStats.this.fontRendererObj.FONT_HEIGHT << 2);
/* 764 */       setShowSelectionBox(false);
/*     */       
/* 766 */       for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values()) {
/*     */         
/* 768 */         if (GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151512_d) > 0 || GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151513_e) > 0)
/*     */         {
/* 770 */           this.field_148222_l.add(entitylist$entityegginfo);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 777 */       return this.field_148222_l.size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 786 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 791 */       return getSize() * GuiStats.this.fontRendererObj.FONT_HEIGHT * 4;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 796 */       GuiStats.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 801 */       EntityList.EntityEggInfo entitylist$entityegginfo = this.field_148222_l.get(entryID);
/* 802 */       String s = I18n.format("entity." + EntityList.getStringFromID(entitylist$entityegginfo.spawnedID) + ".name", new Object[0]);
/* 803 */       int i = GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151512_d);
/* 804 */       int j = GuiStats.this.field_146546_t.readStat(entitylist$entityegginfo.field_151513_e);
/* 805 */       String s1 = I18n.format("stat.entityKills", new Object[] { Integer.valueOf(i), s });
/* 806 */       String s2 = I18n.format("stat.entityKilledBy", new Object[] { s, Integer.valueOf(j) });
/*     */       
/* 808 */       if (i == 0)
/*     */       {
/* 810 */         s1 = I18n.format("stat.entityKills.none", new Object[] { s });
/*     */       }
/*     */       
/* 813 */       if (j == 0)
/*     */       {
/* 815 */         s2 = I18n.format("stat.entityKilledBy.none", new Object[] { s });
/*     */       }
/*     */       
/* 818 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 16777215);
/* 819 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT, (i == 0) ? 6316128 : 9474192);
/* 820 */       GuiStats.this.drawString(GuiStats.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 1 + (GuiStats.this.fontRendererObj.FONT_HEIGHT << 1), (j == 0) ? 6316128 : 9474192);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\achievement\GuiStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */