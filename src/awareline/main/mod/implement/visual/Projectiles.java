/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Projectiles
/*     */   extends Module {
/*     */   private MovingObjectPosition blockCollision;
/*     */   
/*     */   public Projectiles() {
/*  28 */     super("Projectiles", ModuleType.Render);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void arrowESP(EventRender3D event) {
/*  34 */     if (mc.thePlayer.inventory.getCurrentItem() != null) {
/*  35 */       EntityPlayerSP player = mc.thePlayer;
/*  36 */       ItemStack stack = player.inventory.getCurrentItem();
/*  37 */       int item = Item.getIdFromItem(mc.thePlayer.getHeldItem().getItem());
/*  38 */       if (item == 261 || item == 368 || item == 332 || item == 344) {
/*  39 */         AxisAlignedBB aim; double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks - Math.cos(Math.toRadians(player.rotationYaw)) * 0.1599999964237213D;
/*  40 */         double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks + player.getEyeHeight() - 0.1D;
/*  41 */         double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks - Math.sin(Math.toRadians(player.rotationYaw)) * 0.1599999964237213D;
/*     */         
/*  43 */         double itemBow = (stack.getItem() instanceof net.minecraft.item.ItemBow) ? 1.0D : 0.4000000059604645D;
/*     */         
/*  45 */         double yaw = Math.toRadians(player.rotationYaw);
/*  46 */         double pitch = Math.toRadians(player.rotationPitch);
/*     */         
/*  48 */         double trajectoryX = -Math.sin(yaw) * Math.cos(pitch) * itemBow;
/*  49 */         double trajectoryY = -Math.sin(pitch) * itemBow;
/*  50 */         double trajectoryZ = Math.cos(yaw) * Math.cos(pitch) * itemBow;
/*  51 */         double trajectory = Math.sqrt(trajectoryX * trajectoryX + trajectoryY * trajectoryY + trajectoryZ * trajectoryZ);
/*     */         
/*  53 */         trajectoryX /= trajectory;
/*  54 */         trajectoryY /= trajectory;
/*  55 */         trajectoryZ /= trajectory;
/*     */         
/*  57 */         if (stack.getItem() instanceof net.minecraft.item.ItemBow) {
/*  58 */           float bowPower = (72000 - player.getItemInUseCount()) / 20.0F;
/*  59 */           bowPower = (bowPower * bowPower + bowPower * 2.0F) / 3.0F;
/*     */           
/*  61 */           if (bowPower > 1.0F) {
/*  62 */             bowPower = 1.0F;
/*     */           }
/*     */           
/*  65 */           bowPower *= 3.0F;
/*  66 */           trajectoryX *= bowPower;
/*  67 */           trajectoryY *= bowPower;
/*  68 */           trajectoryZ *= bowPower;
/*     */         } else {
/*  70 */           trajectoryX *= 1.5D;
/*  71 */           trajectoryY *= 1.5D;
/*  72 */           trajectoryZ *= 1.5D;
/*     */         } 
/*     */         
/*  75 */         GL11.glPushMatrix();
/*  76 */         GL11.glDisable(3553);
/*  77 */         GL11.glEnable(3042);
/*  78 */         GL11.glBlendFunc(770, 771);
/*  79 */         GL11.glDisable(2929);
/*  80 */         GL11.glDepthMask(false);
/*  81 */         GL11.glEnable(2848);
/*  82 */         GL11.glLineWidth(2.0F);
/*  83 */         double gravity = (stack.getItem() instanceof net.minecraft.item.ItemBow) ? 0.05D : 0.03D;
/*     */         
/*  85 */         GL11.glColor4f(((Double)HUD.r.getValue()).intValue() / 255.0F, ((Double)HUD.g.getValue()).intValue() / 255.0F, ((Double)HUD.b
/*  86 */             .getValue()).intValue() / 255.0F, 0.5F);
/*     */         
/*  88 */         GL11.glBegin(3);
/*     */         
/*  90 */         for (int i = 0; i < 2000; i++) {
/*  91 */           GL11.glVertex3d(posX - (mc.getRenderManager()).renderPosX, posY - (mc.getRenderManager()).renderPosY, posZ - (mc.getRenderManager()).renderPosZ);
/*     */           
/*  93 */           posX += trajectoryX * 0.1D;
/*  94 */           posY += trajectoryY * 0.1D;
/*  95 */           posZ += trajectoryZ * 0.1D;
/*     */           
/*  97 */           trajectoryX *= 0.999D;
/*  98 */           trajectoryY *= 0.999D;
/*  99 */           trajectoryZ *= 0.999D;
/*     */           
/* 101 */           trajectoryY -= gravity * 0.1D;
/* 102 */           Vec3 vec = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
/* 103 */           this.blockCollision = mc.theWorld.rayTraceBlocks(vec, new Vec3(posX, posY, posZ));
/*     */           
/* 105 */           for (Entity o : mc.theWorld.getLoadedEntityList()) {
/* 106 */             if (o instanceof EntityLivingBase && !(o instanceof EntityPlayerSP)) {
/* 107 */               EntityLivingBase entity = (EntityLivingBase)o;
/* 108 */               AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(0.3D, 0.3D, 0.3D);
/* 109 */               MovingObjectPosition entityCollision = entityBoundingBox.calculateIntercept(vec, new Vec3(posX, posY, posZ));
/*     */               
/* 111 */               if (entityCollision != null) {
/* 112 */                 this.blockCollision = entityCollision;
/*     */               }
/*     */               
/* 115 */               if (entityCollision != null) {
/* 116 */                 GL11.glColor4f(1.0F, 0.0F, 0.2F, 0.5F);
/*     */               }
/*     */               
/* 119 */               if (entityCollision != null) {
/* 120 */                 this.blockCollision = entityCollision;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 125 */           if (this.blockCollision != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 130 */         GL11.glEnd();
/*     */         
/* 132 */         double renderX = posX - (mc.getRenderManager()).renderPosX;
/* 133 */         double renderY = posY - (mc.getRenderManager()).renderPosY;
/* 134 */         double renderZ = posZ - (mc.getRenderManager()).renderPosZ;
/*     */         
/* 136 */         GL11.glPushMatrix();
/* 137 */         GL11.glTranslated(renderX - 0.5D, renderY - 0.5D, renderZ - 0.5D);
/*     */ 
/*     */         
/* 140 */         switch (this.blockCollision.sideHit.getIndex()) {
/*     */           case 2:
/*     */           case 3:
/* 143 */             GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 144 */             aim = new AxisAlignedBB(0.0D, 0.5D, -1.0D, 1.0D, 0.45D, 0.0D);
/*     */             break;
/*     */           
/*     */           case 4:
/*     */           case 5:
/* 149 */             GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 150 */             aim = new AxisAlignedBB(0.0D, -0.5D, 0.0D, 1.0D, -0.45D, 1.0D);
/*     */             break;
/*     */           
/*     */           default:
/* 154 */             aim = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.45D, 1.0D);
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 159 */         drawBox(aim);
/* 160 */         RenderGlobal.drawSelectionBoundingBox(aim);
/* 161 */         GL11.glPopMatrix();
/* 162 */         GL11.glDisable(3042);
/* 163 */         GL11.glEnable(3553);
/* 164 */         GL11.glEnable(2929);
/* 165 */         GL11.glDepthMask(true);
/* 166 */         GL11.glDisable(2848);
/* 167 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     for (Object object : mc.theWorld.loadedEntityList) {
/* 172 */       if (object instanceof EntityArrow) {
/* 173 */         EntityArrow arrow = (EntityArrow)object;
/* 174 */         if (!arrow.inGround) {
/* 175 */           double posX = arrow.posX;
/* 176 */           double posY = arrow.posY;
/* 177 */           double posZ = arrow.posZ;
/* 178 */           double motionX = arrow.motionX;
/* 179 */           double motionY = arrow.motionY;
/* 180 */           double motionZ = arrow.motionZ;
/*     */           
/* 182 */           boolean hasLanded2 = false;
/* 183 */           enableRender3D();
/* 184 */           setColor(3196666);
/* 185 */           GL11.glLineWidth(2.0F);
/* 186 */           GL11.glBegin(3);
/* 187 */           for (int limit2 = 0; !hasLanded2 && limit2 < 300; limit2++) {
/* 188 */             Vec3 posBefore2 = new Vec3(posX, posY, posZ);
/* 189 */             Vec3 posAfter2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
/* 190 */             MovingObjectPosition landingPosition2 = mc.theWorld.rayTraceBlocks(posBefore2, posAfter2, false, true, false);
/* 191 */             if (landingPosition2 != null) {
/* 192 */               hasLanded2 = true;
/*     */             }
/* 194 */             posX += motionX;
/* 195 */             posY += motionY;
/* 196 */             posZ += motionZ;
/* 197 */             BlockPos var20 = new BlockPos(posX, posY, posZ);
/* 198 */             Block var21 = mc.theWorld.getBlockState(var20).getBlock();
/* 199 */             if (var21.getMaterial() == Material.water) {
/* 200 */               motionX *= 0.6D;
/* 201 */               motionY *= 0.6D;
/* 202 */               motionZ *= 0.6D;
/*     */             } else {
/* 204 */               motionX *= 0.99D;
/* 205 */               motionY *= 0.99D;
/* 206 */               motionZ *= 0.99D;
/*     */             } 
/* 208 */             motionY -= 0.05000000074505806D;
/* 209 */             GL11.glVertex3d(posX - (mc.getRenderManager()).renderPosX, posY - (mc.getRenderManager()).renderPosY, posZ - (mc.getRenderManager()).renderPosZ);
/*     */           } 
/* 211 */           GL11.glEnd();
/* 212 */           disableRender3D();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawBox(AxisAlignedBB bb) {
/* 219 */     GL11.glBegin(7);
/* 220 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 221 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 222 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 223 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 224 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 225 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 226 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 227 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 228 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 229 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 230 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 231 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 232 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 233 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 234 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 235 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 236 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 237 */     GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 238 */     GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 239 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 240 */     GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 241 */     GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 242 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 243 */     GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 244 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public void setColor(int colorHex) {
/* 248 */     float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
/* 249 */     float red = (colorHex >> 16 & 0xFF) / 255.0F;
/* 250 */     float green = (colorHex >> 8 & 0xFF) / 255.0F;
/* 251 */     float blue = (colorHex & 0xFF) / 255.0F;
/*     */     
/* 253 */     GL11.glColor4f(red, green, blue, (alpha == 0.0F) ? 1.0F : alpha);
/*     */   }
/*     */   
/*     */   private void enableRender3D() {
/* 257 */     GL11.glDepthMask(false);
/* 258 */     GL11.glDisable(2929);
/* 259 */     GL11.glDisable(3008);
/* 260 */     GL11.glEnable(3042);
/* 261 */     GL11.glDisable(3553);
/* 262 */     GL11.glBlendFunc(770, 771);
/* 263 */     GL11.glEnable(2848);
/* 264 */     GL11.glHint(3154, 4354);
/* 265 */     GL11.glLineWidth(1.0F);
/*     */   }
/*     */   
/*     */   private void disableRender3D() {
/* 269 */     GL11.glDepthMask(true);
/* 270 */     GL11.glEnable(2929);
/* 271 */     GL11.glEnable(3553);
/* 272 */     GL11.glDisable(3042);
/* 273 */     GL11.glEnable(3008);
/* 274 */     GL11.glDisable(2848);
/* 275 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Projectiles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */