/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ChestESP;
/*     */ import java.awt.Color;
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.model.ModelLargeChest;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntityChestRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntityChest> {
/*  20 */   private static final ResourceLocation textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
/*  21 */   private static final ResourceLocation textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
/*  22 */   private static final ResourceLocation textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
/*  23 */   private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
/*  24 */   private static final ResourceLocation textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
/*  25 */   private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
/*  26 */   private final ModelChest simpleChest = new ModelChest();
/*  27 */   private final ModelChest largeChest = (ModelChest)new ModelLargeChest();
/*     */   private boolean isChristmas;
/*     */   
/*     */   public TileEntityChestRenderer() {
/*  31 */     Calendar calendar = Calendar.getInstance();
/*     */     
/*  33 */     if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
/*  34 */       this.isChristmas = true; 
/*     */   }
/*     */   
/*     */   public void renderTileEntityAt(TileEntityChest te, double x, double y, double z, float partialTicks, int destroyStage) {
/*     */     int i;
/*  39 */     GlStateManager.enableDepth();
/*  40 */     GlStateManager.depthFunc(515);
/*  41 */     GlStateManager.depthMask(true);
/*     */ 
/*     */     
/*  44 */     if (!te.hasWorldObj()) {
/*  45 */       i = 0;
/*     */     } else {
/*  47 */       Block block = te.getBlockType();
/*  48 */       i = te.getBlockMetadata();
/*     */       
/*  50 */       if (block instanceof BlockChest && i == 0) {
/*  51 */         ((BlockChest)block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
/*  52 */         i = te.getBlockMetadata();
/*     */       } 
/*     */       
/*  55 */       te.checkForAdjacentChests();
/*     */     } 
/*     */     
/*  58 */     if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null) {
/*     */       ModelChest modelchest;
/*     */       
/*  61 */       if (te.adjacentChestXPos == null && te.adjacentChestZPos == null) {
/*  62 */         modelchest = this.simpleChest;
/*     */         
/*  64 */         if (destroyStage >= 0) {
/*  65 */           bindTexture(DESTROY_STAGES[destroyStage]);
/*  66 */           GlStateManager.matrixMode(5890);
/*  67 */           GlStateManager.pushMatrix();
/*  68 */           GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  69 */           GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  70 */           GlStateManager.matrixMode(5888);
/*  71 */         } else if (this.isChristmas) {
/*  72 */           bindTexture(textureChristmas);
/*  73 */         } else if (te.getChestType() == 1) {
/*  74 */           bindTexture(textureTrapped);
/*     */         } else {
/*  76 */           bindTexture(textureNormal);
/*     */         } 
/*     */       } else {
/*  79 */         modelchest = this.largeChest;
/*     */         
/*  81 */         if (destroyStage >= 0) {
/*  82 */           bindTexture(DESTROY_STAGES[destroyStage]);
/*  83 */           GlStateManager.matrixMode(5890);
/*  84 */           GlStateManager.pushMatrix();
/*  85 */           GlStateManager.scale(8.0F, 4.0F, 1.0F);
/*  86 */           GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  87 */           GlStateManager.matrixMode(5888);
/*  88 */         } else if (this.isChristmas) {
/*  89 */           bindTexture(textureChristmasDouble);
/*  90 */         } else if (te.getChestType() == 1) {
/*  91 */           bindTexture(textureTrappedDouble);
/*     */         } else {
/*  93 */           bindTexture(textureNormalDouble);
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       GlStateManager.pushMatrix();
/*  98 */       GlStateManager.enableRescaleNormal();
/*     */       
/* 100 */       if (destroyStage < 0) {
/* 101 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 104 */       GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
/* 105 */       GlStateManager.scale(1.0F, -1.0F, -1.0F);
/* 106 */       GlStateManager.translate(0.5F, 0.5F, 0.5F);
/* 107 */       int j = 0;
/*     */       
/* 109 */       if (i == 2) {
/* 110 */         j = 180;
/*     */       }
/*     */       
/* 113 */       if (i == 3) {
/* 114 */         j = 0;
/*     */       }
/*     */       
/* 117 */       if (i == 4) {
/* 118 */         j = 90;
/*     */       }
/*     */       
/* 121 */       if (i == 5) {
/* 122 */         j = -90;
/*     */       }
/*     */       
/* 125 */       if (i == 2 && te.adjacentChestXPos != null) {
/* 126 */         GlStateManager.translate(1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 129 */       if (i == 5 && te.adjacentChestZPos != null) {
/* 130 */         GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */       }
/*     */       
/* 133 */       GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
/* 134 */       GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/* 135 */       float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
/*     */       
/* 137 */       if (te.adjacentChestZNeg != null) {
/* 138 */         float f1 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;
/*     */         
/* 140 */         if (f1 > f) {
/* 141 */           f = f1;
/*     */         }
/*     */       } 
/*     */       
/* 145 */       if (te.adjacentChestXNeg != null) {
/* 146 */         float f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;
/*     */         
/* 148 */         if (f2 > f) {
/* 149 */           f = f2;
/*     */         }
/*     */       } 
/*     */       
/* 153 */       f = 1.0F - f;
/* 154 */       f = 1.0F - f * f * f;
/* 155 */       modelchest.chestLid.rotateAngleX = -(f * 3.1415927F / 2.0F);
/* 156 */       if (ChestESP.getInstance.isEnabled() && te.hasWorldObj()) {
/* 157 */         ChestESP chestESP = ChestESP.getInstance;
/* 158 */         if (chestESP.mode.is("Outline") || chestESP.mode.is("Combined")) {
/* 159 */           float[] hexColor = chestESP.getColorForTileEntity();
/* 160 */           int color = chestESP.toRGBAHex(hexColor[0] / 255.0F, hexColor[1] / 255.0F, hexColor[2] / 255.0F, 1.0F);
/* 161 */           modelchest.renderAll();
/* 162 */           chestESP.pre3D();
/* 163 */           modelchest.renderAll();
/* 164 */           chestESP.setupStencil();
/* 165 */           modelchest.renderAll();
/* 166 */           modelchest.renderAll();
/* 167 */           chestESP.setupStencil2();
/* 168 */           chestESP.renderOutline(color);
/* 169 */           modelchest.renderAll();
/* 170 */           chestESP.post3D();
/* 171 */         } else if (chestESP.mode.is("Chams")) {
/* 172 */           int sexyHidden = (new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue())).getRGB();
/* 173 */           GL11.glPushMatrix();
/* 174 */           GL11.glDisable(3553);
/* 175 */           GL11.glEnable(3042);
/* 176 */           GL11.glDisable(2896);
/* 177 */           GL11.glEnable(2960);
/* 178 */           GL11.glBlendFunc(770, 771);
/* 179 */           GlStateManager.disableDepth();
/* 180 */           float[] hexColor = chestESP.getColorForTileEntity();
/* 181 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/* 182 */           GL11.glColor4f((sexyHidden >> 16 & 0xFF) / 255.0F, (sexyHidden >> 8 & 0xFF) / 255.0F, (sexyHidden & 0xFF) / 255.0F, 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 187 */           modelchest.renderAll();
/* 188 */           GlStateManager.enableDepth();
/* 189 */           sexyHidden = (new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue())).getRGB();
/* 190 */           GL11.glColor4f((sexyHidden >> 16 & 0xFF) / 255.0F, (sexyHidden >> 8 & 0xFF) / 255.0F, (sexyHidden & 0xFF) / 255.0F, 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 195 */           modelchest.renderAll();
/*     */           
/* 197 */           GL11.glEnable(2896);
/* 198 */           GL11.glDisable(3042);
/* 199 */           GL11.glEnable(3553);
/* 200 */           GL11.glPopMatrix();
/* 201 */         } else if (chestESP.mode.is("Box")) {
/* 202 */           GlStateManager.disableDepth();
/* 203 */           modelchest.renderAll();
/* 204 */           GlStateManager.enableDepth();
/*     */         } else {
/* 206 */           modelchest.renderAll();
/*     */         } 
/*     */       } else {
/* 209 */         modelchest.renderAll();
/*     */       } 
/* 211 */       GlStateManager.disableRescaleNormal();
/* 212 */       GlStateManager.popMatrix();
/* 213 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 215 */       if (destroyStage >= 0) {
/* 216 */         GlStateManager.matrixMode(5890);
/* 217 */         GlStateManager.popMatrix();
/* 218 */         GlStateManager.matrixMode(5888);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */