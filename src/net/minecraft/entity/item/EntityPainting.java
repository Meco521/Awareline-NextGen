/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityPainting
/*     */   extends EntityHanging
/*     */ {
/*     */   public EnumArt art;
/*     */   
/*     */   public EntityPainting(World worldIn) {
/*  22 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing) {
/*  27 */     super(worldIn, pos);
/*  28 */     List<EnumArt> list = Lists.newArrayList();
/*     */     
/*  30 */     for (EnumArt entitypainting$enumart : EnumArt.values()) {
/*     */       
/*  32 */       this.art = entitypainting$enumart;
/*  33 */       updateFacingWithBoundingBox(facing);
/*     */       
/*  35 */       if (onValidSurface())
/*     */       {
/*  37 */         list.add(entitypainting$enumart);
/*     */       }
/*     */     } 
/*     */     
/*  41 */     if (!list.isEmpty())
/*     */     {
/*  43 */       this.art = list.get(this.rand.nextInt(list.size()));
/*     */     }
/*     */     
/*  46 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing, String title) {
/*  51 */     this(worldIn, pos, facing);
/*     */     
/*  53 */     for (EnumArt entitypainting$enumart : EnumArt.values()) {
/*     */       
/*  55 */       if (entitypainting$enumart.title.equals(title)) {
/*     */         
/*  57 */         this.art = entitypainting$enumart;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  62 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  70 */     tagCompound.setString("Motive", this.art.title);
/*  71 */     super.writeEntityToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  79 */     String s = tagCompund.getString("Motive");
/*     */     
/*  81 */     for (EnumArt entitypainting$enumart : EnumArt.values()) {
/*     */       
/*  83 */       if (entitypainting$enumart.title.equals(s))
/*     */       {
/*  85 */         this.art = entitypainting$enumart;
/*     */       }
/*     */     } 
/*     */     
/*  89 */     if (this.art == null)
/*     */     {
/*  91 */       this.art = EnumArt.KEBAB;
/*     */     }
/*     */     
/*  94 */     super.readEntityFromNBT(tagCompund);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  99 */     return this.art.sizeX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeightPixels() {
/* 104 */     return this.art.sizeY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(Entity brokenEntity) {
/* 112 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*     */       
/* 114 */       if (brokenEntity instanceof EntityPlayer) {
/*     */         
/* 116 */         EntityPlayer entityplayer = (EntityPlayer)brokenEntity;
/*     */         
/* 118 */         if (entityplayer.capabilities.isCreativeMode) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 124 */       entityDropItem(new ItemStack(Items.painting), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 133 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 134 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 139 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 140 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */   
/*     */   public enum EnumArt
/*     */   {
/* 145 */     KEBAB("Kebab", 16, 16, 0, 0),
/* 146 */     AZTEC("Aztec", 16, 16, 16, 0),
/* 147 */     ALBAN("Alban", 16, 16, 32, 0),
/* 148 */     AZTEC_2("Aztec2", 16, 16, 48, 0),
/* 149 */     BOMB("Bomb", 16, 16, 64, 0),
/* 150 */     PLANT("Plant", 16, 16, 80, 0),
/* 151 */     WASTELAND("Wasteland", 16, 16, 96, 0),
/* 152 */     POOL("Pool", 32, 16, 0, 32),
/* 153 */     COURBET("Courbet", 32, 16, 32, 32),
/* 154 */     SEA("Sea", 32, 16, 64, 32),
/* 155 */     SUNSET("Sunset", 32, 16, 96, 32),
/* 156 */     CREEBET("Creebet", 32, 16, 128, 32),
/* 157 */     WANDERER("Wanderer", 16, 32, 0, 64),
/* 158 */     GRAHAM("Graham", 16, 32, 16, 64),
/* 159 */     MATCH("Match", 32, 32, 0, 128),
/* 160 */     BUST("Bust", 32, 32, 32, 128),
/* 161 */     STAGE("Stage", 32, 32, 64, 128),
/* 162 */     VOID("Void", 32, 32, 96, 128),
/* 163 */     SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
/* 164 */     WITHER("Wither", 32, 32, 160, 128),
/* 165 */     FIGHTERS("Fighters", 64, 32, 0, 96),
/* 166 */     POINTER("Pointer", 64, 64, 0, 192),
/* 167 */     PIGSCENE("Pigscene", 64, 64, 64, 192),
/* 168 */     BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
/* 169 */     SKELETON("Skeleton", 64, 48, 192, 64),
/* 170 */     DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);
/*     */     
/* 172 */     public static final int field_180001_A = "SkullAndRoses".length();
/*     */     
/*     */     public final String title;
/*     */     
/*     */     public final int sizeX;
/*     */     
/*     */     public final int sizeY;
/*     */     
/*     */     EnumArt(String titleIn, int width, int height, int textureU, int textureV) {
/* 181 */       this.title = titleIn;
/* 182 */       this.sizeX = width;
/* 183 */       this.sizeY = height;
/* 184 */       this.offsetX = textureU;
/* 185 */       this.offsetY = textureV;
/*     */     }
/*     */     
/*     */     public final int offsetX;
/*     */     public final int offsetY;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */