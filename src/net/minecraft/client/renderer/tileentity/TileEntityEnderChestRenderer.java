/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.visual.ctype.ChestESP;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntityEnderChestRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntityEnderChest> {
/*  16 */   private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
/*  17 */   private final ModelChest field_147521_c = new ModelChest();
/*     */   
/*     */   public void renderTileEntityAt(TileEntityEnderChest te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  20 */     int i = 0;
/*     */     
/*  22 */     if (te.hasWorldObj()) {
/*  23 */       i = te.getBlockMetadata();
/*     */     }
/*     */     
/*  26 */     if (destroyStage >= 0) {
/*  27 */       bindTexture(DESTROY_STAGES[destroyStage]);
/*  28 */       GlStateManager.matrixMode(5890);
/*  29 */       GlStateManager.pushMatrix();
/*  30 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  31 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  32 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*  34 */       bindTexture(ENDER_CHEST_TEXTURE);
/*     */     } 
/*     */     
/*  37 */     GlStateManager.pushMatrix();
/*  38 */     GlStateManager.enableRescaleNormal();
/*  39 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  40 */     GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
/*  41 */     GlStateManager.scale(1.0F, -1.0F, -1.0F);
/*  42 */     GlStateManager.translate(0.5F, 0.5F, 0.5F);
/*  43 */     int j = 0;
/*     */     
/*  45 */     if (i == 2) {
/*  46 */       j = 180;
/*     */     }
/*     */     
/*  49 */     if (i == 3) {
/*  50 */       j = 0;
/*     */     }
/*     */     
/*  53 */     if (i == 4) {
/*  54 */       j = 90;
/*     */     }
/*     */     
/*  57 */     if (i == 5) {
/*  58 */       j = -90;
/*     */     }
/*     */     
/*  61 */     GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
/*  62 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  63 */     float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
/*  64 */     f = 1.0F - f;
/*  65 */     f = 1.0F - f * f * f;
/*  66 */     this.field_147521_c.chestLid.rotateAngleX = -(f * 3.1415927F / 2.0F);
/*  67 */     if (ChestESP.getInstance.isEnabled() && te
/*  68 */       .hasWorldObj()) {
/*  69 */       ChestESP chestESP = ChestESP.getInstance;
/*  70 */       if (chestESP.mode.is("Outline")) {
/*  71 */         float[] hexColor = chestESP.getColorForTileEntity();
/*  72 */         int color = chestESP.toRGBAHex(hexColor[0] / 255.0F, hexColor[1] / 255.0F, hexColor[2] / 255.0F, 1.0F);
/*  73 */         this.field_147521_c.renderAll();
/*  74 */         chestESP.pre3D();
/*  75 */         this.field_147521_c.renderAll();
/*  76 */         chestESP.setupStencilFirst();
/*  77 */         this.field_147521_c.renderAll();
/*  78 */         chestESP.setupStencilSecond();
/*  79 */         chestESP.renderOutline(color);
/*  80 */         this.field_147521_c.renderAll();
/*  81 */         chestESP.post3D();
/*  82 */       } else if (chestESP.mode.is("Chams")) {
/*  83 */         int sexyHidden = (new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue())).getRGB();
/*  84 */         GL11.glPushMatrix();
/*  85 */         GL11.glDisable(3553);
/*  86 */         GL11.glEnable(3042);
/*  87 */         GL11.glDisable(2896);
/*  88 */         GL11.glEnable(2960);
/*  89 */         GL11.glBlendFunc(770, 771);
/*  90 */         GlStateManager.disableDepth();
/*  91 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*  92 */         GL11.glColor4f((sexyHidden >> 16 & 0xFF) / 255.0F, (sexyHidden >> 8 & 0xFF) / 255.0F, (sexyHidden & 0xFF) / 255.0F, 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         this.field_147521_c.renderAll();
/*  98 */         GlStateManager.enableDepth();
/*  99 */         sexyHidden = (new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue())).getRGB();
/* 100 */         GL11.glColor4f((sexyHidden >> 16 & 0xFF) / 255.0F, (sexyHidden >> 8 & 0xFF) / 255.0F, (sexyHidden & 0xFF) / 255.0F, 255.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 105 */         this.field_147521_c.renderAll();
/*     */         
/* 107 */         GL11.glEnable(2896);
/* 108 */         GL11.glDisable(3042);
/* 109 */         GL11.glEnable(3553);
/* 110 */         GL11.glPopMatrix();
/*     */       } else {
/* 112 */         this.field_147521_c.renderAll();
/*     */       } 
/*     */     } else {
/* 115 */       this.field_147521_c.renderAll();
/*     */     } 
/* 117 */     GlStateManager.disableRescaleNormal();
/* 118 */     GlStateManager.popMatrix();
/* 119 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 121 */     if (destroyStage >= 0) {
/* 122 */       GlStateManager.matrixMode(5890);
/* 123 */       GlStateManager.popMatrix();
/* 124 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityEnderChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */