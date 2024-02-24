/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.font.cfont.CFontRenderer;
/*    */ import java.awt.Color;
/*    */ import java.text.DecimalFormat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderTNTPrimed;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class TNTTag
/*    */   extends Module {
/*    */   public static TNTTag getInstance;
/*    */   
/*    */   public TNTTag() {
/* 22 */     super("TNTTag", ModuleType.Render);
/* 23 */     getInstance = this;
/*    */   }
/*    */   
/*    */   public void renderTag(RenderTNTPrimed renderTNTPrimed, EntityTNTPrimed tntPrimed, double x, double y, double z, float partialTicks) {
/* 27 */     if (tntPrimed.fuse < 1)
/*    */       return; 
/* 29 */     double d0 = tntPrimed.getDistanceSqToEntity((renderTNTPrimed.getRenderManager()).livingPlayer);
/* 30 */     if (d0 <= 4096.0D) {
/* 31 */       float number = (tntPrimed.fuse - partialTicks) / 20.0F;
/* 32 */       String str = (new DecimalFormat("0.00")).format(number);
/* 33 */       CFontRenderer fontrenderer = Client.instance.FontLoaders.Comfortaa20;
/* 34 */       float f = 1.6F;
/* 35 */       float f1 = 0.016666668F * f;
/* 36 */       GlStateManager.pushMatrix();
/* 37 */       GlStateManager.translate((float)x + 0.0F, (float)y + tntPrimed.height + 0.5F, (float)z);
/* 38 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 39 */       GlStateManager.rotate(-(renderTNTPrimed.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 40 */       int xMultiplier = 1;
/* 41 */       if (mc != null && mc.gameSettings != null && mc.gameSettings.thirdPersonView == 2)
/* 42 */         xMultiplier = -1; 
/* 43 */       GlStateManager.rotate((renderTNTPrimed.getRenderManager()).playerViewX * xMultiplier, 1.0F, 0.0F, 0.0F);
/* 44 */       GlStateManager.scale(-f1, -f1, f1);
/* 45 */       GlStateManager.disableLighting();
/* 46 */       GlStateManager.depthMask(false);
/* 47 */       GlStateManager.disableDepth();
/* 48 */       GlStateManager.enableBlend();
/* 49 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 50 */       Tessellator tessellator = Tessellator.getInstance();
/* 51 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 52 */       int i = 0;
/* 53 */       int j = fontrenderer.getStringWidth(str) / 2;
/* 54 */       float green = Math.min(tntPrimed.fuse / 80.0F, 1.0F);
/* 55 */       Color color = new Color(1.0F - green, green, 0.0F);
/* 56 */       GlStateManager.enableDepth();
/* 57 */       GlStateManager.depthMask(true);
/* 58 */       GlStateManager.disableTexture2D();
/* 59 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 60 */       worldrenderer.pos((-j - 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 61 */       worldrenderer.pos((-j - 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 62 */       worldrenderer.pos((j + 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 63 */       worldrenderer.pos((j + 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 64 */       tessellator.draw();
/* 65 */       GlStateManager.enableTexture2D();
/* 66 */       fontrenderer.drawString(str, (-fontrenderer.getStringWidth(str) / 2), (i - 3), color.getRGB());
/* 67 */       GlStateManager.enableLighting();
/* 68 */       GlStateManager.disableBlend();
/* 69 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 70 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\TNTTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */