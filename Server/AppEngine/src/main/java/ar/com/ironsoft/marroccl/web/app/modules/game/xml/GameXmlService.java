package ar.com.ironsoft.marroccl.web.app.modules.game.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import ar.com.ironsoft.marroccl.web.app.modules.game.model.Commentary;
import ar.com.ironsoft.marroccl.web.app.modules.game.xml.model.CommentaryElement;

import com.google.inject.Singleton;

/**
 * @author Tomas de Priede
 */
@Singleton
public class GameXmlService {

    private Logger logger = Logger.getLogger(GameXmlService.class
            .getSimpleName());

    public Commentary parseGameXml(String xml) {
        try {
            InputStream stream = new ByteArrayInputStream(
                    xml.getBytes(StandardCharsets.UTF_8));
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            //
            CommentaryElement commentaryElement = new CommentaryElement(
                    document.getRootElement());
            commentaryElement.parse();
            return commentaryElement;
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to parse the incidents game", e);
        }
    }
}
