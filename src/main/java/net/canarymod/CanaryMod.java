package net.canarymod;

import net.canarymod.api.CanaryServer;
import net.canarymod.api.channels.CanaryChannelManager;
import net.canarymod.api.commandsys.CanaryPlayerSelector;
import net.canarymod.api.factory.CanaryFactory;
import net.canarymod.api.scoreboard.CanaryScoreboardManager;
import net.canarymod.bansystem.BanManager;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.commandsys.CommandList;
import net.canarymod.commandsys.CommandManager;
import net.canarymod.commandsys.DuplicateCommandException;
import net.canarymod.config.Configuration;
import net.canarymod.database.DatabaseLoader;
import net.canarymod.help.HelpManager;
import net.canarymod.hook.HookExecutor;
import net.canarymod.kit.KitProvider;
import net.canarymod.motd.CanaryMessageOfTheDayListener;
import net.canarymod.motd.MessageOfTheDay;
import net.canarymod.permissionsystem.PermissionManager;
import net.canarymod.plugin.DefaultPluginManager;
import net.canarymod.plugin.PluginLangLoader;
import net.canarymod.user.OperatorsProvider;
import net.canarymod.user.ReservelistProvider;
import net.canarymod.user.UserAndGroupsProvider;
import net.canarymod.user.WhitelistProvider;
import net.canarymod.util.CanaryJsonNBTUtility;
import net.canarymod.warp.WarpProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * The implementation of Canary, the new catch-all etc replacement, only much better :P
 *
 * @author Chris (damagefilter)
 * @author Jos Kuijpers
 * @author Brian (WWOL)
 * @author Jason (darkdiplomat)
 */
public class CanaryMod extends Canary {

    private boolean isInitialised;
    /**
     * Creates a new CanaryMod
     */
    public CanaryMod() {
        setLoggerLevelDynamic(); //Once we know if debug is enabled, you can change the level accordingly
        Canary.setCanary(this);
        // This must be the first thing to call!
        DatabaseLoader.load();
        PluginLangLoader.load(); // Load the plugin language libraries
        NativeTranslate.initialize(); // Intialize native translation bridge

        this.jsonNBT = new CanaryJsonNBTUtility(); // Set up the Json to/from NBT utility
        this.motd = new MessageOfTheDay();
        // Initialize the subsystems that do not rely on others
        this.commandManager = new CommandManager();
        // this.permissionManager = new PermissionManager();
        this.hookExecutor = new HookExecutor();
        this.helpManager = new HelpManager();
        this.banManager = new BanManager();
        this.whitelist = new WhitelistProvider();
        this.ops = new OperatorsProvider();
        this.reservelist = new ReservelistProvider();
        this.factory = new CanaryFactory();
        this.playerSelector = new CanaryPlayerSelector();
        this.channelManager = new CanaryChannelManager();
        // Initialize the plugin loader and scan for plugins
        this.pluginManager = new DefaultPluginManager();
        this.scoreboardManager = new CanaryScoreboardManager();
    }
    
    void scanForPlugins(String path) {
        pluginManager.setPluginPath(path);
        pluginManager.scanForPlugins();
    }

    /**
     * Separately set users and groups provider
     */
    public void initUserAndGroupsManager() {
        this.userAndGroupsProvider = new UserAndGroupsProvider();
    }

    /**
     * Separately set the warps provider
     */
    public void initWarps() {
        this.warpProvider = new WarpProvider();
    }

    public void initKits() {
        this.kitProvider = new KitProvider();
    }

    public void registerCanaryCommands() {
        try {
            this.commandManager.registerCommands(new CommandList(), Canary.getServer(), false);
        }
        catch (CommandDependencyException e) {
            log.error("Failed to set up system commands! Dependency reolution failed!", e);
        }
        catch (DuplicateCommandException f) {
            log.error("Failed to set up system commands! A command already exists!", f);
        }
    }

    public void initPermissions() {
        this.permissionManager = new PermissionManager();
    }

    public void initMOTDListener() {
        motd().registerMOTDListener(new CanaryMessageOfTheDayListener(), (net.canarymod.motd.MOTDOwner) getServer(), false);
    }

    /**
     * Initialises all subsystems that need the native Minecraft server to be bootstrapped.
     * Must be called before plugins initialise.
     */
    public void lateInitialisation() {
        if (isInitialised) {
            return;
        }
        // They need the server to be set
        this.initPermissions();
        // Initialize providers that require Canary to be set already
        this.initUserAndGroupsManager();
        this.initKits();
        // Warps need the DimensionType data which is created upon servre start
        this.initWarps();
        // commands require a valid commandOwner which is the server.
        // That means for commands to work, we gotta load Minecraft first
        this.registerCanaryCommands();
        // and finally throw in the MOTDListner
        this.initMOTDListener();
        isInitialised = true;
    }

    @Override
    public void reload() {
        super.reload();
        setLoggerLevelDynamic();
        // Reload minecraft variables
        // ((CanaryConfigurationManager) instance.server.getConfigurationManager()).reload();
        // TODO RCON + QUERY?
        ((CanaryServer) instance.server).getHandle().reload();
    }

    static void setLoggerLevelDynamic() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        LoggerConfig logger = ctx.getConfiguration().getLoggers().get(LogManager.ROOT_LOGGER_NAME);
        logger.setLevel(Configuration.getServerConfig().getLoggerLevel());
        ctx.updateLoggers();
    }
}
