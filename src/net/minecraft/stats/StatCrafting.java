/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class StatCrafting
/*    */   extends StatBase
/*    */ {
/*    */   private final Item field_150960_a;
/*    */   
/*    */   public StatCrafting(String p_i45910_1_, String p_i45910_2_, IChatComponent statNameIn, Item p_i45910_4_) {
/* 13 */     super(p_i45910_1_ + p_i45910_2_, statNameIn);
/* 14 */     this.field_150960_a = p_i45910_4_;
/* 15 */     int i = Item.getIdFromItem(p_i45910_4_);
/*    */     
/* 17 */     if (i != 0)
/*    */     {
/* 19 */       IScoreObjectiveCriteria.INSTANCES.put(p_i45910_1_ + i, getCriteria());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Item func_150959_a() {
/* 25 */     return this.field_150960_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\StatCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */