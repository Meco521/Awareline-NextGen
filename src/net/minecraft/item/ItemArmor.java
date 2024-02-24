/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmor
/*     */   extends Item {
/*  24 */   static final int[] maxDamageArray = new int[] { 11, 16, 15, 13 };
/*  25 */   public static final String[] EMPTY_SLOT_NAMES = new String[] { "minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots" };
/*  26 */   private static final IBehaviorDispenseItem dispenserBehavior = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem()
/*     */     {
/*     */       protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */       {
/*  30 */         BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*  31 */         int i = blockpos.getX();
/*  32 */         int j = blockpos.getY();
/*  33 */         int k = blockpos.getZ();
/*  34 */         AxisAlignedBB axisalignedbb = new AxisAlignedBB(i, j, k, (i + 1), (j + 1), (k + 1));
/*  35 */         List<EntityLivingBase> list = source.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new EntitySelectors.ArmoredMob(stack)));
/*     */         
/*  37 */         if (!list.isEmpty()) {
/*     */           
/*  39 */           EntityLivingBase entitylivingbase = list.get(0);
/*  40 */           int l = (entitylivingbase instanceof EntityPlayer) ? 1 : 0;
/*  41 */           int i1 = EntityLiving.getArmorPosition(stack);
/*  42 */           ItemStack itemstack = stack.copy();
/*  43 */           itemstack.stackSize = 1;
/*  44 */           entitylivingbase.setCurrentItemOrArmor(i1 - l, itemstack);
/*     */           
/*  46 */           if (entitylivingbase instanceof EntityLiving)
/*     */           {
/*  48 */             ((EntityLiving)entitylivingbase).setEquipmentDropChance(i1, 2.0F);
/*     */           }
/*     */           
/*  51 */           stack.stackSize--;
/*  52 */           return stack;
/*     */         } 
/*     */ 
/*     */         
/*  56 */         return super.dispenseStack(source, stack);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int armorType;
/*     */ 
/*     */ 
/*     */   
/*     */   public final int damageReduceAmount;
/*     */ 
/*     */ 
/*     */   
/*     */   public final int renderIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArmorMaterial material;
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemArmor(ArmorMaterial material, int renderIndex, int armorType) {
/*  80 */     this.material = material;
/*  81 */     this.armorType = armorType;
/*  82 */     this.renderIndex = renderIndex;
/*  83 */     this.damageReduceAmount = material.getDamageReductionAmount(armorType);
/*  84 */     setMaxDamage(material.getDurability(armorType));
/*  85 */     this.maxStackSize = 1;
/*  86 */     setCreativeTab(CreativeTabs.tabCombat);
/*  87 */     BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  92 */     if (renderPass > 0)
/*     */     {
/*  94 */       return 16777215;
/*     */     }
/*     */ 
/*     */     
/*  98 */     int i = getColor(stack);
/*     */     
/* 100 */     if (i < 0)
/*     */     {
/* 102 */       i = 16777215;
/*     */     }
/*     */     
/* 105 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 114 */     return this.material.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArmorMaterial getArmorMaterial() {
/* 122 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColor(ItemStack stack) {
/* 130 */     return (this.material != ArmorMaterial.LEATHER) ? false : (!stack.hasTagCompound() ? false : (!stack.getTagCompound().hasKey("display", 10) ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(ItemStack stack) {
/* 138 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 140 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 144 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 146 */     if (nbttagcompound != null) {
/*     */       
/* 148 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */       
/* 150 */       if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
/*     */       {
/* 152 */         return nbttagcompound1.getInteger("color");
/*     */       }
/*     */     } 
/*     */     
/* 156 */     return 10511680;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeColor(ItemStack stack) {
/* 165 */     if (this.material == ArmorMaterial.LEATHER) {
/*     */       
/* 167 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 169 */       if (nbttagcompound != null) {
/*     */         
/* 171 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */         
/* 173 */         if (nbttagcompound1.hasKey("color"))
/*     */         {
/* 175 */           nbttagcompound1.removeTag("color");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(ItemStack stack, int color) {
/* 186 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 188 */       throw new UnsupportedOperationException("Can't dye non-leather!");
/*     */     }
/*     */ 
/*     */     
/* 192 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 194 */     if (nbttagcompound == null) {
/*     */       
/* 196 */       nbttagcompound = new NBTTagCompound();
/* 197 */       stack.setTagCompound(nbttagcompound);
/*     */     } 
/*     */     
/* 200 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */     
/* 202 */     if (!nbttagcompound.hasKey("display", 10))
/*     */     {
/* 204 */       nbttagcompound.setTag("display", (NBTBase)nbttagcompound1);
/*     */     }
/*     */     
/* 207 */     nbttagcompound1.setInteger("color", color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 216 */     return (this.material.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 224 */     int i = EntityLiving.getArmorPosition(itemStackIn) - 1;
/* 225 */     ItemStack itemstack = playerIn.getCurrentArmor(i);
/*     */     
/* 227 */     if (itemstack == null) {
/*     */       
/* 229 */       playerIn.setCurrentItemOrArmor(i, itemStackIn.copy());
/* 230 */       itemStackIn.stackSize = 0;
/*     */     } 
/*     */     
/* 233 */     return itemStackIn;
/*     */   }
/*     */   
/*     */   public enum ArmorMaterial
/*     */   {
/* 238 */     LEATHER("leather", 5, (String)new int[] { 1, 3, 2, 1 }, 15),
/* 239 */     CHAIN("chainmail", 15, (String)new int[] { 2, 5, 4, 1 }, 12),
/* 240 */     IRON("iron", 15, (String)new int[] { 2, 6, 5, 2 }, 9),
/* 241 */     GOLD("gold", 7, (String)new int[] { 2, 5, 3, 1 }, 25),
/* 242 */     DIAMOND("diamond", 33, (String)new int[] { 3, 8, 6, 3 }, 10);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final int maxDamageFactor;
/*     */     private final int[] damageReductionAmountArray;
/*     */     private final int enchantability;
/*     */     
/*     */     ArmorMaterial(String name, int maxDamage, int[] reductionAmounts, int enchantability) {
/* 251 */       this.name = name;
/* 252 */       this.maxDamageFactor = maxDamage;
/* 253 */       this.damageReductionAmountArray = reductionAmounts;
/* 254 */       this.enchantability = enchantability;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDurability(int armorType) {
/* 259 */       return ItemArmor.maxDamageArray[armorType] * this.maxDamageFactor;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDamageReductionAmount(int armorType) {
/* 264 */       return this.damageReductionAmountArray[armorType];
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEnchantability() {
/* 269 */       return this.enchantability;
/*     */     }
/*     */ 
/*     */     
/*     */     public Item getRepairItem() {
/* 274 */       return (this == LEATHER) ? Items.leather : ((this == CHAIN) ? Items.iron_ingot : ((this == GOLD) ? Items.gold_ingot : ((this == IRON) ? Items.iron_ingot : ((this == DIAMOND) ? Items.diamond : null))));
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 279 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */