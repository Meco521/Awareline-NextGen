/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class EnumTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   public <T> TypeAdapter<T> create(Gson p_create_1_, TypeToken<T> p_create_2_) {
/* 20 */     Class<T> oclass = p_create_2_.getRawType();
/*    */     
/* 22 */     if (!oclass.isEnum())
/*    */     {
/* 24 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 28 */     final Map<String, T> map = Maps.newHashMap();
/*    */     
/* 30 */     for (T t : oclass.getEnumConstants())
/*    */     {
/* 32 */       map.put(func_151232_a(t), t);
/*    */     }
/*    */     
/* 35 */     return new TypeAdapter<T>()
/*    */       {
/*    */         public void write(JsonWriter p_write_1_, T p_write_2_) throws IOException
/*    */         {
/* 39 */           if (p_write_2_ == null) {
/*    */             
/* 41 */             p_write_1_.nullValue();
/*    */           }
/*    */           else {
/*    */             
/* 45 */             p_write_1_.value(EnumTypeAdapterFactory.this.func_151232_a(p_write_2_));
/*    */           } 
/*    */         }
/*    */         
/*    */         public T read(JsonReader p_read_1_) throws IOException {
/* 50 */           if (p_read_1_.peek() == JsonToken.NULL) {
/*    */             
/* 52 */             p_read_1_.nextNull();
/* 53 */             return null;
/*    */           } 
/*    */ 
/*    */           
/* 57 */           return (T)map.get(p_read_1_.nextString());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   String func_151232_a(Object p_151232_1_) {
/* 66 */     return (p_151232_1_ instanceof Enum) ? ((Enum)p_151232_1_).name().toLowerCase(Locale.US) : p_151232_1_.toString().toLowerCase(Locale.US);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\EnumTypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */