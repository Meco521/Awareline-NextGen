/*     */ package net.minecraft.client.audio;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ import paulscode.sound.SoundSystemConfig;
/*     */ import paulscode.sound.SoundSystemException;
/*     */ import paulscode.sound.SoundSystemLogger;
/*     */ import paulscode.sound.Source;
/*     */ import paulscode.sound.libraries.LibraryLWJGLOpenAL;
/*     */ 
/*     */ public class SoundManager {
/*  32 */   static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
/*  33 */   static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final SoundHandler sndHandler;
/*     */ 
/*     */   
/*     */   final GameSettings options;
/*     */ 
/*     */   
/*     */   SoundSystemStarterThread sndSystem;
/*     */ 
/*     */   
/*     */   boolean loaded;
/*     */ 
/*     */   
/*  48 */   private int playTime = 0;
/*  49 */   private final Map<String, ISound> playingSounds = (Map<String, ISound>)HashBiMap.create();
/*     */   
/*     */   private final Map<ISound, String> invPlayingSounds;
/*     */   private final Map<ISound, SoundPoolEntry> playingSoundPoolEntries;
/*     */   private final Multimap<SoundCategory, String> categorySounds;
/*     */   private final List<ITickableSound> tickableSounds;
/*     */   private final Map<ISound, Integer> delayedSounds;
/*     */   private final Map<String, Integer> playingSoundsStopTime;
/*     */   
/*     */   public SoundManager(SoundHandler p_i45119_1_, GameSettings p_i45119_2_) {
/*  59 */     this.invPlayingSounds = (Map<ISound, String>)((BiMap)this.playingSounds).inverse();
/*  60 */     this.playingSoundPoolEntries = Maps.newHashMap();
/*  61 */     this.categorySounds = (Multimap<SoundCategory, String>)HashMultimap.create();
/*  62 */     this.tickableSounds = Lists.newArrayList();
/*  63 */     this.delayedSounds = Maps.newHashMap();
/*  64 */     this.playingSoundsStopTime = Maps.newHashMap();
/*  65 */     this.sndHandler = p_i45119_1_;
/*  66 */     this.options = p_i45119_2_;
/*     */ 
/*     */     
/*     */     try {
/*  70 */       SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
/*  71 */       SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
/*     */     }
/*  73 */     catch (SoundSystemException soundsystemexception) {
/*     */       
/*  75 */       logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", (Throwable)soundsystemexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadSoundSystem() {
/*  81 */     unloadSoundSystem();
/*  82 */     loadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void loadSoundSystem() {
/*  90 */     if (!this.loaded) {
/*     */       
/*     */       try {
/*     */         
/*  94 */         (new Thread(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/*  98 */                 SoundSystemConfig.setLogger(new SoundSystemLogger()
/*     */                     {
/*     */                       public void message(String p_message_1_, int p_message_2_)
/*     */                       {
/* 102 */                         if (!p_message_1_.isEmpty())
/*     */                         {
/* 104 */                           SoundManager.logger.info(p_message_1_);
/*     */                         }
/*     */                       }
/*     */                       
/*     */                       public void importantMessage(String p_importantMessage_1_, int p_importantMessage_2_) {
/* 109 */                         if (!p_importantMessage_1_.isEmpty())
/*     */                         {
/* 111 */                           SoundManager.logger.warn(p_importantMessage_1_);
/*     */                         }
/*     */                       }
/*     */                       
/*     */                       public void errorMessage(String p_errorMessage_1_, String p_errorMessage_2_, int p_errorMessage_3_) {
/* 116 */                         if (!p_errorMessage_2_.isEmpty()) {
/*     */                           
/* 118 */                           SoundManager.logger.error("Error in class '" + p_errorMessage_1_ + "'");
/* 119 */                           SoundManager.logger.error(p_errorMessage_2_);
/*     */                         } 
/*     */                       }
/*     */                     },  );
/* 123 */                 SoundManager.this.sndSystem = new SoundManager.SoundSystemStarterThread();
/* 124 */                 SoundManager.this.loaded = true;
/* 125 */                 SoundManager.this.sndSystem.setMasterVolume(SoundManager.this.options.getSoundLevel(SoundCategory.MASTER));
/* 126 */                 SoundManager.logger.info(SoundManager.LOG_MARKER, "Sound engine started");
/*     */               }
/* 128 */             }"Sound Library Loader")).start();
/*     */       }
/* 130 */       catch (RuntimeException runtimeexception) {
/*     */         
/* 132 */         logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", runtimeexception);
/* 133 */         this.options.setSoundLevel(SoundCategory.MASTER, 0.0F);
/* 134 */         this.options.saveOptions();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getSoundCategoryVolume(SoundCategory category) {
/* 144 */     return (category != null && category != SoundCategory.MASTER) ? this.options.getSoundLevel(category) : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundCategoryVolume(SoundCategory category, float volume) {
/* 152 */     if (this.loaded)
/*     */     {
/* 154 */       if (category == SoundCategory.MASTER) {
/*     */         
/* 156 */         this.sndSystem.setMasterVolume(volume);
/*     */       }
/*     */       else {
/*     */         
/* 160 */         for (String s : this.categorySounds.get(category)) {
/*     */           
/* 162 */           ISound isound = this.playingSounds.get(s);
/* 163 */           float f = getNormalizedVolume(isound, this.playingSoundPoolEntries.get(isound), category);
/*     */           
/* 165 */           if (f <= 0.0F) {
/*     */             
/* 167 */             stopSound(isound);
/*     */             
/*     */             continue;
/*     */           } 
/* 171 */           this.sndSystem.setVolume(s, f);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadSoundSystem() {
/* 183 */     if (this.loaded) {
/*     */       
/* 185 */       stopAllSounds();
/* 186 */       this.sndSystem.cleanup();
/* 187 */       this.loaded = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopAllSounds() {
/* 196 */     if (this.loaded) {
/*     */       
/* 198 */       for (String s : this.playingSounds.keySet())
/*     */       {
/* 200 */         this.sndSystem.stop(s);
/*     */       }
/*     */       
/* 203 */       this.playingSounds.clear();
/* 204 */       this.delayedSounds.clear();
/* 205 */       this.tickableSounds.clear();
/* 206 */       this.categorySounds.clear();
/* 207 */       this.playingSoundPoolEntries.clear();
/* 208 */       this.playingSoundsStopTime.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAllSounds() {
/* 214 */     this.playTime++;
/*     */     
/* 216 */     for (ITickableSound itickablesound : this.tickableSounds) {
/*     */       
/* 218 */       itickablesound.update();
/*     */       
/* 220 */       if (itickablesound.isDonePlaying()) {
/*     */         
/* 222 */         stopSound(itickablesound);
/*     */         
/*     */         continue;
/*     */       } 
/* 226 */       String s = this.invPlayingSounds.get(itickablesound);
/* 227 */       this.sndSystem.setVolume(s, getNormalizedVolume(itickablesound, this.playingSoundPoolEntries.get(itickablesound), this.sndHandler.getSound(itickablesound.getSoundLocation()).getSoundCategory()));
/* 228 */       this.sndSystem.setPitch(s, getNormalizedPitch(itickablesound, this.playingSoundPoolEntries.get(itickablesound)));
/* 229 */       this.sndSystem.setPosition(s, itickablesound.getXPosF(), itickablesound.getYPosF(), itickablesound.getZPosF());
/*     */     } 
/*     */ 
/*     */     
/* 233 */     Iterator<Map.Entry<String, ISound>> iterator = this.playingSounds.entrySet().iterator();
/*     */     
/* 235 */     while (iterator.hasNext()) {
/*     */       
/* 237 */       Map.Entry<String, ISound> entry = iterator.next();
/* 238 */       String s1 = entry.getKey();
/* 239 */       ISound isound = entry.getValue();
/*     */       
/* 241 */       if (!this.sndSystem.playing(s1)) {
/*     */         
/* 243 */         int i = ((Integer)this.playingSoundsStopTime.get(s1)).intValue();
/*     */         
/* 245 */         if (i <= this.playTime) {
/*     */           
/* 247 */           int j = isound.getRepeatDelay();
/*     */           
/* 249 */           if (isound.canRepeat() && j > 0)
/*     */           {
/* 251 */             this.delayedSounds.put(isound, Integer.valueOf(this.playTime + j));
/*     */           }
/*     */           
/* 254 */           iterator.remove();
/* 255 */           logger.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", new Object[] { s1 });
/* 256 */           this.sndSystem.removeSource(s1);
/* 257 */           this.playingSoundsStopTime.remove(s1);
/* 258 */           this.playingSoundPoolEntries.remove(isound);
/*     */ 
/*     */           
/*     */           try {
/* 262 */             this.categorySounds.remove(this.sndHandler.getSound(isound.getSoundLocation()).getSoundCategory(), s1);
/*     */           }
/* 264 */           catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 269 */           if (isound instanceof ITickableSound)
/*     */           {
/* 271 */             this.tickableSounds.remove(isound);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 277 */     Iterator<Map.Entry<ISound, Integer>> iterator1 = this.delayedSounds.entrySet().iterator();
/*     */     
/* 279 */     while (iterator1.hasNext()) {
/*     */       
/* 281 */       Map.Entry<ISound, Integer> entry1 = iterator1.next();
/*     */       
/* 283 */       if (this.playTime >= ((Integer)entry1.getValue()).intValue()) {
/*     */         
/* 285 */         ISound isound1 = entry1.getKey();
/*     */         
/* 287 */         if (isound1 instanceof ITickableSound)
/*     */         {
/* 289 */           ((ITickableSound)isound1).update();
/*     */         }
/*     */         
/* 292 */         playSound(isound1);
/* 293 */         iterator1.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 303 */     if (!this.loaded)
/*     */     {
/* 305 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 309 */     String s = this.invPlayingSounds.get(sound);
/* 310 */     return (s == null) ? false : ((this.sndSystem.playing(s) || (this.playingSoundsStopTime.containsKey(s) && ((Integer)this.playingSoundsStopTime.get(s)).intValue() <= this.playTime)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopSound(ISound sound) {
/* 316 */     if (this.loaded) {
/*     */       
/* 318 */       String s = this.invPlayingSounds.get(sound);
/*     */       
/* 320 */       if (s != null)
/*     */       {
/* 322 */         this.sndSystem.stop(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(ISound p_sound) {
/* 329 */     if (this.loaded)
/*     */     {
/* 331 */       if (this.sndSystem.getMasterVolume() <= 0.0F) {
/*     */         
/* 333 */         logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[] { p_sound.getSoundLocation() });
/*     */       }
/*     */       else {
/*     */         
/* 337 */         SoundEventAccessorComposite soundeventaccessorcomposite = this.sndHandler.getSound(p_sound.getSoundLocation());
/*     */         
/* 339 */         if (soundeventaccessorcomposite == null) {
/*     */           
/* 341 */           logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[] { p_sound.getSoundLocation() });
/*     */         }
/*     */         else {
/*     */           
/* 345 */           SoundPoolEntry soundpoolentry = soundeventaccessorcomposite.cloneEntry();
/*     */           
/* 347 */           if (soundpoolentry == SoundHandler.missing_sound) {
/*     */             
/* 349 */             logger.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", new Object[] { soundeventaccessorcomposite.getSoundEventLocation() });
/*     */           }
/*     */           else {
/*     */             
/* 353 */             float f = p_sound.getVolume();
/* 354 */             float f1 = 16.0F;
/*     */             
/* 356 */             if (f > 1.0F)
/*     */             {
/* 358 */               f1 *= f;
/*     */             }
/*     */             
/* 361 */             SoundCategory soundcategory = soundeventaccessorcomposite.getSoundCategory();
/* 362 */             float f2 = getNormalizedVolume(p_sound, soundpoolentry, soundcategory);
/* 363 */             double d0 = getNormalizedPitch(p_sound, soundpoolentry);
/* 364 */             ResourceLocation resourcelocation = soundpoolentry.getSoundPoolEntryLocation();
/*     */             
/* 366 */             if (f2 == 0.0F) {
/*     */               
/* 368 */               logger.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", new Object[] { resourcelocation });
/*     */             }
/*     */             else {
/*     */               
/* 372 */               boolean flag = (p_sound.canRepeat() && p_sound.getRepeatDelay() == 0);
/* 373 */               String s = MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()).toString();
/*     */               
/* 375 */               if (soundpoolentry.isStreamingSound()) {
/*     */                 
/* 377 */                 this.sndSystem.newStreamingSource(false, s, getURLForSoundResource(resourcelocation), resourcelocation.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f1);
/*     */               }
/*     */               else {
/*     */                 
/* 381 */                 this.sndSystem.newSource(false, s, getURLForSoundResource(resourcelocation), resourcelocation.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f1);
/*     */               } 
/*     */               
/* 384 */               logger.debug(LOG_MARKER, "Playing sound {} for event {} as channel {}", new Object[] { soundpoolentry.getSoundPoolEntryLocation(), soundeventaccessorcomposite.getSoundEventLocation(), s });
/* 385 */               this.sndSystem.setPitch(s, (float)d0);
/* 386 */               this.sndSystem.setVolume(s, f2);
/* 387 */               this.sndSystem.play(s);
/* 388 */               this.playingSoundsStopTime.put(s, Integer.valueOf(this.playTime + 20));
/* 389 */               this.playingSounds.put(s, p_sound);
/* 390 */               this.playingSoundPoolEntries.put(p_sound, soundpoolentry);
/*     */               
/* 392 */               if (soundcategory != SoundCategory.MASTER)
/*     */               {
/* 394 */                 this.categorySounds.put(soundcategory, s);
/*     */               }
/*     */               
/* 397 */               if (p_sound instanceof ITickableSound)
/*     */               {
/* 399 */                 this.tickableSounds.add((ITickableSound)p_sound);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getNormalizedPitch(ISound sound, SoundPoolEntry entry) {
/* 413 */     return (float)MathHelper.clamp_double(sound.getPitch() * entry.getPitch(), 0.5D, 2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getNormalizedVolume(ISound sound, SoundPoolEntry entry, SoundCategory category) {
/* 421 */     return (float)MathHelper.clamp_double(sound.getVolume() * entry.getVolume(), 0.0D, 1.0D) * getSoundCategoryVolume(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseAllSounds() {
/* 429 */     for (String s : this.playingSounds.keySet()) {
/*     */       
/* 431 */       logger.debug(LOG_MARKER, "Pausing channel {}", new Object[] { s });
/* 432 */       this.sndSystem.pause(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeAllSounds() {
/* 441 */     for (String s : this.playingSounds.keySet()) {
/*     */       
/* 443 */       logger.debug(LOG_MARKER, "Resuming channel {}", new Object[] { s });
/* 444 */       this.sndSystem.play(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 453 */     this.delayedSounds.put(sound, Integer.valueOf(this.playTime + delay));
/*     */   }
/*     */ 
/*     */   
/*     */   private static URL getURLForSoundResource(final ResourceLocation p_148612_0_) {
/* 458 */     String s = String.format("%s:%s:%s", new Object[] { "mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath() });
/* 459 */     URLStreamHandler urlstreamhandler = new URLStreamHandler()
/*     */       {
/*     */         protected URLConnection openConnection(URL p_openConnection_1_)
/*     */         {
/* 463 */           return new URLConnection(p_openConnection_1_)
/*     */             {
/*     */               public void connect() {}
/*     */ 
/*     */               
/*     */               public InputStream getInputStream() throws IOException {
/* 469 */                 return Minecraft.getMinecraft().getResourceManager().getResource(p_148612_0_).getInputStream();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     try {
/* 477 */       return new URL((URL)null, s, urlstreamhandler);
/*     */     }
/* 479 */     catch (MalformedURLException var4) {
/*     */       
/* 481 */       throw new Error("TODO: Sanely handle url exception! :D");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_148615_2_) {
/* 490 */     if (this.loaded && player != null) {
/*     */       
/* 492 */       float f = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * p_148615_2_;
/* 493 */       float f1 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * p_148615_2_;
/* 494 */       double d0 = player.prevPosX + (player.posX - player.prevPosX) * p_148615_2_;
/* 495 */       double d1 = player.prevPosY + (player.posY - player.prevPosY) * p_148615_2_ + player.getEyeHeight();
/* 496 */       double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * p_148615_2_;
/* 497 */       float f2 = MathHelper.cos((f1 + 90.0F) * 0.017453292F);
/* 498 */       float f3 = MathHelper.sin((f1 + 90.0F) * 0.017453292F);
/* 499 */       float f4 = MathHelper.cos(-f * 0.017453292F);
/* 500 */       float f5 = MathHelper.sin(-f * 0.017453292F);
/* 501 */       float f6 = MathHelper.cos((-f + 90.0F) * 0.017453292F);
/* 502 */       float f7 = MathHelper.sin((-f + 90.0F) * 0.017453292F);
/* 503 */       float f8 = f2 * f4;
/* 504 */       float f9 = f3 * f4;
/* 505 */       float f10 = f2 * f6;
/* 506 */       float f11 = f3 * f6;
/* 507 */       this.sndSystem.setListenerPosition((float)d0, (float)d1, (float)d2);
/* 508 */       this.sndSystem.setListenerOrientation(f8, f5, f9, f10, f7, f11);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SoundSystemStarterThread
/*     */     extends SoundSystem
/*     */   {
/*     */     public boolean playing(String p_playing_1_) {
/* 520 */       synchronized (SoundSystemConfig.THREAD_SYNC) {
/*     */         
/* 522 */         if (this.soundLibrary == null)
/*     */         {
/* 524 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 528 */         Source source = (Source)this.soundLibrary.getSources().get(p_playing_1_);
/* 529 */         return (source == null) ? false : ((source.playing() || source.paused() || source.preLoad));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */