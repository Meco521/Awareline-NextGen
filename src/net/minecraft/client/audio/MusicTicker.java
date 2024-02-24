/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class MusicTicker
/*     */   implements ITickable
/*     */ {
/*  12 */   private final Random rand = new Random();
/*     */   private final Minecraft mc;
/*     */   private ISound currentMusic;
/*  15 */   private int timeUntilNextMusic = 100;
/*     */ 
/*     */   
/*     */   public MusicTicker(Minecraft mcIn) {
/*  19 */     this.mc = mcIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  27 */     MusicType musicticker$musictype = this.mc.getAmbientMusicType();
/*     */     
/*  29 */     if (this.currentMusic != null) {
/*     */       
/*  31 */       if (!musicticker$musictype.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
/*     */         
/*  33 */         this.mc.getSoundHandler().stopSound(this.currentMusic);
/*  34 */         this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, musicticker$musictype.getMinDelay() / 2);
/*     */       } 
/*     */       
/*  37 */       if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
/*     */         
/*  39 */         this.currentMusic = null;
/*  40 */         this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextMusic);
/*     */       } 
/*     */     } 
/*     */     
/*  44 */     if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
/*     */     {
/*  46 */       func_181558_a(musicticker$musictype);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181558_a(MusicType p_181558_1_) {
/*  52 */     this.currentMusic = PositionedSoundRecord.create(p_181558_1_.getMusicLocation());
/*  53 */     this.mc.getSoundHandler().playSound(this.currentMusic);
/*  54 */     this.timeUntilNextMusic = Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181557_a() {
/*  59 */     if (this.currentMusic != null) {
/*     */       
/*  61 */       this.mc.getSoundHandler().stopSound(this.currentMusic);
/*  62 */       this.currentMusic = null;
/*  63 */       this.timeUntilNextMusic = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum MusicType
/*     */   {
/*  69 */     MENU((String)new ResourceLocation("minecraft:music.menu"), 20, 600),
/*  70 */     GAME((String)new ResourceLocation("minecraft:music.game"), 12000, 24000),
/*  71 */     CREATIVE((String)new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
/*  72 */     CREDITS((String)new ResourceLocation("minecraft:music.game.end.credits"), 2147483647, 2147483647),
/*  73 */     NETHER((String)new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),
/*  74 */     END_BOSS((String)new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
/*  75 */     END((String)new ResourceLocation("minecraft:music.game.end"), 6000, 24000);
/*     */     
/*     */     private final ResourceLocation musicLocation;
/*     */     
/*     */     private final int minDelay;
/*     */     private final int maxDelay;
/*     */     
/*     */     MusicType(ResourceLocation location, int minDelayIn, int maxDelayIn) {
/*  83 */       this.musicLocation = location;
/*  84 */       this.minDelay = minDelayIn;
/*  85 */       this.maxDelay = maxDelayIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceLocation getMusicLocation() {
/*  90 */       return this.musicLocation;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMinDelay() {
/*  95 */       return this.minDelay;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxDelay() {
/* 100 */       return this.maxDelay;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\MusicTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */