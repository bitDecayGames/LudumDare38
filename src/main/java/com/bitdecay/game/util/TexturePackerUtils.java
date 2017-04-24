package com.bitdecay.game.util;

import com.bytebreakstudios.animagic.texture.AnimagicTexturePacker;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;

/**
 * Decides whether or not to pack textures based on the lastModified date of folder and files within the resources/img/packable folder
 */
public class TexturePackerUtils {

    private static final Logger log = LogManager.getLogger(TexturePackerUtils.class);

    private TexturePackerUtils(){}

    public static void pack(){
        int needToPackResult = needToPack();
        if (needToPackResult != 0) {
            log.info("Need to pack images");
            AnimagicTexturePacker.pack(new File("src/main/resources/img/packable"), new File("src/main/resources/img/packed"));
            if (needToPackResult < 0){
                log.error("NOTE: It was detected that this is the first time you've run the packer.  You will need to re-run the game for the packed files to appear.  Exiting now.");
                System.exit(0);
            }
        } else log.info("Did not need to pack images");
    }

    /**
     * Only run the texture packer if the ATLAS file is older than any of the files within the img/packable folder
     * @return 0 for false, > 0 for true, < 0 for uh oh
     */
    private static int needToPack(){
        // this has the src/main/resources in front because it refers to the file on the file system
        // this will only ever be called when we are developing, never when it is run in PROD mode
        File atlasFile = new File("src/main/resources/img/packed/main.atlas");
        if (!atlasFile.exists()) return -1;
        long atlasFileModifiedDate = atlasFile.lastModified();

        File packableFolder = new File("src/main/resources/img/packable");
        if (!packableFolder.exists()) return 0;
        long mostRecentLastModifiedDateInPacked = recursiveFileLastModified(packableFolder);

        return mostRecentLastModifiedDateInPacked > atlasFileModifiedDate ? 1 : 0;
    }

    private static long recursiveFileLastModified(File f){
        if (f == null) return 0;
        else if (!f.exists()) return 0;
        else if (f.isDirectory()) {
            long mostRecent = f.lastModified();
            for (File sub : f.listFiles()){
                long curMod = recursiveFileLastModified(sub);
                if (curMod > mostRecent) mostRecent = curMod;
            }
            return mostRecent;
        }
        else return f.lastModified();
    }
}
