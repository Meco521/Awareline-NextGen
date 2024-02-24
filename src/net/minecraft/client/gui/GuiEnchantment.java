/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.model.ModelBook;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnchantmentNameParts;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ public class GuiEnchantment
/*     */   extends GuiContainer {
/*  29 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final ModelBook MODEL_BOOK = new ModelBook();
/*     */ 
/*     */   
/*     */   private final InventoryPlayer playerInventory;
/*     */ 
/*     */   
/*  45 */   private final Random random = new Random();
/*     */   
/*     */   private final ContainerEnchantment container;
/*     */   public int field_147073_u;
/*     */   public float field_147071_v;
/*     */   public float field_147069_w;
/*     */   public float field_147082_x;
/*     */   public float field_147081_y;
/*     */   public float field_147080_z;
/*     */   public float field_147076_A;
/*     */   ItemStack field_147077_B;
/*     */   private final IWorldNameable field_175380_I;
/*     */   
/*     */   public GuiEnchantment(InventoryPlayer inventory, World worldIn, IWorldNameable p_i45502_3_) {
/*  59 */     super((Container)new ContainerEnchantment(inventory, worldIn));
/*  60 */     this.playerInventory = inventory;
/*  61 */     this.container = (ContainerEnchantment)this.inventorySlots;
/*  62 */     this.field_175380_I = p_i45502_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  70 */     this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12, 5, 4210752);
/*  71 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  79 */     super.updateScreen();
/*  80 */     func_147068_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  88 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  89 */     int i = (this.width - this.xSize) / 2;
/*  90 */     int j = (this.height - this.ySize) / 2;
/*     */     
/*  92 */     for (int k = 0; k < 3; k++) {
/*     */       
/*  94 */       int l = mouseX - i + 60;
/*  95 */       int i1 = mouseY - j + 14 + 19 * k;
/*     */       
/*  97 */       if (l >= 0 && i1 >= 0 && l < 108 && i1 < 19 && this.container.enchantItem((EntityPlayer)this.mc.thePlayer, k))
/*     */       {
/*  99 */         this.mc.playerController.sendEnchantPacket(this.container.windowId, k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 109 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 110 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 111 */     int i = (this.width - this.xSize) / 2;
/* 112 */     int j = (this.height - this.ySize) / 2;
/* 113 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 114 */     GlStateManager.pushMatrix();
/* 115 */     GlStateManager.matrixMode(5889);
/* 116 */     GlStateManager.pushMatrix();
/* 117 */     GlStateManager.loadIdentity();
/* 118 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 119 */     GlStateManager.viewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
/* 120 */     GlStateManager.translate(-0.34F, 0.23F, 0.0F);
/* 121 */     Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
/* 122 */     float f = 1.0F;
/* 123 */     GlStateManager.matrixMode(5888);
/* 124 */     GlStateManager.loadIdentity();
/* 125 */     RenderHelper.enableStandardItemLighting();
/* 126 */     GlStateManager.translate(0.0F, 3.3F, -16.0F);
/* 127 */     GlStateManager.scale(f, f, f);
/* 128 */     float f1 = 5.0F;
/* 129 */     GlStateManager.scale(f1, f1, f1);
/* 130 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 131 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
/* 132 */     GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 133 */     float f2 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * partialTicks;
/* 134 */     GlStateManager.translate((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
/* 135 */     GlStateManager.rotate(-(1.0F - f2) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 136 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 137 */     float f3 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.25F;
/* 138 */     float f4 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.75F;
/* 139 */     f3 = (f3 - MathHelper.truncateDoubleToInt(f3)) * 1.6F - 0.3F;
/* 140 */     f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
/*     */     
/* 142 */     if (f3 < 0.0F)
/*     */     {
/* 144 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 147 */     if (f4 < 0.0F)
/*     */     {
/* 149 */       f4 = 0.0F;
/*     */     }
/*     */     
/* 152 */     if (f3 > 1.0F)
/*     */     {
/* 154 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 157 */     if (f4 > 1.0F)
/*     */     {
/* 159 */       f4 = 1.0F;
/*     */     }
/*     */     
/* 162 */     GlStateManager.enableRescaleNormal();
/* 163 */     MODEL_BOOK.render((Entity)null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
/* 164 */     GlStateManager.disableRescaleNormal();
/* 165 */     RenderHelper.disableStandardItemLighting();
/* 166 */     GlStateManager.matrixMode(5889);
/* 167 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 168 */     GlStateManager.popMatrix();
/* 169 */     GlStateManager.matrixMode(5888);
/* 170 */     GlStateManager.popMatrix();
/* 171 */     RenderHelper.disableStandardItemLighting();
/* 172 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 173 */     EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
/* 174 */     int k = this.container.getLapisAmount();
/*     */     
/* 176 */     for (int l = 0; l < 3; l++) {
/*     */       
/* 178 */       int i1 = i + 60;
/* 179 */       int j1 = i1 + 20;
/* 180 */       int k1 = 86;
/* 181 */       String s = EnchantmentNameParts.getInstance().generateNewRandomName();
/* 182 */       this.zLevel = 0.0F;
/* 183 */       this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 184 */       int l1 = this.container.enchantLevels[l];
/* 185 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 187 */       if (l1 == 0) {
/*     */         
/* 189 */         drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/*     */       }
/*     */       else {
/*     */         
/* 193 */         String s1 = String.valueOf(l1);
/* 194 */         FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
/* 195 */         int i2 = 6839882;
/*     */         
/* 197 */         if ((k < l + 1 || this.mc.thePlayer.experienceLevel < l1) && !this.mc.thePlayer.capabilities.isCreativeMode) {
/*     */           
/* 199 */           drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/* 200 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 239, 16, 16);
/* 201 */           fontrenderer.drawSplitString(s, j1, j + 16 + 19 * l, k1, (i2 & 0xFEFEFE) >> 1);
/* 202 */           i2 = 4226832;
/*     */         }
/*     */         else {
/*     */           
/* 206 */           int j2 = mouseX - i + 60;
/* 207 */           int k2 = mouseY - j + 14 + 19 * l;
/*     */           
/* 209 */           if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
/*     */             
/* 211 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 204, 108, 19);
/* 212 */             i2 = 16777088;
/*     */           }
/*     */           else {
/*     */             
/* 216 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 166, 108, 19);
/*     */           } 
/*     */           
/* 219 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 223, 16, 16);
/* 220 */           fontrenderer.drawSplitString(s, j1, j + 16 + 19 * l, k1, i2);
/* 221 */           i2 = 8453920;
/*     */         } 
/*     */         
/* 224 */         fontrenderer = this.mc.fontRendererObj;
/* 225 */         fontrenderer.drawStringWithShadow(s1, (j1 + 86 - fontrenderer.getStringWidth(s1)), (j + 16 + 19 * l + 7), i2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 235 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 236 */     boolean flag = this.mc.thePlayer.capabilities.isCreativeMode;
/* 237 */     int i = this.container.getLapisAmount();
/*     */     
/* 239 */     for (int j = 0; j < 3; j++) {
/*     */       
/* 241 */       int k = this.container.enchantLevels[j];
/* 242 */       int l = this.container.enchantmentIds[j];
/* 243 */       int i1 = j + 1;
/*     */       
/* 245 */       if (isPointInRegion(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0 && l >= 0) {
/*     */         
/* 247 */         List<String> list = Lists.newArrayList();
/*     */         
/* 249 */         if (l >= 0 && Enchantment.getEnchantmentById(l & 0xFF) != null) {
/*     */           
/* 251 */           String s = Enchantment.getEnchantmentById(l & 0xFF).getTranslatedName((l & 0xFF00) >> 8);
/* 252 */           list.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", new Object[] { s }));
/*     */         } 
/*     */         
/* 255 */         if (!flag) {
/*     */           
/* 257 */           if (l >= 0)
/*     */           {
/* 259 */             list.add("");
/*     */           }
/*     */           
/* 262 */           if (this.mc.thePlayer.experienceLevel < k) {
/*     */             
/* 264 */             list.add(EnumChatFormatting.RED.toString() + "Level Requirement: " + this.container.enchantLevels[j]);
/*     */           }
/*     */           else {
/*     */             
/* 268 */             String s1 = "";
/*     */             
/* 270 */             if (i1 == 1) {
/*     */               
/* 272 */               s1 = I18n.format("container.enchant.lapis.one", new Object[0]);
/*     */             }
/*     */             else {
/*     */               
/* 276 */               s1 = I18n.format("container.enchant.lapis.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 279 */             if (i >= i1) {
/*     */               
/* 281 */               list.add(EnumChatFormatting.GRAY.toString() + s1);
/*     */             }
/*     */             else {
/*     */               
/* 285 */               list.add(EnumChatFormatting.RED.toString() + s1);
/*     */             } 
/*     */             
/* 288 */             if (i1 == 1) {
/*     */               
/* 290 */               s1 = I18n.format("container.enchant.level.one", new Object[0]);
/*     */             }
/*     */             else {
/*     */               
/* 294 */               s1 = I18n.format("container.enchant.level.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 297 */             list.add(EnumChatFormatting.GRAY.toString() + s1);
/*     */           } 
/*     */         } 
/*     */         
/* 301 */         drawHoveringText(list, mouseX, mouseY);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147068_g() {
/* 309 */     ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();
/*     */     
/* 311 */     if (!ItemStack.areItemStacksEqual(itemstack, this.field_147077_B)) {
/*     */       
/* 313 */       this.field_147077_B = itemstack;
/*     */ 
/*     */       
/*     */       do {
/* 317 */         this.field_147082_x += (this.random.nextInt(4) - this.random.nextInt(4));
/*     */       }
/* 319 */       while (this.field_147071_v <= this.field_147082_x + 1.0F && this.field_147071_v >= this.field_147082_x - 1.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     this.field_147073_u++;
/* 327 */     this.field_147069_w = this.field_147071_v;
/* 328 */     this.field_147076_A = this.field_147080_z;
/* 329 */     boolean flag = false;
/*     */     
/* 331 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 333 */       if (this.container.enchantLevels[i] != 0) {
/* 334 */         flag = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 339 */     if (flag) {
/*     */       
/* 341 */       this.field_147080_z += 0.2F;
/*     */     }
/*     */     else {
/*     */       
/* 345 */       this.field_147080_z -= 0.2F;
/*     */     } 
/*     */     
/* 348 */     this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0F, 1.0F);
/* 349 */     float f1 = (this.field_147082_x - this.field_147071_v) * 0.4F;
/* 350 */     float f = 0.2F;
/* 351 */     f1 = MathHelper.clamp_float(f1, -f, f);
/* 352 */     this.field_147081_y += (f1 - this.field_147081_y) * 0.9F;
/* 353 */     this.field_147071_v += this.field_147081_y;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */