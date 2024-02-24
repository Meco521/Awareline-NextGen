package net.minecraftforge.client.model.pipeline;

import net.minecraft.client.renderer.vertex.VertexFormat;

public interface IVertexConsumer {
  VertexFormat getVertexFormat();
  
  void put(int paramInt, float... paramVarArgs);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraftforge\client\model\pipeline\IVertexConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */