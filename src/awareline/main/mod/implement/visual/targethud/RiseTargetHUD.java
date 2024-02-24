/*     */ package awareline.main.mod.implement.visual.targethud;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.visual.sucks.tenacityColor.ColorUtil;
/*     */ import awareline.main.utility.animations.ContinualAnimation;
/*     */ import awareline.main.utility.animations.TimerUtil;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import awareline.main.utility.render.render.GradientUtils;
/*     */ import awareline.main.utility.render.render.blur.util.GLUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ 
/*     */ public class RiseTargetHUD extends TargetHUD {
/*  21 */   public final List<Particle> particles = new ArrayList<>();
/*  22 */   private final TimerUtil timer = new TimerUtil();
/*  23 */   private final ContinualAnimation animatedHealthBar = new ContinualAnimation();
/*     */   
/*     */   private boolean sentParticles;
/*     */   
/*     */   public RiseTargetHUD() {
/*  28 */     super("Rise");
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float x, float y, float alpha, EntityLivingBase target) {
/*  33 */     setWidth(Math.max(128, Client.instance.FontLoaders.SF20.getStringWidth("Name: " + target.getName()) + 60));
/*  34 */     setHeight(50.0F);
/*     */ 
/*     */ 
/*     */     
/*  38 */     int textColor = ColorUtil.applyOpacity(-1, alpha);
/*     */     
/*  40 */     RoundedUtil.drawRound(x, y, getWidth(), getHeight(), 9.0F, new Color(0, 0, 0, (int)(110.0F * alpha)));
/*  41 */     int scaleOffset = (int)(target.hurtTime * 0.35F);
/*     */     
/*  43 */     float healthPercent = (target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount());
/*  44 */     float var = (getWidth() - 28.0F) * healthPercent;
/*     */     
/*  46 */     this.animatedHealthBar.animate(var, 18);
/*     */     
/*  48 */     GLUtil.startBlend();
/*  49 */     GradientUtils.drawGradientLR(x + 5.0F, y + 40.0F, this.animatedHealthBar.getOutput(), 5.0F, alpha, Client.instance.getClientColorNoHash(255), Client.instance
/*  50 */         .getClientColorNoHash(255));
/*     */ 
/*     */     
/*  53 */     for (Particle p : this.particles) {
/*     */       
/*  55 */       p.x = x + 20.0F;
/*  56 */       p.y = y + 20.0F;
/*  57 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  58 */       if (p.opacity > 4.0F) p.render2D();
/*     */     
/*     */     } 
/*  61 */     if (target instanceof AbstractClientPlayer) {
/*  62 */       double offset = -(target.hurtTime * 23);
/*  63 */       RenderUtil.color(ColorUtil.applyOpacity(new Color(255, (int)(255.0D + offset), (int)(255.0D + offset)), alpha).getRGB());
/*     */       
/*  65 */       renderPlayer2D(x + 5.0F + scaleOffset / 2.0F, y + 5.0F + scaleOffset / 2.0F, (30 - scaleOffset), (30 - scaleOffset), (AbstractClientPlayer)target);
/*  66 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */ 
/*     */     
/*  70 */     if (this.timer.hasTimeElapsed(16L, true)) {
/*  71 */       for (Particle p : this.particles) {
/*  72 */         p.updatePosition();
/*  73 */         if (p.opacity < 1.0F) this.particles.remove(p);
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*  78 */     double healthNum = MathUtil.round((target.getHealth() + target.getAbsorptionAmount()), 1);
/*  79 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  80 */     Client.instance.FontLoaders.SF18.drawString(String.valueOf(healthNum), x + this.animatedHealthBar.getOutput() + 7.0F, y + 39.0F, textColor);
/*  81 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  82 */     Client.instance.FontLoaders.SF20.drawString("Name: " + target.getName(), x + 40.0F, y + 10.0F, textColor);
/*  83 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  84 */     Client.instance.FontLoaders.SF20.drawString("Distance: " + MathUtil.round(mc.thePlayer.getDistanceToEntity((Entity)target), 1) + " Hurt: " + target.hurtTime, x + 40.0F, y + 22.0F, textColor);
/*     */     
/*  86 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  87 */     if (target.hurtTime == 9 && !this.sentParticles) {
/*  88 */       for (int i = 0; i <= 15; i++) {
/*  89 */         Particle particle = new Particle();
/*  90 */         particle.init(x + 20.0F, y + 20.0F, (float)((Math.random() - 0.5D) * 2.0D * 1.4D), (float)((Math.random() - 0.5D) * 2.0D * 1.4D), 
/*  91 */             (float)(Math.random() * 4.0D), Client.instance.getClientColorNoHash(255));
/*  92 */         this.particles.add(particle);
/*     */       } 
/*  94 */       this.sentParticles = true;
/*     */     } 
/*  96 */     if (target.hurtTime == 8) this.sentParticles = false;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderEffects(float x, float y, float alpha, boolean glow) {
/* 102 */     if (glow) {
/* 103 */       RoundedUtil.drawGradientRound(x, y, getWidth(), getHeight(), 6.0F, 
/* 104 */           ColorUtil.applyOpacity(Client.instance.getClientColorNoHash(255), alpha), 
/* 105 */           ColorUtil.applyOpacity(Client.instance.getClientColorNoHash(255), alpha), 
/* 106 */           ColorUtil.applyOpacity(Client.instance.getClientColorNoHash(255), alpha), 
/* 107 */           ColorUtil.applyOpacity(Client.instance.getClientColorNoHash(255), alpha));
/*     */     } else {
/* 109 */       RoundedUtil.drawRound(x, y, getWidth(), getHeight(), 6.0F, ColorUtil.applyOpacity(Color.BLACK, alpha));
/*     */     } 
/*     */   }
/*     */   public static class Particle { public float x;
/*     */     public float y;
/*     */     public float adjustedX;
/*     */     public float adjustedY;
/*     */     
/*     */     public void render2D() {
/* 118 */       RoundedUtil.drawRound(this.x + this.adjustedX, this.y + this.adjustedY, this.size, this.size, this.size / 2.0F - 0.5F, ColorUtil.applyOpacity(this.color, this.opacity / 255.0F));
/*     */     }
/*     */     public float deltaX; public float deltaY; public float size; public float opacity; public Color color;
/*     */     public void updatePosition() {
/* 122 */       for (int i = 1; i <= 2; i++) {
/* 123 */         this.adjustedX += this.deltaX;
/* 124 */         this.adjustedY += this.deltaY;
/* 125 */         this.deltaY = (float)(this.deltaY * 0.97D);
/* 126 */         this.deltaX = (float)(this.deltaX * 0.97D);
/* 127 */         this.opacity--;
/* 128 */         if (this.opacity < 1.0F) this.opacity = 1.0F; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void init(float x, float y, float deltaX, float deltaY, float size, Color color) {
/* 133 */       this.x = x;
/* 134 */       this.y = y;
/* 135 */       this.deltaX = deltaX;
/* 136 */       this.deltaY = deltaY;
/* 137 */       this.size = size;
/* 138 */       this.opacity = 254.0F;
/* 139 */       this.color = color;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\targethud\RiseTargetHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */