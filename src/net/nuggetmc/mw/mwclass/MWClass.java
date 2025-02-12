package net.nuggetmc.mw.mwclass;

import net.md_5.bungee.api.ChatColor;
import net.nuggetmc.mw.MegaWalls;
import net.nuggetmc.mw.energy.EnergyManager;
import net.nuggetmc.mw.mwclass.info.Diamond;
import net.nuggetmc.mw.mwclass.info.MWClassInfo;
import net.nuggetmc.mw.mwclass.info.Playstyle;
import net.nuggetmc.mw.utils.MWHealth;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;
import java.util.Set;

public abstract class MWClass implements Listener {

    protected final MegaWalls plugin;
    protected final MWClassManager manager;
    protected final MWHealth mwhealth;
    protected final EnergyManager energyManager;

    protected String[] name;
    protected Material icon;
    protected ChatColor color;
    protected Playstyle[] playstyles;
    protected Diamond[] diamonds;
    protected MWClassInfo classInfo;

    public MWClass() {
        this.plugin = MegaWalls.getInstance();
        this.manager = plugin.getClassManager();
        this.mwhealth = plugin.getMWHealth();
        this.energyManager = plugin.getEnergyManager();
    }

    public String getName() {
        return plugin.isChinese()?name[0]:name[1];
    }

    protected Set<Player> inRange(Player player, double radius) {
        World world = player.getWorld();
        Location locUp = player.getEyeLocation();
        Set<Player> result = new HashSet<>();

        for (Player victim : Bukkit.getOnlinePlayers()) {
            if (world != victim.getWorld()) continue;

            Location loc = victim.getEyeLocation();

            if (locUp.distance(loc) <= radius && player != victim && !victim.isDead()) {
                result.add(victim);
            }
        }

        return result;
    }
    /*@EventHandler
    public void onBow(EntityDamageByEntityEvent e){
        if (e.getDamager()instanceof Arrow){
            if (e.getEntity() instanceof Player){
                ((Player) e.getEntity()).getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1*20, 0));
            }
        }
    }*/
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        this.hit(e);
    }

    public Material getIcon() {
        return icon;
    }

    public ChatColor getColor() {
        return color;
    }

    public Playstyle[] getPlaystyles() {
        return playstyles;
    }

    public Diamond[] getDiamonds() {
        return diamonds;
    }

    public MWClassInfo getInfo() {
        return classInfo;
    }
    public String getShortName(){
        return plugin.isChinese()?name[0] :name[2];
    }

    public abstract void ability(Player player);

    public abstract void assign(Player player);
    public abstract void hit(EntityDamageByEntityEvent e);
    public int getPrice(){
        return 0;
    }
}
