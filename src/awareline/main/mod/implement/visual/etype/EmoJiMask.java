/*    */ package awareline.main.mod.implement.visual.etype;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class EmoJiMask extends Module {
/*    */   public EmoJiMask() {
/* 15 */     super("EmoJiMask", new String[] { "mask" }, ModuleType.Render);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onEmoji(EventRender3D event) {
/* 20 */     for (EntityPlayer entity : mc.theWorld.playerEntities) {
/* 21 */       if (entity == mc.thePlayer) {
/*    */         continue;
/*    */       }
/* 24 */       GL11.glPushMatrix();
/* 25 */       GL11.glEnable(3042);
/* 26 */       GL11.glDisable(2929);
/* 27 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 28 */       GlStateManager.enableBlend();
/* 29 */       GL11.glBlendFunc(770, 771);
/* 30 */       GL11.glDisable(3553);
/* 31 */       float partialTicks = mc.timer.renderPartialTicks;
/* 32 */       double n = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 33 */       double x = n - (mc.getRenderManager()).renderPosX;
/* 34 */       double n2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 35 */       double y = n2 - (mc.getRenderManager()).renderPosY;
/* 36 */       double n3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 37 */       double z = n3 - (mc.getRenderManager()).renderPosZ;
/* 38 */       float SCALE = 0.035F;
/* 39 */       GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (
/* 40 */           entity.isChild() ? (entity.height / 2.0F) : 0.0F), (float)z);
/* 41 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 42 */       GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 43 */       GL11.glScalef(-SCALE, -SCALE, -(SCALE / 2.0F));
/* 44 */       double xLeft = -20.0D;
/* 45 */       double yUp = 27.0D;
/* 46 */       GL11.glEnable(3553);
/* 47 */       GL11.glEnable(2929);
/* 48 */       GlStateManager.disableBlend();
/* 49 */       GL11.glDisable(3042);
/*    */       
/* 51 */       RenderUtil.drawImage(new ResourceLocation("client/emoji.png"), -11.0F, 7.0F, 25.0F, 25.0F);
/*    */       
/* 53 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 54 */       GL11.glNormal3f(1.0F, 1.0F, 1.0F);
/* 55 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\etype\EmoJiMask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */