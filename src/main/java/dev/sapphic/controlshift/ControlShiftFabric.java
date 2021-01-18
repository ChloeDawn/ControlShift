/*
 * Copyright 2021 Chloe Dawn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sapphic.controlshift;

import net.minecraft.client.Options;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Options.class)
public final class ControlShiftFabric {
  @ModifyConstant(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
    require = 1, allow = 1, constant = @Constant(intValue = GLFW.GLFW_KEY_LEFT_SHIFT))
  private static int shiftToControl(final int shift) {
    return GLFW.GLFW_KEY_LEFT_CONTROL;
  }

  @ModifyConstant(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
    require = 1, allow = 1, constant = @Constant(intValue = GLFW.GLFW_KEY_LEFT_CONTROL))
  private static int controlToShift(final int control) {
    return GLFW.GLFW_KEY_LEFT_SHIFT;
  }
}
