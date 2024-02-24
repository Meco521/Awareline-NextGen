/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ 
/*    */ public class LayerEnderDragonDeath
/*    */   implements LayerRenderer<EntityDragon>
/*    */ {
/*    */   public void doRenderLayer(EntityDragon entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 16 */     if (entitylivingbaseIn.deathTicks > 0) {
/*    */       
/* 18 */       Tessellator tessellator = Tessellator.getInstance();
/* 19 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 20 */       RenderHelper.disableStandardItemLighting();
/* 21 */       float f = (entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
/* 22 */       float f1 = 0.0F;
/*    */       
/* 24 */       if (f > 0.8F)
/*    */       {
/* 26 */         f1 = (f - 0.8F) / 0.2F;
/*    */       }
/*    */       
/* 29 */       Random random = new Random(432L);
/* 30 */       GlStateManager.disableTexture2D();
/* 31 */       GlStateManager.shadeModel(7425);
/* 32 */       GlStateManager.enableBlend();
/* 33 */       GlStateManager.blendFunc(770, 1);
/* 34 */       GlStateManager.disableAlpha();
/* 35 */       GlStateManager.enableCull();
/* 36 */       GlStateManager.depthMask(false);
/* 37 */       GlStateManager.pushMatrix();
/* 38 */       GlStateManager.translate(0.0F, -1.0F, -2.0F);
/*    */       
/* 40 */       for (int i = 0; i < (f + f * f) / 2.0F * 60.0F; i++) {
/*    */         
/* 42 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 43 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 44 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
/* 45 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 46 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 47 */         GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
/* 48 */         float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
/* 49 */         float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
/* 50 */         worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 51 */         worldrenderer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
/* 52 */         worldrenderer.pos(-0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
/* 53 */         worldrenderer.pos(0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
/* 54 */         worldrenderer.pos(0.0D, f2, f3).color(255, 0, 255, 0).endVertex();
/* 55 */         worldrenderer.pos(-0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
/* 56 */         tessellator.draw();
/*    */       } 
/*    */       
/* 59 */       GlStateManager.popMatrix();
/* 60 */       GlStateManager.depthMask(true);
/* 61 */       GlStateManager.disableCull();
/* 62 */       GlStateManager.disableBlend();
/* 63 */       GlStateManager.shadeModel(7424);
/* 64 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 65 */       GlStateManager.enableTexture2D();
/* 66 */       GlStateManager.enableAlpha();
/* 67 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerEnderDragonDeath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */