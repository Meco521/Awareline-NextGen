/*     */ package com.me.guichaguri.betterfps.clones.tileentity;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeaconLogic
/*     */   extends TileEntityBeacon
/*     */ {
/*  32 */   private int tickCount = 0;
/*     */ 
/*     */   
/*     */   public void update() {
/*  36 */     this.tickCount--;
/*  37 */     if (this.tickCount == 100) {
/*  38 */       updateEffects(this.pos.getX(), this.pos.getY(), this.pos.getZ());
/*  39 */     } else if (this.tickCount <= 0) {
/*  40 */       updateBeacon();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBeacon() {
/*  46 */     int x = this.pos.getX();
/*  47 */     int y = this.pos.getY();
/*  48 */     int z = this.pos.getZ();
/*  49 */     if (this.worldObj.isRemote) {
/*  50 */       updateGlassLayers(x, y, z);
/*     */     } else {
/*  52 */       updateActivation(x, y, z);
/*     */     } 
/*  54 */     updateLevels(x, y, z);
/*  55 */     updateEffects(x, y, z);
/*  56 */     this.tickCount = 200;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSegmentColors() {
/*  61 */     int x = this.pos.getX();
/*  62 */     int y = this.pos.getY();
/*  63 */     int z = this.pos.getZ();
/*  64 */     if (this.worldObj.isRemote) {
/*  65 */       updateGlassLayers(x, y, z);
/*     */     } else {
/*  67 */       updateActivation(x, y, z);
/*     */     } 
/*  69 */     updateLevels(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffectsToPlayers() {
/*  74 */     updateEffects(this.pos.getX(), this.pos.getY(), this.pos.getZ());
/*     */   }
/*     */   
/*     */   private void updateEffects(int x, int y, int z) {
/*  78 */     if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
/*  79 */       int radius = (this.levels + 1) * 10;
/*  80 */       byte effectLevel = 0;
/*  81 */       if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
/*  82 */         effectLevel = 1;
/*     */       }
/*  84 */       AxisAlignedBB box = new AxisAlignedBB(x, y, z, (x + 1), (y + 1), (z + 1));
/*  85 */       box = box.expand(radius, radius, radius).addCoord(0.0D, this.worldObj.getHeight(), 0.0D);
/*  86 */       Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box).iterator();
/*     */       
/*  88 */       boolean hasSecondaryEffect = (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0);
/*     */       
/*  90 */       while (iterator.hasNext()) {
/*  91 */         EntityPlayer player = iterator.next();
/*  92 */         player.addPotionEffect(new PotionEffect(this.primaryEffect, 180, effectLevel, true, true));
/*  93 */         if (hasSecondaryEffect) {
/*  94 */           player.addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true, true));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateGlassLayers(int x, int y, int z) {
/* 104 */     this.isComplete = true;
/* 105 */     this.beamSegments.clear();
/* 106 */     TileEntityBeacon.BeamSegment beam = new TileEntityBeacon.BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.WHITE));
/* 107 */     float[] oldColor = null;
/* 108 */     this.beamSegments.add(beam);
/* 109 */     int height = this.worldObj.getActualHeight();
/* 110 */     int blockY = y + 1; while (true) { if (blockY < height) {
/* 111 */         float[] color; BlockPos pos = new BlockPos(x, blockY, z);
/* 112 */         IBlockState state = this.worldObj.getBlockState(pos);
/* 113 */         Block b = state.getBlock();
/*     */         
/* 115 */         if (b == Blocks.stained_glass) {
/* 116 */           color = EntitySheep.getDyeRgb((EnumDyeColor)state.getValue((IProperty)BlockStainedGlass.COLOR));
/* 117 */         } else if (b == Blocks.stained_glass_pane) {
/* 118 */           color = EntitySheep.getDyeRgb((EnumDyeColor)state.getValue((IProperty)BlockStainedGlassPane.COLOR));
/*     */         } else {
/* 120 */           if (b.getLightOpacity() >= 15) {
/* 121 */             this.isComplete = false;
/* 122 */             this.beamSegments.clear();
/*     */             break;
/*     */           } 
/* 125 */           beam.incrementHeight();
/*     */           
/*     */           blockY++;
/*     */         } 
/* 129 */         if (oldColor != null) {
/* 130 */           color = new float[] { (oldColor[0] + color[0]) / 2.0F, (oldColor[1] + color[1]) / 2.0F, (oldColor[2] + color[2]) / 2.0F };
/*     */         }
/* 132 */         if (Arrays.equals(color, oldColor)) {
/* 133 */           beam.incrementHeight();
/*     */         } else {
/* 135 */           beam = new TileEntityBeacon.BeamSegment(color);
/* 136 */           this.beamSegments.add(beam);
/* 137 */           oldColor = color;
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */       blockY++; }
/*     */   
/*     */   } private void updateActivation(int x, int y, int z) {
/* 145 */     this.isComplete = true;
/* 146 */     int height = this.worldObj.getActualHeight();
/* 147 */     for (int blockY = y + 1; blockY < height; blockY++) {
/* 148 */       BlockPos pos = new BlockPos(x, blockY, z);
/* 149 */       IBlockState state = this.worldObj.getBlockState(pos);
/* 150 */       Block b = state.getBlock();
/* 151 */       if (b.getLightOpacity() >= 15) {
/* 152 */         this.isComplete = false;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateLevels(int x, int y, int z) {
/* 160 */     boolean isClient = this.worldObj.isRemote;
/* 161 */     int levelsOld = this.levels;
/*     */     int lvl;
/* 163 */     label36: for (lvl = 1; lvl <= 4; lvl++) {
/* 164 */       this.levels = lvl;
/* 165 */       int blockY = y - lvl;
/* 166 */       if (blockY < 0)
/*     */         break; 
/* 168 */       for (int blockX = x - lvl; blockX <= x + lvl; blockX++) {
/* 169 */         for (int blockZ = z - lvl; blockZ <= z + lvl; blockZ++) {
/* 170 */           BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
/* 171 */           Block block = this.worldObj.getBlockState(blockPos).getBlock();
/* 172 */           if (!block.isFullBlock()) {
/* 173 */             this.levels--;
/*     */             
/*     */             break label36;
/*     */           } 
/*     */         } 
/*     */       } 
/* 179 */       if (isClient)
/*     */         break; 
/* 181 */     }  if (this.levels == 0) {
/* 182 */       this.isComplete = false;
/*     */     }
/*     */     
/* 185 */     if (!isClient && this.levels == 4 && levelsOld < this.levels) {
/* 186 */       AxisAlignedBB box = (new AxisAlignedBB(x, y, z, x, (y - 4), z)).expand(10.0D, 5.0D, 10.0D);
/* 187 */       Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box).iterator();
/* 188 */       while (iterator.hasNext()) {
/* 189 */         EntityPlayer entityplayer = iterator.next();
/* 190 */         entityplayer.triggerAchievement((StatBase)AchievementList.fullBeacon);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\tileentity\BeaconLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */