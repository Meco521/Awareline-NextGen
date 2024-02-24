/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ 
/*     */ public enum RenderEntityParameterBool
/*     */   implements IExpressionBool {
/*  13 */   IS_ALIVE("is_alive"),
/*  14 */   IS_BURNING("is_burning"),
/*  15 */   IS_CHILD("is_child"),
/*  16 */   IS_GLOWING("is_glowing"),
/*  17 */   IS_HURT("is_hurt"),
/*  18 */   IS_IN_LAVA("is_in_lava"),
/*  19 */   IS_IN_WATER("is_in_water"),
/*  20 */   IS_INVISIBLE("is_invisible"),
/*  21 */   IS_ON_GROUND("is_on_ground"),
/*  22 */   IS_RIDDEN("is_ridden"),
/*  23 */   IS_RIDING("is_riding"),
/*  24 */   IS_SNEAKING("is_sneaking"),
/*  25 */   IS_SPRINTING("is_sprinting"),
/*  26 */   IS_WET("is_wet");
/*     */   private final String name;
/*     */   
/*     */   static {
/*  30 */     VALUES = values();
/*     */   }
/*     */   private final RenderManager renderManager; private static final RenderEntityParameterBool[] VALUES;
/*     */   RenderEntityParameterBool(String name) {
/*  34 */     this.name = name;
/*  35 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  40 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  45 */     return ExpressionType.BOOL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean eval() {
/*  50 */     Render render = this.renderManager.renderRender;
/*     */     
/*  52 */     if (render == null)
/*     */     {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  58 */     if (render instanceof RendererLivingEntity) {
/*     */       
/*  60 */       RendererLivingEntity rendererlivingentity = (RendererLivingEntity)render;
/*  61 */       EntityLivingBase entitylivingbase = rendererlivingentity.renderEntity;
/*     */       
/*  63 */       if (entitylivingbase == null)
/*     */       {
/*  65 */         return false;
/*     */       }
/*     */       
/*  68 */       switch (this) {
/*     */         
/*     */         case IS_ALIVE:
/*  71 */           return entitylivingbase.isEntityAlive();
/*     */         
/*     */         case IS_BURNING:
/*  74 */           return entitylivingbase.isBurning();
/*     */         
/*     */         case IS_CHILD:
/*  77 */           return entitylivingbase.isChild();
/*     */         
/*     */         case IS_HURT:
/*  80 */           return (entitylivingbase.hurtTime > 0);
/*     */         
/*     */         case IS_IN_LAVA:
/*  83 */           return entitylivingbase.isInLava();
/*     */         
/*     */         case IS_IN_WATER:
/*  86 */           return entitylivingbase.isInWater();
/*     */         
/*     */         case IS_INVISIBLE:
/*  89 */           return entitylivingbase.isInvisible();
/*     */         
/*     */         case IS_ON_GROUND:
/*  92 */           return entitylivingbase.onGround;
/*     */         
/*     */         case IS_RIDDEN:
/*  95 */           return (entitylivingbase.riddenByEntity != null);
/*     */         
/*     */         case IS_RIDING:
/*  98 */           return entitylivingbase.isRiding();
/*     */         
/*     */         case IS_SNEAKING:
/* 101 */           return entitylivingbase.isSneaking();
/*     */         
/*     */         case IS_SPRINTING:
/* 104 */           return entitylivingbase.isSprinting();
/*     */         
/*     */         case IS_WET:
/* 107 */           return entitylivingbase.isWet();
/*     */       } 
/*     */     
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RenderEntityParameterBool parse(String str) {
/* 117 */     if (str == null)
/*     */     {
/* 119 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 123 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 125 */       RenderEntityParameterBool renderentityparameterbool = VALUES[i];
/*     */       
/* 127 */       if (renderentityparameterbool.name.equals(str))
/*     */       {
/* 129 */         return renderentityparameterbool;
/*     */       }
/*     */     } 
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\anim\RenderEntityParameterBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */