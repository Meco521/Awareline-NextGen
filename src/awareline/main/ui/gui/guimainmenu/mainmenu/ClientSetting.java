/*     */ package awareline.main.ui.gui.guimainmenu.mainmenu;
/*     */ 
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.Rotation;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientSetting
/*     */   extends Module
/*     */ {
/*  32 */   public static final Option<Boolean> clMode = new Option("Auto CL", Boolean.valueOf(false)), fakeForge = new Option("Fake Forge", Boolean.valueOf(false));
/*  33 */   public static final Option<Boolean> betterFPS = new Option("Better FPS", Boolean.valueOf(true)); public static final Option<Boolean> shaderBG = new Option("Shader Background", Boolean.valueOf(true));
/*     */   
/*  35 */   public static final Mode<String> shaderMode = new Mode("Shader Backgrounds", new String[] { "Purple", "BPurple", "DPurple", "Stick", "Red", "Blue", "BBlue", "BGreen", "Cloud", "Sky", "LB", "Rise", "Flux" }, "DPurple", shaderBG::get);
/*     */ 
/*     */   
/*     */   public static ClientSetting getInstance;
/*     */ 
/*     */   
/*  41 */   public final List clicks = new ArrayList();
/*     */   public boolean wasPressed = true;
/*     */   
/*     */   public ClientSetting() {
/*  45 */     super("ClientSetting", new String[] { "cs" }, ModuleType.Globals);
/*  46 */     addSettings(new Value[] { (Value)shaderMode, (Value)shaderBG, (Value)clMode, (Value)betterFPS, (Value)fakeForge });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     setHide(true);
/*  54 */     getInstance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  59 */     Helper.sendMessage("Don't enable this module");
/*  60 */     setEnabled(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getRotation(EntityLivingBase target) {
/*  65 */     double xDiff = target.posX - mc.thePlayer.posX;
/*  66 */     double yDiff = target.posY - mc.thePlayer.posY - 0.2D;
/*  67 */     double zDiff = target.posZ - mc.thePlayer.posZ;
/*     */     
/*  69 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/*  70 */     double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
/*  71 */     float pitch = (float)(-Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/*  72 */     float[] array = new float[2];
/*  73 */     int n = 0;
/*  74 */     float rotationYaw = mc.thePlayer.rotationYaw;
/*  75 */     array[n] = rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw);
/*  76 */     int n3 = 1;
/*  77 */     float rotationPitch = mc.thePlayer.rotationPitch;
/*  78 */     array[n3] = rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch);
/*  79 */     return array;
/*     */   }
/*     */   
/*     */   public Rotation limitAngleChange(Rotation currentRotation, Rotation targetRotation, float turnSpeed) {
/*  83 */     float yawDifference = getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
/*  84 */     float pitchDifference = getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
/*  85 */     return new Rotation(currentRotation.getYaw() + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
/*     */   }
/*     */   
/*     */   public float getAngleDifference(float a, float b) {
/*  89 */     return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
/*     */   }
/*     */   
/*     */   public float getDistanceBetweenAngles(float angle1, float angle2) {
/*  93 */     float angle3 = Math.abs(angle1 - angle2) % 360.0F;
/*  94 */     if (angle3 > 180.0F) {
/*  95 */       angle3 = 0.0F;
/*     */     }
/*  97 */     return angle3;
/*     */   }
/*     */   
/*     */   public int getCPS() {
/* 101 */     long time = System.currentTimeMillis();
/* 102 */     Iterator<Long> iterator = this.clicks.iterator();
/* 103 */     while (iterator.hasNext()) {
/* 104 */       if (((Long)iterator.next()).longValue() + 1000L >= time)
/* 105 */         continue;  iterator.remove();
/*     */     } 
/* 107 */     return this.clicks.size();
/*     */   }
/*     */   
/*     */   public void boxESP(EntityLivingBase curTarget) {
/* 111 */     double x = curTarget.lastTickPosX + (curTarget.posX - curTarget.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/* 112 */     double y = curTarget.lastTickPosY + (curTarget.posY - curTarget.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/* 113 */     double z = curTarget.lastTickPosZ + (curTarget.posZ - curTarget.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/* 114 */     float boxSize = 1.15F;
/* 115 */     if (curTarget instanceof net.minecraft.entity.player.EntityPlayer) {
/* 116 */       double width = (curTarget.getEntityBoundingBox()).maxX - (curTarget.getEntityBoundingBox()).minX;
/* 117 */       double height2 = (curTarget.getEntityBoundingBox()).maxY - (curTarget.getEntityBoundingBox()).minY + 0.25D;
/* 118 */       float red = (curTarget.hurtTime > 0) ? 1.0F : 0.0F;
/* 119 */       float green = (curTarget.hurtTime > 0) ? 0.2F : 1.0F;
/* 120 */       float blue = 0.0F;
/* 121 */       float alpha = 0.2F;
/* 122 */       float lineRed = (curTarget.hurtTime > 0) ? 1.0F : 0.0F;
/* 123 */       float lineGreen = (curTarget.hurtTime > 0) ? 0.2F : 0.5F;
/* 124 */       float lineBlue = (curTarget.hurtTime > 0) ? 0.0F : 1.0F;
/* 125 */       float lineAlpha = 1.0F;
/* 126 */       RenderUtil.drawEntityESPVapeMark(x, y, z, width - boxSize, height2, red, green, blue, alpha, lineRed, lineGreen, lineBlue, lineAlpha, 0.1F);
/*     */     } else {
/* 128 */       double width = (curTarget.getEntityBoundingBox()).maxX - (curTarget.getEntityBoundingBox()).minX + 0.1D;
/* 129 */       double height3 = (curTarget.getEntityBoundingBox()).maxY - (curTarget.getEntityBoundingBox()).minY + 0.25D;
/* 130 */       float red = (curTarget.hurtTime > 0) ? 1.0F : 0.0F;
/* 131 */       float green = (curTarget.hurtTime > 0) ? 0.2F : 1.0F;
/* 132 */       float blue = 0.0F;
/* 133 */       float alpha = 0.2F;
/* 134 */       float lineRed = (curTarget.hurtTime > 0) ? 1.0F : 0.0F;
/* 135 */       float lineGreen = (curTarget.hurtTime > 0) ? 0.2F : 0.5F;
/* 136 */       float lineBlue = (curTarget.hurtTime > 0) ? 0.0F : 1.0F;
/* 137 */       float lineAlpha = 1.0F;
/* 138 */       float lineWdith = 2.0F;
/* 139 */       RenderUtil.drawEntityESPVapeMark(x, y, z, width - boxSize, height3, red, green, blue, alpha, lineRed, lineGreen, lineBlue, lineAlpha, lineWdith);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawCircle(Entity entity, double rad, boolean shade) {
/* 144 */     GL11.glPushMatrix();
/* 145 */     GL11.glDisable(3553);
/* 146 */     GL11.glEnable(2848);
/* 147 */     GL11.glEnable(2832);
/* 148 */     GL11.glEnable(3042);
/* 149 */     GL11.glBlendFunc(770, 771);
/* 150 */     GL11.glHint(3154, 4354);
/* 151 */     GL11.glHint(3155, 4354);
/* 152 */     GL11.glHint(3153, 4354);
/* 153 */     GL11.glDepthMask(false);
/* 154 */     GlStateManager.alphaFunc(516, 0.0F);
/* 155 */     if (shade) {
/* 156 */       GL11.glShadeModel(7425);
/*     */     }
/* 158 */     GlStateManager.disableCull();
/* 159 */     GL11.glBegin(5);
/*     */     
/* 161 */     double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/*     */     
/* 163 */     double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY + Math.sin(System.currentTimeMillis() / 200.0D) + 1.0D;
/* 164 */     double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/*     */     
/*     */     float i;
/*     */     
/* 168 */     for (i = 0.0F; i < 6.283185307179586D; i = (float)(i + 0.09817477042468103D)) {
/* 169 */       Color c; double vecX = x + rad * MathHelper.cos(i);
/* 170 */       double vecZ = z + rad * MathHelper.sin(i);
/* 171 */       if (((Boolean)HUD.rainbow.get()).booleanValue()) {
/* 172 */         c = HUD.getInstance.rainbowToEffect();
/*     */       } else {
/* 174 */         c = new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue());
/*     */       } 
/*     */       
/* 177 */       if (shade) {
/* 178 */         GL11.glColor4f(c.getRed() / 255.0F, c
/* 179 */             .getGreen() / 255.0F, c
/* 180 */             .getBlue() / 255.0F, 0.0F);
/*     */ 
/*     */         
/* 183 */         GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 200.0D) / 2.0D, vecZ);
/* 184 */         GL11.glColor4f(c.getRed() / 255.0F, c
/* 185 */             .getGreen() / 255.0F, c
/* 186 */             .getBlue() / 255.0F, 0.85F);
/*     */       } 
/*     */ 
/*     */       
/* 190 */       GL11.glVertex3d(vecX, y, vecZ);
/*     */     } 
/*     */     
/* 193 */     GL11.glEnd();
/* 194 */     if (shade) {
/* 195 */       GL11.glShadeModel(7424);
/*     */     }
/* 197 */     GL11.glDepthMask(true);
/* 198 */     GL11.glEnable(2929);
/* 199 */     GlStateManager.alphaFunc(516, 0.1F);
/* 200 */     GlStateManager.enableCull();
/* 201 */     GL11.glDisable(2848);
/* 202 */     GL11.glDisable(2848);
/* 203 */     GL11.glEnable(2832);
/* 204 */     GL11.glEnable(3553);
/* 205 */     GL11.glPopMatrix();
/* 206 */     GL11.glColor3f(255.0F, 255.0F, 255.0F);
/*     */   }
/*     */   
/*     */   public void normalMark(EntityLivingBase target) {
/* 210 */     Color color = new Color(255, 255, 255);
/* 211 */     if (target.hurtResistantTime > 0) {
/* 212 */       color = new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b.get()).intValue());
/*     */     }
/* 214 */     double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/* 215 */     double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/* 216 */     double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/* 217 */     if (target instanceof net.minecraft.entity.player.EntityPlayer) {
/* 218 */       x -= 0.5D;
/* 219 */       z -= 0.5D;
/* 220 */       y += target.getEyeHeight() + 0.35D - (target.isSneaking() ? 0.25D : 0.0D);
/* 221 */       double mid = 0.5D;
/* 222 */       GL11.glPushMatrix();
/* 223 */       GL11.glEnable(3042);
/* 224 */       GL11.glBlendFunc(770, 771);
/* 225 */       GL11.glTranslated(x + mid, y + mid, z + mid);
/* 226 */       GL11.glRotated((-target.rotationYaw % 360.0F), 0.0D, 1.0D, 0.0D);
/* 227 */       GL11.glTranslated(-(x + mid), -(y + mid), -(z + mid));
/* 228 */       GL11.glDisable(3553);
/* 229 */       GL11.glEnable(2848);
/* 230 */       GL11.glDisable(2929);
/* 231 */       GL11.glDepthMask(false);
/* 232 */       GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 0.5F);
/* 233 */       RenderUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 0.05D, z + 1.0D));
/* 234 */       GL11.glDisable(2848);
/* 235 */       GL11.glEnable(3553);
/* 236 */       GL11.glEnable(2929);
/* 237 */       GL11.glDepthMask(true);
/* 238 */       GL11.glDisable(3042);
/* 239 */       GL11.glPopMatrix();
/*     */     } else {
/* 241 */       double width = (target.getEntityBoundingBox()).maxZ - (target.getEntityBoundingBox()).minZ;
/* 242 */       double height = 0.1D;
/* 243 */       float red = 0.0F;
/* 244 */       float green = 0.5F;
/* 245 */       float blue = 1.0F;
/* 246 */       float alpha = 0.5F;
/* 247 */       float lineRed = 0.0F;
/* 248 */       float lineGreen = 0.5F;
/* 249 */       float lineBlue = 1.0F;
/* 250 */       float lineAlpha = 1.0F;
/* 251 */       float lineWidth = 2.0F;
/* 252 */       RenderUtil.drawEntityESP(x, y + target.getEyeHeight() + 0.25D, z, width, height, red, green, blue, alpha, lineRed, lineGreen, lineBlue, lineAlpha, lineWidth);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\mainmenu\ClientSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */