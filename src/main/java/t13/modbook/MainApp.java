package t13.modbook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import t13.modbook.commons.core.Config;
import t13.modbook.commons.core.LogsCenter;
import t13.modbook.commons.core.Version;
import t13.modbook.commons.exceptions.DataConversionException;
import t13.modbook.commons.util.ConfigUtil;
import t13.modbook.commons.util.StringUtil;
import t13.modbook.logic.Logic;
import t13.modbook.logic.LogicManager;
import t13.modbook.model.ModBook;
import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.ReadOnlyModBook;
import t13.modbook.model.ReadOnlyUserPrefs;
import t13.modbook.model.UserPrefs;
import t13.modbook.model.util.SampleDataUtil;
import t13.modbook.storage.JsonModBookStorage;
import t13.modbook.storage.JsonUserPrefsStorage;
import t13.modbook.storage.ModBookStorage;
import t13.modbook.storage.Storage;
import t13.modbook.storage.StorageManager;
import t13.modbook.storage.UserPrefsStorage;
import t13.modbook.ui.Ui;
import t13.modbook.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ModBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ModBookStorage modBookStorage = new JsonModBookStorage(userPrefs.getModBookFilePath());
        storage = new StorageManager(modBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s mod book and {@code userPrefs}.
     * <br> The data from the sample mod book will be used instead if {@code storage}'s mod book is not
     * found, or an empty mod book will be used instead if errors occur when reading {@code storage}'s
     * mod book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {

        Optional<ReadOnlyModBook> modBookOptional;
        ReadOnlyModBook initialModBook;

        try {
            modBookOptional = storage.readModBook();
            if (!modBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ModBook");
            }
            initialModBook = modBookOptional.orElseGet(SampleDataUtil::getSampleModBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ModBook");
            initialModBook = new ModBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ModBook");
            initialModBook = new ModBook();
        }

        return new ModelManager(initialModBook, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ModBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ModBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Mod Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
