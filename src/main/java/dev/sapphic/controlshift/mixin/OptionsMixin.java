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

package dev.sapphic.controlshift.mixin;

import net.minecraft.client.Options;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Pseudo
@Mixin(value = Options.class, targets = { "net.minecraft.client.GameSettings" })
abstract class OptionsMixin {
  @Group(name = "shiftToControl", min = 1, max = 1)
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = GLFW.GLFW_KEY_LEFT_SHIFT), allow = 1)
  private static int shiftToControlGlfw(final int shift) {
    return GLFW.GLFW_KEY_LEFT_CONTROL;
  }

  @Group(name = "controlToShift", max = 1)
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = GLFW.GLFW_KEY_LEFT_CONTROL), allow = 1)
  private static int controlToShiftGlfw(final int control) {
    return GLFW.GLFW_KEY_LEFT_SHIFT;
  }

  @Group(name = "shiftToControl", min = 1, max = 1)
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = JInput.KEY_LEFTSHIFT), allow = 1)
  private static int shiftToControlJinput(final int shift) {
    return JInput.KEY_LEFTCTRL;
  }

  @Group(name = "controlToShift", max = 1)
  @ModifyConstant(method = "<init>", constant = @Constant(intValue = JInput.KEY_LEFTCTRL), allow = 1)
  private static int controlToShiftJinput(final int control) {
    return JInput.KEY_LEFTSHIFT;
  }
}
