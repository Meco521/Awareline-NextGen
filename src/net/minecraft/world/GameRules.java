/*     */ package net.minecraft.world;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ public class GameRules
/*     */ {
/*  11 */   private final TreeMap<String, Value> theGameRules = new TreeMap<>();
/*     */ 
/*     */   
/*     */   public GameRules() {
/*  15 */     addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
/*  16 */     addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
/*  17 */     addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
/*  18 */     addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
/*  19 */     addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
/*  20 */     addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
/*  21 */     addGameRule("doEntityDrops", "true", ValueType.BOOLEAN_VALUE);
/*  22 */     addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
/*  23 */     addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
/*  24 */     addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
/*  25 */     addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
/*  26 */     addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
/*  27 */     addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
/*  28 */     addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
/*  29 */     addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addGameRule(String key, String value, ValueType type) {
/*  34 */     this.theGameRules.put(key, new Value(value, type));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrCreateGameRule(String key, String ruleValue) {
/*  39 */     Value gamerules$value = this.theGameRules.get(key);
/*     */     
/*  41 */     if (gamerules$value != null) {
/*     */       
/*  43 */       gamerules$value.setValue(ruleValue);
/*     */     }
/*     */     else {
/*     */       
/*  47 */       addGameRule(key, ruleValue, ValueType.ANY_VALUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String name) {
/*  56 */     Value gamerules$value = this.theGameRules.get(name);
/*  57 */     return (gamerules$value != null) ? gamerules$value.getString() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String name) {
/*  65 */     Value gamerules$value = this.theGameRules.get(name);
/*  66 */     return (gamerules$value != null) ? gamerules$value.getBoolean() : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(String name) {
/*  71 */     Value gamerules$value = this.theGameRules.get(name);
/*  72 */     return (gamerules$value != null) ? gamerules$value.getInt() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT() {
/*  80 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  82 */     for (Map.Entry<String, Value> entry : this.theGameRules.entrySet()) {
/*     */       
/*  84 */       Value gamerules$value = entry.getValue();
/*  85 */       nbttagcompound.setString(entry.getKey(), gamerules$value.getString());
/*     */     } 
/*     */     
/*  88 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  96 */     for (String s : nbt.getKeySet()) {
/*     */       
/*  98 */       String s1 = nbt.getString(s);
/*  99 */       setOrCreateGameRule(s, s1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRules() {
/* 108 */     Set<String> set = this.theGameRules.keySet();
/* 109 */     return set.<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRule(String name) {
/* 117 */     return this.theGameRules.containsKey(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean areSameType(String key, ValueType otherValue) {
/* 122 */     Value gamerules$value = this.theGameRules.get(key);
/* 123 */     return (gamerules$value != null && (gamerules$value.getType() == otherValue || otherValue == ValueType.ANY_VALUE));
/*     */   }
/*     */ 
/*     */   
/*     */   static class Value
/*     */   {
/*     */     private String valueString;
/*     */     private boolean valueBoolean;
/*     */     private int valueInteger;
/*     */     private double valueDouble;
/*     */     private final GameRules.ValueType type;
/*     */     
/*     */     public Value(String value, GameRules.ValueType type) {
/* 136 */       this.type = type;
/* 137 */       setValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(String value) {
/* 142 */       this.valueString = value;
/*     */       
/* 144 */       if (value != null) {
/*     */         
/* 146 */         if (value.equals("false")) {
/*     */           
/* 148 */           this.valueBoolean = false;
/*     */           
/*     */           return;
/*     */         } 
/* 152 */         if (value.equals("true")) {
/*     */           
/* 154 */           this.valueBoolean = true;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 159 */       this.valueBoolean = Boolean.parseBoolean(value);
/* 160 */       this.valueInteger = this.valueBoolean ? 1 : 0;
/*     */ 
/*     */       
/*     */       try {
/* 164 */         this.valueInteger = Integer.parseInt(value);
/*     */       }
/* 166 */       catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 173 */         this.valueDouble = Double.parseDouble(value);
/*     */       }
/* 175 */       catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/* 183 */       return this.valueString;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean() {
/* 188 */       return this.valueBoolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInt() {
/* 193 */       return this.valueInteger;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameRules.ValueType getType() {
/* 198 */       return this.type;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ValueType
/*     */   {
/* 204 */     ANY_VALUE,
/* 205 */     BOOLEAN_VALUE,
/* 206 */     NUMERICAL_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\GameRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */