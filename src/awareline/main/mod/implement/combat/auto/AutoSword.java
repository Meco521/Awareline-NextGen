/*    */ package awareline.main.mod.implement.combat.auto;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.EnumCreatureAttribute;
/*    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class AutoSword
/*    */   extends Module
/*    */ {
/*    */   public AutoSword() {
/* 18 */     super("AutoSword", ModuleType.Combat);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacket(EventPacketSend e) {
/* 23 */     if (e.getPacket() instanceof net.minecraft.network.play.client.C02PacketUseEntity) {
/* 24 */       float damage = 1.0F;
/* 25 */       int bestSwordSlot = -1;
/* 26 */       for (int i = 0; i < 9; i++) {
/*    */         
/* 28 */         ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i); float damageLevel;
/* 29 */         if (itemStack != null && itemStack.getItem() instanceof net.minecraft.item.ItemSword && (damageLevel = (float)getSwordDamage(itemStack)) > damage) {
/*    */           
/* 31 */           damage = damageLevel;
/* 32 */           bestSwordSlot = i;
/*    */         } 
/* 34 */       }  if (bestSwordSlot != -1) {
/* 35 */         mc.thePlayer.inventory.currentItem = bestSwordSlot;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   double getSwordDamage(ItemStack itemStack) {
/* 41 */     double damage = 0.0D;
/* 42 */     Optional<AttributeModifier> attributeModifier = itemStack.getAttributeModifiers().values().stream().findFirst();
/* 43 */     if (attributeModifier.isPresent()) {
/* 44 */       damage = ((AttributeModifier)attributeModifier.get()).getAmount();
/*    */     }
/* 46 */     return damage + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoSword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */