function initializeCoreMod() {
  'use strict';

  return {
    'ControlShift': {
      'target': {
        'type': 'METHOD',
        'class': 'net.minecraft.client.GameSettings',
        'methodName': '<init>',
        'methodDesc': '(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V'
      },
      'transformer': function(methodNode) {
        function logTransform(from, to) {
          Java.type('net.minecraftforge.coremod.api.ASMAPI')
            .log('INFO', 'SIPUSH ' + from + ' -> SIPUSH ' + to);
        }

        var OPCODE_SIPUSH = 17;
        var GLFW_KEY_LEFT_SHIFT = 340;
        var GLFW_KEY_LEFT_CONTROL = 341;
        var shiftToControl = false;
        var controlToShift = false;

        methodNode.instructions.forEach(function (insn, index) {
          if (insn.opcode === OPCODE_SIPUSH) {
            if (insn.operand === GLFW_KEY_LEFT_SHIFT) {
              if (shiftToControl) {
                throw 'Too many matches for GLFW_KEY_LEFT_SHIFT (340)';
              }
              logTransform(GLFW_KEY_LEFT_SHIFT, GLFW_KEY_LEFT_CONTROL);
              insn.operand = GLFW_KEY_LEFT_CONTROL;
              shiftToControl = true;
            } else if (insn.operand === GLFW_KEY_LEFT_CONTROL) {
              if (controlToShift) {
                throw 'Too many matches for GLFW_KEY_LEFT_CONTROL (341)';
              }
              logTransform(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_LEFT_SHIFT);
              insn.operand = GLFW_KEY_LEFT_SHIFT;
              controlToShift = true;
            }
          }
        });

        if (!shiftToControl || !controlToShift) {
          if (!shiftToControl && !controlToShift) {
            throw 'No matches found for GLFW_KEY_LEFT_SHIFT (340) and GLFW_KEY_LEFT_CONTROL (341)';
          }

          throw 'No match found for ' + (shiftToControl ? 'GLFW_KEY_LEFT_CONTROL (341)' : 'GLFW_KEY_LEFT_SHIFT (340)');
        }

        return methodNode;
      }
    }
  };
}
