/*    */ package net.minecraft.client.renderer.block.model;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class BlockPartFace {
/* 11 */   public static final EnumFacing FACING_DEFAULT = null;
/*    */   
/*    */   public final EnumFacing cullFace;
/*    */   public final int tintIndex;
/*    */   public final String texture;
/*    */   public final BlockFaceUV blockFaceUV;
/*    */   
/*    */   public BlockPartFace(EnumFacing cullFaceIn, int tintIndexIn, String textureIn, BlockFaceUV blockFaceUVIn) {
/* 19 */     this.cullFace = cullFaceIn;
/* 20 */     this.tintIndex = tintIndexIn;
/* 21 */     this.texture = textureIn;
/* 22 */     this.blockFaceUV = blockFaceUVIn;
/*    */   }
/*    */   
/*    */   static class Deserializer
/*    */     implements JsonDeserializer<BlockPartFace>
/*    */   {
/*    */     public BlockPartFace deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 29 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 30 */       EnumFacing enumfacing = parseCullFace(jsonobject);
/* 31 */       int i = parseTintIndex(jsonobject);
/* 32 */       String s = parseTexture(jsonobject);
/* 33 */       BlockFaceUV blockfaceuv = (BlockFaceUV)p_deserialize_3_.deserialize((JsonElement)jsonobject, BlockFaceUV.class);
/* 34 */       return new BlockPartFace(enumfacing, i, s, blockfaceuv);
/*    */     }
/*    */ 
/*    */     
/*    */     protected int parseTintIndex(JsonObject p_178337_1_) {
/* 39 */       return JsonUtils.getInt(p_178337_1_, "tintindex", -1);
/*    */     }
/*    */ 
/*    */     
/*    */     private String parseTexture(JsonObject p_178340_1_) {
/* 44 */       return JsonUtils.getString(p_178340_1_, "texture");
/*    */     }
/*    */ 
/*    */     
/*    */     private EnumFacing parseCullFace(JsonObject p_178339_1_) {
/* 49 */       String s = JsonUtils.getString(p_178339_1_, "cullface", "");
/* 50 */       return EnumFacing.byName(s);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\BlockPartFace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */