/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BreakingFour
/*    */   extends BakedQuad
/*    */ {
/*    */   private final TextureAtlasSprite texture;
/*    */   
/*    */   public BreakingFour(BakedQuad quad, TextureAtlasSprite textureIn) {
/* 13 */     super(Arrays.copyOf(quad.getVertexData(), (quad.getVertexData()).length), quad.tintIndex, FaceBakery.getFacingFromVertexData(quad.getVertexData()));
/* 14 */     this.texture = textureIn;
/* 15 */     remapQuad();
/* 16 */     fixVertexData();
/*    */   }
/*    */ 
/*    */   
/*    */   private void remapQuad() {
/* 21 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 23 */       remapVert(i);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void remapVert(int vertex) {
/* 29 */     int i = this.vertexData.length / 4;
/* 30 */     int j = i * vertex;
/* 31 */     float f = Float.intBitsToFloat(this.vertexData[j]);
/* 32 */     float f1 = Float.intBitsToFloat(this.vertexData[j + 1]);
/* 33 */     float f2 = Float.intBitsToFloat(this.vertexData[j + 2]);
/* 34 */     float f3 = 0.0F;
/* 35 */     float f4 = 0.0F;
/*    */     
/* 37 */     switch (this.face) {
/*    */       
/*    */       case DOWN:
/* 40 */         f3 = f * 16.0F;
/* 41 */         f4 = (1.0F - f2) * 16.0F;
/*    */         break;
/*    */       
/*    */       case UP:
/* 45 */         f3 = f * 16.0F;
/* 46 */         f4 = f2 * 16.0F;
/*    */         break;
/*    */       
/*    */       case NORTH:
/* 50 */         f3 = (1.0F - f) * 16.0F;
/* 51 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 55 */         f3 = f * 16.0F;
/* 56 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */       
/*    */       case WEST:
/* 60 */         f3 = f2 * 16.0F;
/* 61 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */       
/*    */       case EAST:
/* 65 */         f3 = (1.0F - f2) * 16.0F;
/* 66 */         f4 = (1.0F - f1) * 16.0F;
/*    */         break;
/*    */     } 
/* 69 */     this.vertexData[j + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU(f3));
/* 70 */     this.vertexData[j + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(f4));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\BreakingFour.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */