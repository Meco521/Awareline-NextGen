/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class AnimationMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final List<AnimationFrame> animationFrames;
/*    */   private final int frameWidth;
/*    */   private final int frameHeight;
/*    */   private final int frameTime;
/*    */   private final boolean interpolate;
/*    */   
/*    */   public AnimationMetadataSection(List<AnimationFrame> p_i46088_1_, int p_i46088_2_, int p_i46088_3_, int p_i46088_4_, boolean p_i46088_5_) {
/* 18 */     this.animationFrames = p_i46088_1_;
/* 19 */     this.frameWidth = p_i46088_2_;
/* 20 */     this.frameHeight = p_i46088_3_;
/* 21 */     this.frameTime = p_i46088_4_;
/* 22 */     this.interpolate = p_i46088_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameHeight() {
/* 27 */     return this.frameHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameWidth() {
/* 32 */     return this.frameWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameCount() {
/* 37 */     return this.animationFrames.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameTime() {
/* 42 */     return this.frameTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInterpolate() {
/* 47 */     return this.interpolate;
/*    */   }
/*    */ 
/*    */   
/*    */   private AnimationFrame getAnimationFrame(int p_130072_1_) {
/* 52 */     return this.animationFrames.get(p_130072_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameTimeSingle(int p_110472_1_) {
/* 57 */     AnimationFrame animationframe = getAnimationFrame(p_110472_1_);
/* 58 */     return animationframe.hasNoTime() ? this.frameTime : animationframe.getFrameTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean frameHasTime(int p_110470_1_) {
/* 63 */     return !((AnimationFrame)this.animationFrames.get(p_110470_1_)).hasNoTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameIndex(int p_110468_1_) {
/* 68 */     return ((AnimationFrame)this.animationFrames.get(p_110468_1_)).getFrameIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Integer> getFrameIndexSet() {
/* 73 */     Set<Integer> set = Sets.newHashSet();
/*    */     
/* 75 */     for (AnimationFrame animationframe : this.animationFrames)
/*    */     {
/* 77 */       set.add(Integer.valueOf(animationframe.getFrameIndex()));
/*    */     }
/*    */     
/* 80 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\data\AnimationMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */