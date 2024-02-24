/*     */ package awareline.main.mod.implement.visual.ctype;
/*     */ 
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ChinaHat extends Module {
/*  23 */   private static final Color[] rainbows = new Color[] { new Color(30, 250, 215), new Color(0, 200, 255), new Color(50, 100, 255), new Color(100, 50, 255), new Color(255, 50, 240), new Color(255, 0, 0), new Color(255, 150, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(80, 240, 155) };
/*     */
/*  35 */   private static final Color[] astolfo = new Color[] { new Color(252, 106, 140), new Color(252, 106, 213), new Color(218, 106, 252), new Color(145, 106, 252), new Color(106, 140, 252), new Color(106, 213, 252), new Color(106, 213, 252), new Color(106, 140, 252), new Color(145, 106, 252), new Color(218, 106, 252), new Color(252, 106, 213), new Color(252, 106, 140) };
/*     */
/*     */   static int lastPoints;
/*     */
/*     */   static double lastSize;
/*     */
/*  51 */   private final Numbers<Double> pointsValue = new Numbers("Point", Double.valueOf(30.0D), Double.valueOf(3.0D), Double.valueOf(180.0D), Double.valueOf(1.0D));
/*  52 */   private final Numbers<Double> sizeValue = new Numbers("Size", Double.valueOf(0.5D), Double.valueOf(0.0D), Double.valueOf(2.0D), Double.valueOf(0.1D));
/*  53 */   private final Option<Boolean> renderInFirstPerson = new Option("RenderInFirstPerson", Boolean.valueOf(false));
/*  54 */   private final Mode<String> colorMode = new Mode("ColorMode", new String[] { "Astolfo", "Rainbow" }, "Astolfo");
/*  55 */   private final double[][] pointsCache = new double[181][2];
/*     */   
/*     */   public ChinaHat() {
/*  58 */     super("ChinaHat", ModuleType.Render);
/*  59 */     addSettings(new Value[] { (Value)this.colorMode, (Value)this.renderInFirstPerson, (Value)this.pointsValue, (Value)this.sizeValue });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void renderHud(EventRender3D event) {
/*  64 */     VerifyData.instance.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     if (lastSize != ((Double)this.sizeValue.getValue()).doubleValue() || lastPoints != ((Double)this.pointsValue.getValue()).doubleValue()) {
/*  70 */       lastSize = ((Double)this.sizeValue.getValue()).doubleValue();
/*  71 */       lastPoints = ((Double)this.pointsValue.getValue()).intValue();
/*  72 */       genPoints(lastPoints, lastSize);
/*     */     } 
/*     */     
/*  75 */     for (EntityPlayer entity : mc.theWorld.playerEntities) {
/*  76 */       if (Client.instance.friendManager.isFriend(entity.getName()) || entity == mc.thePlayer)
/*  77 */         drawHat(event, (EntityLivingBase)entity); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawHat(EventRender3D e, EntityLivingBase entity) {
/*  82 */     boolean isPlayerSP = entity.isEntityEqual((Entity)mc.thePlayer);
/*  83 */     if (mc.gameSettings.thirdPersonView == 0 && isPlayerSP && !((Boolean)this.renderInFirstPerson.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  86 */     GL11.glDisable(3553);
/*  87 */     GL11.glDisable(2884);
/*  88 */     GL11.glDepthMask(false);
/*  89 */     GL11.glDisable(2929);
/*  90 */     GL11.glShadeModel(7425);
/*  91 */     GL11.glEnable(3042);
/*  92 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*     */     
/*  94 */     RenderManager renderManager = mc.getRenderManager();
/*  95 */     double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * e.getPartialTicks() - (mc.getRenderManager()).renderPosX;
/*  96 */     double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * e.getPartialTicks() - (mc.getRenderManager()).renderPosY;
/*  97 */     double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * e.getPartialTicks() - (mc.getRenderManager()).renderPosZ;
/*     */     
/*  99 */     Color[] colors = new Color[181];
/* 100 */     for (int i = 0; i < colors.length; i++) {
/* 101 */       colors[i] = this.colorMode.is("Rainbow") ? 
/* 102 */         fadeBetween(rainbows, 6000.0D, i * 6000.0D / ((Double)this.pointsValue.getValue()).doubleValue()) : 
/* 103 */         fadeBetween(astolfo, 2000.0D, i * 2000.0D / ((Double)this.pointsValue.getValue()).doubleValue());
/*     */     }
/*     */     
/* 106 */     GL11.glPushMatrix();
/* 107 */     GL11.glTranslated(x, y + 1.9D, z);
/*     */     
/* 109 */     if (entity.isSneaking()) {
/* 110 */       GL11.glTranslated(0.0D, -0.2D, 0.0D);
/*     */     }
/* 112 */     GL11.glRotatef(
/* 113 */         (float)RenderUtil.interpolate(isPlayerSP ? mc.thePlayer.prevRotationYaw : entity.prevRotationYaw, isPlayerSP ? mc.thePlayer.rotationYaw : entity.rotationYaw, e.getPartialTicks()), 0.0F, -1.0F, 0.0F);
/*     */ 
/*     */     
/* 116 */     float interpolate = (float)RenderUtil.interpolate(isPlayerSP ? mc.thePlayer.prevRotationPitch : entity.prevRotationPitch, isPlayerSP ? mc.thePlayer.rotationPitch : entity.rotationPitch, e.getPartialTicks());
/*     */     
/* 118 */     GL11.glRotatef(interpolate / 3.0F, 1.0F, 0.0F, 0.0F);
/* 119 */     GL11.glTranslated(0.0D, 0.0D, (interpolate / 270.0F));
/* 120 */     GL11.glEnable(2848);
/* 121 */     GL11.glHint(3154, 4354);
/* 122 */     GL11.glLineWidth(2.0F);
/* 123 */     GL11.glBegin(2);
/*     */     
/* 125 */     drawCircle(((Double)this.pointsValue.getValue()).intValue() - 1, colors, 255);
/*     */     
/* 127 */     GL11.glEnd();
/* 128 */     GL11.glDisable(2848);
/* 129 */     GL11.glHint(3154, 4352);
/* 130 */     GL11.glBegin(6);
/* 131 */     GL11.glVertex3d(0.0D, ((Double)this.sizeValue.getValue()).doubleValue() / 2.0D, 0.0D);
/*     */     
/* 133 */     drawCircle(((Double)this.pointsValue.getValue()).intValue(), colors, 85);
/*     */     
/* 135 */     GL11.glEnd();
/* 136 */     GL11.glPopMatrix();
/* 137 */     GL11.glDisable(3042);
/* 138 */     GL11.glDepthMask(true);
/* 139 */     GL11.glShadeModel(7424);
/* 140 */     GL11.glEnable(2929);
/* 141 */     GL11.glEnable(2884);
/* 142 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   private void drawCircle(int points, Color[] colors, int alpha) {
/* 146 */     for (int i = 0; i <= points; i++) {
/* 147 */       double[] point = this.pointsCache[i];
/* 148 */       Color clr = colors[i];
/* 149 */       GL11.glColor4f(clr.getRed() / 255.0F, clr.getGreen() / 255.0F, clr.getBlue() / 255.0F, alpha / 255.0F);
/* 150 */       GL11.glVertex3d(point[0], 0.0D, point[1]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void genPoints(int points, double size) {
/* 155 */     for (int i = 0; i <= points; i++) {
/* 156 */       double cos = size * Math.cos(i * Math.PI * 2.0D / points);
/* 157 */       double sin = size * Math.sin(i * Math.PI * 2.0D / points);
/* 158 */       this.pointsCache[i][0] = cos;
/* 159 */       this.pointsCache[i][1] = sin;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Color fadeBetween(Color[] table, double progress) {
/* 164 */     int i = table.length;
/* 165 */     if (progress == 1.0D)
/* 166 */       return table[0]; 
/* 167 */     if (progress == 0.0D)
/* 168 */       return table[i - 1]; 
/* 169 */     double d = Math.max(0.0D, (1.0D - progress) * (i - 1));
/* 170 */     int j = (int)d;
/* 171 */     return fadeBetween(table[j], table[j + 1], d - j);
/*     */   }
/*     */   
/*     */   public Color fadeBetween(Color start, Color end, double progress) {
/* 175 */     if (progress > 1.0D)
/* 176 */       progress = 1.0D - progress % 1.0D; 
/* 177 */     return gradient(start, end, progress);
/*     */   }
/*     */   
/*     */   public Color gradient(Color start, Color end, double progress) {
/* 181 */     double invert = 1.0D - progress;
/* 182 */     return new Color((int)(start.getRed() * invert + end.getRed() * progress), 
/* 183 */         (int)(start.getGreen() * invert + end.getGreen() * progress), 
/* 184 */         (int)(start.getBlue() * invert + end.getBlue() * progress), 
/* 185 */         (int)(start.getAlpha() * invert + end.getAlpha() * progress));
/*     */   }
/*     */   
/*     */   public Color fadeBetween(Color[] table, double speed, double offset) {
/* 189 */     return fadeBetween(table, (System.currentTimeMillis() + offset) % speed / speed);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\ChinaHat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */