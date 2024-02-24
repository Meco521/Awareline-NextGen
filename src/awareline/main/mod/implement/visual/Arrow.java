/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.WingRenderer.ColorUtils;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Arrow
/*     */   extends Module
/*     */ {
/*  30 */   private final Option<Boolean> players = new Option("Player", Boolean.valueOf(true));
/*  31 */   private final Option<Boolean> mobs = new Option("Mobs", Boolean.valueOf(false));
/*  32 */   private final Option<Boolean> animals = new Option("Animals", Boolean.valueOf(false));
/*  33 */   private final Option<Boolean> invisibles = new Option("Invisible", Boolean.valueOf(false));
/*     */ 
/*     */ 
/*     */   
/*  37 */   private final Option<Boolean> renderPlayerName = new Option("ShowName", Boolean.valueOf(false));
/*  38 */   private final Numbers<Double> searchRange = new Numbers("SearchRange", 
/*  39 */       Double.valueOf(25.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(0.1D));
/*  40 */   private final Numbers<Double> round = new Numbers("Round", 
/*  41 */       Double.valueOf(25.0D), Double.valueOf(1.0D), Double.valueOf(200.0D), Double.valueOf(0.1D));
/*     */ 
/*     */ 
/*     */   
/*  45 */   private final Option<Boolean> rainbow = new Option("Rainbow", Boolean.valueOf(false));
/*  46 */   private final Option<Boolean> distColor = new Option("DistColor", 
/*  47 */       Boolean.valueOf(false), () -> Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue()));
/*  48 */   private final Numbers<Double> alpha = new Numbers("Alpha", 
/*  49 */       Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*     */   float rounds;
/*     */   boolean shouldStopOnViewing;
/*     */   
/*     */   public Arrow() {
/*  54 */     super("Arrows", new String[] { "osr" }, ModuleType.Render);
/*  55 */     addSettings(new Value[] { (Value)this.players, (Value)this.mobs, (Value)this.animals, (Value)this.invisibles, (Value)this.renderPlayerName, (Value)this.searchRange, (Value)this.round, (Value)this.alpha, (Value)this.rainbow, (Value)this.distColor });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  62 */     this.rounds = 0.0F;
/*  63 */     this.shouldStopOnViewing = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void on2D(EventRender2D e) {
/*  68 */     GlStateManager.pushMatrix();
/*  69 */     double size = 50.0D;
/*  70 */     ScaledResolution sr = e.getResolution();
/*  71 */     double playerOffsetX = mc.thePlayer.posX;
/*  72 */     double playerOffSetZ = mc.thePlayer.posZ;
/*  73 */     double cos = Math.cos(mc.thePlayer.rotationYaw * 0.017453292519943295D);
/*  74 */     double sin = Math.sin(mc.thePlayer.rotationYaw * 0.017453292519943295D);
/*  75 */     double loaddist = 0.2D;
/*  76 */     float pTicks = e.getPartialTicks();
/*  77 */     if (this.rounds != ((Double)this.round.get()).floatValue()) {
/*  78 */       this.rounds = AnimationUtil.moveUD(this.rounds, ((Double)this.round.get()).floatValue(), 
/*  79 */           SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */     }
/*  81 */     for (int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
/*  82 */       Entity gay = mc.theWorld.loadedEntityList.get(i);
/*  83 */       if (gay != mc.thePlayer && mc.thePlayer
/*  84 */         .getDistanceToEntity(gay) <= ((Double)this.searchRange.getValue()).doubleValue() && (((
/*  85 */         (Boolean)this.mobs.getValue()).booleanValue() && (gay instanceof net.minecraft.entity.monster.EntityMob || gay instanceof net.minecraft.entity.monster.EntitySlime || gay instanceof net.minecraft.entity.passive.EntityVillager)) || (((Boolean)this.animals.getValue()).booleanValue() && (gay instanceof net.minecraft.entity.passive.EntityAnimal || gay instanceof net.minecraft.entity.passive.EntitySquid)) || (((Boolean)this.players.getValue()).booleanValue() && gay instanceof net.minecraft.entity.player.EntityPlayer && gay != mc.thePlayer)) && (((Boolean)this.invisibles.getValue()).booleanValue() || !gay.isInvisible())) {
/*  86 */         double pos1 = (gay.posX + (gay.posX - gay.lastTickPosX) * pTicks - playerOffsetX) * loaddist;
/*  87 */         double pos2 = (gay.posZ + (gay.posZ - gay.lastTickPosZ) * pTicks - playerOffSetZ) * loaddist;
/*  88 */         double rotY = -(pos2 * cos - pos1 * sin);
/*  89 */         double rotX = -(pos1 * cos + pos2 * sin);
/*  90 */         double var7 = 0.0D - rotX;
/*  91 */         double var9 = 0.0D - rotY;
/*  92 */         if (MathHelper.sqrt_double(var7 * var7 + var9 * var9) < 21.0D) {
/*  93 */           float angle = (float)(Math.atan2(rotY - 0.0D, rotX - 0.0D) * 180.0D / Math.PI);
/*  94 */           double x = this.rounds * Math.cos(Math.toRadians(angle)) + (sr.getScaledWidth() / 2.0F - 24.5F) + 25.0D;
/*  95 */           double y = this.rounds * Math.sin(Math.toRadians(angle)) + (sr.getScaledHeight() / 2.0F - 25.2F) + 25.0D;
/*  96 */           if (((Boolean)this.renderPlayerName.getValue()).booleanValue()) {
/*  97 */             GlStateManager.pushMatrix();
/*  98 */             GlStateManager.scale(0.5D, 0.5D, 0.5D);
/*  99 */             mc.fontRendererObj.drawStringWithShadow(gay.getName(), (float)x * 2.0F - mc.fontRendererObj
/* 100 */                 .getStringWidth(gay.getName()) + 20.0F, (float)y * 2.0F - 20.0F, -1);
/*     */             
/* 102 */             GlStateManager.popMatrix();
/*     */           } 
/* 104 */           GlStateManager.pushMatrix();
/* 105 */           GlStateManager.translate(x, y, 0.0D);
/* 106 */           GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
/* 107 */           GlStateManager.scale(1.5D, 1.0D, 1.0D);
/* 108 */           drawESPCircle(gay, 0.0D, 0.0D, 3.2D, 3.0D);
/* 109 */           drawESPCircle(gay, 0.0D, 0.0D, 3.0D, 3.0D);
/* 110 */           drawESPCircle(gay, 0.0D, 0.0D, 2.5D, 3.0D);
/* 111 */           drawESPCircle(gay, 0.0D, 0.0D, 2.0D, 3.0D);
/* 112 */           drawESPCircle(gay, 0.0D, 0.0D, 1.5D, 3.0D);
/* 113 */           drawESPCircle(gay, 0.0D, 0.0D, 1.0D, 3.0D);
/* 114 */           drawESPCircle(gay, 0.0D, 0.0D, 0.5D, 3.0D);
/* 115 */           GlStateManager.popMatrix();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void enableGL2D() {
/* 125 */     GL11.glDisable(2929);
/* 126 */     GL11.glEnable(3042);
/* 127 */     GL11.glDisable(3553);
/* 128 */     GL11.glBlendFunc(770, 771);
/* 129 */     GL11.glDepthMask(true);
/* 130 */     GL11.glEnable(2848);
/* 131 */     GL11.glHint(3154, 4354);
/* 132 */     GL11.glHint(3155, 4354);
/*     */   }
/*     */   
/*     */   private void disableGL2D() {
/* 136 */     GL11.glEnable(3553);
/* 137 */     GL11.glDisable(3042);
/* 138 */     GL11.glEnable(2929);
/* 139 */     GL11.glDisable(2848);
/* 140 */     GL11.glHint(3154, 4352);
/* 141 */     GL11.glHint(3155, 4352);
/*     */   }
/*     */   
/*     */   private void drawESPCircle(Entity target, double cx, double cy, double r, double n) {
/* 145 */     GL11.glPushMatrix();
/* 146 */     cx *= 2.0D;
/* 147 */     cy *= 2.0D;
/* 148 */     double b = 6.2831852D / n;
/* 149 */     double p = Math.cos(b);
/* 150 */     double s = Math.sin(b);
/* 151 */     double x = r * 2.0D;
/* 152 */     double y = 0.0D;
/* 153 */     enableGL2D();
/* 154 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 155 */     GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 156 */     GlStateManager.resetColor();
/* 157 */     if (!((Boolean)this.rainbow.getValue()).booleanValue()) {
/* 158 */       if (!((Boolean)this.distColor.get()).booleanValue()) {
/* 159 */         GL11.glColor4f(((Double)HUD.r.getValue()).intValue() / 255.0F, ((Double)HUD.g.getValue()).intValue() / 255.0F, ((Double)HUD.b.getValue()).intValue() / 255.0F, ((Double)this.alpha.getValue()).intValue() / 255.0F);
/*     */       } else {
/*     */         double[] arrd;
/* 162 */         float color = (float)Math.round(255.0D - mc.thePlayer.getDistanceSqToEntity(target) * 255.0D / MathUtil.square(mc.gameSettings.renderDistanceChunks * 2.5D)) / 255.0F;
/* 163 */         if (Client.instance.friendManager.isFriend(target.getName())) {
/* 164 */           double[] arrd2 = new double[3];
/* 165 */           arrd2[0] = 0.0D;
/* 166 */           arrd2[1] = 1.0D;
/* 167 */           arrd = arrd2;
/* 168 */           arrd2[2] = 1.0D;
/*     */         } else {
/* 170 */           double[] arrd3 = new double[3];
/* 171 */           arrd3[0] = color;
/* 172 */           arrd3[1] = (1.0F - color);
/* 173 */           arrd = arrd3;
/* 174 */           arrd3[2] = 0.0D;
/*     */         } 
/* 176 */         GL11.glColor3d(arrd[0], arrd[1], arrd[2]);
/*     */       } 
/*     */     } else {
/* 179 */       GL11.glColor4f(ColorUtils.rainbow(1L, 1.0F).getRed() / 255.0F, ColorUtils.rainbow(1L, 1.0F).getGreen() / 255.0F, ColorUtils.rainbow(1L, 1.0F).getBlue() / 255.0F, ((Double)this.alpha.getValue()).intValue() / 255.0F);
/*     */     } 
/* 181 */     GL11.glBegin(2);
/* 182 */     double ii = 0.0D;
/* 183 */     while (ii < n) {
/* 184 */       GL11.glVertex2d(x + cx, y + cy);
/* 185 */       double t = x;
/* 186 */       x = p * x - s * y;
/* 187 */       y = s * t + p * y;
/* 188 */       ii++;
/*     */     } 
/* 190 */     GL11.glEnd();
/* 191 */     GL11.glScaled(2.0D, 2.0D, 2.0D);
/* 192 */     disableGL2D();
/*     */     
/* 194 */     GlStateManager.disableBlend();
/* 195 */     GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 196 */     GlStateManager.disableDepth();
/* 197 */     GlStateManager.disableLighting();
/* 198 */     GlStateManager.enableDepth();
/* 199 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 200 */     GlStateManager.enableAlpha();
/* 201 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 202 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Arrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */