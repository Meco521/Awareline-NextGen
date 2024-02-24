/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ 
/*     */ public enum ShaderParameterBool
/*     */   implements IExpressionBool {
/*  12 */   IS_ALIVE("is_alive"),
/*  13 */   IS_BURNING("is_burning"),
/*  14 */   IS_CHILD("is_child"),
/*  15 */   IS_GLOWING("is_glowing"),
/*  16 */   IS_HURT("is_hurt"),
/*  17 */   IS_IN_LAVA("is_in_lava"),
/*  18 */   IS_IN_WATER("is_in_water"),
/*  19 */   IS_INVISIBLE("is_invisible"),
/*  20 */   IS_ON_GROUND("is_on_ground"),
/*  21 */   IS_RIDDEN("is_ridden"),
/*  22 */   IS_RIDING("is_riding"),
/*  23 */   IS_SNEAKING("is_sneaking"),
/*  24 */   IS_SPRINTING("is_sprinting"),
/*  25 */   IS_WET("is_wet");
/*     */   private final String name;
/*     */   
/*     */   static {
/*  29 */     VALUES = values();
/*     */   }
/*     */   private final RenderManager renderManager; private static final ShaderParameterBool[] VALUES;
/*     */   ShaderParameterBool(String name) {
/*  33 */     this.name = name;
/*  34 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  39 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/*  44 */     return ExpressionType.BOOL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean eval() {
/*  49 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/*  51 */     if (entity instanceof EntityLivingBase) {
/*     */       
/*  53 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*     */       
/*  55 */       switch (this) {
/*     */         
/*     */         case IS_ALIVE:
/*  58 */           return entitylivingbase.isEntityAlive();
/*     */         
/*     */         case IS_BURNING:
/*  61 */           return entitylivingbase.isBurning();
/*     */         
/*     */         case IS_CHILD:
/*  64 */           return entitylivingbase.isChild();
/*     */         
/*     */         case IS_HURT:
/*  67 */           return (entitylivingbase.hurtTime > 0);
/*     */         
/*     */         case IS_IN_LAVA:
/*  70 */           return entitylivingbase.isInLava();
/*     */         
/*     */         case IS_IN_WATER:
/*  73 */           return entitylivingbase.isInWater();
/*     */         
/*     */         case IS_INVISIBLE:
/*  76 */           return entitylivingbase.isInvisible();
/*     */         
/*     */         case IS_ON_GROUND:
/*  79 */           return entitylivingbase.onGround;
/*     */         
/*     */         case IS_RIDDEN:
/*  82 */           return (entitylivingbase.riddenByEntity != null);
/*     */         
/*     */         case IS_RIDING:
/*  85 */           return entitylivingbase.isRiding();
/*     */         
/*     */         case IS_SNEAKING:
/*  88 */           return entitylivingbase.isSneaking();
/*     */         
/*     */         case IS_SPRINTING:
/*  91 */           return entitylivingbase.isSprinting();
/*     */         
/*     */         case IS_WET:
/*  94 */           return entitylivingbase.isWet();
/*     */       } 
/*     */     
/*     */     } 
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderParameterBool parse(String str) {
/* 103 */     if (str == null)
/*     */     {
/* 105 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 109 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 111 */       ShaderParameterBool shaderparameterbool = VALUES[i];
/*     */       
/* 113 */       if (shaderparameterbool.name.equals(str))
/*     */       {
/* 115 */         return shaderparameterbool;
/*     */       }
/*     */     } 
/*     */     
/* 119 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderParameterBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */