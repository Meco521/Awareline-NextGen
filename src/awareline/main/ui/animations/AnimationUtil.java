/*     */ package awareline.main.ui.animations;
/*     */ 
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class AnimationUtil
/*     */ {
/*     */   public static double delta;
/*     */   
/*     */   public static float animation(float animation, float target, float speedTarget) {
/*  12 */     float dif = (target - animation) / Math.max(Minecraft.getDebugFPS(), 5.0F) * 15.0F;
/*     */     
/*  14 */     if (dif > 0.0F) {
/*  15 */       dif = Math.max(speedTarget, dif);
/*  16 */       dif = Math.min(target - animation, dif);
/*  17 */     } else if (dif < 0.0F) {
/*  18 */       dif = Math.min(-speedTarget, dif);
/*  19 */       dif = Math.max(target - animation, dif);
/*     */     } 
/*  21 */     return animation + dif;
/*     */   }
/*     */   
/*     */   public static double animation(double animation, double target, double speedTarget) {
/*  25 */     double dif = (target - animation) / Math.max(Minecraft.getDebugFPS(), 5) * speedTarget;
/*  26 */     if (dif > 0.0D) {
/*  27 */       dif = Math.max(speedTarget, dif);
/*  28 */       dif = Math.min(target - animation, dif);
/*  29 */     } else if (dif < 0.0D) {
/*  30 */       dif = Math.min(-speedTarget, dif);
/*  31 */       dif = Math.max(target - animation, dif);
/*     */     } 
/*  33 */     return animation + dif;
/*     */   }
/*     */   
/*     */   public static float moveUDSmooth(float current, float end) {
/*  37 */     return moveUD(current, end);
/*     */   }
/*     */   
/*     */   public static float lstransition(float now, float desired, double speed) {
/*  41 */     double dif = Math.abs(desired - now);
/*  42 */     float a = (float)Math.abs((desired - desired - Math.abs(desired - now)) / (100.0D - speed * 10.0D));
/*  43 */     float x = now;
/*  44 */     if (dif != 0.0D && dif < a) {
/*  45 */       a = (float)dif;
/*     */     }
/*     */     
/*  48 */     if (dif > 0.0D) {
/*  49 */       if (now < desired) {
/*  50 */         x = (float)(now + a * delta);
/*  51 */       } else if (now > desired) {
/*  52 */         x = (float)(now - a * delta);
/*     */       } 
/*     */     } else {
/*  55 */       x = desired;
/*     */     } 
/*     */     
/*  58 */     if (Math.abs(desired - x) < 0.05D && x != desired) {
/*  59 */       x = desired;
/*     */     }
/*     */     
/*  62 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
/*  67 */     float movement = (end - current) * smoothSpeed;
/*  68 */     if (movement > 0.0F) {
/*  69 */       movement = Math.max(minSpeed, movement);
/*  70 */       movement = Math.min(end - current, movement);
/*  71 */     } else if (movement < 0.0F) {
/*  72 */       movement = Math.min(-minSpeed, movement);
/*  73 */       movement = Math.max(end - current, movement);
/*     */     } 
/*  75 */     return current + movement;
/*     */   }
/*     */   
/*     */   public static float smoothAnimation(float ani, float finalState, float speed, float scale) {
/*  79 */     return getAnimationStateFlux(ani, finalState, Math.max(10.0F, Math.abs(ani - finalState) * speed) * scale);
/*     */   }
/*     */   
/*     */   public static float smoothAnimation(float ani, float finalState, float speed) {
/*  83 */     return getAnimationStateFlux(ani, finalState, Math.max(10.0F, Math.abs(ani - finalState) * speed) * 1.0F);
/*     */   }
/*     */   
/*     */   public static float smoothAnimation(float ani, float finalState) {
/*  87 */     return getAnimationStateFlux(ani, finalState, Math.max(10.0F, Math.abs(ani - finalState) * 50.0F) * 0.3F);
/*     */   }
/*     */   
/*     */   public static float moveUD(float current, float end) {
/*  91 */     return lstransition(current, end, 2.0D);
/*     */   }
/*     */   
/*     */   public static double getAnimationStateEasing(double animation, double finalState, double speed) {
/*  95 */     if (animation == finalState) return finalState;
/*     */     
/*  97 */     double add = SimpleRender.delta * Math.max(Math.abs(finalState - animation) * speed, 0.01D);
/*  98 */     animation = (animation < finalState) ? Math.min(animation + add, finalState) : Math.max(animation - add, finalState);
/*  99 */     return animation;
/*     */   }
/*     */   
/*     */   public static float moveUDFaster(float current, float end) {
/* 103 */     float smoothSpeed = SimpleRender.processFPS(0.025F);
/* 104 */     float minSpeed = SimpleRender.processFPS(0.023F);
/* 105 */     boolean larger = (end > current);
/* 106 */     if (smoothSpeed < 0.0F) {
/* 107 */       smoothSpeed = 0.0F;
/* 108 */     } else if (smoothSpeed > 1.0F) {
/* 109 */       smoothSpeed = 1.0F;
/*     */     } 
/* 111 */     if (minSpeed < 0.0F) {
/* 112 */       minSpeed = 0.0F;
/* 113 */     } else if (minSpeed > 1.0F) {
/* 114 */       minSpeed = 1.0F;
/*     */     } 
/* 116 */     float movement = (end - current) * smoothSpeed;
/* 117 */     if (movement > 0.0F) {
/* 118 */       movement = Math.max(minSpeed, movement);
/* 119 */       movement = Math.min(end - current, movement);
/* 120 */     } else if (movement < 0.0F) {
/* 121 */       movement = Math.min(-minSpeed, movement);
/* 122 */       movement = Math.max(end - current, movement);
/*     */     } 
/* 124 */     if (larger) {
/* 125 */       if (end <= current + movement) {
/* 126 */         return end;
/*     */       }
/*     */     }
/* 129 */     else if (end >= current + movement) {
/* 130 */       return end;
/*     */     } 
/*     */     
/* 133 */     return current + movement;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double deltaTime() {
/* 138 */     Minecraft.getMinecraft(); Minecraft.getMinecraft(); return (Minecraft.getDebugFPS() > 0) ? (1.0D / Minecraft.getDebugFPS()) : 1.0D;
/*     */   }
/*     */   
/*     */   public static float getAnimationStateFlux(float animation, float finalState, float speed) {
/* 142 */     float add = (float)(delta * (speed / 1000.0F));
/* 143 */     if (animation < finalState) {
/* 144 */       if (animation + add < finalState) {
/* 145 */         animation += add;
/*     */       } else {
/* 147 */         animation = finalState;
/*     */       } 
/* 149 */     } else if (animation - add > finalState) {
/* 150 */       animation -= add;
/*     */     } else {
/* 152 */       animation = finalState;
/*     */     } 
/* 154 */     return animation;
/*     */   }
/*     */   
/*     */   public static float calculateCompensation(float target, float current, long delta, int speed) {
/* 158 */     float diff = current - target;
/* 159 */     if (delta < 1L) {
/* 160 */       delta = 1L;
/*     */     }
/* 162 */     double v = ((speed * delta / 16L) < 0.25D) ? 0.5D : (speed * delta / 16L);
/* 163 */     if (diff > speed) {
/* 164 */       current -= (float)v;
/* 165 */       if (current < target) {
/* 166 */         current = target;
/*     */       }
/* 168 */     } else if (diff < -speed) {
/* 169 */       current += (float)v;
/* 170 */       if (current > target) {
/* 171 */         current = target;
/*     */       }
/*     */     } else {
/* 174 */       current = target;
/*     */     } 
/* 176 */     return current;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getAnimationState(float animation, float finalState, float speed) {
/* 181 */     float add = (float)(0.01D * speed);
/* 182 */     animation = (animation < finalState) ? Math.min(animation + add, finalState) : Math.max(animation - add, finalState);
/* 183 */     return animation;
/*     */   }
/*     */   
/*     */   public static float animate(float target, float current, float speed) {
/* 187 */     boolean larger = (target > current);
/* 188 */     if (speed < 0.0F) {
/* 189 */       speed = 0.0F;
/* 190 */     } else if (speed > 1.0F) {
/* 191 */       speed = 1.0F;
/*     */     } 
/* 193 */     if (target == current) {
/* 194 */       return target;
/*     */     }
/* 196 */     float dif = Math.max(target, current) - Math.min(target, current);
/* 197 */     float factor = Math.max(dif * speed, 1.0F);
/* 198 */     if (factor < 0.1F) {
/* 199 */       factor = 0.1F;
/*     */     }
/* 201 */     if (larger) {
/* 202 */       if (current + factor > target) {
/* 203 */         current = target;
/*     */       } else {
/* 205 */         current += factor;
/*     */       } 
/* 207 */     } else if (current - factor < target) {
/* 208 */       current = target;
/*     */     } else {
/* 210 */       current -= factor;
/*     */     } 
/* 212 */     return current;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float clamp(float number, float min, float max) {
/* 217 */     return (number < min) ? min : Math.min(number, max);
/*     */   }
/*     */   
/*     */   public static Color getColorAnimationState(Color animation, Color finalState, double speed) {
/* 221 */     float add = (float)(0.01D * speed);
/* 222 */     float animationr = animation.getRed();
/* 223 */     float animationg = animation.getGreen();
/* 224 */     float animationb = animation.getBlue();
/* 225 */     float finalStater = finalState.getRed();
/* 226 */     float finalStateg = finalState.getGreen();
/* 227 */     float finalStateb = finalState.getBlue();
/* 228 */     float finalStatea = finalState.getAlpha();
/*     */     
/* 230 */     if (animationr < finalStater) {
/* 231 */       if (animationr + add < finalStater) {
/* 232 */         animationr += add;
/*     */       } else {
/* 234 */         animationr = finalStater;
/*     */       } 
/* 236 */     } else if (animationr - add > finalStater) {
/* 237 */       animationr -= add;
/*     */     } else {
/* 239 */       animationr = finalStater;
/*     */     } 
/*     */     
/* 242 */     if (animationg < finalStateg) {
/* 243 */       if (animationg + add < finalStateg) {
/* 244 */         animationg += add;
/*     */       } else {
/* 246 */         animationg = finalStateg;
/*     */       } 
/* 248 */     } else if (animationg - add > finalStateg) {
/* 249 */       animationg -= add;
/*     */     } else {
/* 251 */       animationg = finalStateg;
/*     */     } 
/*     */     
/* 254 */     if (animationb < finalStateb) {
/* 255 */       if (animationb + add < finalStateb) {
/* 256 */         animationb += add;
/*     */       } else {
/* 258 */         animationb = finalStateb;
/*     */       } 
/* 260 */     } else if (animationb - add > finalStateb) {
/* 261 */       animationb -= add;
/*     */     } else {
/* 263 */       animationb = finalStateb;
/*     */     } 
/* 265 */     animationr /= 255.0F;
/* 266 */     animationg /= 255.0F;
/* 267 */     animationb /= 255.0F;
/* 268 */     finalStatea /= 255.0F;
/* 269 */     if (animationr > 1.0F) animationr = 1.0F; 
/* 270 */     if (animationg > 1.0F) animationg = 1.0F; 
/* 271 */     if (animationb > 1.0F) animationb = 1.0F; 
/* 272 */     if (finalStatea > 1.0F) finalStatea = 1.0F; 
/* 273 */     return new Color(animationr, animationg, animationb, finalStatea);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\animations\AnimationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */