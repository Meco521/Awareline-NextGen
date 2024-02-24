/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SoundHandler implements IResourceManagerReloadListener, ITickable {
/*  28 */   private static final Logger logger = LogManager.getLogger();
/*  29 */   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
/*  30 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  34 */         return new Type[] { String.class, SoundList.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  38 */         return Map.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  42 */         return null;
/*     */       }
/*     */     };
/*  45 */   public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0D, 0.0D, false);
/*  46 */   final SoundRegistry sndRegistry = new SoundRegistry();
/*     */   
/*     */   private final SoundManager sndManager;
/*     */   private final IResourceManager mcResourceManager;
/*     */   
/*     */   public SoundHandler(IResourceManager manager, GameSettings gameSettingsIn) {
/*  52 */     this.mcResourceManager = manager;
/*  53 */     this.sndManager = new SoundManager(this, gameSettingsIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  58 */     this.sndManager.reloadSoundSystem();
/*  59 */     this.sndRegistry.clearMap();
/*     */     
/*  61 */     for (String s : resourceManager.getResourceDomains()) {
/*     */ 
/*     */       
/*     */       try {
/*  65 */         for (IResource iresource : resourceManager.getAllResources(new ResourceLocation(s, "sounds.json"))) {
/*     */           
/*     */           try
/*     */           {
/*  69 */             Map<String, SoundList> map = getSoundMap(iresource.getInputStream());
/*     */             
/*  71 */             for (Map.Entry<String, SoundList> entry : map.entrySet())
/*     */             {
/*  73 */               loadSoundResource(new ResourceLocation(s, entry.getKey()), entry.getValue());
/*     */             }
/*     */           }
/*  76 */           catch (RuntimeException runtimeexception)
/*     */           {
/*  78 */             logger.warn("Invalid sounds.json", runtimeexception);
/*     */           }
/*     */         
/*     */         } 
/*  82 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, SoundList> getSoundMap(InputStream stream) {
/*     */     Map<String, SoundList> map;
/*     */     try {
/*  95 */       map = (Map)GSON.fromJson(new InputStreamReader(stream), TYPE);
/*     */     }
/*     */     finally {
/*     */       
/*  99 */       IOUtils.closeQuietly(stream);
/*     */     } 
/*     */     
/* 102 */     return map;
/*     */   }
/*     */   
/*     */   private void loadSoundResource(ResourceLocation location, SoundList sounds) {
/*     */     SoundEventAccessorComposite soundeventaccessorcomposite;
/* 107 */     boolean flag = !this.sndRegistry.containsKey(location);
/*     */ 
/*     */     
/* 110 */     if (!flag && !sounds.canReplaceExisting()) {
/*     */       
/* 112 */       soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
/*     */     }
/*     */     else {
/*     */       
/* 116 */       if (!flag)
/*     */       {
/* 118 */         logger.debug("Replaced sound event location {}", new Object[] { location });
/*     */       }
/*     */       
/* 121 */       soundeventaccessorcomposite = new SoundEventAccessorComposite(location, 1.0D, 1.0D, sounds.getSoundCategory());
/* 122 */       this.sndRegistry.registerSound(soundeventaccessorcomposite);
/*     */     } 
/*     */     
/* 125 */     for (SoundList.SoundEntry soundlist$soundentry : sounds.getSoundList()) {
/*     */       ISoundEventAccessor<SoundPoolEntry> isoundeventaccessor; ResourceLocation resourcelocation1; InputStream inputstream;
/* 127 */       String s = soundlist$soundentry.getSoundEntryName();
/* 128 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 129 */       final String s1 = s.contains(":") ? resourcelocation.getResourceDomain() : location.getResourceDomain();
/*     */ 
/*     */       
/* 132 */       switch (soundlist$soundentry.getSoundEntryType()) {
/*     */         
/*     */         case FILE:
/* 135 */           resourcelocation1 = new ResourceLocation(s1, "sounds/" + resourcelocation.getResourcePath() + ".ogg");
/* 136 */           inputstream = null;
/*     */ 
/*     */           
/*     */           try {
/* 140 */             inputstream = this.mcResourceManager.getResource(resourcelocation1).getInputStream();
/*     */           }
/* 142 */           catch (FileNotFoundException var18) {
/*     */             
/* 144 */             logger.warn("File {} does not exist, cannot add it to event {}", new Object[] { resourcelocation1, location });
/*     */             
/*     */             continue;
/* 147 */           } catch (IOException ioexception) {
/*     */             
/* 149 */             logger.warn("Could not load sound file " + resourcelocation1 + ", cannot add it to event " + location, ioexception);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } finally {
/* 154 */             IOUtils.closeQuietly(inputstream);
/*     */           } 
/*     */           
/* 157 */           isoundeventaccessor = new SoundEventAccessor(new SoundPoolEntry(resourcelocation1, soundlist$soundentry.getSoundEntryPitch(), soundlist$soundentry.getSoundEntryVolume(), soundlist$soundentry.isStreaming()), soundlist$soundentry.getSoundEntryWeight());
/*     */           break;
/*     */         
/*     */         case SOUND_EVENT:
/* 161 */           isoundeventaccessor = new ISoundEventAccessor<SoundPoolEntry>()
/*     */             {
/* 163 */               final ResourceLocation field_148726_a = new ResourceLocation(s1, soundlist$soundentry.getSoundEntryName());
/*     */               
/*     */               public int getWeight() {
/* 166 */                 SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
/* 167 */                 return (soundeventaccessorcomposite1 == null) ? 0 : soundeventaccessorcomposite1.getWeight();
/*     */               }
/*     */               
/*     */               public SoundPoolEntry cloneEntry() {
/* 171 */                 SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
/* 172 */                 return (soundeventaccessorcomposite1 == null) ? SoundHandler.missing_sound : soundeventaccessorcomposite1.cloneEntry();
/*     */               }
/*     */             };
/*     */           break;
/*     */         
/*     */         default:
/* 178 */           throw new IllegalStateException("IN YOU FACE");
/*     */       } 
/*     */       
/* 181 */       soundeventaccessorcomposite.addSoundToEventPool(isoundeventaccessor);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEventAccessorComposite getSound(ResourceLocation location) {
/* 187 */     return (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(ISound sound) {
/* 195 */     this.sndManager.playSound(sound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 203 */     this.sndManager.playDelayedSound(sound, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_147691_2_) {
/* 208 */     this.sndManager.setListener(player, p_147691_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseSounds() {
/* 213 */     this.sndManager.pauseAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSounds() {
/* 218 */     this.sndManager.stopAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloadSounds() {
/* 223 */     this.sndManager.unloadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 231 */     this.sndManager.updateAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeSounds() {
/* 236 */     this.sndManager.resumeAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSoundLevel(SoundCategory category, float volume) {
/* 241 */     if (category == SoundCategory.MASTER && volume <= 0.0F)
/*     */     {
/* 243 */       stopSounds();
/*     */     }
/*     */     
/* 246 */     this.sndManager.setSoundCategoryVolume(category, volume);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSound(ISound p_147683_1_) {
/* 251 */     this.sndManager.stopSound(p_147683_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory... categories) {
/* 259 */     List<SoundEventAccessorComposite> list = Lists.newArrayList();
/*     */     
/* 261 */     for (ResourceLocation resourcelocation : this.sndRegistry.getKeys()) {
/*     */       
/* 263 */       SoundEventAccessorComposite soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(resourcelocation);
/*     */       
/* 265 */       if (ArrayUtils.contains((Object[])categories, soundeventaccessorcomposite.getSoundCategory()))
/*     */       {
/* 267 */         list.add(soundeventaccessorcomposite);
/*     */       }
/*     */     } 
/*     */     
/* 271 */     if (list.isEmpty())
/*     */     {
/* 273 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 277 */     return list.get((new Random()).nextInt(list.size()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 283 */     return this.sndManager.isSoundPlaying(sound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */