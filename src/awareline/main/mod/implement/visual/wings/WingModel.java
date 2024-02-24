/*    */ package awareline.main.mod.implement.visual.wings;
/*    */ 
/*    */ import awareline.main.mod.implement.visual.RotationAnimation;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WingModel
/*    */   extends ModelBase
/*    */ {
/* 24 */   private final ResourceLocation location = new ResourceLocation("client/Wings/wings.png");
/*    */   private final ModelRenderer wingTip;
/*    */   
/*    */   public WingModel() {
/* 28 */     setTextureOffset("wing.bone", 0, 0);
/* 29 */     setTextureOffset("wing.skin", -10, 8);
/* 30 */     setTextureOffset("wingtip.bone", 0, 5);
/* 31 */     setTextureOffset("wingtip.skin", -10, 18);
/* 32 */     (this.wing = new ModelRenderer(this, "wing")).setTextureSize(30, 30);
/* 33 */     this.wing.setRotationPoint(-2.0F, 0.0F, 0.0F);
/* 34 */     this.wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
/* 35 */     this.wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
/* 36 */     (this.wingTip = new ModelRenderer(this, "wingtip")).setTextureSize(30, 30);
/* 37 */     this.wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
/* 38 */     this.wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
/* 39 */     this.wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
/* 40 */     this.wing.addChild(this.wingTip);
/*    */   }
/*    */   private final ModelRenderer wing;
/*    */   public void renderWings(EntityPlayer player, float partialTicks, double scale, Color color) {
/* 44 */     double angle = interpolate(MathHelper.wrapAngleTo180_float(
/* 45 */           RotationAnimation.getInstance.isEnabled() ? RotationAnimation.getInstance.prevYaw : player.prevRenderYawOffset), 
/* 46 */         MathHelper.wrapAngleTo180_float(
/* 47 */           RotationAnimation.getInstance.isEnabled() ? RotationAnimation.getInstance.yaw : player.renderYawOffset), partialTicks);
/*    */     
/* 49 */     GL11.glPushMatrix();
/* 50 */     GL11.glScaled(-scale, -scale, scale);
/* 51 */     GL11.glRotated(angle + 180.0D, 0.0D, 1.0D, 0.0D);
/* 52 */     GL11.glTranslated(0.0D, -1.25D / scale, 0.0D);
/* 53 */     GL11.glTranslated(0.0D, 0.0D, 0.2D / scale);
/* 54 */     if (player.isSneaking()) {
/* 55 */       GL11.glTranslated(0.0D, 0.125D / scale, 0.0D);
/*    */     }
/*    */     
/* 58 */     RenderUtil.color(color.getRGB());
/* 59 */     Minecraft.getMinecraft().getTextureManager().bindTexture(this.location);
/*    */     
/* 61 */     for (int i = 0; i < 2; i++) {
/* 62 */       GL11.glEnable(2884);
/* 63 */       float f11 = (float)(System.currentTimeMillis() % 1500L) / 1500.0F * MathHelper.PI * 2.0F;
/* 64 */       this.wing.rotateAngleX = -1.4F - (float)Math.cos(f11) * 0.2F;
/* 65 */       this.wing.rotateAngleY = 0.35F + (float)Math.sin(f11) * 0.4F;
/* 66 */       this.wing.rotateAngleZ = 0.35F;
/* 67 */       this.wingTip.rotateAngleZ = -((float)(Math.sin((f11 + 2.0F)) + 0.5D)) * 0.75F;
/* 68 */       this.wing.render(0.0625F);
/* 69 */       GL11.glScalef(-1.0F, 1.0F, 1.0F);
/* 70 */       if (i == 0) {
/* 71 */         GL11.glCullFace(1028);
/*    */       }
/*    */     } 
/*    */     
/* 75 */     GL11.glCullFace(1029);
/* 76 */     GL11.glDisable(2884);
/* 77 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 78 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   private float interpolate(float current, float target, float percent) {
/* 82 */     float f = (current + (target - current) * percent) % 360.0F;
/* 83 */     return (f < 0.0F) ? (f + 360.0F) : f;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\wings\WingModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */