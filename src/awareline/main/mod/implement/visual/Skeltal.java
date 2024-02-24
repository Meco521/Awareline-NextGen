/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.WingRenderer.ColorUtils;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Skeltal extends Module {
/*  20 */   private static final Map<EntityPlayer, float[][]> modelRotations = (Map)new HashMap<>();
/*  21 */   private final Option<Boolean> rainbow = new Option("Rainbow", Boolean.valueOf(false));
/*  22 */   private final Numbers<Double> r = new Numbers("Red", 
/*  23 */       Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue()));
/*  24 */   private final Numbers<Double> g = new Numbers("Green", 
/*  25 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue()));
/*  26 */   private final Numbers<Double> b = new Numbers("Blue", 
/*  27 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue()));
/*  28 */   private final Numbers<Double> a = new Numbers("Alpha", 
/*  29 */       Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.rainbow.get()).booleanValue()));
/*     */   public static Skeltal getInstance;
/*     */   
/*     */   public Skeltal() {
/*  33 */     super("Skeltal", ModuleType.Render);
/*  34 */     addSettings(new Value[] { (Value)this.r, (Value)this.g, (Value)this.b, (Value)this.a, (Value)this.rainbow });
/*  35 */     getInstance = this;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender3D e) {
/*  40 */     GlStateManager.enableBlend();
/*  41 */     GL11.glEnable(2848);
/*  42 */     GlStateManager.disableDepth();
/*  43 */     GlStateManager.disableTexture2D();
/*  44 */     GlStateManager.blendFunc(770, 771);
/*  45 */     GL11.glHint(3154, 4354);
/*  46 */     GlStateManager.depthMask(false);
/*  47 */     GL11.glDisable(2848);
/*  48 */     GlStateManager.disableLighting();
/*  49 */     modelRotations.keySet().removeIf(player -> (!mc.theWorld.playerEntities.contains(player) || !player.isEntityAlive()));
/*  50 */     mc.theWorld.playerEntities.forEach(player -> {
/*     */           if (player != mc.thePlayer && !player.isInvisible()) {
/*     */             float[][] modelRotations = Skeltal.modelRotations.get(player);
/*     */             
/*     */             if (modelRotations != null) {
/*     */               GL11.glPushMatrix();
/*     */               
/*     */               GL11.glLineWidth(1.0F);
/*     */               
/*     */               if (((Boolean)this.rainbow.get()).booleanValue()) {
/*     */                 GL11.glColor4f((ColorUtils.rainbow(1L, 1.0F).getRed() / 255), (ColorUtils.rainbow(1L, 1.0F).getGreen() / 255), (ColorUtils.rainbow(1L, 1.0F).getBlue() / 255), (((Double)this.a.get()).intValue() / 255));
/*     */               } else {
/*     */                 GL11.glColor4f((((Double)this.r.get()).intValue() / 255), (((Double)this.g.get()).intValue() / 255), (((Double)this.b.get()).intValue() / 255), (((Double)this.a.get()).intValue() / 255));
/*     */               } 
/*     */               
/*     */               float part = mc.timer.renderPartialTicks;
/*     */               
/*     */               double interpX = player.lastTickPosX + (player.posX - player.lastTickPosX) * part;
/*     */               
/*     */               double interpY = player.lastTickPosY + (player.posY - player.lastTickPosY) * part;
/*     */               
/*     */               double interpZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * part;
/*     */               
/*     */               Vec3 interp = new Vec3(interpX, interpY, interpZ);
/*     */               double x = interp.xCoord - (mc.getRenderManager()).renderPosX;
/*     */               double y = interp.yCoord - (mc.getRenderManager()).renderPosY;
/*     */               double z = interp.zCoord - (mc.getRenderManager()).renderPosZ;
/*     */               GL11.glTranslated(x, y, z);
/*     */               float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
/*     */               GL11.glRotatef(-bodyYawOffset, 0.0F, 1.0F, 0.0F);
/*     */               GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? -0.235D : 0.0D);
/*     */               float legHeight = player.isSneaking() ? 0.6F : 0.75F;
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(-0.125D, legHeight, 0.0D);
/*     */               if (modelRotations[3][0] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[3][1] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[3][2] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */               }
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.0D, -legHeight, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.125D, legHeight, 0.0D);
/*     */               if (modelRotations[4][0] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[4][1] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[4][2] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */               }
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.0D, -legHeight, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glTranslated(0.0D, 0.0D, player.isSneaking() ? 0.25D : 0.0D);
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.0D, player.isSneaking() ? -0.05D : 0.0D, player.isSneaking() ? -0.01725D : 0.0D);
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(-0.375D, legHeight + 0.55D, 0.0D);
/*     */               if (modelRotations[1][0] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[1][1] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[1][2] != 0.0F) {
/*     */                 GL11.glRotatef(-modelRotations[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */               }
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.0D, -0.5D, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.375D, legHeight + 0.55D, 0.0D);
/*     */               if (modelRotations[2][0] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[2][1] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */               }
/*     */               if (modelRotations[2][2] != 0.0F) {
/*     */                 GL11.glRotatef(-modelRotations[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */               }
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.0D, -0.5D, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glRotatef(bodyYawOffset - player.rotationYawHead, 0.0F, 1.0F, 0.0F);
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.0D, legHeight + 0.55D, 0.0D);
/*     */               if (modelRotations[0][0] != 0.0F) {
/*     */                 GL11.glRotatef(modelRotations[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */               }
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.0D, 0.3D, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glRotatef(player.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
/*     */               GL11.glTranslated(0.0D, player.isSneaking() ? -0.16175D : 0.0D, player.isSneaking() ? -0.48025D : 0.0D);
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.0D, legHeight, 0.0D);
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.125D, 0.0D, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.0D, legHeight, 0.0D);
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(0.0D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.0D, 0.55D, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glPushMatrix();
/*     */               GL11.glTranslated(0.0D, legHeight + 0.55D, 0.0D);
/*     */               GL11.glBegin(3);
/*     */               GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
/*     */               GL11.glVertex3d(0.375D, 0.0D, 0.0D);
/*     */               GL11.glEnd();
/*     */               GL11.glPopMatrix();
/*     */               GL11.glPopMatrix();
/*     */             } 
/*     */           } 
/*     */         });
/* 188 */     GlStateManager.disableBlend();
/* 189 */     GlStateManager.enableTexture2D();
/* 190 */     GL11.glDisable(2848);
/* 191 */     GlStateManager.enableDepth();
/* 192 */     GlStateManager.depthMask(true);
/*     */   }
/*     */   
/*     */   public static void updateModel(EntityPlayer player, ModelPlayer model) {
/* 196 */     modelRotations.put(player, new float[][] { { model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ }, { model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ }, { model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ }, { model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ } });
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Skeltal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */