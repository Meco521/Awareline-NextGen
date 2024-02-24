/*     */ package awareline.main.mod.implement.visual;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.event.events.world.updateEvents.EventLivingUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.ParticlesUtils.Location;
/*     */ import awareline.main.mod.implement.visual.sucks.ParticlesUtils.Particles;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class DamageParticle extends Module {
/*  27 */   private final Mode<String> mode = new Mode("DisplayMode", new String[] { "Red", "Gold", "Texture" }, "Red");
/*  28 */   public final Numbers<Double> sizeV = new Numbers("Size", Double.valueOf(3.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*     */   private final HashMap<EntityLivingBase, Float> healthMap;
/*     */   private final List<Particles> particles;
/*     */   private boolean isCriti;
/*     */   
/*     */   public DamageParticle() {
/*  34 */     super("DamageParticle", new String[] { "DMGParticle" }, ModuleType.Render);
/*  35 */     addSettings(new Value[] { (Value)this.mode, (Value)this.sizeV });
/*  36 */     this.healthMap = new HashMap<>();
/*  37 */     this.particles = new ArrayList<>();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onLivingUpdate(EventLivingUpdate e) {
/*  42 */     EntityLivingBase entity = (EntityLivingBase)e.getEntity();
/*  43 */     if (entity == mc.thePlayer) {
/*     */       return;
/*     */     }
/*  46 */     if (!this.healthMap.containsKey(entity)) {
/*  47 */       this.healthMap.put(entity, Float.valueOf(entity.getHealth()));
/*     */     }
/*  49 */     float floatValue = ((Float)this.healthMap.get(entity)).floatValue();
/*  50 */     float health = entity.getHealth();
/*  51 */     if (floatValue != health) {
/*     */       try {
/*     */         String text;
/*  54 */         if (floatValue - health < 0.0F) {
/*  55 */           text = EnumChatFormatting.GREEN + String.valueOf(roundToPlace(((floatValue - health) * -1.0F), 1));
/*     */         }
/*  57 */         else if (this.isCriti) {
/*  58 */           text = EnumChatFormatting.AQUA + String.valueOf(roundToPlace((floatValue - health), 1));
/*  59 */           this.isCriti = false;
/*     */         } else {
/*  61 */           text = (this.mode.isCurrentMode("Red") ? (String)EnumChatFormatting.RED : (String)EnumChatFormatting.YELLOW) + String.valueOf(roundToPlace((floatValue - health), 1));
/*     */         } 
/*     */         
/*  64 */         Location location = new Location(entity);
/*  65 */         location.setY((entity.getEntityBoundingBox()).minY + ((entity.getEntityBoundingBox()).maxY - (entity.getEntityBoundingBox()).minY) / 2.0D);
/*  66 */         location.setX(location.getX() - 0.5D + (new Random(System.currentTimeMillis())).nextInt(5) * 0.1D);
/*  67 */         location.setZ(location.getZ() - 0.5D + (new Random(System.currentTimeMillis() + 1L)).nextInt(5) * 0.1D);
/*  68 */         this.particles.add(new Particles(location, text));
/*  69 */         this.healthMap.remove(entity);
/*  70 */         this.healthMap.put(entity, Float.valueOf(entity.getHealth()));
/*  71 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onSendPacket(EventPacketSend e) {
/*  79 */     if (e.getPacket() instanceof C02PacketUseEntity) {
/*  80 */       C02PacketUseEntity c02 = (C02PacketUseEntity)e.getPacket();
/*  81 */       if (c02.getAction() == C02PacketUseEntity.Action.INTERACT) {
/*  82 */         this.isCriti = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender3D e) {
/*  89 */     for (Particles p : this.particles) {
/*  90 */       float textY; double x = p.location.getX();
/*  91 */       double n = x - (mc.getRenderManager()).renderPosX;
/*  92 */       double y = p.location.getY();
/*  93 */       double n2 = y - (mc.getRenderManager()).renderPosY;
/*  94 */       double z = p.location.getZ();
/*  95 */       double n3 = z - (mc.getRenderManager()).renderPosZ;
/*  96 */       GlStateManager.pushMatrix();
/*  97 */       GlStateManager.enablePolygonOffset();
/*  98 */       GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
/*  99 */       GlStateManager.translate((float)n, (float)n2, (float)n3);
/* 100 */       GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/*     */       
/* 102 */       if (mc.gameSettings.thirdPersonView == 2) {
/* 103 */         textY = -1.0F;
/*     */       } else {
/* 105 */         textY = 1.0F;
/*     */       } 
/* 107 */       GlStateManager.rotate((mc.getRenderManager()).playerViewX, textY, 0.0F, 0.0F);
/* 108 */       double size = ((Double)this.sizeV.getValue()).doubleValue() / 100.0D;
/* 109 */       GlStateManager.scale(-size, -size, size);
/* 110 */       RenderUtil.enableGL2D();
/* 111 */       RenderUtil.disableGL2D();
/* 112 */       GL11.glDepthMask(false);
/* 113 */       if (this.mode.isCurrentMode("Texture")) {
/* 114 */         mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
/* 115 */         if (p.text.contains(String.valueOf(EnumChatFormatting.GREEN))) {
/* 116 */           mc.ingameGUI.drawTexturedModalRect(-(mc.fontRendererObj.getStringWidth(p.text) / 2), -(mc.fontRendererObj.FONT_HEIGHT - 1), 52, 0, 9, 9);
/*     */         } else {
/* 118 */           mc.ingameGUI.drawTexturedModalRect(-(mc.fontRendererObj.getStringWidth(p.text) / 2), -(mc.fontRendererObj.FONT_HEIGHT - 1), 124, 0, 9, 9);
/*     */         } 
/*     */       } else {
/* 121 */         mc.fontRendererObj.drawStringWithShadow(p.text, -(mc.fontRendererObj.getStringWidth(p.text) / 2), -(mc.fontRendererObj.FONT_HEIGHT - 1), 0);
/*     */       } 
/* 123 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 124 */       GL11.glDepthMask(true);
/* 125 */       GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
/* 126 */       GlStateManager.disablePolygonOffset();
/* 127 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public double roundToPlace(double p_roundToPlace_0_, int p_roundToPlace_2_) {
/* 132 */     if (p_roundToPlace_2_ < 0) {
/* 133 */       throw new IllegalArgumentException();
/*     */     }
/* 135 */     return (new BigDecimal(p_roundToPlace_0_)).setScale(p_roundToPlace_2_, RoundingMode.HALF_UP).doubleValue();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/*     */     try {
/* 141 */       this.particles.forEach(this::lambda$onUpdate$0);
/* 142 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void lambda$onUpdate$0(Particles update) {
/* 148 */     update.ticks++;
/* 149 */     if (update.ticks <= 10) {
/* 150 */       update.location.setY(update.location.getY() + update.ticks * 0.005D);
/*     */     }
/* 152 */     if (update.ticks > 20)
/* 153 */       this.particles.remove(update); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\DamageParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */