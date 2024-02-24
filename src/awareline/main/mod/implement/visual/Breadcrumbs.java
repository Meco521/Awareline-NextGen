/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.render.RenderUtils;
/*    */ import awareline.main.utility.render.gl.GLUtils;
/*    */ import java.awt.Color;
/*    */ import java.util.LinkedList;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class Breadcrumbs
/*    */   extends Module {
/* 19 */   public final Numbers<Double> colorRedValue = new Numbers("Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 20 */   public final Numbers<Double> colorGreenValue = new Numbers("Green", Double.valueOf(179.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 21 */   public final Numbers<Double> colorBlueValue = new Numbers("Blue", Double.valueOf(72.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 22 */   public final Option<Boolean> colorRainbow = new Option("Rainbow", Boolean.valueOf(false));
/* 23 */   private final LinkedList<double[]> positions = (LinkedList)new LinkedList<>();
/*    */   public static Breadcrumbs getInstance;
/*    */   
/*    */   public Breadcrumbs() {
/* 27 */     super("Breadcrumbs", new String[] { "Breadcrumb" }, ModuleType.Render);
/* 28 */     addSettings(new Value[] { (Value)this.colorRedValue, (Value)this.colorGreenValue, (Value)this.colorBlueValue, (Value)this.colorRainbow });
/* 29 */     getInstance = this;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onRender3D(EventRender3D event) {
/* 34 */     int color = ((Boolean)this.colorRainbow.getValue()).booleanValue() ? RenderUtils.rainbow(0) : (new Color(((Double)this.colorRedValue.getValue()).intValue(), ((Double)this.colorGreenValue.getValue()).intValue(), ((Double)this.colorBlueValue.getValue()).intValue())).getRGB();
/*    */     
/* 36 */     synchronized (this.positions) {
/* 37 */       GL11.glPushMatrix();
/*    */       
/* 39 */       GL11.glDisable(3553);
/* 40 */       GL11.glBlendFunc(770, 771);
/* 41 */       GL11.glEnable(2848);
/* 42 */       GL11.glEnable(3042);
/* 43 */       GL11.glDisable(2929);
/* 44 */       mc.entityRenderer.disableLightmap();
/* 45 */       GL11.glBegin(3);
/* 46 */       GLUtils.glColor(color);
/* 47 */       double renderPosX = (mc.getRenderManager()).viewerPosX;
/* 48 */       double renderPosY = (mc.getRenderManager()).viewerPosY;
/* 49 */       double renderPosZ = (mc.getRenderManager()).viewerPosZ;
/*    */       
/* 51 */       for (double[] pos : this.positions) {
/* 52 */         GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
/*    */       }
/* 54 */       GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
/* 55 */       GL11.glEnd();
/* 56 */       GL11.glEnable(2929);
/* 57 */       GL11.glDisable(2848);
/* 58 */       GL11.glDisable(3042);
/* 59 */       GL11.glEnable(3553);
/* 60 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 66 */     synchronized (this.positions) {
/* 67 */       this.positions.add(new double[] { mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY, mc.thePlayer.posZ });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 73 */     if (mc.thePlayer == null) {
/*    */       return;
/*    */     }
/* 76 */     synchronized (this.positions) {
/* 77 */       this.positions.add(new double[] { mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + (mc.thePlayer.getEyeHeight() * 0.5F), mc.thePlayer.posZ });
/* 78 */       this.positions.add(new double[] { mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY, mc.thePlayer.posZ });
/*    */     } 
/* 80 */     super.onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 85 */     synchronized (this.positions) {
/* 86 */       this.positions.clear();
/*    */     } 
/* 88 */     super.onDisable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Breadcrumbs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */