package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import ar.com.ironsoft.marroccl.web.app.modules.game.daos.CommentaryDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.GameGoalDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.daos.MessageDao;
import ar.com.ironsoft.marroccl.web.app.modules.game.model.TitleMessage;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.Message;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class CommentaryService {

    private Logger logger = Logger.getLogger(CommentaryService.class
            .getSimpleName());
    private CommentaryDao commentaryDao;
    private MessageDao messageDao;
    private GameGoalDao gameGoalDao;

    public void saveCommentary(Commentary commentary) {
        logger.log(Level.INFO, "Removing old commentary");
        // Look for existent game id;
        commentaryDao.delete(Commentary.class, commentary.getId());
        messageDao.deleteEntities(commentary.getMessageList());
        gameGoalDao.deleteAll();
        //
        logger.log(Level.INFO,
                "Saving new commentary with id: " + commentary.getGameId());
        //
        commentaryDao.save(commentary);
        messageDao.saveAll(commentary.getMessageList());
    }

    public TitleMessage parseTitleMessage(Message message) {
        TitleMessage titleMessage = new TitleMessage();
        String type = message.getType().toLowerCase();
        String comment = message.getComment().trim();
        if ("goal".equals(type)) {
            titleMessage.setTitle(comment.split("\\.", 2)[0]);
            titleMessage.setMessage(comment.split("\\.", 2)[1]);
        } else if ("substitution".equals(type)) {
            titleMessage.setTitle(StringUtils.split(comment, ",")[0]);
            titleMessage.setMessage(StringUtils.split(comment, ",")[1].trim());
            titleMessage.setMessage(StringUtils.capitalize(titleMessage
                    .getMessage()));
        } else if ("yellow card".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.split(team, ")")[0];
            titleMessage.setTitle("Amarilla para " + team);
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("red card".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.split(team, ")")[0];
            titleMessage.setTitle("Roja para " + team);
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("free kick lost".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.split(team, ")")[0];
            String typeText = StringUtils
                    .splitByWholeSeparator(comment, " de ")[0];
            titleMessage.setTitle(typeText + " de " + team);
            //
            comment = StringUtils.splitByWholeSeparator(comment, "(")[0];
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("free kick won".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.split(team, ")")[0];
            titleMessage.setTitle("Tiro libre para " + team);
            //
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("offside".equals(type)) {
            String team = StringUtils.splitByWholeSeparator(comment, "lugar, ")[1];
            team = StringUtils.splitByWholeSeparator(team, ". ")[0];
            comment = StringUtils.splitByWholeSeparator(comment, ". ")[1];
            titleMessage.setTitle("Offside de " + team);
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("corner".equals(type)) {
            String team = StringUtils.splitByWholeSeparator(comment,
                    "esquina, ")[1];
            team = StringUtils.splitByWholeSeparator(team, ". ")[0];
            comment = StringUtils.splitByWholeSeparator(comment, ". ")[1];
            titleMessage.setTitle("Saque de esquina para " + team);
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("attempt blocked".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.splitByWholeSeparator(team, "),")[0];
            titleMessage.setTitle("Remate rechazado de " + team);
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("attempt saved".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.split(team, ")")[0];
            titleMessage.setTitle("Tiro de " + team + " atajado");
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else if ("miss".equals(type)) {
            String team = StringUtils.split(comment, "(")[1];
            team = StringUtils.split(team, ")")[0];
            titleMessage.setTitle("Disparo desviado de " + team);
            titleMessage.setMessage(StringUtils.capitalize(comment));
        } else {
            titleMessage.setTitle(comment);
            titleMessage.setMessage("");
        }
        titleMessage.setTitle(titleMessage.getTitle().trim());
        titleMessage.setMessage(titleMessage.getMessage().trim());
        return titleMessage;
    }

    public String[] parsePlayer(Message message) {
        String playerName = "";
        String playerName2 = "";
        String comment = message.getComment();
        String type = message.getType().toLowerCase();
        //
        if ("goal".equals(type)) {
            String goalMsg = StringUtils.splitByWholeSeparator(comment, "(")[0];
            playerName = StringUtils.splitByWholeSeparator(goalMsg, ".")[1];
        } else if ("yellow card".equals(type) || "red card".equals(type)
                || "free kick won".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment, "(")[0];
        } else if ("free kick lost".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment, "(")[0];
            playerName = StringUtils.remove(playerName, "Falta de");
            playerName = StringUtils.remove(playerName, "Mano de");
        } else if ("offside".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment,
                    "profundidad pero")[1];
            playerName = StringUtils.splitByWholeSeparator(playerName,
                    "estaba en")[0];
        } else if ("corner".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment,
                    "cometido por")[1];
        } else if ("attempt blocked".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment, "(")[0];
            playerName = StringUtils.splitByWholeSeparator(playerName,
                    "rechazado de")[1];
        } else if ("attempt saved".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment, "(")[0];
            playerName = StringUtils.splitByWholeSeparator(playerName, ". ")[1];
        } else if ("miss".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment, "(")[0];
            playerName = StringUtils.splitByWholeSeparator(playerName,
                    "desviado de")[1];
        } else if ("substitution".equals(type)) {
            playerName = StringUtils.splitByWholeSeparator(comment, "al campo")[1];
            playerName = StringUtils.splitByWholeSeparator(playerName,
                    "sustituyendo a")[0];
            //
            playerName2 = StringUtils.splitByWholeSeparator(comment,
                    "sustituyendo a")[1];
        }
        //
        return new String[] { playerName.trim(), playerName2.trim()};
    }

    @Inject
    public void setGameGoalDao(GameGoalDao gameGoalDao) {
        this.gameGoalDao = gameGoalDao;
    }

    @Inject
    public void setCommentaryDao(CommentaryDao commentaryDao) {
        this.commentaryDao = commentaryDao;
    }

    @Inject
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
