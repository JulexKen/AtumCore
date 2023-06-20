package me.phoenixra.atum.core.placeholders.types;


import me.phoenixra.atum.core.AtumPlugin;
import me.phoenixra.atum.core.placeholders.RegistrablePlaceholder;
import me.phoenixra.atum.core.placeholders.context.PlaceholderContext;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
public class PlayerDynamicPlaceholder implements RegistrablePlaceholder {
    /**
     * The arguments pattern.
     */
    private final Pattern pattern;

    /**
     * The function to retrieve the output of the arguments.
     */
    private final BiFunction<@NotNull String, @NotNull Player, @Nullable String> function;

    /**
     * The plugin for the arguments.
     */
    private final AtumPlugin plugin;

    /**
     * Create a new dynamic arguments.
     *
     * @param plugin   The plugin.
     * @param pattern  The pattern.
     * @param function The function to retrieve the value.
     */
    public PlayerDynamicPlaceholder(@NotNull final AtumPlugin plugin,
                                    @NotNull final Pattern pattern,
                                    @NotNull final BiFunction<@NotNull String, @NotNull Player, @Nullable String> function) {
        this.plugin = plugin;
        this.pattern = Pattern.compile("%"+plugin.getName()+"_"+pattern.pattern()+"%");
        this.function = function;
    }

    @Override
    public @Nullable String getValue(@NotNull final String args,
                                     @NotNull final PlaceholderContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return null;
        }

        return function.apply(args, player);
    }
    @Override
    public @NotNull AtumPlugin getPlugin() {
        return this.plugin;
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        return this.pattern;
    }

    @Override
    public @NotNull PlayerDynamicPlaceholder register() {
        return (PlayerDynamicPlaceholder) RegistrablePlaceholder.super.register();
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PlayerDynamicPlaceholder that)) {
            return false;
        }

        return Objects.equals(this.getPattern(), that.getPattern())
                && Objects.equals(this.getPlugin(), that.getPlugin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPattern(), this.getPlugin());
    }
}
