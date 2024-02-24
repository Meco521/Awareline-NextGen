package net.minecraftforge.client.model;

import com.google.common.base.Optional;

public interface IModelState {
  Optional<TRSRTransformation> apply(Optional<? extends IModelPart> paramOptional);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraftforge\client\model\IModelState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */