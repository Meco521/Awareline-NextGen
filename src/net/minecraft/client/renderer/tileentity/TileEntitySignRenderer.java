/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiUtilRenderComponents;
/*     */ import net.minecraft.client.model.ModelSign;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntitySignRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntitySign>
/*     */ {
/*  24 */   private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
/*     */ 
/*     */   
/*  27 */   private final ModelSign model = new ModelSign();
/*  28 */   private static double textRenderDistanceSq = 4096.0D;
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  32 */     Block block = te.getBlockType();
/*  33 */     GlStateManager.pushMatrix();
/*  34 */     float f = 0.6666667F;
/*     */     
/*  36 */     if (block == Blocks.standing_sign) {
/*     */       
/*  38 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  39 */       float f1 = (te.getBlockMetadata() * 360) / 16.0F;
/*  40 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/*  41 */       this.model.signStick.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  45 */       int k = te.getBlockMetadata();
/*  46 */       float f2 = 0.0F;
/*     */       
/*  48 */       if (k == 2)
/*     */       {
/*  50 */         f2 = 180.0F;
/*     */       }
/*     */       
/*  53 */       if (k == 4)
/*     */       {
/*  55 */         f2 = 90.0F;
/*     */       }
/*     */       
/*  58 */       if (k == 5)
/*     */       {
/*  60 */         f2 = -90.0F;
/*     */       }
/*     */       
/*  63 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  64 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  65 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/*  66 */       this.model.signStick.showModel = false;
/*     */     } 
/*     */     
/*  69 */     if (destroyStage >= 0) {
/*     */       
/*  71 */       bindTexture(DESTROY_STAGES[destroyStage]);
/*  72 */       GlStateManager.matrixMode(5890);
/*  73 */       GlStateManager.pushMatrix();
/*  74 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  75 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  76 */       GlStateManager.matrixMode(5888);
/*     */     }
/*     */     else {
/*     */       
/*  80 */       bindTexture(SIGN_TEXTURE);
/*     */     } 
/*     */     
/*  83 */     GlStateManager.enableRescaleNormal();
/*  84 */     GlStateManager.pushMatrix();
/*  85 */     GlStateManager.scale(f, -f, -f);
/*  86 */     this.model.renderSign();
/*  87 */     GlStateManager.popMatrix();
/*     */     
/*  89 */     if (isRenderText(te)) {
/*     */       
/*  91 */       FontRenderer fontrenderer = getFontRenderer();
/*  92 */       float f3 = 0.015625F * f;
/*  93 */       GlStateManager.translate(0.0F, 0.5F * f, 0.07F * f);
/*  94 */       GlStateManager.scale(f3, -f3, f3);
/*  95 */       GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
/*  96 */       GlStateManager.depthMask(false);
/*  97 */       int i = 0;
/*     */       
/*  99 */       if (Config.isCustomColors())
/*     */       {
/* 101 */         i = CustomColors.getSignTextColor(i);
/*     */       }
/*     */       
/* 104 */       if (destroyStage < 0)
/*     */       {
/* 106 */         for (int j = 0; j < te.signText.length; j++) {
/*     */           
/* 108 */           if (te.signText[j] != null) {
/*     */             
/* 110 */             IChatComponent ichatcomponent = te.signText[j];
/* 111 */             List<IChatComponent> list = GuiUtilRenderComponents.splitText(ichatcomponent, 90, fontrenderer, false, true);
/* 112 */             String s = (list != null && !list.isEmpty()) ? ((IChatComponent)list.get(0)).getFormattedText() : "";
/*     */             
/* 114 */             if (j == te.lineBeingEdited) {
/*     */               
/* 116 */               s = "> " + s + " <";
/* 117 */               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
/*     */             }
/*     */             else {
/*     */               
/* 121 */               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 128 */     GlStateManager.depthMask(true);
/* 129 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 130 */     GlStateManager.popMatrix();
/*     */     
/* 132 */     if (destroyStage >= 0) {
/*     */       
/* 134 */       GlStateManager.matrixMode(5890);
/* 135 */       GlStateManager.popMatrix();
/* 136 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRenderText(TileEntitySign p_isRenderText_0_) {
/* 142 */     if (Shaders.isShadowPass)
/*     */     {
/* 144 */       return false;
/*     */     }
/* 146 */     if ((Config.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.inventory.GuiEditSign)
/*     */     {
/* 148 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 152 */     if (!Config.zoomMode && p_isRenderText_0_.lineBeingEdited < 0) {
/*     */       
/* 154 */       Entity entity = Config.getMinecraft().getRenderViewEntity();
/* 155 */       double d0 = p_isRenderText_0_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */       
/* 157 */       if (d0 > textRenderDistanceSq)
/*     */       {
/* 159 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateTextRenderDistance() {
/* 169 */     Minecraft minecraft = Config.getMinecraft();
/* 170 */     double d0 = Config.limit(minecraft.gameSettings.fovSetting, 1.0F, 120.0F);
/* 171 */     double d1 = Math.max(1.5D * minecraft.displayHeight / d0, 16.0D);
/* 172 */     textRenderDistanceSq = d1 * d1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntitySignRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */