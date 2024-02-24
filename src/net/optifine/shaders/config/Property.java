/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ public class Property
/*     */ {
/*  10 */   private int defaultValue = 0;
/*  11 */   private String propertyName = null;
/*  12 */   private String[] propertyValues = null;
/*  13 */   private String userName = null;
/*  14 */   private String[] userValues = null;
/*  15 */   private int value = 0;
/*     */ 
/*     */   
/*     */   public Property(String propertyName, String[] propertyValues, String userName, String[] userValues, int defaultValue) {
/*  19 */     this.propertyName = propertyName;
/*  20 */     this.propertyValues = propertyValues;
/*  21 */     this.userName = userName;
/*  22 */     this.userValues = userValues;
/*  23 */     this.defaultValue = defaultValue;
/*     */     
/*  25 */     if (propertyValues.length != userValues.length)
/*     */     {
/*  27 */       throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length);
/*     */     }
/*  29 */     if (defaultValue >= 0 && defaultValue < propertyValues.length) {
/*     */       
/*  31 */       this.value = defaultValue;
/*     */     }
/*     */     else {
/*     */       
/*  35 */       throw new IllegalArgumentException("Invalid default value: " + defaultValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setPropertyValue(String propVal) {
/*  41 */     if (propVal == null) {
/*     */       
/*  43 */       this.value = this.defaultValue;
/*  44 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  48 */     this.value = ArrayUtils.indexOf((Object[])this.propertyValues, propVal);
/*     */     
/*  50 */     if (this.value >= 0 && this.value < this.propertyValues.length)
/*     */     {
/*  52 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  56 */     this.value = this.defaultValue;
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue(boolean forward) {
/*  64 */     int i = 0;
/*  65 */     int j = this.propertyValues.length - 1;
/*  66 */     this.value = Config.limit(this.value, i, j);
/*     */     
/*  68 */     if (forward) {
/*     */       
/*  70 */       this.value++;
/*     */       
/*  72 */       if (this.value > j)
/*     */       {
/*  74 */         this.value = i;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  79 */       this.value--;
/*     */       
/*  81 */       if (this.value < i)
/*     */       {
/*  83 */         this.value = j;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(int val) {
/*  90 */     this.value = val;
/*     */     
/*  92 */     if (this.value < 0 || this.value >= this.propertyValues.length)
/*     */     {
/*  94 */       this.value = this.defaultValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 100 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserValue() {
/* 105 */     return this.userValues[this.value];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyValue() {
/* 110 */     return this.propertyValues[this.value];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserName() {
/* 115 */     return this.userName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/* 120 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetValue() {
/* 125 */     this.value = this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadFrom(Properties props) {
/* 130 */     resetValue();
/*     */     
/* 132 */     if (props == null)
/*     */     {
/* 134 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 138 */     String s = props.getProperty(this.propertyName);
/* 139 */     return (s == null) ? false : setPropertyValue(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveTo(Properties props) {
/* 145 */     if (props != null)
/*     */     {
/* 147 */       props.setProperty(this.propertyName, getPropertyValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return this.propertyName + "=" + getPropertyValue() + " [" + Config.arrayToString((Object[])this.propertyValues) + "], value: " + this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\Property.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */