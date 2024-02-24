/*     */ package awareline.main.ui.animations;
/*     */ 
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnimationHandler
/*     */ {
/*  16 */   final WeakHashMap<RenderChunk, AnimationData> timeStamps = new WeakHashMap<>();
/*     */ 
/*     */   
/*     */   public void preRender(RenderChunk renderChunk) {
/*  20 */     if (this.timeStamps.containsKey(renderChunk)) {
/*  21 */       AnimationData animationData = this.timeStamps.get(renderChunk);
/*  22 */       long time = animationData.timeStamp;
/*     */       
/*  24 */       if (time == -1L) {
/*  25 */         EnumFacing chunkFacing; time = System.currentTimeMillis();
/*     */         
/*  27 */         animationData.timeStamp = time;
/*     */         
/*  29 */         BlockPos zeroedPlayerPosition = (Minecraft.getMinecraft()).thePlayer.getPosition();
/*  30 */         zeroedPlayerPosition = zeroedPlayerPosition.add(0, -zeroedPlayerPosition.getY(), 0);
/*     */         
/*  32 */         BlockPos zeroedCenteredChunkPos = renderChunk.getPosition().add(8, -renderChunk.getPosition().getY(), 8);
/*     */ 
/*     */         
/*  35 */         BlockPos blockPos1 = zeroedPlayerPosition.subtract((Vec3i)zeroedCenteredChunkPos);
/*     */         
/*  37 */         int difX = Math.abs(blockPos1.getX());
/*  38 */         int difZ = Math.abs(blockPos1.getZ());
/*     */ 
/*     */ 
/*     */         
/*  42 */         if (difX > difZ) {
/*  43 */           if (blockPos1.getX() > 0) {
/*  44 */             chunkFacing = EnumFacing.EAST;
/*     */           } else {
/*  46 */             chunkFacing = EnumFacing.WEST;
/*     */           }
/*     */         
/*  49 */         } else if (blockPos1.getZ() > 0) {
/*  50 */           chunkFacing = EnumFacing.SOUTH;
/*     */         } else {
/*  52 */           chunkFacing = EnumFacing.NORTH;
/*     */         } 
/*     */ 
/*     */         
/*  56 */         animationData.chunkFacing = chunkFacing;
/*     */       } 
/*     */       
/*  59 */       long timeDif = System.currentTimeMillis() - time;
/*     */       
/*  61 */       int animationDuration = 1000;
/*     */       
/*  63 */       if (timeDif < animationDuration) {
/*  64 */         double chunkY = renderChunk.getPosition().getY();
/*     */         
/*  66 */         EnumFacing chunkFacing = animationData.chunkFacing;
/*     */         
/*  68 */         if (chunkFacing != null) {
/*  69 */           Vec3i vec = chunkFacing.getDirectionVec();
/*  70 */           double mod = -(200.0D - 200.0D / animationDuration * timeDif);
/*     */           
/*  72 */           GlStateManager.translate(vec.getX() * mod, 0.0D, vec.getZ() * mod);
/*     */         } 
/*     */       } else {
/*  75 */         this.timeStamps.remove(renderChunk);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPosition(RenderChunk renderChunk, BlockPos position) {
/*  81 */     if ((Minecraft.getMinecraft()).thePlayer != null) {
/*  82 */       BlockPos zeroedPlayerPosition = (Minecraft.getMinecraft()).thePlayer.getPosition();
/*  83 */       zeroedPlayerPosition = zeroedPlayerPosition.add(0, -zeroedPlayerPosition.getY(), 0);
/*  84 */       BlockPos zeroedCenteredChunkPos = position.add(8, -position.getY(), 8);
/*     */       
/*  86 */       EnumFacing chunkFacing = null;
/*     */       
/*  88 */       AnimationData animationData = new AnimationData(-1L, null);
/*  89 */       this.timeStamps.put(renderChunk, animationData);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class AnimationData
/*     */   {
/*     */     public long timeStamp;
/*     */     public EnumFacing chunkFacing;
/*     */     
/*     */     public AnimationData(long timeStamp, EnumFacing chunkFacing) {
/*  99 */       this.timeStamp = timeStamp;
/* 100 */       this.chunkFacing = chunkFacing;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\animations\AnimationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */