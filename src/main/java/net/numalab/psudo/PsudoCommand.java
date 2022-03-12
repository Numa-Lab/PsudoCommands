package net.numalab.psudo;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.PsudoCommodoreExtension;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class PsudoCommand extends JavaPlugin {
    @Override
    public void onEnable() {
        PsudoCommandExecutor executor = new PsudoCommandExecutor();
        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {
            Commodore commodore = CommodoreProvider.getCommodore(this);
            registerCommand(commodore, executor, "psudo", PsudoCommandExecutor.PsudoCommandType.PSUDO);
            registerCommand(commodore, executor, "psudoAs", PsudoCommandExecutor.PsudoCommandType.PSUDO_AS);
        }
    }

    private static void registerCommand(Commodore commodore, PsudoCommandExecutor executor, String commandName, PsudoCommandExecutor.PsudoCommandType commandType) {
        commodore.register(LiteralArgumentBuilder.literal(commandName)
                .then(
                        RequiredArgumentBuilder.argument("arguments", StringArgumentType.greedyString())
                                .suggests((ctx, builder) -> builder.buildFuture())
                                .executes(cs -> {
                                    String[] args = StringArgumentType.getString(cs, "arguments").split(" ");
                                    Object source = cs.getSource();
                                    CommandSender baseSender = commodore.getBukkitSender(source);
                                    CommandSender sender = PsudoCommodoreExtension.getBukkitSender(source);
                                    boolean result = executor.onCommand(baseSender, sender, commandType, args);
                                    return result ? SINGLE_SUCCESS : 0;
                                })
                )
        );
    }
}
