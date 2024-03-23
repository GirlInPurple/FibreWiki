package xyz.blurple.fibrewiki.git;

import net.fabricmc.loader.api.FabricLoader;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import xyz.blurple.fibrewiki.FibreWiki;
import xyz.blurple.fibrewiki.config.ModConfigs;
import xyz.blurple.fibrewiki.util.VerboseLogger;

import java.io.File;
import java.nio.file.Path;

public class GitHandler {

    public static final Path REPO_PATH = FabricLoader.getInstance().getGameDir().resolve(ModConfigs.WEB_ROOT).resolve("wiki");
    public static Repository repo = null;

    public static void repositoryStartup() {
        try {
            // Initialize a new repository
            InitCommand initCommand = Git.init();
            initCommand.setDirectory(new File(REPO_PATH.toUri()));
            repo = initCommand.call().getRepository();

            VerboseLogger.info("Repository created at: " + repo.getDirectory());
        } catch (GitAPIException e) {
            FibreWiki.LOGGER.info("Error during Git Startup! "+e.getMessage());
            e.printStackTrace();
        }
    }
}
