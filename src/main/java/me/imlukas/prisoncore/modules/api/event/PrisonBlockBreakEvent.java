package me.imlukas.prisoncore.modules.api.event;

import lombok.Data;
import lombok.Getter;
import me.imlukas.prisoncore.modules.database.user.PrisonUser;
import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

@Getter
public class PrisonBlockBreakEvent extends BlockBreakEvent  {

    private final BaseTool usedTool;
    private final PrisonUser prisonUser;
    private final UUID playerId;

    public PrisonBlockBreakEvent(BaseTool usedTool, PrisonUser prisonUser, Block block, Player player) {
        super(block, player);
        this.usedTool = usedTool;
        this.prisonUser = prisonUser;
        this.playerId = player.getUniqueId();
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
