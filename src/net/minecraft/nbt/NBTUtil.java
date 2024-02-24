/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NBTUtil
/*     */ {
/*     */   public static GameProfile readGameProfileFromNBT(NBTTagCompound compound) {
/*     */     UUID uuid;
/*  16 */     String s = null;
/*  17 */     String s1 = null;
/*     */     
/*  19 */     if (compound.hasKey("Name", 8))
/*     */     {
/*  21 */       s = compound.getString("Name");
/*     */     }
/*     */     
/*  24 */     if (compound.hasKey("Id", 8))
/*     */     {
/*  26 */       s1 = compound.getString("Id");
/*     */     }
/*     */     
/*  29 */     if (StringUtils.isNullOrEmpty(s) && StringUtils.isNullOrEmpty(s1))
/*     */     {
/*  31 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  39 */       uuid = UUID.fromString(s1);
/*     */     }
/*  41 */     catch (Throwable var12) {
/*     */       
/*  43 */       uuid = null;
/*     */     } 
/*     */     
/*  46 */     GameProfile gameprofile = new GameProfile(uuid, s);
/*     */     
/*  48 */     if (compound.hasKey("Properties", 10)) {
/*     */       
/*  50 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");
/*     */       
/*  52 */       for (String s2 : nbttagcompound.getKeySet()) {
/*     */         
/*  54 */         NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);
/*     */         
/*  56 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */           
/*  58 */           NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  59 */           String s3 = nbttagcompound1.getString("Value");
/*     */           
/*  61 */           if (nbttagcompound1.hasKey("Signature", 8)) {
/*     */             
/*  63 */             gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
/*     */           }
/*     */           else {
/*     */             
/*  67 */             gameprofile.getProperties().put(s2, new Property(s2, s3));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     return gameprofile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
/*  82 */     if (!StringUtils.isNullOrEmpty(profile.getName()))
/*     */     {
/*  84 */       tagCompound.setString("Name", profile.getName());
/*     */     }
/*     */     
/*  87 */     if (profile.getId() != null)
/*     */     {
/*  89 */       tagCompound.setString("Id", profile.getId().toString());
/*     */     }
/*     */     
/*  92 */     if (!profile.getProperties().isEmpty()) {
/*     */       
/*  94 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/*  96 */       for (String s : profile.getProperties().keySet()) {
/*     */         
/*  98 */         NBTTagList nbttaglist = new NBTTagList();
/*     */         
/* 100 */         for (Property property : profile.getProperties().get(s)) {
/*     */           
/* 102 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 103 */           nbttagcompound1.setString("Value", property.getValue());
/*     */           
/* 105 */           if (property.hasSignature())
/*     */           {
/* 107 */             nbttagcompound1.setString("Signature", property.getSignature());
/*     */           }
/*     */           
/* 110 */           nbttaglist.appendTag(nbttagcompound1);
/*     */         } 
/*     */         
/* 113 */         nbttagcompound.setTag(s, nbttaglist);
/*     */       } 
/*     */       
/* 116 */       tagCompound.setTag("Properties", nbttagcompound);
/*     */     } 
/*     */     
/* 119 */     return tagCompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_181123_a(NBTBase p_181123_0_, NBTBase p_181123_1_, boolean p_181123_2_) {
/* 124 */     if (p_181123_0_ == p_181123_1_)
/*     */     {
/* 126 */       return true;
/*     */     }
/* 128 */     if (p_181123_0_ == null)
/*     */     {
/* 130 */       return true;
/*     */     }
/* 132 */     if (p_181123_1_ == null)
/*     */     {
/* 134 */       return false;
/*     */     }
/* 136 */     if (!p_181123_0_.getClass().equals(p_181123_1_.getClass()))
/*     */     {
/* 138 */       return false;
/*     */     }
/* 140 */     if (p_181123_0_ instanceof NBTTagCompound) {
/*     */       
/* 142 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_181123_0_;
/* 143 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)p_181123_1_;
/*     */       
/* 145 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 147 */         NBTBase nbtbase1 = nbttagcompound.getTag(s);
/*     */         
/* 149 */         if (!func_181123_a(nbtbase1, nbttagcompound1.getTag(s), p_181123_2_))
/*     */         {
/* 151 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 155 */       return true;
/*     */     } 
/* 157 */     if (p_181123_0_ instanceof NBTTagList && p_181123_2_) {
/*     */       
/* 159 */       NBTTagList nbttaglist = (NBTTagList)p_181123_0_;
/* 160 */       NBTTagList nbttaglist1 = (NBTTagList)p_181123_1_;
/*     */       
/* 162 */       if (nbttaglist.tagCount() == 0)
/*     */       {
/* 164 */         return (nbttaglist1.tagCount() == 0);
/*     */       }
/*     */ 
/*     */       
/* 168 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 170 */         NBTBase nbtbase = nbttaglist.get(i);
/* 171 */         boolean flag = false;
/*     */         
/* 173 */         for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */           
/* 175 */           if (func_181123_a(nbtbase, nbttaglist1.get(j), p_181123_2_)) {
/*     */             
/* 177 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 182 */         if (!flag)
/*     */         {
/* 184 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 188 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 193 */     return p_181123_0_.equals(p_181123_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\NBTUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */