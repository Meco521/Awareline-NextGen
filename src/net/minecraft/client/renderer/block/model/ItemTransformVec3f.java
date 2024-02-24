/*    */ package net.minecraft.client.renderer.block.model;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import org.lwjgl.util.vector.ReadableVector3f;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ public class ItemTransformVec3f {
/* 12 */   public static final ItemTransformVec3f DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
/*    */   
/*    */   public final Vector3f rotation;
/*    */   public final Vector3f translation;
/*    */   public final Vector3f scale;
/*    */   
/*    */   public ItemTransformVec3f(Vector3f rotation, Vector3f translation, Vector3f scale) {
/* 19 */     this.rotation = new Vector3f((ReadableVector3f)rotation);
/* 20 */     this.translation = new Vector3f((ReadableVector3f)translation);
/* 21 */     this.scale = new Vector3f((ReadableVector3f)scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 26 */     if (this == p_equals_1_)
/*    */     {
/* 28 */       return true;
/*    */     }
/* 30 */     if (getClass() != p_equals_1_.getClass())
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 36 */     ItemTransformVec3f itemtransformvec3f = (ItemTransformVec3f)p_equals_1_;
/* 37 */     return !this.rotation.equals(itemtransformvec3f.rotation) ? false : (!this.scale.equals(itemtransformvec3f.scale) ? false : this.translation.equals(itemtransformvec3f.translation));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     int i = this.rotation.hashCode();
/* 44 */     i = 31 * i + this.translation.hashCode();
/* 45 */     i = 31 * i + this.scale.hashCode();
/* 46 */     return i;
/*    */   }
/*    */   
/*    */   static class Deserializer
/*    */     implements JsonDeserializer<ItemTransformVec3f> {
/* 51 */     private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
/* 52 */     private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
/* 53 */     private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);
/*    */ 
/*    */     
/*    */     public ItemTransformVec3f deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 57 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 58 */       Vector3f vector3f = parseVector3f(jsonobject, "rotation", ROTATION_DEFAULT);
/* 59 */       Vector3f vector3f1 = parseVector3f(jsonobject, "translation", TRANSLATION_DEFAULT);
/* 60 */       vector3f1.scale(0.0625F);
/* 61 */       vector3f1.x = MathHelper.clamp_float(vector3f1.x, -1.5F, 1.5F);
/* 62 */       vector3f1.y = MathHelper.clamp_float(vector3f1.y, -1.5F, 1.5F);
/* 63 */       vector3f1.z = MathHelper.clamp_float(vector3f1.z, -1.5F, 1.5F);
/* 64 */       Vector3f vector3f2 = parseVector3f(jsonobject, "scale", SCALE_DEFAULT);
/* 65 */       vector3f2.x = MathHelper.clamp_float(vector3f2.x, -4.0F, 4.0F);
/* 66 */       vector3f2.y = MathHelper.clamp_float(vector3f2.y, -4.0F, 4.0F);
/* 67 */       vector3f2.z = MathHelper.clamp_float(vector3f2.z, -4.0F, 4.0F);
/* 68 */       return new ItemTransformVec3f(vector3f, vector3f1, vector3f2);
/*    */     }
/*    */ 
/*    */     
/*    */     private Vector3f parseVector3f(JsonObject jsonObject, String key, Vector3f defaultValue) {
/* 73 */       if (!jsonObject.has(key))
/*    */       {
/* 75 */         return defaultValue;
/*    */       }
/*    */ 
/*    */       
/* 79 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonObject, key);
/*    */       
/* 81 */       if (jsonarray.size() != 3)
/*    */       {
/* 83 */         throw new JsonParseException("Expected 3 " + key + " values, found: " + jsonarray.size());
/*    */       }
/*    */ 
/*    */       
/* 87 */       float[] afloat = new float[3];
/*    */       
/* 89 */       for (int i = 0; i < afloat.length; i++)
/*    */       {
/* 91 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), key + "[" + i + "]");
/*    */       }
/*    */       
/* 94 */       return new Vector3f(afloat[0], afloat[1], afloat[2]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\ItemTransformVec3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */