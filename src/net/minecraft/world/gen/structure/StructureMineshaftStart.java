/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StructureMineshaftStart
/*    */   extends StructureStart
/*    */ {
/*    */   public StructureMineshaftStart() {}
/*    */   
/*    */   public StructureMineshaftStart(World worldIn, Random rand, int chunkX, int chunkZ) {
/* 15 */     super(chunkX, chunkZ);
/* 16 */     StructureMineshaftPieces.Room structuremineshaftpieces$room = new StructureMineshaftPieces.Room(0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
/* 17 */     this.components.add(structuremineshaftpieces$room);
/* 18 */     structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, rand);
/* 19 */     updateBoundingBox();
/* 20 */     markAvailableHeight(worldIn, rand, 10);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureMineshaftStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */