/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.ui.font.fastuni.FontLoader;
/*    */ import awareline.main.utility.render.RenderUtil;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class BlockOverlay
/*    */   extends Module {
/*    */   public final Numbers<Double> r;
/*    */   public final Numbers<Double> g;
/*    */   public final Numbers<Double> b;
/*    */   public final Option<Boolean> togg;
/*    */   
/*    */   public BlockOverlay() {
/* 28 */     super("BlockOverlay", ModuleType.Render);
/* 29 */     this.r = new Numbers("Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 30 */     this.g = new Numbers("Green", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 31 */     this.b = new Numbers("Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
/* 32 */     this.togg = new Option("RenderString", Boolean.valueOf(true));
/* 33 */     addSettings(new Value[] { (Value)this.r, (Value)this.g, (Value)this.b, (Value)this.togg });
/*    */   }
/*    */   
/*    */   public int getRed() {
/* 37 */     return ((Double)this.r.get()).intValue();
/*    */   }
/*    */   
/*    */   public int getGreen() {
/* 41 */     return ((Double)this.g.get()).intValue();
/*    */   }
/*    */   
/*    */   public int getBlue() {
/* 45 */     return ((Double)this.b.get()).intValue();
/*    */   }
/*    */   
/*    */   public boolean getRender() {
/* 49 */     return ((Boolean)this.togg.get()).booleanValue();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onRender(EventRender2D event) {
/* 54 */     if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && 
/* 55 */       getRender()) {
/* 56 */       BlockPos pos = mc.objectMouseOver.getBlockPos();
/* 57 */       Block block = mc.theWorld.getBlockState(pos).getBlock();
/* 58 */       String s2 = block.getLocalizedName();
/* 59 */       ScaledResolution res = event.getResolution();
/* 60 */       int x = res.getScaledWidth() / 2 + 6;
/* 61 */       int y = res.getScaledHeight() / 2 - 1;
/* 62 */       FontLoader.PF18.drawString(s2, x + 3.5F, y - 2.65F, -1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onRender3D(EventRender3D event) {
/* 69 */     if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 70 */       BlockPos pos = mc.objectMouseOver.getBlockPos();
/* 71 */       Block block = mc.theWorld.getBlockState(pos).getBlock();
/* 72 */       double n = pos.getX();
/* 73 */       double x = n - (mc.getRenderManager()).renderPosX;
/* 74 */       double n2 = pos.getY();
/* 75 */       double y = n2 - (mc.getRenderManager()).renderPosY;
/* 76 */       double n3 = pos.getZ();
/* 77 */       double z = n3 - (mc.getRenderManager()).renderPosZ;
/* 78 */       GL11.glPushMatrix();
/* 79 */       GL11.glEnable(3042);
/* 80 */       GL11.glBlendFunc(770, 771);
/* 81 */       GL11.glDisable(3553);
/* 82 */       GL11.glEnable(2848);
/* 83 */       GL11.glDisable(2929);
/* 84 */       GL11.glDepthMask(false);
/* 85 */       GL11.glColor4f(getRed() / 255.0F, getGreen() / 255.0F, getBlue() / 255.0F, 0.15F);
/* 86 */       double minX = (block instanceof net.minecraft.block.BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0D : block.getBlockBoundsMinX();
/* 87 */       double minY = (block instanceof net.minecraft.block.BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0D : block.getBlockBoundsMinY();
/* 88 */       double minZ = (block instanceof net.minecraft.block.BlockStairs || Block.getIdFromBlock(block) == 134) ? 0.0D : block.getBlockBoundsMinZ();
/* 89 */       RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
/* 90 */       GL11.glColor4f(getRed() / 255.0F, getGreen() / 255.0F, getBlue() / 255.0F, 1.0F);
/* 91 */       GL11.glLineWidth(0.5F);
/* 92 */       RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
/* 93 */       GL11.glDisable(2848);
/* 94 */       GL11.glEnable(3553);
/* 95 */       GL11.glEnable(2929);
/* 96 */       GL11.glDepthMask(true);
/* 97 */       GL11.glDisable(3042);
/* 98 */       GL11.glPopMatrix();
/* 99 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\BlockOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */