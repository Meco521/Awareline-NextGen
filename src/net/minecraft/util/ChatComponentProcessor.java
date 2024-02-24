/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.EntityNotFoundException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerSelector;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatComponentProcessor
/*    */ {
/*    */   public static IChatComponent processComponent(ICommandSender commandSender, IChatComponent component, Entity entityIn) throws CommandException {
/* 15 */     IChatComponent ichatcomponent = null;
/*    */     
/* 17 */     if (component instanceof ChatComponentScore) {
/*    */       
/* 19 */       ChatComponentScore chatcomponentscore = (ChatComponentScore)component;
/* 20 */       String s = chatcomponentscore.getName();
/*    */       
/* 22 */       if (PlayerSelector.hasArguments(s)) {
/*    */         
/* 24 */         List<Entity> list = PlayerSelector.matchEntities(commandSender, s, Entity.class);
/*    */         
/* 26 */         if (list.size() != 1)
/*    */         {
/* 28 */           throw new EntityNotFoundException();
/*    */         }
/*    */         
/* 31 */         s = ((Entity)list.get(0)).getName();
/*    */       } 
/*    */       
/* 34 */       ichatcomponent = (entityIn != null && s.equals("*")) ? new ChatComponentScore(entityIn.getName(), chatcomponentscore.getObjective()) : new ChatComponentScore(s, chatcomponentscore.getObjective());
/* 35 */       ((ChatComponentScore)ichatcomponent).setValue(chatcomponentscore.getUnformattedTextForChat());
/*    */     }
/* 37 */     else if (component instanceof ChatComponentSelector) {
/*    */       
/* 39 */       String s1 = ((ChatComponentSelector)component).getSelector();
/* 40 */       ichatcomponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, s1);
/*    */       
/* 42 */       if (ichatcomponent == null)
/*    */       {
/* 44 */         ichatcomponent = new ChatComponentText("");
/*    */       }
/*    */     }
/* 47 */     else if (component instanceof ChatComponentText) {
/*    */       
/* 49 */       ichatcomponent = new ChatComponentText(((ChatComponentText)component).getChatComponentText_TextValue());
/*    */     }
/*    */     else {
/*    */       
/* 53 */       if (!(component instanceof ChatComponentTranslation))
/*    */       {
/* 55 */         return component;
/*    */       }
/*    */       
/* 58 */       Object[] aobject = ((ChatComponentTranslation)component).getFormatArgs();
/*    */       
/* 60 */       for (int i = 0; i < aobject.length; i++) {
/*    */         
/* 62 */         Object object = aobject[i];
/*    */         
/* 64 */         if (object instanceof IChatComponent)
/*    */         {
/* 66 */           aobject[i] = processComponent(commandSender, (IChatComponent)object, entityIn);
/*    */         }
/*    */       } 
/*    */       
/* 70 */       ichatcomponent = new ChatComponentTranslation(((ChatComponentTranslation)component).getKey(), aobject);
/*    */     } 
/*    */     
/* 73 */     ChatStyle chatstyle = component.getChatStyle();
/*    */     
/* 75 */     if (chatstyle != null)
/*    */     {
/* 77 */       ichatcomponent.setChatStyle(chatstyle.createShallowCopy());
/*    */     }
/*    */     
/* 80 */     for (IChatComponent ichatcomponent1 : component.getSiblings())
/*    */     {
/* 82 */       ichatcomponent.appendSibling(processComponent(commandSender, ichatcomponent1, entityIn));
/*    */     }
/*    */     
/* 85 */     return ichatcomponent;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ChatComponentProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */