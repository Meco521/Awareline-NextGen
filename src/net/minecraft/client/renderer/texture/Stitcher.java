/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.StitcherException;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class Stitcher
/*     */ {
/*     */   private final int mipmapLevelStitcher;
/*  15 */   private final Set<Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
/*  16 */   private final List<Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
/*     */   
/*     */   private int currentWidth;
/*     */   
/*     */   private int currentHeight;
/*     */   
/*     */   private final int maxWidth;
/*     */   private final int maxHeight;
/*     */   private final boolean forcePowerOf2;
/*     */   private final int maxTileDimension;
/*     */   
/*     */   public Stitcher(int maxTextureWidth, int maxTextureHeight, boolean p_i45095_3_, int p_i45095_4_, int mipmapLevel) {
/*  28 */     this.mipmapLevelStitcher = mipmapLevel;
/*  29 */     this.maxWidth = maxTextureWidth;
/*  30 */     this.maxHeight = maxTextureHeight;
/*  31 */     this.forcePowerOf2 = p_i45095_3_;
/*  32 */     this.maxTileDimension = p_i45095_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentWidth() {
/*  37 */     return this.currentWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentHeight() {
/*  42 */     return this.currentHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSprite(TextureAtlasSprite p_110934_1_) {
/*  47 */     Holder stitcher$holder = new Holder(p_110934_1_, this.mipmapLevelStitcher);
/*     */     
/*  49 */     if (this.maxTileDimension > 0)
/*     */     {
/*  51 */       stitcher$holder.setNewDimension(this.maxTileDimension);
/*     */     }
/*     */     
/*  54 */     this.setStitchHolders.add(stitcher$holder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doStitch() {
/*  59 */     Holder[] astitcher$holder = this.setStitchHolders.<Holder>toArray(new Holder[0]);
/*  60 */     Arrays.sort((Object[])astitcher$holder);
/*     */     
/*  62 */     for (Holder stitcher$holder : astitcher$holder) {
/*     */       
/*  64 */       if (!allocateSlot(stitcher$holder)) {
/*     */         
/*  66 */         String s = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", new Object[] { stitcher$holder.getAtlasSprite().getIconName(), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconWidth()), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconHeight()), Integer.valueOf(this.currentWidth), Integer.valueOf(this.currentHeight), Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight) });
/*  67 */         throw new StitcherException(stitcher$holder, s);
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     if (this.forcePowerOf2) {
/*     */       
/*  73 */       this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/*  74 */       this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TextureAtlasSprite> getStichSlots() {
/*  80 */     List<Slot> list = Lists.newArrayList();
/*     */     
/*  82 */     for (Slot stitcher$slot : this.stitchSlots)
/*     */     {
/*  84 */       stitcher$slot.getAllStitchSlots(list);
/*     */     }
/*     */     
/*  87 */     List<TextureAtlasSprite> list1 = Lists.newArrayList();
/*     */     
/*  89 */     for (Slot stitcher$slot1 : list) {
/*     */       
/*  91 */       Holder stitcher$holder = stitcher$slot1.getStitchHolder();
/*  92 */       TextureAtlasSprite textureatlassprite = stitcher$holder.getAtlasSprite();
/*  93 */       textureatlassprite.initSprite(this.currentWidth, this.currentHeight, stitcher$slot1.getOriginX(), stitcher$slot1.getOriginY(), stitcher$holder.isRotated());
/*  94 */       list1.add(textureatlassprite);
/*     */     } 
/*     */     
/*  97 */     return list1;
/*     */   }
/*     */ 
/*     */   
/*     */   static int getMipmapDimension(int p_147969_0_, int p_147969_1_) {
/* 102 */     return (p_147969_0_ >> p_147969_1_) + (((p_147969_0_ & (1 << p_147969_1_) - 1) == 0) ? 0 : 1) << p_147969_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean allocateSlot(Holder p_94310_1_) {
/* 110 */     for (int i = 0; i < this.stitchSlots.size(); i++) {
/*     */       
/* 112 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_))
/*     */       {
/* 114 */         return true;
/*     */       }
/*     */       
/* 117 */       p_94310_1_.rotate();
/*     */       
/* 119 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_))
/*     */       {
/* 121 */         return true;
/*     */       }
/*     */       
/* 124 */       p_94310_1_.rotate();
/*     */     } 
/*     */     
/* 127 */     return expandAndAllocateSlot(p_94310_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expandAndAllocateSlot(Holder p_94311_1_) {
/*     */     boolean flag1;
/*     */     Slot stitcher$slot;
/* 135 */     int i = Math.min(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/* 136 */     boolean flag = (this.currentWidth == 0 && this.currentHeight == 0);
/*     */ 
/*     */     
/* 139 */     if (this.forcePowerOf2) {
/*     */       
/* 141 */       int j = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/* 142 */       int k = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/* 143 */       int l = MathHelper.roundUpToPowerOfTwo(this.currentWidth + i);
/* 144 */       int i1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + i);
/* 145 */       boolean flag2 = (l <= this.maxWidth);
/* 146 */       boolean flag3 = (i1 <= this.maxHeight);
/*     */       
/* 148 */       if (!flag2 && !flag3)
/*     */       {
/* 150 */         return false;
/*     */       }
/*     */       
/* 153 */       boolean flag4 = (j != l);
/* 154 */       boolean flag5 = (k != i1);
/*     */       
/* 156 */       if (flag4 ^ flag5)
/*     */       {
/* 158 */         flag1 = !flag4;
/*     */       }
/*     */       else
/*     */       {
/* 162 */         flag1 = (flag2 && j <= k);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 167 */       boolean flag6 = (this.currentWidth + i <= this.maxWidth);
/* 168 */       boolean flag7 = (this.currentHeight + i <= this.maxHeight);
/*     */       
/* 170 */       if (!flag6 && !flag7)
/*     */       {
/* 172 */         return false;
/*     */       }
/*     */       
/* 175 */       flag1 = (flag6 && (flag || this.currentWidth <= this.currentHeight));
/*     */     } 
/*     */     
/* 178 */     int j1 = Math.max(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/*     */     
/* 180 */     if (MathHelper.roundUpToPowerOfTwo((!flag1 ? this.currentHeight : this.currentWidth) + j1) > (!flag1 ? this.maxHeight : this.maxWidth))
/*     */     {
/* 182 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (flag1) {
/*     */       
/* 190 */       if (p_94311_1_.getWidth() > p_94311_1_.getHeight())
/*     */       {
/* 192 */         p_94311_1_.rotate();
/*     */       }
/*     */       
/* 195 */       if (this.currentHeight == 0)
/*     */       {
/* 197 */         this.currentHeight = p_94311_1_.getHeight();
/*     */       }
/*     */       
/* 200 */       stitcher$slot = new Slot(this.currentWidth, 0, p_94311_1_.getWidth(), this.currentHeight);
/* 201 */       this.currentWidth += p_94311_1_.getWidth();
/*     */     }
/*     */     else {
/*     */       
/* 205 */       stitcher$slot = new Slot(0, this.currentHeight, this.currentWidth, p_94311_1_.getHeight());
/* 206 */       this.currentHeight += p_94311_1_.getHeight();
/*     */     } 
/*     */     
/* 209 */     stitcher$slot.addSlot(p_94311_1_);
/* 210 */     this.stitchSlots.add(stitcher$slot);
/* 211 */     return true;
/*     */   }
/*     */   
/*     */   public static class Holder
/*     */     implements Comparable<Holder>
/*     */   {
/*     */     private final TextureAtlasSprite theTexture;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private final int mipmapLevelHolder;
/*     */     private boolean rotated;
/* 222 */     private float scaleFactor = 1.0F;
/*     */ 
/*     */     
/*     */     public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_) {
/* 226 */       this.theTexture = p_i45094_1_;
/* 227 */       this.width = p_i45094_1_.getIconWidth();
/* 228 */       this.height = p_i45094_1_.getIconHeight();
/* 229 */       this.mipmapLevelHolder = p_i45094_2_;
/* 230 */       this.rotated = (Stitcher.getMipmapDimension(this.height, p_i45094_2_) > Stitcher.getMipmapDimension(this.width, p_i45094_2_));
/*     */     }
/*     */ 
/*     */     
/*     */     public TextureAtlasSprite getAtlasSprite() {
/* 235 */       return this.theTexture;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 240 */       return this.rotated ? Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 245 */       return this.rotated ? Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */ 
/*     */     
/*     */     public void rotate() {
/* 250 */       this.rotated = !this.rotated;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRotated() {
/* 255 */       return this.rotated;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setNewDimension(int p_94196_1_) {
/* 260 */       if (this.width > p_94196_1_ && this.height > p_94196_1_)
/*     */       {
/* 262 */         this.scaleFactor = p_94196_1_ / Math.min(this.width, this.height);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 268 */       return "Holder{width=" + this.width + ", height=" + this.height + '}';
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Holder p_compareTo_1_) {
/*     */       int i;
/* 275 */       if (getHeight() == p_compareTo_1_.getHeight()) {
/*     */         
/* 277 */         if (getWidth() == p_compareTo_1_.getWidth()) {
/*     */           
/* 279 */           if (this.theTexture.getIconName() == null)
/*     */           {
/* 281 */             return (p_compareTo_1_.theTexture.getIconName() == null) ? 0 : -1;
/*     */           }
/*     */           
/* 284 */           return this.theTexture.getIconName().compareTo(p_compareTo_1_.theTexture.getIconName());
/*     */         } 
/*     */         
/* 287 */         i = (getWidth() < p_compareTo_1_.getWidth()) ? 1 : -1;
/*     */       }
/*     */       else {
/*     */         
/* 291 */         i = (getHeight() < p_compareTo_1_.getHeight()) ? 1 : -1;
/*     */       } 
/*     */       
/* 294 */       return i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Slot
/*     */   {
/*     */     private final int originX;
/*     */     private final int originY;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private List<Slot> subSlots;
/*     */     private Stitcher.Holder holder;
/*     */     
/*     */     public Slot(int p_i1277_1_, int p_i1277_2_, int widthIn, int heightIn) {
/* 309 */       this.originX = p_i1277_1_;
/* 310 */       this.originY = p_i1277_2_;
/* 311 */       this.width = widthIn;
/* 312 */       this.height = heightIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Stitcher.Holder getStitchHolder() {
/* 317 */       return this.holder;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOriginX() {
/* 322 */       return this.originX;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOriginY() {
/* 327 */       return this.originY;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addSlot(Stitcher.Holder holderIn) {
/* 332 */       if (this.holder != null)
/*     */       {
/* 334 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 338 */       int i = holderIn.getWidth();
/* 339 */       int j = holderIn.getHeight();
/*     */       
/* 341 */       if (i <= this.width && j <= this.height) {
/*     */         
/* 343 */         if (i == this.width && j == this.height) {
/*     */           
/* 345 */           this.holder = holderIn;
/* 346 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 350 */         if (this.subSlots == null) {
/*     */           
/* 352 */           this.subSlots = Lists.newArrayListWithCapacity(1);
/* 353 */           this.subSlots.add(new Slot(this.originX, this.originY, i, j));
/* 354 */           int k = this.width - i;
/* 355 */           int l = this.height - j;
/*     */           
/* 357 */           if (l > 0 && k > 0) {
/*     */             
/* 359 */             int i1 = Math.max(this.height, k);
/* 360 */             int j1 = Math.max(this.width, l);
/*     */             
/* 362 */             if (i1 >= j1)
/*     */             {
/* 364 */               this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/* 365 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, this.height));
/*     */             }
/*     */             else
/*     */             {
/* 369 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/* 370 */               this.subSlots.add(new Slot(this.originX, this.originY + j, this.width, l));
/*     */             }
/*     */           
/* 373 */           } else if (k == 0) {
/*     */             
/* 375 */             this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/*     */           }
/* 377 */           else if (l == 0) {
/*     */             
/* 379 */             this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/*     */           } 
/*     */         } 
/*     */         
/* 383 */         for (Slot stitcher$slot : this.subSlots) {
/*     */           
/* 385 */           if (stitcher$slot.addSlot(holderIn))
/*     */           {
/* 387 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 391 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 396 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void getAllStitchSlots(List<Slot> p_94184_1_) {
/* 403 */       if (this.holder != null) {
/*     */         
/* 405 */         p_94184_1_.add(this);
/*     */       }
/* 407 */       else if (this.subSlots != null) {
/*     */         
/* 409 */         for (Slot stitcher$slot : this.subSlots)
/*     */         {
/* 411 */           stitcher$slot.getAllStitchSlots(p_94184_1_);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 418 */       return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\Stitcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */