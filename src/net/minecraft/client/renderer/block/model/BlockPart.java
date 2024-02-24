/*     */ package net.minecraft.client.renderer.block.model;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class BlockPart {
/*     */   public final Vector3f positionFrom;
/*     */   public final Vector3f positionTo;
/*     */   public final Map<EnumFacing, BlockPartFace> mapFaces;
/*     */   public final BlockPartRotation partRotation;
/*     */   public final boolean shade;
/*     */   
/*     */   public BlockPart(Vector3f positionFromIn, Vector3f positionToIn, Map<EnumFacing, BlockPartFace> mapFacesIn, BlockPartRotation partRotationIn, boolean shadeIn) {
/*  24 */     this.positionFrom = positionFromIn;
/*  25 */     this.positionTo = positionToIn;
/*  26 */     this.mapFaces = mapFacesIn;
/*  27 */     this.partRotation = partRotationIn;
/*  28 */     this.shade = shadeIn;
/*  29 */     setDefaultUvs();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultUvs() {
/*  34 */     for (Map.Entry<EnumFacing, BlockPartFace> entry : this.mapFaces.entrySet()) {
/*     */       
/*  36 */       float[] afloat = getFaceUvs(entry.getKey());
/*  37 */       ((BlockPartFace)entry.getValue()).blockFaceUV.setUvs(afloat);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] getFaceUvs(EnumFacing p_178236_1_) {
/*     */     float[] afloat;
/*  45 */     switch (p_178236_1_) {
/*     */       
/*     */       case DOWN:
/*     */       case UP:
/*  49 */         afloat = new float[] { this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  63 */         return afloat;case NORTH: case SOUTH: afloat = new float[] { this.positionFrom.x, 16.0F - this.positionTo.y, this.positionTo.x, 16.0F - this.positionFrom.y }; return afloat;case WEST: case EAST: afloat = new float[] { this.positionFrom.z, 16.0F - this.positionTo.y, this.positionTo.z, 16.0F - this.positionFrom.y }; return afloat;
/*     */     } 
/*     */     throw new NullPointerException();
/*     */   }
/*     */   
/*     */   static class Deserializer implements JsonDeserializer<BlockPart> {
/*     */     public BlockPart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  70 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  71 */       Vector3f vector3f = parsePositionFrom(jsonobject);
/*  72 */       Vector3f vector3f1 = parsePositionTo(jsonobject);
/*  73 */       BlockPartRotation blockpartrotation = parseRotation(jsonobject);
/*  74 */       Map<EnumFacing, BlockPartFace> map = parseFacesCheck(p_deserialize_3_, jsonobject);
/*     */       
/*  76 */       if (jsonobject.has("shade") && !JsonUtils.isBoolean(jsonobject, "shade"))
/*     */       {
/*  78 */         throw new JsonParseException("Expected shade to be a Boolean");
/*     */       }
/*     */ 
/*     */       
/*  82 */       boolean flag = JsonUtils.getBoolean(jsonobject, "shade", true);
/*  83 */       return new BlockPart(vector3f, vector3f1, map, blockpartrotation, flag);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private BlockPartRotation parseRotation(JsonObject p_178256_1_) {
/*  89 */       BlockPartRotation blockpartrotation = null;
/*     */       
/*  91 */       if (p_178256_1_.has("rotation")) {
/*     */         
/*  93 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_178256_1_, "rotation");
/*  94 */         Vector3f vector3f = parsePosition(jsonobject, "origin");
/*  95 */         vector3f.scale(0.0625F);
/*  96 */         EnumFacing.Axis enumfacing$axis = parseAxis(jsonobject);
/*  97 */         float f = parseAngle(jsonobject);
/*  98 */         boolean flag = JsonUtils.getBoolean(jsonobject, "rescale", false);
/*  99 */         blockpartrotation = new BlockPartRotation(vector3f, enumfacing$axis, f, flag);
/*     */       } 
/*     */       
/* 102 */       return blockpartrotation;
/*     */     }
/*     */ 
/*     */     
/*     */     private float parseAngle(JsonObject p_178255_1_) {
/* 107 */       float f = JsonUtils.getFloat(p_178255_1_, "angle");
/*     */       
/* 109 */       if (f != 0.0F && MathHelper.abs(f) != 22.5F && MathHelper.abs(f) != 45.0F)
/*     */       {
/* 111 */         throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
/*     */       }
/*     */ 
/*     */       
/* 115 */       return f;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private EnumFacing.Axis parseAxis(JsonObject p_178252_1_) {
/* 121 */       String s = JsonUtils.getString(p_178252_1_, "axis");
/* 122 */       EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.byName(s.toLowerCase());
/*     */       
/* 124 */       if (enumfacing$axis == null)
/*     */       {
/* 126 */         throw new JsonParseException("Invalid rotation axis: " + s);
/*     */       }
/*     */ 
/*     */       
/* 130 */       return enumfacing$axis;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<EnumFacing, BlockPartFace> parseFacesCheck(JsonDeserializationContext p_178250_1_, JsonObject p_178250_2_) {
/* 136 */       Map<EnumFacing, BlockPartFace> map = parseFaces(p_178250_1_, p_178250_2_);
/*     */       
/* 138 */       if (map.isEmpty())
/*     */       {
/* 140 */         throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
/*     */       }
/*     */ 
/*     */       
/* 144 */       return map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<EnumFacing, BlockPartFace> parseFaces(JsonDeserializationContext p_178253_1_, JsonObject p_178253_2_) {
/* 150 */       Map<EnumFacing, BlockPartFace> map = Maps.newEnumMap(EnumFacing.class);
/* 151 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_178253_2_, "faces");
/*     */       
/* 153 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */         
/* 155 */         EnumFacing enumfacing = parseEnumFacing(entry.getKey());
/* 156 */         map.put(enumfacing, (BlockPartFace)p_178253_1_.deserialize(entry.getValue(), BlockPartFace.class));
/*     */       } 
/*     */       
/* 159 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     private EnumFacing parseEnumFacing(String name) {
/* 164 */       EnumFacing enumfacing = EnumFacing.byName(name);
/*     */       
/* 166 */       if (enumfacing == null)
/*     */       {
/* 168 */         throw new JsonParseException("Unknown facing: " + name);
/*     */       }
/*     */ 
/*     */       
/* 172 */       return enumfacing;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector3f parsePositionTo(JsonObject p_178247_1_) {
/* 178 */       Vector3f vector3f = parsePosition(p_178247_1_, "to");
/*     */       
/* 180 */       if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F)
/*     */       {
/* 182 */         return vector3f;
/*     */       }
/*     */ 
/*     */       
/* 186 */       throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector3f parsePositionFrom(JsonObject p_178249_1_) {
/* 192 */       Vector3f vector3f = parsePosition(p_178249_1_, "from");
/*     */       
/* 194 */       if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F)
/*     */       {
/* 196 */         return vector3f;
/*     */       }
/*     */ 
/*     */       
/* 200 */       throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector3f parsePosition(JsonObject p_178251_1_, String p_178251_2_) {
/* 206 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_178251_1_, p_178251_2_);
/*     */       
/* 208 */       if (jsonarray.size() != 3)
/*     */       {
/* 210 */         throw new JsonParseException("Expected 3 " + p_178251_2_ + " values, found: " + jsonarray.size());
/*     */       }
/*     */ 
/*     */       
/* 214 */       float[] afloat = new float[3];
/*     */       
/* 216 */       for (int i = 0; i < afloat.length; i++)
/*     */       {
/* 218 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), p_178251_2_ + "[" + i + "]");
/*     */       }
/*     */       
/* 221 */       return new Vector3f(afloat[0], afloat[1], afloat[2]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\BlockPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */