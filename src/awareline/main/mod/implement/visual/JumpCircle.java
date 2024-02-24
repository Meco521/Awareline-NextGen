/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.InstanceAccess;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.animations.Animation;
/*     */ import awareline.main.utility.animations.Direction;
/*     */ import awareline.main.utility.animations.impl.DecelerateAnimation;
/*     */ import awareline.main.utility.render.gl.GLUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class JumpCircle extends Module {
/*  25 */   public static final Numbers<Double> radius = new Numbers("Radius", 
/*  26 */       Double.valueOf(2.5D), Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(0.25D));
/*  27 */   private final List<Circle> circles = new ArrayList<>();
/*  28 */   private final List<Circle> toRemove = new ArrayList<>();
/*     */   private boolean inAir;
/*     */   
/*     */   public JumpCircle() {
/*  32 */     super("JumpCircle", ModuleType.Render);
/*  33 */     addSettings(new Value[] { (Value)radius });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMotionEvent(EventPreUpdate event) {
/*  38 */     if (!event.isOnGround()) {
/*  39 */       this.inAir = true;
/*  40 */     } else if (event.isOnGround() && this.inAir) {
/*  41 */       this.circles.add(new Circle(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
/*  42 */       this.inAir = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender3DEvent(EventRender3D event) {
/*  48 */     for (Circle circle : this.circles) {
/*  49 */       circle.drawCircle();
/*  50 */       if (circle.fadeAnimation != null && circle.fadeAnimation.finished(Direction.BACKWARDS)) {
/*  51 */         this.toRemove.add(circle);
/*     */       }
/*     */     } 
/*  54 */     for (Circle circle : this.toRemove) {
/*  55 */       this.circles.remove(circle);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Circle
/*     */   {
/*     */     final Animation fadeAnimation;
/*     */     private final float x;
/*     */     private final float y;
/*     */     private final float z;
/*     */     private final Animation expandAnimation;
/*     */     
/*     */     public Circle(double x, double y, double z) {
/*  68 */       this.x = (float)x;
/*  69 */       this.y = (float)y;
/*  70 */       this.z = (float)z;
/*  71 */       this.fadeAnimation = (Animation)new DecelerateAnimation(600, 1.0D);
/*  72 */       this.expandAnimation = (Animation)new DecelerateAnimation(1000, ((Double)JumpCircle.radius.getValue()).doubleValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawCircle() {
/*  77 */       Color colors = Client.instance.getClientColorNoHash(255);
/*  78 */       if (this.expandAnimation.getOutput().doubleValue() > ((Double)JumpCircle.radius.getValue()).doubleValue() * 0.699999988079071D) {
/*  79 */         this.fadeAnimation.setDirection(Direction.BACKWARDS);
/*     */       }
/*     */ 
/*     */       
/*  83 */       GL11.glPushMatrix();
/*  84 */       RenderUtil.setAlphaLimit(0.0F);
/*     */       
/*  86 */       float animation = this.expandAnimation.getOutput().floatValue();
/*  87 */       float fade = this.fadeAnimation.getOutput().floatValue();
/*  88 */       GLUtil.setup2DRendering();
/*  89 */       GL11.glDisable(2929);
/*  90 */       GL11.glDepthMask(false);
/*  91 */       GL11.glShadeModel(7425);
/*  92 */       double pi2 = MathHelper.PI2;
/*     */       
/*  94 */       double xVal = this.x - (InstanceAccess.mc.getRenderManager()).renderPosX;
/*  95 */       double yVal = this.y - (InstanceAccess.mc.getRenderManager()).renderPosY;
/*  96 */       double zVal = this.z - (InstanceAccess.mc.getRenderManager()).renderPosZ;
/*     */       
/*  98 */       GL11.glBegin(5);
/*     */       
/* 100 */       int color1 = colors.getRGB();
/* 101 */       int color2 = colors.getRGB();
/*     */ 
/*     */       
/* 104 */       float newAnim = (float)Math.max(0.0D, animation - 0.30000001192092896D * animation / this.expandAnimation.getEndPoint());
/*     */       
/* 106 */       for (int i = 0; i <= 90; i++) {
/* 107 */         float value = (float)Math.sin(((i << 2) * MathHelper.PI / 180.0F));
/* 108 */         int color = ColorUtil.interpolateColor(color1, color2, Math.abs(value));
/* 109 */         RenderUtil.color(color, fade * 0.6F);
/* 110 */         GL11.glVertex3d(xVal + animation * Math.cos(i * pi2 / 45.0D), yVal, zVal + animation * Math.sin(i * pi2 / 45.0D));
/*     */ 
/*     */         
/* 113 */         RenderUtil.color(color, fade * 0.15F);
/* 114 */         GL11.glVertex3d(xVal + newAnim * Math.cos(i * pi2 / 45.0D), yVal, zVal + newAnim * Math.sin(i * pi2 / 45.0D));
/*     */       } 
/*     */       
/* 117 */       GL11.glEnd();
/*     */ 
/*     */       
/* 120 */       GL11.glShadeModel(7424);
/* 121 */       GL11.glDepthMask(true);
/* 122 */       GL11.glEnable(2929);
/* 123 */       GLUtil.end2DRendering();
/* 124 */       GL11.glPopMatrix();
/* 125 */       RenderUtil.resetColor();
/* 126 */       RenderUtil.color(-1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\JumpCircle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */