/*
 * Copyright (C) 2020 Chloe Dawn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sapphic.controlshift;

import net.minecraft.client.options.GameOptions;
import org.checkerframework.common.value.qual.IntVal;
import org.checkerframework.dataflow.qual.Pure;
import org.jetbrains.annotations.ApiStatus;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@ApiStatus.Internal
@Mixin(value = GameOptions.class, remap = false)
abstract class ControlShift {
  @Pure
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = GLFW.GLFW_KEY_LEFT_SHIFT), require = 1, allow = 1)
  private @IntVal(GLFW.GLFW_KEY_LEFT_CONTROL) int shiftToControl(final @IntVal(GLFW.GLFW_KEY_LEFT_SHIFT) int shift) {
    return GLFW.GLFW_KEY_LEFT_CONTROL;
  }

  @Pure
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = GLFW.GLFW_KEY_LEFT_CONTROL), require = 1, allow = 1)
  private @IntVal(GLFW.GLFW_KEY_LEFT_SHIFT) int controlToShift(final @IntVal(GLFW.GLFW_KEY_LEFT_CONTROL) int control) {
    return GLFW.GLFW_KEY_LEFT_SHIFT;
  }
}
