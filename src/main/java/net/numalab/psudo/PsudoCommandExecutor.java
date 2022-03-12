package net.numalab.psudo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class PsudoCommandExecutor {
    public enum PsudoCommandType {
        PSUDO,
        PSUDO_AS,
    }

    public boolean onCommand(CommandSender baseSender, CommandSender sender, PsudoCommandType type, String[] args) {
        if (type == PsudoCommandType.PSUDO && baseSender.hasPermission("psudocommand.psudo")
                || type == PsudoCommandType.PSUDO_AS && baseSender.hasPermission("psudocommand.psudoas")) {
            if (args.length <= 0) {
                sender.sendMessage(ChatColor.GRAY + "[PsudoCommands] Please provide a valid command.");
                return false;
            }
            int coordCounter = 0;
            List<StringBuilder> cmds = new ArrayList<>();
            cmds.add(new StringBuilder());
            for (int i = 0; i < args.length; i++) {
                List<StringBuilder> temps = new ArrayList<>();
                for (StringBuilder cmd : cmds) {
                    if (args[i].startsWith("~") || args[i].startsWith("^")) {
                        if (!args[i + 1].startsWith("~") && (coordCounter == 0 || coordCounter >= 3)) {
                            cmd.append(sender.getName());
                        } else {
                            if (coordCounter == 0)
                                cmd.append(PsudoCommandUtils.getIntRelative(args[i], "x", (Entity) sender));
                            if (coordCounter == 1)
                                cmd.append(PsudoCommandUtils.getIntRelative(args[i], "y", (Entity) sender));
                            if (coordCounter == 2)
                                cmd.append(PsudoCommandUtils.getIntRelative(args[i], "z", (Entity) sender));
                            coordCounter++;
                        }
                    } else if (args[i].startsWith("@")) {
                        Entity[] e = PsudoCommandUtils.getTargets(sender, args[i]);
                        if (e == null)
                            continue;
                        boolean works = true;
                        for (int j = 1; j < e.length; j++) {
                            StringBuilder sb = new StringBuilder(cmd.toString());
                            if (e[j] == null) {
                                works = false;
                                break;
                            }
                            sb.append((e[j].getCustomName() != null ? e[j].getCustomName() : e[j].getName()));
                            if (i + 1 < args.length) {
                                sb.append(" ");
                            }
                            temps.add(sb);
                        }
                        if (!works)
                            return false;
                        if (e.length == 0 || e[0] == null) {
                            return false;
                        } else {
                            cmd.append(e[0].getCustomName() != null ? e[0].getCustomName() : e[0].getName());
                        }
                    } else {
                        cmd.append(args[i]);
                    }
                    if (i + 1 < args.length) {
                        cmd.append(" ");
                    }
                }
                if (temps.size() > 0) {
                    cmds.addAll(temps);
                    temps.clear();
                }
            }
            boolean atleastOne = false;
            for (StringBuilder cmd : cmds) {
                if (Bukkit.dispatchCommand(type == PsudoCommandType.PSUDO ? baseSender : sender, cmd.toString()))
                    atleastOne = true;
            }
            return atleastOne;
        }
        return false;
    }
}
