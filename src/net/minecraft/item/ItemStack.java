/*      */ package net.minecraft.item;
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentDurability;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.event.HoverEvent;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public final class ItemStack {
/*   33 */   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
/*      */ 
/*      */   
/*      */   public int stackSize;
/*      */ 
/*      */   
/*      */   public int animationsToGo;
/*      */   
/*      */   public Item item;
/*      */   
/*      */   private NBTTagCompound stackTagCompound;
/*      */   
/*      */   private int itemDamage;
/*      */   
/*      */   private EntityItemFrame itemFrame;
/*      */   
/*      */   private Block canDestroyCacheBlock;
/*      */   
/*      */   private boolean canDestroyCacheResult;
/*      */   
/*      */   private Block canPlaceOnCacheBlock;
/*      */   
/*      */   private boolean canPlaceOnCacheResult;
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn) {
/*   59 */     this(blockIn, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn, int amount) {
/*   64 */     this(blockIn, amount, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn, int amount, int meta) {
/*   69 */     this(Item.getItemFromBlock(blockIn), amount, meta);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn) {
/*   74 */     this(itemIn, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn, int amount) {
/*   79 */     this(itemIn, amount, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn, int amount, int meta) {
/*   84 */     this.canDestroyCacheBlock = null;
/*   85 */     this.canDestroyCacheResult = false;
/*   86 */     this.canPlaceOnCacheBlock = null;
/*   87 */     this.canPlaceOnCacheResult = false;
/*   88 */     this.item = itemIn;
/*   89 */     this.stackSize = amount;
/*   90 */     this.itemDamage = meta;
/*      */     
/*   92 */     if (this.itemDamage < 0)
/*      */     {
/*   94 */       this.itemDamage = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
/*  100 */     ItemStack itemstack = new ItemStack();
/*  101 */     itemstack.readFromNBT(nbt);
/*  102 */     return (itemstack.item != null) ? itemstack : null;
/*      */   }
/*      */ 
/*      */   
/*      */   private ItemStack() {
/*  107 */     this.canDestroyCacheBlock = null;
/*  108 */     this.canDestroyCacheResult = false;
/*  109 */     this.canPlaceOnCacheBlock = null;
/*  110 */     this.canPlaceOnCacheResult = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack splitStack(int amount) {
/*  118 */     ItemStack itemstack = new ItemStack(this.item, amount, this.itemDamage);
/*      */     
/*  120 */     if (this.stackTagCompound != null)
/*      */     {
/*  122 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*      */     }
/*      */     
/*  125 */     this.stackSize -= amount;
/*  126 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItem() {
/*  134 */     return this.item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  143 */     boolean flag = this.item.onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*      */     
/*  145 */     if (flag)
/*      */     {
/*  147 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*      */     }
/*      */     
/*  150 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(Block blockIn) {
/*  155 */     return this.item.getStrVsBlock(this, blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack useItemRightClick(World worldIn, EntityPlayer playerIn) {
/*  164 */     return this.item.onItemRightClick(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(World worldIn, EntityPlayer playerIn) {
/*  172 */     return this.item.onItemUseFinish(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/*  180 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
/*  181 */     nbt.setString("id", (resourcelocation == null) ? "minecraft:air" : resourcelocation.toString());
/*  182 */     nbt.setByte("Count", (byte)this.stackSize);
/*  183 */     nbt.setShort("Damage", (short)this.itemDamage);
/*      */     
/*  185 */     if (this.stackTagCompound != null)
/*      */     {
/*  187 */       nbt.setTag("tag", (NBTBase)this.stackTagCompound);
/*      */     }
/*      */     
/*  190 */     return nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound nbt) {
/*  198 */     if (nbt.hasKey("id", 8)) {
/*      */       
/*  200 */       this.item = Item.getByNameOrId(nbt.getString("id"));
/*      */     }
/*      */     else {
/*      */       
/*  204 */       this.item = Item.getItemById(nbt.getShort("id"));
/*      */     } 
/*      */     
/*  207 */     this.stackSize = nbt.getByte("Count");
/*  208 */     this.itemDamage = nbt.getShort("Damage");
/*      */     
/*  210 */     if (this.itemDamage < 0)
/*      */     {
/*  212 */       this.itemDamage = 0;
/*      */     }
/*      */     
/*  215 */     if (nbt.hasKey("tag", 10)) {
/*      */       
/*  217 */       this.stackTagCompound = nbt.getCompoundTag("tag");
/*      */       
/*  219 */       if (this.item != null)
/*      */       {
/*  221 */         this.item.updateItemStackNBT(this.stackTagCompound);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStackSize() {
/*  231 */     return this.item.getItemStackLimit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStackable() {
/*  239 */     return (getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemStackDamageable() {
/*  247 */     return (this.item == null) ? false : ((this.item.getMaxDamage() <= 0) ? false : ((!hasTagCompound() || !this.stackTagCompound.getBoolean("Unbreakable"))));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  252 */     return this.item.getHasSubtypes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemDamaged() {
/*  260 */     return (isItemStackDamageable() && this.itemDamage > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemDamage() {
/*  265 */     return this.itemDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMetadata() {
/*  270 */     return this.itemDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemDamage(int meta) {
/*  275 */     this.itemDamage = meta;
/*      */     
/*  277 */     if (this.itemDamage < 0)
/*      */     {
/*  279 */       this.itemDamage = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  288 */     return this.item.getMaxDamage();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attemptDamageItem(int amount, Random rand) {
/*  299 */     if (!isItemStackDamageable())
/*      */     {
/*  301 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  305 */     if (amount > 0) {
/*      */       
/*  307 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
/*  308 */       int j = 0;
/*      */       
/*  310 */       for (int k = 0; i > 0 && k < amount; k++) {
/*      */         
/*  312 */         if (EnchantmentDurability.negateDamage(this, i, rand))
/*      */         {
/*  314 */           j++;
/*      */         }
/*      */       } 
/*      */       
/*  318 */       amount -= j;
/*      */       
/*  320 */       if (amount <= 0)
/*      */       {
/*  322 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  326 */     this.itemDamage += amount;
/*  327 */     return (this.itemDamage > getMaxDamage());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void damageItem(int amount, EntityLivingBase entityIn) {
/*  336 */     if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).capabilities.isCreativeMode)
/*      */     {
/*  338 */       if (isItemStackDamageable())
/*      */       {
/*  340 */         if (attemptDamageItem(amount, entityIn.getRNG())) {
/*      */           
/*  342 */           entityIn.renderBrokenItemStack(this);
/*  343 */           this.stackSize--;
/*      */           
/*  345 */           if (entityIn instanceof EntityPlayer) {
/*      */             
/*  347 */             EntityPlayer entityplayer = (EntityPlayer)entityIn;
/*  348 */             entityplayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
/*      */             
/*  350 */             if (this.stackSize == 0 && this.item instanceof ItemBow)
/*      */             {
/*  352 */               entityplayer.destroyCurrentEquippedItem();
/*      */             }
/*      */           } 
/*      */           
/*  356 */           if (this.stackSize < 0)
/*      */           {
/*  358 */             this.stackSize = 0;
/*      */           }
/*      */           
/*  361 */           this.itemDamage = 0;
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
/*  372 */     boolean flag = this.item.hitEntity(this, entityIn, (EntityLivingBase)playerIn);
/*      */     
/*  374 */     if (flag)
/*      */     {
/*  376 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyed(World worldIn, Block blockIn, BlockPos pos, EntityPlayer playerIn) {
/*  385 */     boolean flag = this.item.onBlockDestroyed(this, worldIn, blockIn, pos, (EntityLivingBase)playerIn);
/*      */     
/*  387 */     if (flag)
/*      */     {
/*  389 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockIn) {
/*  398 */     return this.item.canHarvestBlock(blockIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn) {
/*  403 */     return this.item.itemInteractionForEntity(this, playerIn, entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack copy() {
/*  411 */     ItemStack itemstack = new ItemStack(this.item, this.stackSize, this.itemDamage);
/*      */     
/*  413 */     if (this.stackTagCompound != null)
/*      */     {
/*  415 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*      */     }
/*      */     
/*  418 */     return itemstack;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
/*  423 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? ((stackA.stackTagCompound == null && stackB.stackTagCompound != null) ? false : ((stackA.stackTagCompound == null || stackA.stackTagCompound.equals(stackB.stackTagCompound)))) : false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
/*  431 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? stackA.isItemStackEqual(stackB) : false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isItemStackEqual(ItemStack other) {
/*  439 */     return (this.stackSize != other.stackSize) ? false : ((this.item != other.item) ? false : ((this.itemDamage != other.itemDamage) ? false : ((this.stackTagCompound == null && other.stackTagCompound != null) ? false : ((this.stackTagCompound == null || this.stackTagCompound.equals(other.stackTagCompound))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
/*  447 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? stackA.isItemEqual(stackB) : false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEqual(ItemStack other) {
/*  456 */     return (other != null && this.item == other.item && this.itemDamage == other.itemDamage);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  461 */     return this.item.getUnlocalizedName(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ItemStack copyItemStack(ItemStack stack) {
/*  469 */     return (stack == null) ? null : stack.copy();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  474 */     return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
/*  483 */     if (this.animationsToGo > 0)
/*      */     {
/*  485 */       this.animationsToGo--;
/*      */     }
/*      */     
/*  488 */     this.item.onUpdate(this, worldIn, entityIn, inventorySlot, isCurrentItem);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
/*  493 */     playerIn.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], amount);
/*  494 */     this.item.onCreated(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getIsItemStackEqual(ItemStack p_179549_1_) {
/*  499 */     return isItemStackEqual(p_179549_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration() {
/*  504 */     return this.item.getMaxItemUseDuration(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction() {
/*  509 */     return this.item.getItemUseAction(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(World worldIn, EntityPlayer playerIn, int timeLeft) {
/*  517 */     this.item.onPlayerStoppedUsing(this, worldIn, playerIn, timeLeft);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasTagCompound() {
/*  525 */     return (this.stackTagCompound != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getTagCompound() {
/*  533 */     return this.stackTagCompound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getSubCompound(String key, boolean create) {
/*  541 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
/*      */     {
/*  543 */       return this.stackTagCompound.getCompoundTag(key);
/*      */     }
/*  545 */     if (create) {
/*      */       
/*  547 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  548 */       setTagInfo(key, (NBTBase)nbttagcompound);
/*  549 */       return nbttagcompound;
/*      */     } 
/*      */ 
/*      */     
/*  553 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagList getEnchantmentTagList() {
/*  559 */     return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTagCompound(NBTTagCompound nbt) {
/*  567 */     this.stackTagCompound = nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/*  575 */     String s = this.item.getItemStackDisplayName(this);
/*      */     
/*  577 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
/*      */       
/*  579 */       NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*      */       
/*  581 */       if (nbttagcompound.hasKey("Name", 8))
/*      */       {
/*  583 */         s = nbttagcompound.getString("Name");
/*      */       }
/*      */     } 
/*      */     
/*  587 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack setStackDisplayName(String displayName) {
/*  592 */     if (this.stackTagCompound == null)
/*      */     {
/*  594 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/*  597 */     if (!this.stackTagCompound.hasKey("display", 10))
/*      */     {
/*  599 */       this.stackTagCompound.setTag("display", (NBTBase)new NBTTagCompound());
/*      */     }
/*      */     
/*  602 */     this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
/*  603 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearCustomName() {
/*  611 */     if (this.stackTagCompound != null)
/*      */     {
/*  613 */       if (this.stackTagCompound.hasKey("display", 10)) {
/*      */         
/*  615 */         NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*  616 */         nbttagcompound.removeTag("Name");
/*      */         
/*  618 */         if (nbttagcompound.hasNoTags()) {
/*      */           
/*  620 */           this.stackTagCompound.removeTag("display");
/*      */           
/*  622 */           if (this.stackTagCompound.hasNoTags())
/*      */           {
/*  624 */             this.stackTagCompound = (NBTTagCompound)null;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasDisplayName() {
/*  636 */     return (this.stackTagCompound == null) ? false : (!this.stackTagCompound.hasKey("display", 10) ? false : this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8));
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTooltip(EntityPlayer playerIn, boolean advanced) {
/*  641 */     List<String> list = Lists.newArrayList();
/*  642 */     String s = getDisplayName();
/*      */     
/*  644 */     if (hasDisplayName())
/*      */     {
/*  646 */       s = EnumChatFormatting.ITALIC + s;
/*      */     }
/*      */     
/*  649 */     s = s + EnumChatFormatting.RESET;
/*      */     
/*  651 */     if (advanced) {
/*      */       
/*  653 */       String s1 = "";
/*      */       
/*  655 */       if (!s.isEmpty()) {
/*      */         
/*  657 */         s = s + " (";
/*  658 */         s1 = ")";
/*      */       } 
/*      */       
/*  661 */       int i = Item.getIdFromItem(this.item);
/*      */       
/*  663 */       if (getHasSubtypes())
/*      */       {
/*  665 */         s = s + String.format("#%04d/%d%s", new Object[] { Integer.valueOf(i), Integer.valueOf(this.itemDamage), s1 });
/*      */       }
/*      */       else
/*      */       {
/*  669 */         s = s + String.format("#%04d%s", new Object[] { Integer.valueOf(i), s1 });
/*      */       }
/*      */     
/*  672 */     } else if (!hasDisplayName() && this.item == Items.filled_map) {
/*      */       
/*  674 */       s = s + " #" + this.itemDamage;
/*      */     } 
/*      */     
/*  677 */     list.add(s);
/*  678 */     int i1 = 0;
/*      */     
/*  680 */     if (hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99))
/*      */     {
/*  682 */       i1 = this.stackTagCompound.getInteger("HideFlags");
/*      */     }
/*      */     
/*  685 */     if ((i1 & 0x20) == 0)
/*      */     {
/*  687 */       this.item.addInformation(this, playerIn, list, advanced);
/*      */     }
/*      */     
/*  690 */     if (hasTagCompound()) {
/*      */       
/*  692 */       if ((i1 & 0x1) == 0) {
/*      */         
/*  694 */         NBTTagList nbttaglist = getEnchantmentTagList();
/*      */         
/*  696 */         if (nbttaglist != null)
/*      */         {
/*  698 */           for (int j = 0; j < nbttaglist.tagCount(); j++) {
/*      */             
/*  700 */             int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/*  701 */             int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*      */             
/*  703 */             if (Enchantment.getEnchantmentById(k) != null)
/*      */             {
/*  705 */               list.add(Enchantment.getEnchantmentById(k).getTranslatedName(l));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  711 */       if (this.stackTagCompound.hasKey("display", 10)) {
/*      */         
/*  713 */         NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*      */         
/*  715 */         if (nbttagcompound.hasKey("color", 3))
/*      */         {
/*  717 */           if (advanced) {
/*      */             
/*  719 */             list.add("Color: #" + Integer.toHexString(nbttagcompound.getInteger("color")).toUpperCase());
/*      */           }
/*      */           else {
/*      */             
/*  723 */             list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
/*      */           } 
/*      */         }
/*      */         
/*  727 */         if (nbttagcompound.getTagId("Lore") == 9) {
/*      */           
/*  729 */           NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
/*      */           
/*  731 */           if (nbttaglist1.tagCount() > 0)
/*      */           {
/*  733 */             for (int j1 = 0; j1 < nbttaglist1.tagCount(); j1++)
/*      */             {
/*  735 */               list.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + nbttaglist1.getStringTagAt(j1));
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  742 */     Multimap<String, AttributeModifier> multimap = getAttributeModifiers();
/*      */     
/*  744 */     if (!multimap.isEmpty() && (i1 & 0x2) == 0) {
/*      */       
/*  746 */       list.add("");
/*      */       
/*  748 */       for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)multimap.entries()) {
/*      */         double d1;
/*  750 */         AttributeModifier attributemodifier = entry.getValue();
/*  751 */         double d0 = attributemodifier.getAmount();
/*      */         
/*  753 */         if (attributemodifier.getID() == Item.itemModifierUUID)
/*      */         {
/*  755 */           d0 += EnchantmentHelper.getModifierForCreature(this, EnumCreatureAttribute.UNDEFINED);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  760 */         if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
/*      */           
/*  762 */           d1 = d0;
/*      */         }
/*      */         else {
/*      */           
/*  766 */           d1 = d0 * 100.0D;
/*      */         } 
/*      */         
/*  769 */         if (d0 > 0.0D) {
/*      */           
/*  771 */           list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) })); continue;
/*      */         } 
/*  773 */         if (d0 < 0.0D) {
/*      */           
/*  775 */           d1 *= -1.0D;
/*  776 */           list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) }));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  781 */     if (hasTagCompound() && this.stackTagCompound.getBoolean("Unbreakable") && (i1 & 0x4) == 0)
/*      */     {
/*  783 */       list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
/*      */     }
/*      */     
/*  786 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (i1 & 0x8) == 0) {
/*      */       
/*  788 */       NBTTagList nbttaglist2 = this.stackTagCompound.getTagList("CanDestroy", 8);
/*      */       
/*  790 */       if (nbttaglist2.tagCount() > 0) {
/*      */         
/*  792 */         list.add("");
/*  793 */         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));
/*      */         
/*  795 */         for (int k1 = 0; k1 < nbttaglist2.tagCount(); k1++) {
/*      */           
/*  797 */           Block block = Block.getBlockFromName(nbttaglist2.getStringTagAt(k1));
/*      */           
/*  799 */           if (block != null) {
/*      */             
/*  801 */             list.add(EnumChatFormatting.DARK_GRAY + block.getLocalizedName());
/*      */           }
/*      */           else {
/*      */             
/*  805 */             list.add(EnumChatFormatting.DARK_GRAY + "missingno");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  811 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (i1 & 0x10) == 0) {
/*      */       
/*  813 */       NBTTagList nbttaglist3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*      */       
/*  815 */       if (nbttaglist3.tagCount() > 0) {
/*      */         
/*  817 */         list.add("");
/*  818 */         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));
/*      */         
/*  820 */         for (int l1 = 0; l1 < nbttaglist3.tagCount(); l1++) {
/*      */           
/*  822 */           Block block1 = Block.getBlockFromName(nbttaglist3.getStringTagAt(l1));
/*      */           
/*  824 */           if (block1 != null) {
/*      */             
/*  826 */             list.add(EnumChatFormatting.DARK_GRAY + block1.getLocalizedName());
/*      */           }
/*      */           else {
/*      */             
/*  830 */             list.add(EnumChatFormatting.DARK_GRAY + "missingno");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  836 */     if (advanced) {
/*      */       
/*  838 */       if (isItemDamaged())
/*      */       {
/*  840 */         list.add("Durability: " + (getMaxDamage() - this.itemDamage) + " / " + getMaxDamage());
/*      */       }
/*      */       
/*  843 */       list.add(EnumChatFormatting.DARK_GRAY + ((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
/*      */       
/*  845 */       if (hasTagCompound())
/*      */       {
/*  847 */         list.add(EnumChatFormatting.DARK_GRAY + "NBT: " + this.stackTagCompound.getKeySet().size() + " tag(s)");
/*      */       }
/*      */     } 
/*      */     
/*  851 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect() {
/*  856 */     return this.item.hasEffect(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity() {
/*  861 */     return this.item.getRarity(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEnchantable() {
/*  869 */     return !this.item.isItemTool(this) ? false : (!isItemEnchanted());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEnchantment(Enchantment ench, int level) {
/*  877 */     if (this.stackTagCompound == null)
/*      */     {
/*  879 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/*  882 */     if (!this.stackTagCompound.hasKey("ench", 9))
/*      */     {
/*  884 */       this.stackTagCompound.setTag("ench", (NBTBase)new NBTTagList());
/*      */     }
/*      */     
/*  887 */     NBTTagList nbttaglist = this.stackTagCompound.getTagList("ench", 10);
/*  888 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  889 */     nbttagcompound.setShort("id", (short)ench.effectId);
/*  890 */     nbttagcompound.setShort("lvl", (short)(byte)level);
/*  891 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEnchanted() {
/*  899 */     return (this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTagInfo(String key, NBTBase value) {
/*  904 */     if (this.stackTagCompound == null)
/*      */     {
/*  906 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/*  909 */     this.stackTagCompound.setTag(key, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEditBlocks() {
/*  914 */     return this.item.canItemEditBlocks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnItemFrame() {
/*  922 */     return (this.itemFrame != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemFrame(EntityItemFrame frame) {
/*  930 */     this.itemFrame = frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItemFrame getItemFrame() {
/*  938 */     return this.itemFrame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRepairCost() {
/*  946 */     return (hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRepairCost(int cost) {
/*  954 */     if (!hasTagCompound())
/*      */     {
/*  956 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/*  959 */     this.stackTagCompound.setInteger("RepairCost", cost);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getAttributeModifiers() {
/*      */     Multimap<String, AttributeModifier> multimap;
/*  966 */     if (hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
/*      */       
/*  968 */       HashMultimap hashMultimap = HashMultimap.create();
/*  969 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/*      */       
/*  971 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*      */       {
/*  973 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  974 */         AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);
/*      */         
/*  976 */         if (attributemodifier != null && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L)
/*      */         {
/*  978 */           hashMultimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  984 */       multimap = this.item.getItemAttributeModifiers();
/*      */     } 
/*      */     
/*  987 */     return multimap;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItem(Item newItem) {
/*  992 */     this.item = newItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getChatComponent() {
/* 1000 */     ChatComponentText chatcomponenttext = new ChatComponentText(getDisplayName());
/*      */     
/* 1002 */     if (hasDisplayName())
/*      */     {
/* 1004 */       chatcomponenttext.getChatStyle().setItalic(Boolean.valueOf(true));
/*      */     }
/*      */     
/* 1007 */     IChatComponent ichatcomponent = (new ChatComponentText("[")).appendSibling((IChatComponent)chatcomponenttext).appendText("]");
/*      */     
/* 1009 */     if (this.item != null) {
/*      */       
/* 1011 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 1012 */       writeToNBT(nbttagcompound);
/* 1013 */       ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, (IChatComponent)new ChatComponentText(nbttagcompound.toString())));
/* 1014 */       ichatcomponent.getChatStyle().setColor((getRarity()).rarityColor);
/*      */     } 
/*      */     
/* 1017 */     return ichatcomponent;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canDestroy(Block blockIn) {
/* 1022 */     if (blockIn == this.canDestroyCacheBlock)
/*      */     {
/* 1024 */       return this.canDestroyCacheResult;
/*      */     }
/*      */ 
/*      */     
/* 1028 */     this.canDestroyCacheBlock = blockIn;
/*      */     
/* 1030 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
/*      */       
/* 1032 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanDestroy", 8);
/*      */       
/* 1034 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1036 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*      */         
/* 1038 */         if (block == blockIn) {
/*      */           
/* 1040 */           this.canDestroyCacheResult = true;
/* 1041 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1046 */     this.canDestroyCacheResult = false;
/* 1047 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceOn(Block blockIn) {
/* 1053 */     if (blockIn == this.canPlaceOnCacheBlock)
/*      */     {
/* 1055 */       return this.canPlaceOnCacheResult;
/*      */     }
/*      */ 
/*      */     
/* 1059 */     this.canPlaceOnCacheBlock = blockIn;
/*      */     
/* 1061 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
/*      */       
/* 1063 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*      */       
/* 1065 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1067 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*      */         
/* 1069 */         if (block == blockIn) {
/*      */           
/* 1071 */           this.canPlaceOnCacheResult = true;
/* 1072 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1077 */     this.canPlaceOnCacheResult = false;
/* 1078 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */