package me.lucko.commodore;

import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.Objects;

public class PsudoCommodoreExtension {
    private static final Method GET_ENTITY_METHOD;
    private static final Method GET_BUKKIT_SENDER_METHOD;

    public static CommandSender getBukkitSender(Object commandWrapperListener) {
        Objects.requireNonNull(commandWrapperListener, "commandWrapperListener");

        try {
            Object entity = GET_ENTITY_METHOD.invoke(commandWrapperListener);
            Objects.requireNonNull(entity, "commandWrapperListener.entity");
            return (CommandSender) GET_BUKKIT_SENDER_METHOD.invoke(entity, commandWrapperListener);
        } catch (ReflectiveOperationException var3) {
            throw new RuntimeException(var3);
        }
    }

    static {
        try {
            Class<?> commandListenerWrapper;
            Class<?> commandListener;
            if (ReflectionUtil.minecraftVersion() > 16) {
                commandListenerWrapper = ReflectionUtil.mcClass("commands.CommandListenerWrapper");
                commandListener = ReflectionUtil.mcClass("commands.ICommandListener");
            } else {
                commandListenerWrapper = ReflectionUtil.nmsClass("CommandListenerWrapper");
                commandListener = ReflectionUtil.nmsClass("ICommandListener");
            }
            GET_ENTITY_METHOD = commandListenerWrapper.getDeclaredMethod("getEntity");
            GET_ENTITY_METHOD.setAccessible(true);
            GET_BUKKIT_SENDER_METHOD = commandListener.getDeclaredMethod("getBukkitSender", commandListenerWrapper);
            GET_BUKKIT_SENDER_METHOD.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
