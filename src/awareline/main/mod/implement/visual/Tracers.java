/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.WingRenderer.ColorUtils;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import awareline.main.utility.render.RenderUtils;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.entity.Entity;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Tracers
/*     */   extends Module
/*     */ {
/*  23 */   private final Mode<String> colormode = new Mode("Color", new String[] { "DistanceHealth", "Custom" }, "DistanceHealth");
/*  24 */   private final Option<Boolean> rainbow = new Option("Rainbow", Boolean.valueOf(false));
/*  25 */   private final Option<Boolean> Breathinglamp = new Option("DynamicColor", Boolean.valueOf(false));
/*  26 */   private final Option<Boolean> HUDColor = new Option("HUDColor", Boolean.valueOf(true));
/*  27 */   private final Numbers<Double> r = new Numbers("Red", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  28 */   private final Numbers<Double> g = new Numbers("Green", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  29 */   private final Numbers<Double> b = new Numbers("Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*     */   
/*     */   public Tracers() {
/*  32 */     super("Tracers", ModuleType.Render);
/*  33 */     addSettings(new Value[] { (Value)this.colormode, (Value)this.r, (Value)this.g, (Value)this.b, (Value)this.HUDColor, (Value)this.rainbow, (Value)this.Breathinglamp });
/*     */   }
/*     */   
/*     */   @EventHandler(3)
/*     */   public void on3DRender(EventRender3D e) {
/*  38 */     for (Entity o : mc.theWorld.loadedEntityList) {
/*     */       double[] arrd;
/*  40 */       if (!o.isEntityAlive() || !(o instanceof net.minecraft.entity.player.EntityPlayer) || o == mc.thePlayer)
/*  41 */         continue;  double posX = o.lastTickPosX + (o.posX - o.lastTickPosX) * e.getPartialTicks() - (mc.getRenderManager()).renderPosX;
/*  42 */       double posY = o.lastTickPosY + (o.posY - o.lastTickPosY) * e.getPartialTicks() - (mc.getRenderManager()).renderPosY;
/*  43 */       double posZ = o.lastTickPosZ + (o.posZ - o.lastTickPosZ) * e.getPartialTicks() - (mc.getRenderManager()).renderPosZ;
/*  44 */       boolean old = mc.gameSettings.viewBobbing;
/*  45 */       RenderUtil.startDrawing();
/*  46 */       mc.gameSettings.viewBobbing = false;
/*  47 */       mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
/*  48 */       mc.gameSettings.viewBobbing = old;
/*  49 */       float color = (float)Math.round(255.0D - mc.thePlayer.getDistanceSqToEntity(o) * 255.0D / MathUtil.square(mc.gameSettings.renderDistanceChunks * 2.5D)) / 255.0F;
/*  50 */       if (Client.instance.friendManager.isFriend(o.getName())) {
/*  51 */         double[] arrd2 = new double[3];
/*  52 */         arrd2[0] = 0.0D;
/*  53 */         arrd2[1] = 1.0D;
/*  54 */         arrd = arrd2;
/*  55 */         arrd2[2] = 1.0D;
/*     */       } else {
/*  57 */         double[] arrd3 = new double[3];
/*  58 */         arrd3[0] = color;
/*  59 */         arrd3[1] = (1.0F - color);
/*  60 */         arrd = arrd3;
/*  61 */         arrd3[2] = 0.0D;
/*     */       } 
/*  63 */       drawLine(arrd, posX, posY, posZ);
/*  64 */       RenderUtil.stopDrawing();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawLine(double[] color, double x, double y, double z) {
/*  69 */     GL11.glEnable(2848);
/*  70 */     if (this.colormode.is("DistanceHealth")) {
/*  71 */       if (color.length >= 4) {
/*  72 */         if (color[3] <= 0.1D) {
/*     */           return;
/*     */         }
/*  75 */         GL11.glColor4d(color[0], color[1], color[2], color[3]);
/*     */       } else {
/*  77 */         GL11.glColor3d(color[0], color[1], color[2]);
/*     */       }
/*     */     
/*  80 */     } else if (!((Boolean)this.rainbow.getValue()).booleanValue()) {
/*  81 */       if (((Boolean)this.HUDColor.getValue()).booleanValue()) {
/*  82 */         if (((Boolean)HUD.dynamicColor.getValue()).booleanValue()) {
/*  83 */           Color col = new Color(RenderUtils.rainbow(System.nanoTime(), 0.0F, 1.0F).getRGB());
/*  84 */           Color cuscolor = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue());
/*  85 */           Color Ranbowe = new Color(cuscolor.getRed() / 255.0F, cuscolor.getGreen() / 255.0F, cuscolor.getBlue() / 255.0F, 1.0F - col.getGreen() / 400.0F);
/*  86 */           GL11.glColor4f(Ranbowe.getRed() / 255.0F, Ranbowe.getGreen() / 255.0F, Ranbowe.getBlue() / 255.0F, Ranbowe.getAlpha() / 255.0F);
/*     */         } else {
/*  88 */           GL11.glColor4f(((Double)HUD.r.getValue()).intValue() / 255.0F, ((Double)HUD.g.getValue()).intValue() / 255.0F, ((Double)HUD.b.getValue()).intValue() / 255.0F, 255.0F);
/*     */         }
/*     */       
/*  91 */       } else if (((Boolean)this.Breathinglamp.getValue()).booleanValue()) {
/*  92 */         Color col = new Color(RenderUtils.rainbow(System.nanoTime(), 0.0F, 1.0F).getRGB());
/*  93 */         Color cuscolor = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue());
/*  94 */         Color Ranbowe = new Color(cuscolor.getRed() / 255.0F, cuscolor.getGreen() / 255.0F, cuscolor.getBlue() / 255.0F, 1.0F - col.getGreen() / 400.0F);
/*  95 */         GL11.glColor4f(Ranbowe.getRed() / 255.0F, Ranbowe.getGreen() / 255.0F, Ranbowe.getBlue() / 255.0F, Ranbowe.getAlpha() / 255.0F);
/*     */       } else {
/*  97 */         GL11.glColor4f(((Double)this.r.getValue()).intValue() / 255.0F, ((Double)this.g.getValue()).intValue() / 255.0F, ((Double)this.b.getValue()).intValue() / 255.0F, 255.0F);
/*     */       } 
/*     */     } else {
/*     */       
/* 101 */       GL11.glColor4f(ColorUtils.rainbow(1L, 1.0F).getRed() / 255.0F, ColorUtils.rainbow(1L, 1.0F).getGreen() / 255.0F, ColorUtils.rainbow(1L, 1.0F).getBlue() / 255.0F, 255.0F);
/*     */     } 
/*     */     
/* 104 */     GL11.glLineWidth(1.0F);
/* 105 */     GL11.glBegin(1);
/* 106 */     GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
/* 107 */     GL11.glVertex3d(x, y, z);
/* 108 */     GL11.glEnd();
/* 109 */     GL11.glDisable(2848);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Tracers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */