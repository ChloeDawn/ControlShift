package dev.sapphic.controlshift;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod("controlshift")
public final class ControlShift {
  public ControlShift() {
    ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> {
      return Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (s, v) -> true);
    });
  }
}
