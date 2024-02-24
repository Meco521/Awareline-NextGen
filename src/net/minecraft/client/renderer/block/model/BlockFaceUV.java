/*     */ package net.minecraft.client.renderer.block.model;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ public class BlockFaceUV {
/*     */   public float[] uvs;
/*     */   
/*     */   public BlockFaceUV(float[] uvsIn, int rotationIn) {
/*  15 */     this.uvs = uvsIn;
/*  16 */     this.rotation = rotationIn;
/*     */   }
/*     */   public final int rotation;
/*     */   
/*     */   public float func_178348_a(int p_178348_1_) {
/*  21 */     if (this.uvs == null)
/*     */     {
/*  23 */       throw new NullPointerException("uvs");
/*     */     }
/*     */ 
/*     */     
/*  27 */     int i = func_178347_d(p_178348_1_);
/*  28 */     return (i != 0 && i != 1) ? this.uvs[2] : this.uvs[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_178346_b(int p_178346_1_) {
/*  34 */     if (this.uvs == null)
/*     */     {
/*  36 */       throw new NullPointerException("uvs");
/*     */     }
/*     */ 
/*     */     
/*  40 */     int i = func_178347_d(p_178346_1_);
/*  41 */     return (i != 0 && i != 3) ? this.uvs[3] : this.uvs[1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int func_178347_d(int p_178347_1_) {
/*  47 */     return (p_178347_1_ + this.rotation / 90) % 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_178345_c(int p_178345_1_) {
/*  52 */     return (p_178345_1_ + 4 - this.rotation / 90) % 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUvs(float[] uvsIn) {
/*  57 */     if (this.uvs == null)
/*     */     {
/*  59 */       this.uvs = uvsIn;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Deserializer
/*     */     implements JsonDeserializer<BlockFaceUV>
/*     */   {
/*     */     public BlockFaceUV deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  67 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  68 */       float[] afloat = parseUV(jsonobject);
/*  69 */       int i = parseRotation(jsonobject);
/*  70 */       return new BlockFaceUV(afloat, i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int parseRotation(JsonObject p_178291_1_) {
/*  75 */       int i = JsonUtils.getInt(p_178291_1_, "rotation", 0);
/*     */       
/*  77 */       if (i >= 0 && i % 90 == 0 && i / 90 <= 3)
/*     */       {
/*  79 */         return i;
/*     */       }
/*     */ 
/*     */       
/*  83 */       throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private float[] parseUV(JsonObject p_178292_1_) {
/*  89 */       if (!p_178292_1_.has("uv"))
/*     */       {
/*  91 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  95 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_178292_1_, "uv");
/*     */       
/*  97 */       if (jsonarray.size() != 4)
/*     */       {
/*  99 */         throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
/*     */       }
/*     */ 
/*     */       
/* 103 */       float[] afloat = new float[4];
/*     */       
/* 105 */       for (int i = 0; i < afloat.length; i++)
/*     */       {
/* 107 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), "uv[" + i + "]");
/*     */       }
/*     */       
/* 110 */       return afloat;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\BlockFaceUV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */