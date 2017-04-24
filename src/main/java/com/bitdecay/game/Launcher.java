package com.bitdecay.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bitdecay.game.util.RunMode;
import com.bitdecay.game.util.TexturePackerUtils;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The launcher now reads the program args as well as the lastModified files to determine whether or not to run the texture packer.
 */
public class Launcher {

    private static final Logger log = LogManager.getLogger(Launcher.class);
    public static Config conf = ConfigFactory.load("conf/application.conf");

    public static void main(String[] args) {
        //BasicConfigurator.configure(); // TODO: configure log4j?

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Launcher.conf.getInt("resolution.default.width");
        config.height = Launcher.conf.getInt("resolution.default.height");
        config.title = Launcher.conf.getString("title");
        config.resizable = false;

        RunMode runMode = RunMode.PROD;
        if (args != null && args.length > 0) {
            // check for command line arguments
            if (arg(args, "dev")) runMode = RunMode.DEV;
        }
        log.info("Run Mode: {}", runMode);
        if (runMode == RunMode.DEV) TexturePackerUtils.pack();

        new LwjglApplication(new MyGame(runMode), config);
    }

    private static boolean arg(String[] args, String arg){
        for (String cmd : args) if (cmd.equalsIgnoreCase(arg)) return true;
        return false;
    }
}
