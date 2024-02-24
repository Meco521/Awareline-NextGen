/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemTool
/*     */   extends Item
/*     */ {
/*     */   private final Set<Block> effectiveBlocks;
/*  17 */   public float efficiencyOnProperMaterial = 4.0F;
/*     */ 
/*     */   
/*     */   private final float damageVsEntity;
/*     */ 
/*     */   
/*     */   protected Item.ToolMaterial toolMaterial;
/*     */ 
/*     */   
/*     */   protected ItemTool(float attackDamage, Item.ToolMaterial material, Set<Block> effectiveBlocks) {
/*  27 */     this.toolMaterial = material;
/*  28 */     this.effectiveBlocks = effectiveBlocks;
/*  29 */     this.maxStackSize = 1;
/*  30 */     setMaxDamage(material.getMaxUses());
/*  31 */     this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
/*  32 */     this.damageVsEntity = attackDamage + material.getDamageVsEntity();
/*  33 */     setCreativeTab(CreativeTabs.tabTools);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrVsBlock(ItemStack stack, Block state) {
/*  38 */     return this.effectiveBlocks.contains(state) ? this.efficiencyOnProperMaterial : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  47 */     stack.damageItem(2, attacker);
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  56 */     if (blockIn.getBlockHardness(worldIn, pos) != 0.0D)
/*     */     {
/*  58 */       stack.damageItem(1, playerIn);
/*     */     }
/*     */     
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item.ToolMaterial getToolMaterial() {
/*  74 */     return this.toolMaterial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/*  82 */     return this.toolMaterial.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolMaterialName() {
/*  90 */     return this.toolMaterial.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/*  98 */     return (this.toolMaterial.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/* 103 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
/* 104 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", this.damageVsEntity, 0));
/* 105 */     return multimap;
/*     */   }
/*     */   
/*     */   public float getDamage() {
/* 109 */     return this.damageVsEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */