/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.globals.Shader;
/*     */ import awareline.main.mod.implement.visual.targethud.RiseTargetHUD;
/*     */ import awareline.main.mod.implement.visual.targethud.TargetHUD;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.animations.Animation;
/*     */ import awareline.main.utility.animations.Direction;
/*     */ import awareline.main.utility.animations.impl.DecelerateAnimation;
/*     */ import awareline.main.utility.render.ESPUtil;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import awareline.main.utility.tuples.Pair;
/*     */ import java.awt.Color;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ public class TargetHUDMod
/*     */   extends Module {
/*     */   public static TargetHUDMod getInstance;
/*     */   public static boolean renderLayers = true;
/*  35 */   public final Option<Boolean> blur = new Option("Blur", Boolean.valueOf(false)); public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(false));
/*  36 */   private final Mode<String> targetHud = new Mode("Mode", new String[] { "Akrien", "Classic", "Rise" }, "Rise");
/*  37 */   private final Option<Boolean> trackTarget = new Option("TrackTarget", Boolean.valueOf(false));
/*  38 */   private final Mode<String> trackingMode = new Mode("TrackingMode", new String[] { "Middle", "Top", "Left", "Right" }, "Middle", this.trackTarget::get);
/*     */   
/*  40 */   private final Option<Boolean> showMyHealth = new Option("ShowMyHealth", Boolean.valueOf(false));
/*  41 */   private final Animation openAnimation = (Animation)new DecelerateAnimation(175, 0.5D);
/*     */   
/*     */   float healthBarWidth;
/*     */   private EntityLivingBase target;
/*     */   private Vector4f targetVector;
/*     */   private KillAura killAura;
/*     */   
/*     */   public TargetHUDMod() {
/*  49 */     super("TargetHUD", ModuleType.Render);
/*  50 */     addSettings(new Value[] { (Value)this.targetHud, (Value)this.trackTarget, (Value)this.trackingMode, (Value)this.showMyHealth, (Value)this.blur, (Value)this.shadow });
/*  51 */     TargetHUD.init();
/*  52 */     getInstance = this;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender3DEvent(EventRender3D event) {
/*  57 */     if (((Boolean)this.trackTarget.isEnabled()).booleanValue() && this.target != null) {
/*  58 */       for (Entity entity : mc.theWorld.loadedEntityList) {
/*  59 */         if (entity instanceof EntityLivingBase) {
/*  60 */           EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
/*  61 */           if (this.target.equals(entityLivingBase)) {
/*  62 */             this.targetVector = ESPUtil.getEntityPositionsOn2D(entity);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPreRenderEvent(EventRender2D event) {
/*  73 */     TargetHUD currentTargetHUD = TargetHUD.get((String)this.targetHud.get());
/*     */ 
/*     */ 
/*     */     
/*  77 */     awareline.main.ui.draghud.component.impl.TargetHUDMod drag = (awareline.main.ui.draghud.component.impl.TargetHUDMod)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.TargetHUDMod.class);
/*     */     
/*  79 */     drag.setWidth(currentTargetHUD.getWidth());
/*  80 */     drag.setHeight(currentTargetHUD.getHeight());
/*     */     
/*  82 */     if (this.killAura == null) {
/*  83 */       this.killAura = KillAura.getInstance;
/*     */     }
/*     */     
/*  86 */     if (!(mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
/*  87 */       if (KillAura.getInstance.getTarget() == null) {
/*  88 */         this.openAnimation.setDirection(Direction.BACKWARDS);
/*     */       }
/*  90 */       if (this.target == null && KillAura.getInstance.getTarget() != null) {
/*  91 */         this.target = KillAura.getInstance.getTarget();
/*  92 */         this.openAnimation.setDirection(Direction.FORWARDS);
/*     */       }
/*  94 */       else if (KillAura.getInstance.getTarget() == null || this.target != KillAura.getInstance.getTarget()) {
/*  95 */         this.openAnimation.setDirection(Direction.BACKWARDS);
/*     */       } 
/*     */       
/*  98 */       if (this.openAnimation.finished(Direction.BACKWARDS)) {
/*  99 */         ((RiseTargetHUD)TargetHUD.get(RiseTargetHUD.class)).particles.clear();
/* 100 */         this.target = null;
/*     */       } 
/*     */     } else {
/* 103 */       this.openAnimation.setDirection(Direction.FORWARDS);
/* 104 */       this.target = (EntityLivingBase)mc.thePlayer;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRender2DEvent(EventRender2D e) {
/* 113 */     setSuffix((Serializable)this.targetHud.get());
/* 114 */     boolean tracking = (((Boolean)this.trackTarget.isEnabled()).booleanValue() && this.targetVector != null && this.target != mc.thePlayer);
/*     */     
/* 116 */     TargetHUD currentTargetHUD = TargetHUD.get((String)this.targetHud.get());
/*     */ 
/*     */ 
/*     */     
/* 120 */     awareline.main.ui.draghud.component.impl.TargetHUDMod drag = (awareline.main.ui.draghud.component.impl.TargetHUDMod)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.TargetHUDMod.class);
/* 121 */     if (this.target != null) {
/*     */       
/* 123 */       float trackScale = 1.0F;
/* 124 */       float x = drag.getX(), y = drag.getY();
/* 125 */       if (tracking) {
/* 126 */         float newWidth = (this.targetVector.getZ() - this.targetVector.getX()) * 1.4F;
/* 127 */         trackScale = Math.min(1.0F, newWidth / currentTargetHUD.getWidth());
/*     */         
/* 129 */         Pair<Float, Float> coords = getTrackedCoords();
/* 130 */         x = ((Float)coords.getFirst()).floatValue();
/* 131 */         y = ((Float)coords.getSecond()).floatValue();
/*     */       } 
/*     */ 
/*     */       
/* 135 */       RenderUtil.scaleStart(x + drag.getWidth() / 2.0F, y + drag.getHeight() / 2.0F, 
/* 136 */           (float)(0.5D + this.openAnimation.getOutput().floatValue()) * trackScale);
/* 137 */       float alpha = Math.min(1.0F, this.openAnimation.getOutput().floatValue() * 2.0F);
/* 138 */       myHP(x + 48.0F * (this.trackingMode.is("Right") ? -5.5F : 4.0F), y);
/* 139 */       currentTargetHUD.render(x, y, alpha, this.target);
/*     */       
/* 141 */       RenderUtil.scaleEnd();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void myHP(float x, float y) {
/* 147 */     if (!((Boolean)this.showMyHealth.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     float health = mc.thePlayer.getHealth();
/* 152 */     float hpPercentage = mc.thePlayer.isDead ? 0.0F : (health / mc.thePlayer.getMaxHealth());
/* 153 */     float hpWidth = 152.0F * hpPercentage;
/* 154 */     Color healthColor = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue());
/* 155 */     this.healthBarWidth = AnimationUtil.moveUDSmooth(this.healthBarWidth, hpWidth);
/*     */     
/* 157 */     RoundedUtil.drawRound(x + 40.0F, y + 21.0F, 152.0F, 5.0F, 3.0F, new Color(0, 0, 0));
/*     */     
/* 159 */     RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */     
/* 161 */     RoundedUtil.drawRound(x + 40.0F, y + 21.0F, this.healthBarWidth, 5.0F, 3.0F, healthColor);
/*     */     
/* 163 */     float fontX = 3.0F;
/* 164 */     Client.instance.FontLoaders.regular13.drawString(String.format("%.1f", new Object[] { Float.valueOf(hpPercentage * 100.0F) }) + "%", x + 112.5F + 3.0F - (Client.instance.FontLoaders.regular13
/* 165 */         .getStringWidth(String.format("%.1f", new Object[] { Float.valueOf(hpPercentage * 100.0F) }) + "%") / 2), y + 22.0F, -1);
/*     */     
/* 167 */     Client.instance.FontLoaders.NovICON12.drawString("s", x + 96.0F + 3.0F - (Client.instance.FontLoaders.NovICON10.getStringWidth("s") / 2), y + 23.5F, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onShaderEvent(EventShader e) {
/* 173 */     if (!Shader.getInstance.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 177 */     if (e.onBloom() && !((Boolean)this.shadow.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     if (e.onBlur() && !((Boolean)this.blur.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 186 */     TargetHUD currentTargetHUD = TargetHUD.get((String)this.targetHud.get());
/*     */ 
/*     */     
/* 189 */     awareline.main.ui.draghud.component.impl.TargetHUDMod drag = (awareline.main.ui.draghud.component.impl.TargetHUDMod)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.TargetHUDMod.class);
/*     */     
/* 191 */     drag.setWidth(currentTargetHUD.getWidth());
/* 192 */     drag.setHeight(currentTargetHUD.getHeight());
/*     */     
/* 194 */     float x = drag.getX(), y = drag.getY();
/* 195 */     float trackScale = 1.0F;
/* 196 */     if (((Boolean)this.trackTarget.isEnabled()).booleanValue() && this.targetVector != null && this.target != mc.thePlayer) {
/* 197 */       Pair<Float, Float> coords = getTrackedCoords();
/* 198 */       x = ((Float)coords.getFirst()).floatValue();
/* 199 */       y = ((Float)coords.getSecond()).floatValue();
/*     */       
/* 201 */       float newWidth = (this.targetVector.getZ() - this.targetVector.getX()) * 1.4F;
/* 202 */       trackScale = Math.min(1.0F, newWidth / currentTargetHUD.getWidth());
/*     */     } 
/*     */     
/* 205 */     if (this.target != null) {
/*     */       
/* 207 */       boolean glow = e.onBloom();
/* 208 */       RenderUtil.scaleStart(x + drag.getWidth() / 2.0F, y + drag.getHeight() / 2.0F, 
/* 209 */           (float)(0.5D + this.openAnimation.getOutput().floatValue()) * trackScale);
/* 210 */       float alpha = Math.min(1.0F, this.openAnimation.getOutput().floatValue() * 2.0F);
/*     */       
/* 212 */       myHP(x + 48.0F * (this.trackingMode.is("Right") ? -5.5F : 4.0F), y);
/*     */       
/* 214 */       currentTargetHUD.renderEffects(x, y, alpha, glow);
/*     */       
/* 216 */       RenderUtil.scaleEnd();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 223 */     this.target = null;
/* 224 */     super.onEnable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Pair<Float, Float> getTrackedCoords() {
/* 231 */     awareline.main.ui.draghud.component.impl.TargetHUDMod drag = (awareline.main.ui.draghud.component.impl.TargetHUDMod)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.TargetHUDMod.class);
/* 232 */     float width = drag.getWidth(), height = drag.getHeight();
/* 233 */     float x = this.targetVector.getX(), y = this.targetVector.getY();
/* 234 */     float entityWidth = this.targetVector.getZ() - this.targetVector.getX();
/* 235 */     float entityHeight = this.targetVector.getW() - this.targetVector.getY();
/* 236 */     float middleX = x + entityWidth / 2.0F - width / 2.0F;
/* 237 */     float middleY = y + entityHeight / 2.0F - height / 2.0F;
/* 238 */     switch ((String)this.trackingMode.get()) {
/*     */       case "Middle":
/* 240 */         return Pair.of(Float.valueOf(middleX), Float.valueOf(middleY));
/*     */       case "Top":
/* 242 */         return Pair.of(Float.valueOf(middleX), Float.valueOf(y - height / 2.0F + height / 4.0F));
/*     */       case "Left":
/* 244 */         return Pair.of(Float.valueOf(x - width / 2.0F + width / 4.0F), Float.valueOf(middleY));
/*     */     } 
/* 246 */     return Pair.of(Float.valueOf(x + entityWidth - width / 4.0F), Float.valueOf(middleY));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\TargetHUDMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */