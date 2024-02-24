/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class SoundEventAccessorComposite
/*    */   implements ISoundEventAccessor<SoundPoolEntry>
/*    */ {
/* 11 */   private final List<ISoundEventAccessor<SoundPoolEntry>> soundPool = Lists.newArrayList();
/* 12 */   private final Random rnd = new Random();
/*    */   
/*    */   private final ResourceLocation soundLocation;
/*    */   private final SoundCategory category;
/*    */   private final double eventPitch;
/*    */   private final double eventVolume;
/*    */   
/*    */   public SoundEventAccessorComposite(ResourceLocation soundLocation, double pitch, double volume, SoundCategory category) {
/* 20 */     this.soundLocation = soundLocation;
/* 21 */     this.eventVolume = volume;
/* 22 */     this.eventPitch = pitch;
/* 23 */     this.category = category;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 28 */     int i = 0;
/*    */     
/* 30 */     for (ISoundEventAccessor<SoundPoolEntry> isoundeventaccessor : this.soundPool)
/*    */     {
/* 32 */       i += isoundeventaccessor.getWeight();
/*    */     }
/*    */     
/* 35 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundPoolEntry cloneEntry() {
/* 40 */     int i = getWeight();
/*    */     
/* 42 */     if (!this.soundPool.isEmpty() && i != 0) {
/*    */       
/* 44 */       int j = this.rnd.nextInt(i);
/*    */       
/* 46 */       for (ISoundEventAccessor<SoundPoolEntry> isoundeventaccessor : this.soundPool) {
/*    */         
/* 48 */         j -= isoundeventaccessor.getWeight();
/*    */         
/* 50 */         if (j < 0) {
/*    */           
/* 52 */           SoundPoolEntry soundpoolentry = isoundeventaccessor.cloneEntry();
/* 53 */           soundpoolentry.setPitch(soundpoolentry.getPitch() * this.eventPitch);
/* 54 */           soundpoolentry.setVolume(soundpoolentry.getVolume() * this.eventVolume);
/* 55 */           return soundpoolentry;
/*    */         } 
/*    */       } 
/*    */       
/* 59 */       return SoundHandler.missing_sound;
/*    */     } 
/*    */ 
/*    */     
/* 63 */     return SoundHandler.missing_sound;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSoundToEventPool(ISoundEventAccessor<SoundPoolEntry> sound) {
/* 69 */     this.soundPool.add(sound);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getSoundEventLocation() {
/* 74 */     return this.soundLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundCategory getSoundCategory() {
/* 79 */     return this.category;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundEventAccessorComposite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */