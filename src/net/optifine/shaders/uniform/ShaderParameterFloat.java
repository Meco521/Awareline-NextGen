/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public enum ShaderParameterFloat
/*     */ {
/*  10 */   BIOME("biome"),
/*  11 */   TEMPERATURE("temperature"),
/*  12 */   RAINFALL("rainfall"),
/*  13 */   HELD_ITEM_ID(Shaders.uniform_heldItemId),
/*  14 */   HELD_BLOCK_LIGHT_VALUE(Shaders.uniform_heldBlockLightValue),
/*  15 */   HELD_ITEM_ID2(Shaders.uniform_heldItemId2),
/*  16 */   HELD_BLOCK_LIGHT_VALUE2(Shaders.uniform_heldBlockLightValue2),
/*  17 */   WORLD_TIME(Shaders.uniform_worldTime),
/*  18 */   WORLD_DAY(Shaders.uniform_worldDay),
/*  19 */   MOON_PHASE(Shaders.uniform_moonPhase),
/*  20 */   FRAME_COUNTER(Shaders.uniform_frameCounter),
/*  21 */   FRAME_TIME(Shaders.uniform_frameTime),
/*  22 */   FRAME_TIME_COUNTER(Shaders.uniform_frameTimeCounter),
/*  23 */   SUN_ANGLE(Shaders.uniform_sunAngle),
/*  24 */   SHADOW_ANGLE(Shaders.uniform_shadowAngle),
/*  25 */   RAIN_STRENGTH(Shaders.uniform_rainStrength),
/*  26 */   ASPECT_RATIO(Shaders.uniform_aspectRatio),
/*  27 */   VIEW_WIDTH(Shaders.uniform_viewWidth),
/*  28 */   VIEW_HEIGHT(Shaders.uniform_viewHeight),
/*  29 */   NEAR(Shaders.uniform_near),
/*  30 */   FAR(Shaders.uniform_far),
/*  31 */   WETNESS(Shaders.uniform_wetness),
/*  32 */   EYE_ALTITUDE(Shaders.uniform_eyeAltitude),
/*  33 */   EYE_BRIGHTNESS(Shaders.uniform_eyeBrightness, new String[] { "x", "y" }),
/*  34 */   TERRAIN_TEXTURE_SIZE(Shaders.uniform_terrainTextureSize, new String[] { "x", "y" }),
/*  35 */   TERRRAIN_ICON_SIZE(Shaders.uniform_terrainIconSize),
/*  36 */   IS_EYE_IN_WATER(Shaders.uniform_isEyeInWater),
/*  37 */   NIGHT_VISION(Shaders.uniform_nightVision),
/*  38 */   BLINDNESS(Shaders.uniform_blindness),
/*  39 */   SCREEN_BRIGHTNESS(Shaders.uniform_screenBrightness),
/*  40 */   HIDE_GUI(Shaders.uniform_hideGUI),
/*  41 */   CENTER_DEPT_SMOOTH(Shaders.uniform_centerDepthSmooth),
/*  42 */   ATLAS_SIZE(Shaders.uniform_atlasSize, new String[] { "x", "y" }),
/*  43 */   CAMERA_POSITION(Shaders.uniform_cameraPosition, new String[] { "x", "y", "z" }),
/*  44 */   PREVIOUS_CAMERA_POSITION(Shaders.uniform_previousCameraPosition, new String[] { "x", "y", "z" }),
/*  45 */   SUN_POSITION(Shaders.uniform_sunPosition, new String[] { "x", "y", "z" }),
/*  46 */   MOON_POSITION(Shaders.uniform_moonPosition, new String[] { "x", "y", "z" }),
/*  47 */   SHADOW_LIGHT_POSITION(Shaders.uniform_shadowLightPosition, new String[] { "x", "y", "z" }),
/*  48 */   UP_POSITION(Shaders.uniform_upPosition, new String[] { "x", "y", "z" }),
/*  49 */   SKY_COLOR(Shaders.uniform_skyColor, new String[] { "r", "g", "b" }),
/*  50 */   GBUFFER_PROJECTION(Shaders.uniform_gbufferProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  51 */   GBUFFER_PROJECTION_INVERSE(Shaders.uniform_gbufferProjectionInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  52 */   GBUFFER_PREVIOUS_PROJECTION(Shaders.uniform_gbufferPreviousProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  53 */   GBUFFER_MODEL_VIEW(Shaders.uniform_gbufferModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  54 */   GBUFFER_MODEL_VIEW_INVERSE(Shaders.uniform_gbufferModelViewInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  55 */   GBUFFER_PREVIOUS_MODEL_VIEW(Shaders.uniform_gbufferPreviousModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  56 */   SHADOW_PROJECTION(Shaders.uniform_shadowProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  57 */   SHADOW_PROJECTION_INVERSE(Shaders.uniform_shadowProjectionInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  58 */   SHADOW_MODEL_VIEW(Shaders.uniform_shadowModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }),
/*  59 */   SHADOW_MODEL_VIEW_INVERSE(Shaders.uniform_shadowModelViewInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" });
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private ShaderUniformBase uniform;
/*     */   private String[] indexNames1;
/*     */   private String[] indexNames2;
/*     */   
/*     */   ShaderParameterFloat(String name) {
/*  68 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   ShaderParameterFloat(ShaderUniformBase uniform) {
/*  73 */     this.name = uniform.getName();
/*  74 */     this.uniform = uniform;
/*     */     
/*  76 */     if (!instanceOf(uniform, new Class[] { ShaderUniform1f.class, ShaderUniform1i.class }))
/*     */     {
/*  78 */       throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + uniform.getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   ShaderParameterFloat(ShaderUniformBase uniform, String[] indexNames1) {
/*  84 */     this.name = uniform.getName();
/*  85 */     this.uniform = uniform;
/*  86 */     this.indexNames1 = indexNames1;
/*     */     
/*  88 */     if (!instanceOf(uniform, new Class[] { ShaderUniform2i.class, ShaderUniform2f.class, ShaderUniform3f.class, ShaderUniform4f.class }))
/*     */     {
/*  90 */       throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + uniform.getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   ShaderParameterFloat(ShaderUniformBase uniform, String[] indexNames1, String[] indexNames2) {
/*  96 */     this.name = uniform.getName();
/*  97 */     this.uniform = uniform;
/*  98 */     this.indexNames1 = indexNames1;
/*  99 */     this.indexNames2 = indexNames2;
/*     */     
/* 101 */     if (!instanceOf(uniform, new Class[] { ShaderUniformM4.class }))
/*     */     {
/* 103 */       throw new IllegalArgumentException("Invalid uniform type for enum: " + this + ", uniform: " + uniform.getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 109 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShaderUniformBase getUniform() {
/* 114 */     return this.uniform;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIndexNames1() {
/* 119 */     return this.indexNames1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIndexNames2() {
/* 124 */     return this.indexNames2;
/*     */   }
/*     */ 
/*     */   
/*     */   public float eval(int index1, int index2) {
/* 129 */     if (this.indexNames1 == null || (index1 >= 0 && index1 <= this.indexNames1.length)) {
/*     */       
/* 131 */       if (this.indexNames2 == null || (index2 >= 0 && index2 <= this.indexNames2.length)) {
/*     */         BlockPos blockpos2; BiomeGenBase biomegenbase2; BlockPos blockpos1; BiomeGenBase biomegenbase1; BlockPos pos; BiomeGenBase biome;
/* 133 */         switch (this) {
/*     */           
/*     */           case BIOME:
/* 136 */             blockpos2 = Shaders.getCameraPosition();
/* 137 */             biomegenbase2 = Shaders.getCurrentWorld().getBiomeGenForCoords(blockpos2);
/* 138 */             return biomegenbase2.biomeID;
/*     */           
/*     */           case TEMPERATURE:
/* 141 */             blockpos1 = Shaders.getCameraPosition();
/* 142 */             biomegenbase1 = Shaders.getCurrentWorld().getBiomeGenForCoords(blockpos1);
/* 143 */             return (biomegenbase1 != null) ? biomegenbase1.getFloatTemperature(blockpos1) : 0.0F;
/*     */           
/*     */           case RAINFALL:
/* 146 */             pos = Shaders.getCameraPosition();
/* 147 */             biome = Shaders.getCurrentWorld().getBiomeGenForCoords(pos);
/* 148 */             return (biome != null) ? biome.getFloatRainfall() : 0.0F;
/*     */         } 
/*     */         
/* 151 */         if (this.uniform instanceof ShaderUniform1f)
/*     */         {
/* 153 */           return ((ShaderUniform1f)this.uniform).getValue();
/*     */         }
/* 155 */         if (this.uniform instanceof ShaderUniform1i)
/*     */         {
/* 157 */           return ((ShaderUniform1i)this.uniform).getValue();
/*     */         }
/* 159 */         if (this.uniform instanceof ShaderUniform2i)
/*     */         {
/* 161 */           return ((ShaderUniform2i)this.uniform).getValue()[index1];
/*     */         }
/* 163 */         if (this.uniform instanceof ShaderUniform2f)
/*     */         {
/* 165 */           return ((ShaderUniform2f)this.uniform).getValue()[index1];
/*     */         }
/* 167 */         if (this.uniform instanceof ShaderUniform3f)
/*     */         {
/* 169 */           return ((ShaderUniform3f)this.uniform).getValue()[index1];
/*     */         }
/* 171 */         if (this.uniform instanceof ShaderUniform4f)
/*     */         {
/* 173 */           return ((ShaderUniform4f)this.uniform).getValue()[index1];
/*     */         }
/* 175 */         if (this.uniform instanceof ShaderUniformM4)
/*     */         {
/* 177 */           return ((ShaderUniformM4)this.uniform).getValue(index1, index2);
/*     */         }
/*     */ 
/*     */         
/* 181 */         throw new IllegalArgumentException("Unknown uniform type: " + this);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 187 */       Config.warn("Invalid index2, parameter: " + this + ", index: " + index2);
/* 188 */       return 0.0F;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 193 */     Config.warn("Invalid index1, parameter: " + this + ", index: " + index1);
/* 194 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean instanceOf(Object obj, Class... classes) {
/* 200 */     if (obj == null)
/*     */     {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     Class<?> oclass = obj.getClass();
/*     */     
/* 208 */     for (int i = 0; i < classes.length; i++) {
/*     */       
/* 210 */       Class oclass1 = classes[i];
/*     */       
/* 212 */       if (oclass1.isAssignableFrom(oclass))
/*     */       {
/* 214 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 218 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shader\\uniform\ShaderParameterFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */