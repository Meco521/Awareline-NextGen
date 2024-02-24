/*     */ package net.minecraft.command;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandResultStats
/*     */ {
/*  16 */   private static final int NUM_RESULT_TYPES = (Type.values()).length;
/*  17 */   private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];
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
/*  29 */   private String[] entitiesID = STRING_RESULT_TYPES;
/*  30 */   private String[] objectives = STRING_RESULT_TYPES;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommandStatScore(final ICommandSender sender, Type resultTypeIn, int scorePoint) {
/*  40 */     String s = this.entitiesID[resultTypeIn.getTypeID()];
/*     */     
/*  42 */     if (s != null) {
/*     */       String s1;
/*  44 */       ICommandSender icommandsender = new ICommandSender()
/*     */         {
/*     */           public String getName()
/*     */           {
/*  48 */             return sender.getName();
/*     */           }
/*     */           
/*     */           public IChatComponent getDisplayName() {
/*  52 */             return sender.getDisplayName();
/*     */           }
/*     */           
/*     */           public void addChatMessage(IChatComponent component) {
/*  56 */             sender.addChatMessage(component);
/*     */           }
/*     */           
/*     */           public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  60 */             return true;
/*     */           }
/*     */           
/*     */           public BlockPos getPosition() {
/*  64 */             return sender.getPosition();
/*     */           }
/*     */           
/*     */           public Vec3 getPositionVector() {
/*  68 */             return sender.getPositionVector();
/*     */           }
/*     */           
/*     */           public World getEntityWorld() {
/*  72 */             return sender.getEntityWorld();
/*     */           }
/*     */           
/*     */           public Entity getCommandSenderEntity() {
/*  76 */             return sender.getCommandSenderEntity();
/*     */           }
/*     */           
/*     */           public boolean sendCommandFeedback() {
/*  80 */             return sender.sendCommandFeedback();
/*     */           }
/*     */           
/*     */           public void setCommandStat(CommandResultStats.Type type, int amount) {
/*  84 */             sender.setCommandStat(type, amount);
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  91 */         s1 = CommandBase.getEntityName(icommandsender, s);
/*     */       }
/*  93 */       catch (EntityNotFoundException var11) {
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/*  98 */       String s2 = this.objectives[resultTypeIn.getTypeID()];
/*     */       
/* 100 */       if (s2 != null) {
/*     */         
/* 102 */         Scoreboard scoreboard = sender.getEntityWorld().getScoreboard();
/* 103 */         ScoreObjective scoreobjective = scoreboard.getObjective(s2);
/*     */         
/* 105 */         if (scoreobjective != null)
/*     */         {
/* 107 */           if (scoreboard.entityHasObjective(s1, scoreobjective)) {
/*     */             
/* 109 */             Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/* 110 */             score.setScorePoints(scorePoint);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStatsFromNBT(NBTTagCompound tagcompound) {
/* 119 */     if (tagcompound.hasKey("CommandStats", 10)) {
/*     */       
/* 121 */       NBTTagCompound nbttagcompound = tagcompound.getCompoundTag("CommandStats");
/*     */       
/* 123 */       for (Type commandresultstats$type : Type.values()) {
/*     */         
/* 125 */         String s = commandresultstats$type.getTypeName() + "Name";
/* 126 */         String s1 = commandresultstats$type.getTypeName() + "Objective";
/*     */         
/* 128 */         if (nbttagcompound.hasKey(s, 8) && nbttagcompound.hasKey(s1, 8)) {
/*     */           
/* 130 */           String s2 = nbttagcompound.getString(s);
/* 131 */           String s3 = nbttagcompound.getString(s1);
/* 132 */           setScoreBoardStat(this, commandresultstats$type, s2, s3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStatsToNBT(NBTTagCompound tagcompound) {
/* 140 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 142 */     for (Type commandresultstats$type : Type.values()) {
/*     */       
/* 144 */       String s = this.entitiesID[commandresultstats$type.getTypeID()];
/* 145 */       String s1 = this.objectives[commandresultstats$type.getTypeID()];
/*     */       
/* 147 */       if (s != null && s1 != null) {
/*     */         
/* 149 */         nbttagcompound.setString(commandresultstats$type.getTypeName() + "Name", s);
/* 150 */         nbttagcompound.setString(commandresultstats$type.getTypeName() + "Objective", s1);
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     if (!nbttagcompound.hasNoTags())
/*     */     {
/* 156 */       tagcompound.setTag("CommandStats", (NBTBase)nbttagcompound);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setScoreBoardStat(CommandResultStats stats, Type resultType, String entityID, String objectiveName) {
/* 168 */     if (entityID != null && !entityID.isEmpty() && objectiveName != null && !objectiveName.isEmpty()) {
/*     */       
/* 170 */       if (stats.entitiesID == STRING_RESULT_TYPES || stats.objectives == STRING_RESULT_TYPES) {
/*     */         
/* 172 */         stats.entitiesID = new String[NUM_RESULT_TYPES];
/* 173 */         stats.objectives = new String[NUM_RESULT_TYPES];
/*     */       } 
/*     */       
/* 176 */       stats.entitiesID[resultType.getTypeID()] = entityID;
/* 177 */       stats.objectives[resultType.getTypeID()] = objectiveName;
/*     */     }
/*     */     else {
/*     */       
/* 181 */       removeScoreBoardStat(stats, resultType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeScoreBoardStat(CommandResultStats resultStatsIn, Type resultTypeIn) {
/* 190 */     if (resultStatsIn.entitiesID != STRING_RESULT_TYPES && resultStatsIn.objectives != STRING_RESULT_TYPES) {
/*     */       
/* 192 */       resultStatsIn.entitiesID[resultTypeIn.getTypeID()] = null;
/* 193 */       resultStatsIn.objectives[resultTypeIn.getTypeID()] = null;
/* 194 */       boolean flag = true;
/*     */       
/* 196 */       for (Type commandresultstats$type : Type.values()) {
/*     */         
/* 198 */         if (resultStatsIn.entitiesID[commandresultstats$type.getTypeID()] != null && resultStatsIn.objectives[commandresultstats$type.getTypeID()] != null) {
/*     */           
/* 200 */           flag = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 205 */       if (flag) {
/*     */         
/* 207 */         resultStatsIn.entitiesID = STRING_RESULT_TYPES;
/* 208 */         resultStatsIn.objectives = STRING_RESULT_TYPES;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAllStats(CommandResultStats resultStatsIn) {
/* 218 */     for (Type commandresultstats$type : Type.values())
/*     */     {
/* 220 */       setScoreBoardStat(this, commandresultstats$type, resultStatsIn.entitiesID[commandresultstats$type.getTypeID()], resultStatsIn.objectives[commandresultstats$type.getTypeID()]);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 226 */     SUCCESS_COUNT(0, "SuccessCount"),
/* 227 */     AFFECTED_BLOCKS(1, "AffectedBlocks"),
/* 228 */     AFFECTED_ENTITIES(2, "AffectedEntities"),
/* 229 */     AFFECTED_ITEMS(3, "AffectedItems"),
/* 230 */     QUERY_RESULT(4, "QueryResult");
/*     */     
/*     */     final int typeID;
/*     */     
/*     */     final String typeName;
/*     */     
/*     */     Type(int id, String name) {
/* 237 */       this.typeID = id;
/* 238 */       this.typeName = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTypeID() {
/* 243 */       return this.typeID;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTypeName() {
/* 248 */       return this.typeName;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String[] getTypeNames() {
/* 253 */       String[] astring = new String[(values()).length];
/* 254 */       int i = 0;
/*     */       
/* 256 */       for (Type commandresultstats$type : values())
/*     */       {
/* 258 */         astring[i++] = commandresultstats$type.typeName;
/*     */       }
/*     */       
/* 261 */       return astring;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Type getTypeByName(String name) {
/* 266 */       for (Type commandresultstats$type : values()) {
/*     */         
/* 268 */         if (commandresultstats$type.typeName.equals(name))
/*     */         {
/* 270 */           return commandresultstats$type;
/*     */         }
/*     */       } 
/*     */       
/* 274 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandResultStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */