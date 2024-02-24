/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ 
/*     */ public enum RenderEntityParameterFloat
/*     */   implements IExpressionFloat {
/*  13 */   LIMB_SWING("limb_swing"),
/*  14 */   LIMB_SWING_SPEED("limb_speed"),
/*  15 */   AGE("age"),
/*  16 */   HEAD_YAW("head_yaw"),
/*  17 */   HEAD_PITCH("head_pitch"),
/*  18 */   SCALE("scale"),
/*  19 */   HEALTH("health"),
/*  20 */   HURT_TIME("hurt_time"),
/*  21 */   IDLE_TIME("idle_time"),
/*  22 */   MAX_HEALTH("max_health"),
/*  23 */   MOVE_FORWARD("move_forward"),
/*  24 */   MOVE_STRAFING("move_strafing"),
/*  25 */   PARTIAL_TICKS("partial_ticks"),
/*  26 */   POS_X("pos_x"),
/*  27 */   POS_Y("pos_y"),
/*  28 */   POS_Z("pos_z"),
/*  29 */   REVENGE_TIME("revenge_time"),
/*  30 */   SWING_PROGRESS("swing_progress");
/*     */   private final String name;
/*     */   
/*     */   static {
/*  34 */     VALUES = values();
/*     */   }
/*     */   private final RenderManager renderManager; private static final RenderEntityParameterFloat[] VALUES;
/*     */   RenderEntityParameterFloat(String name) {
/*  38 */     this.name = name;
/*  39 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  44 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  49 */     return ExpressionType.FLOAT;
/*     */   }
/*     */ 
/*     */   
/*     */   public float eval() {
/*  54 */     Render render = this.renderManager.renderRender;
/*     */     
/*  56 */     if (render == null)
/*     */     {
/*  58 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/*  62 */     if (render instanceof RendererLivingEntity) {
/*     */       
/*  64 */       RendererLivingEntity rendererlivingentity = (RendererLivingEntity)render;
/*     */       
/*  66 */       switch (this) {
/*     */         
/*     */         case LIMB_SWING:
/*  69 */           return rendererlivingentity.renderLimbSwing;
/*     */         
/*     */         case LIMB_SWING_SPEED:
/*  72 */           return rendererlivingentity.renderLimbSwingAmount;
/*     */         
/*     */         case AGE:
/*  75 */           return rendererlivingentity.renderAgeInTicks;
/*     */         
/*     */         case HEAD_YAW:
/*  78 */           return rendererlivingentity.renderHeadYaw;
/*     */         
/*     */         case HEAD_PITCH:
/*  81 */           return rendererlivingentity.renderHeadPitch;
/*     */         
/*     */         case SCALE:
/*  84 */           return rendererlivingentity.renderScaleFactor;
/*     */       } 
/*     */       
/*  87 */       EntityLivingBase entitylivingbase = rendererlivingentity.renderEntity;
/*     */       
/*  89 */       if (entitylivingbase == null)
/*     */       {
/*  91 */         return 0.0F;
/*     */       }
/*     */       
/*  94 */       switch (this) {
/*     */         
/*     */         case HEALTH:
/*  97 */           return entitylivingbase.getHealth();
/*     */         
/*     */         case HURT_TIME:
/* 100 */           return entitylivingbase.hurtTime;
/*     */         
/*     */         case IDLE_TIME:
/* 103 */           return entitylivingbase.getAge();
/*     */         
/*     */         case MAX_HEALTH:
/* 106 */           return entitylivingbase.getMaxHealth();
/*     */         
/*     */         case MOVE_FORWARD:
/* 109 */           return entitylivingbase.moveForward;
/*     */         
/*     */         case MOVE_STRAFING:
/* 112 */           return entitylivingbase.moveStrafing;
/*     */         
/*     */         case POS_X:
/* 115 */           return (float)entitylivingbase.posX;
/*     */         
/*     */         case POS_Y:
/* 118 */           return (float)entitylivingbase.posY;
/*     */         
/*     */         case POS_Z:
/* 121 */           return (float)entitylivingbase.posZ;
/*     */         
/*     */         case REVENGE_TIME:
/* 124 */           return entitylivingbase.getRevengeTimer();
/*     */         
/*     */         case SWING_PROGRESS:
/* 127 */           return entitylivingbase.getSwingProgress(rendererlivingentity.renderPartialTicks);
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 132 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RenderEntityParameterFloat parse(String str) {
/* 138 */     if (str == null)
/*     */     {
/* 140 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 144 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 146 */       RenderEntityParameterFloat renderentityparameterfloat = VALUES[i];
/*     */       
/* 148 */       if (renderentityparameterfloat.name.equals(str))
/*     */       {
/* 150 */         return renderentityparameterfloat;
/*     */       }
/*     */     } 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\anim\RenderEntityParameterFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */