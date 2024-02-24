/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S37PacketStatistics;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.TupleIntJsonSerializable;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class StatisticsFile
/*     */   extends StatFileWriter {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final MinecraftServer mcServer;
/*     */   private final File statsFile;
/*  32 */   private final Set<StatBase> field_150888_e = Sets.newHashSet();
/*  33 */   private int field_150885_f = -300;
/*     */   
/*     */   private boolean field_150886_g = false;
/*     */   
/*     */   public StatisticsFile(MinecraftServer serverIn, File statsFileIn) {
/*  38 */     this.mcServer = serverIn;
/*  39 */     this.statsFile = statsFileIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStatFile() {
/*  44 */     if (this.statsFile.isFile()) {
/*     */       
/*     */       try {
/*     */         
/*  48 */         this.statsData.clear();
/*  49 */         this.statsData.putAll(parseJson(FileUtils.readFileToString(this.statsFile)));
/*     */       }
/*  51 */       catch (IOException ioexception) {
/*     */         
/*  53 */         logger.error("Couldn't read statistics file " + this.statsFile, ioexception);
/*     */       }
/*  55 */       catch (JsonParseException jsonparseexception) {
/*     */         
/*  57 */         logger.error("Couldn't parse statistics file " + this.statsFile, (Throwable)jsonparseexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveStatFile() {
/*     */     try {
/*  66 */       FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
/*     */     }
/*  68 */     catch (IOException ioexception) {
/*     */       
/*  70 */       logger.error("Couldn't save stats", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/*  79 */     int i = statIn.isAchievement() ? readStat(statIn) : 0;
/*  80 */     super.unlockAchievement(playerIn, statIn, p_150873_3_);
/*  81 */     this.field_150888_e.add(statIn);
/*     */     
/*  83 */     if (statIn.isAchievement() && i == 0 && p_150873_3_ > 0) {
/*     */       
/*  85 */       this.field_150886_g = true;
/*     */       
/*  87 */       if (this.mcServer.isAnnouncingPlayerAchievements())
/*     */       {
/*  89 */         this.mcServer.getConfigurationManager().sendChatMsg((IChatComponent)new ChatComponentTranslation("chat.type.achievement", new Object[] { playerIn.getDisplayName(), statIn.createChatComponent() }));
/*     */       }
/*     */     } 
/*     */     
/*  93 */     if (statIn.isAchievement() && i > 0 && p_150873_3_ == 0) {
/*     */       
/*  95 */       this.field_150886_g = true;
/*     */       
/*  97 */       if (this.mcServer.isAnnouncingPlayerAchievements())
/*     */       {
/*  99 */         this.mcServer.getConfigurationManager().sendChatMsg((IChatComponent)new ChatComponentTranslation("chat.type.achievement.taken", new Object[] { playerIn.getDisplayName(), statIn.createChatComponent() }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<StatBase> func_150878_c() {
/* 106 */     Set<StatBase> set = Sets.newHashSet(this.field_150888_e);
/* 107 */     this.field_150888_e.clear();
/* 108 */     this.field_150886_g = false;
/* 109 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<StatBase, TupleIntJsonSerializable> parseJson(String p_150881_1_) {
/* 114 */     JsonElement jsonelement = (new JsonParser()).parse(p_150881_1_);
/*     */     
/* 116 */     if (!jsonelement.isJsonObject())
/*     */     {
/* 118 */       return Maps.newHashMap();
/*     */     }
/*     */ 
/*     */     
/* 122 */     JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 123 */     Map<StatBase, TupleIntJsonSerializable> map = Maps.newHashMap();
/*     */     
/* 125 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */       
/* 127 */       StatBase statbase = StatList.getOneShotStat(entry.getKey());
/*     */       
/* 129 */       if (statbase != null) {
/*     */         
/* 131 */         TupleIntJsonSerializable tupleintjsonserializable = new TupleIntJsonSerializable();
/*     */         
/* 133 */         if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
/*     */           
/* 135 */           tupleintjsonserializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
/*     */         }
/* 137 */         else if (((JsonElement)entry.getValue()).isJsonObject()) {
/*     */           
/* 139 */           JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();
/*     */           
/* 141 */           if (jsonobject1.has("value") && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber())
/*     */           {
/* 143 */             tupleintjsonserializable.setIntegerValue(jsonobject1.getAsJsonPrimitive("value").getAsInt());
/*     */           }
/*     */           
/* 146 */           if (jsonobject1.has("progress") && statbase.func_150954_l() != null) {
/*     */             
/*     */             try {
/*     */               
/* 150 */               Constructor<? extends IJsonSerializable> constructor = statbase.func_150954_l().getConstructor(new Class[0]);
/* 151 */               IJsonSerializable ijsonserializable = constructor.newInstance(new Object[0]);
/* 152 */               ijsonserializable.fromJson(jsonobject1.get("progress"));
/* 153 */               tupleintjsonserializable.setJsonSerializableValue(ijsonserializable);
/*     */             }
/* 155 */             catch (Throwable throwable) {
/*     */               
/* 157 */               logger.warn("Invalid statistic progress in " + this.statsFile, throwable);
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 162 */         map.put(statbase, tupleintjsonserializable);
/*     */         
/*     */         continue;
/*     */       } 
/* 166 */       logger.warn("Invalid statistic in " + this.statsFile + ": Don't know what " + (String)entry.getKey() + " is");
/*     */     } 
/*     */ 
/*     */     
/* 170 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String dumpJson(Map<StatBase, TupleIntJsonSerializable> p_150880_0_) {
/* 176 */     JsonObject jsonobject = new JsonObject();
/*     */     
/* 178 */     for (Map.Entry<StatBase, TupleIntJsonSerializable> entry : p_150880_0_.entrySet()) {
/*     */       
/* 180 */       if (((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue() != null) {
/*     */         
/* 182 */         JsonObject jsonobject1 = new JsonObject();
/* 183 */         jsonobject1.addProperty("value", Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).getIntegerValue()));
/*     */ 
/*     */         
/*     */         try {
/* 187 */           jsonobject1.add("progress", ((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue().getSerializableElement());
/*     */         }
/* 189 */         catch (Throwable throwable) {
/*     */           
/* 191 */           logger.warn("Couldn't save statistic " + ((StatBase)entry.getKey()).getStatName() + ": error serializing progress", throwable);
/*     */         } 
/*     */         
/* 194 */         jsonobject.add(((StatBase)entry.getKey()).statId, (JsonElement)jsonobject1);
/*     */         
/*     */         continue;
/*     */       } 
/* 198 */       jsonobject.addProperty(((StatBase)entry.getKey()).statId, Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).getIntegerValue()));
/*     */     } 
/*     */ 
/*     */     
/* 202 */     return jsonobject.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150877_d() {
/* 207 */     this.field_150888_e.addAll(this.statsData.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150876_a(EntityPlayerMP p_150876_1_) {
/* 212 */     int i = this.mcServer.getTickCounter();
/* 213 */     Map<StatBase, Integer> map = Maps.newHashMap();
/*     */     
/* 215 */     if (this.field_150886_g || i - this.field_150885_f > 300) {
/*     */       
/* 217 */       this.field_150885_f = i;
/*     */       
/* 219 */       for (StatBase statbase : func_150878_c())
/*     */       {
/* 221 */         map.put(statbase, Integer.valueOf(readStat(statbase)));
/*     */       }
/*     */     } 
/*     */     
/* 225 */     p_150876_1_.playerNetServerHandler.sendPacket((Packet)new S37PacketStatistics(map));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendAchievements(EntityPlayerMP player) {
/* 230 */     Map<StatBase, Integer> map = Maps.newHashMap();
/*     */     
/* 232 */     for (Achievement achievement : AchievementList.achievementList) {
/*     */       
/* 234 */       if (hasAchievementUnlocked(achievement)) {
/*     */         
/* 236 */         map.put(achievement, Integer.valueOf(readStat(achievement)));
/* 237 */         this.field_150888_e.remove(achievement);
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     player.playerNetServerHandler.sendPacket((Packet)new S37PacketStatistics(map));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_150879_e() {
/* 246 */     return this.field_150886_g;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\StatisticsFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */