/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityNote;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNote
/*     */   extends BlockContainer
/*     */ {
/*  20 */   private static final List<String> INSTRUMENTS = Lists.newArrayList((Object[])new String[] { "harp", "bd", "snare", "hat", "bassattack" });
/*     */ 
/*     */   
/*     */   public BlockNote() {
/*  24 */     super(Material.wood);
/*  25 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  33 */     boolean flag = worldIn.isBlockPowered(pos);
/*  34 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  36 */     if (tileentity instanceof TileEntityNote) {
/*     */       
/*  38 */       TileEntityNote tileentitynote = (TileEntityNote)tileentity;
/*     */       
/*  40 */       if (tileentitynote.previousRedstoneState != flag) {
/*     */         
/*  42 */         if (flag)
/*     */         {
/*  44 */           tileentitynote.triggerNote(worldIn, pos);
/*     */         }
/*     */         
/*  47 */         tileentitynote.previousRedstoneState = flag;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  54 */     if (worldIn.isRemote)
/*     */     {
/*  56 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  60 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  62 */     if (tileentity instanceof TileEntityNote) {
/*     */       
/*  64 */       TileEntityNote tileentitynote = (TileEntityNote)tileentity;
/*  65 */       tileentitynote.changePitch();
/*  66 */       tileentitynote.triggerNote(worldIn, pos);
/*  67 */       playerIn.triggerAchievement(StatList.field_181735_S);
/*     */     } 
/*     */     
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  76 */     if (!worldIn.isRemote) {
/*     */       
/*  78 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  80 */       if (tileentity instanceof TileEntityNote) {
/*     */         
/*  82 */         ((TileEntityNote)tileentity).triggerNote(worldIn, pos);
/*  83 */         playerIn.triggerAchievement(StatList.field_181734_R);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  93 */     return (TileEntity)new TileEntityNote();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getInstrument(int id) {
/*  98 */     if (id < 0 || id >= INSTRUMENTS.size())
/*     */     {
/* 100 */       id = 0;
/*     */     }
/*     */     
/* 103 */     return INSTRUMENTS.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 111 */     float f = (float)Math.pow(2.0D, (eventParam - 12) / 12.0D);
/* 112 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "note." + getInstrument(eventID), 3.0F, f);
/* 113 */     worldIn.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, eventParam / 24.0D, 0.0D, 0.0D, new int[0]);
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 122 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockNote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */