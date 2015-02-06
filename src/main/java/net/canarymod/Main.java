package net.canarymod;

import net.canarymod.api.inventory.CanaryEnchantment;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Enchantment;
import net.canarymod.api.inventory.Item;
import net.canarymod.serialize.EnchantmentSerializer;
import net.canarymod.serialize.ItemSerializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

import static net.canarymod.Canary.log;

public class Main {
    static WarningDialog warningDialog;
    static final Icon icon;
    
    private static boolean nocontrol;
    private static String pluginPath = "plugins/";

    static {
        java.net.URL imgURL = Main.class.getResource("/assets/favicon.png");
        Icon tempI = null;
        if (imgURL != null) {
            tempI = new ImageIcon(imgURL, "DA BIRD");
        }
        icon = tempI;

        // For our jar double-clickers add a message about the libraries loading when the user has a graphics environment and the console isn't present
        if (!GraphicsEnvironment.isHeadless() && System.console() == null) {
            final WarningDialog temp = new LibraryWarningDialog();
            SwingUtilities.invokeLater(new Runnable() {
                                           @Override
                                           public void run() {
                                               temp.create();
                                           }
                                       }
                                      );
            warningDialog = temp;
        }
    }


    private static void initBird() {
        // Initialize the bird
        CanaryMod canary = new CanaryMod();
        canary.scanForPlugins(pluginPath);
        // Add system internal serializers
        Canary.addSerializer(new ItemSerializer(), CanaryItem.class);
        Canary.addSerializer(new ItemSerializer(), Item.class);
        Canary.addSerializer(new EnchantmentSerializer(), CanaryEnchantment.class);
        Canary.addSerializer(new EnchantmentSerializer(), Enchantment.class);
    }

    /**
     * The canary Bootstrap process
     *
     * @param args
     */
    public static void main(String[] args) {
        log.info("Starting: " + Canary.getImplementationTitle() + " " + Canary.getImplementationVersion());
        log.info("Canary Path: " + Canary.getCanaryJarPath() + " & Working From: " + Canary.getWorkingPath());

        // Need to initialize the SQLite driver for some reason, initialize here for plugin use as well
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e) {
        }

        try {
            // Sets the default state for the gui, true is off, false is on
            MinecraftServer.setHeadless(true);
            boolean headless = GraphicsEnvironment.isHeadless();
            for (int index = 0; index < args.length; ++index) {
                String key = args[index].toLowerCase().replaceAll("\\-", "");
                String value = index == args.length - 1 ? null : args[index + 1];
                // Replace the nogui option with gui option so the gui is off by default
                if (key.equals("gui") && !headless) { // if environment is headless, this should have no effect
                    MinecraftServer.setHeadless(false);
                }
                else if (key.equals("nocontrol")) {
                    nocontrol = true;
                }
                else if (key.startsWith("p")) {
                    pluginPath = key.substring(1);
                }
            }

            // Warn the user if there is no known way to control the server
            if (System.console() == null) {
                if (headless && !nocontrol) {
                    log.warn("Server is starting without a known Console or GUI.");
                    log.warn("If this is intentional, use the \"nocontrol\" argument to suppress this warning.");
                }
                else if (!headless) {
                    MinecraftServer.setHeadless(false); // Seems they want the GUI
                    log.debug("Settings server to use GUI...");
                }
            }

            if (!MinecraftServer.isHeadless()) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (UnsupportedLookAndFeelException ignored) {
                }
                MinecraftServerGui.getLog();
            }

            initBird(); // Initialize the Bird
            MinecraftServer.main(args); // Boot up the native server
        }
        catch (Throwable t) {
            log.fatal("Error occurred during start up, unable to continue... ", t);
            System.exit(42); //Just in case something did manage to start going
        }
    }

    /**
     * Restart the server without killing the JVM
     *
     * @param reloadCanary
     */
    public static void restart(boolean reloadCanary) {
        throw new UnsupportedOperationException("Restart is not implemented yet!");
    }

    public static boolean canRunUncontrolled() {
        return nocontrol;
    }

    public static void closeLibWarning() {
        if (warningDialog != null && warningDialog instanceof LibraryWarningDialog) {
            // kill dialog
            warningDialog.setVisible(false);
            warningDialog.dispose();
            warningDialog = null;
        }
    }

    public static void displayEULAWarning() {
        closeLibWarning();
        warningDialog = new EULAWarningDialog();
        SwingUtilities.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           warningDialog.create();
                                       }
                                   }
                                  );
    }

    public static void displayPortBindWarning(String error) {
        closeLibWarning();
        warningDialog = new BindPortWarningDialog(error);
        SwingUtilities.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           warningDialog.create();
                                       }
                                   }
                                  );
    }

    public static boolean warningOpen() {
        return warningDialog != null && !(warningDialog instanceof LibraryWarningDialog);
    }

    private static abstract class WarningDialog extends JDialog {
        abstract void create();
    }

    private static final class LibraryWarningDialog extends WarningDialog {

        public final void create() {
            try {
                final JOptionPane optionPane = new JOptionPane("Please wait while the libraries initialize....", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, new Object[]{ }, null);
                this.setTitle("CanaryMod");
                this.setModal(true);
                this.setContentPane(optionPane);
                this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                this.pack();
                this.setVisible(true);
            }
            catch (Exception exception) {
                Canary.log.debug("Library Warning Dialog Error", exception);
            }
        }
    }

    private static final class EULAWarningDialog extends WarningDialog {
        final void create() {
            try {
                final JOptionPane optionPane = new JOptionPane("You need to agree to the EULA in order to run the server.\nGo to eula.txt for more info.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, new Object[]{ }, null);

                this.setTitle("EULA NOT ACCEPTED");
                this.setModal(true);
                this.setContentPane(optionPane);
                this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                this.pack();
                this.setVisible(true);
                this.addWindowListener(new WindowAdapter() {
                                           @Override
                                           public void windowClosed(WindowEvent e) {
                                               warningDialog = null;
                                           }
                                       }
                                      );
            }
            catch (Exception ignored) {
                Canary.log.debug("EULA Dialog error!", ignored);
                warningDialog = null;
            }
        }
    }

    private static final class BindPortWarningDialog extends WarningDialog {
        private final String error;

        BindPortWarningDialog(String error) {
            this.error = error;
        }

        final void create() {
            try {
                final JOptionPane optionPane = new JOptionPane(MessageFormat.format("The exception was: {}.\nPerhaps a server is already running on that port?", error), JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, icon, new Object[]{ }, null);

                this.setTitle("FAILED TO BIND TO PORT!");
                this.setModal(true);
                this.setContentPane(optionPane);
                this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                this.pack();
                this.setVisible(true);
                this.addWindowListener(new WindowAdapter() {
                                           @Override
                                           public void windowClosed(WindowEvent e) {
                                               warningDialog = null;
                                           }
                                       }
                                      );
            }
            catch (Exception ignored) {
                Canary.log.debug("Bind Port Dialog error!", ignored);
                warningDialog = null;
            }
        }
    }
}
