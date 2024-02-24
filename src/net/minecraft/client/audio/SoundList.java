/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class SoundList
/*     */ {
/*   9 */   private final List<SoundEntry> soundList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private boolean replaceExisting;
/*     */ 
/*     */   
/*     */   private SoundCategory category;
/*     */ 
/*     */   
/*     */   public List<SoundEntry> getSoundList() {
/*  19 */     return this.soundList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReplaceExisting() {
/*  24 */     return this.replaceExisting;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReplaceExisting(boolean p_148572_1_) {
/*  29 */     this.replaceExisting = p_148572_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/*  34 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSoundCategory(SoundCategory soundCat) {
/*  39 */     this.category = soundCat;
/*     */   }
/*     */   
/*     */   public static class SoundEntry
/*     */   {
/*     */     private String name;
/*  45 */     private float volume = 1.0F;
/*  46 */     private float pitch = 1.0F;
/*  47 */     private int weight = 1;
/*  48 */     private Type type = Type.FILE;
/*     */     
/*     */     private boolean streaming = false;
/*     */     
/*     */     public String getSoundEntryName() {
/*  53 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSoundEntryName(String nameIn) {
/*  58 */       this.name = nameIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getSoundEntryVolume() {
/*  63 */       return this.volume;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSoundEntryVolume(float volumeIn) {
/*  68 */       this.volume = volumeIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getSoundEntryPitch() {
/*  73 */       return this.pitch;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSoundEntryPitch(float pitchIn) {
/*  78 */       this.pitch = pitchIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSoundEntryWeight() {
/*  83 */       return this.weight;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSoundEntryWeight(int weightIn) {
/*  88 */       this.weight = weightIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Type getSoundEntryType() {
/*  93 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSoundEntryType(Type typeIn) {
/*  98 */       this.type = typeIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isStreaming() {
/* 103 */       return this.streaming;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setStreaming(boolean isStreaming) {
/* 108 */       this.streaming = isStreaming;
/*     */     }
/*     */     
/*     */     public enum Type
/*     */     {
/* 113 */       FILE("file"),
/* 114 */       SOUND_EVENT("event");
/*     */       
/*     */       private final String field_148583_c;
/*     */ 
/*     */       
/*     */       Type(String p_i45109_3_) {
/* 120 */         this.field_148583_c = p_i45109_3_;
/*     */       }
/*     */ 
/*     */       
/*     */       public static Type getType(String p_148580_0_) {
/* 125 */         for (Type soundlist$soundentry$type : values()) {
/*     */           
/* 127 */           if (soundlist$soundentry$type.field_148583_c.equals(p_148580_0_))
/*     */           {
/* 129 */             return soundlist$soundentry$type;
/*     */           }
/*     */         } 
/*     */         
/* 133 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */