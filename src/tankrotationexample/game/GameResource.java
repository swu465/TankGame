package tankrotationexample.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class GameResource {
    private static Map<String, BufferedImage> resources;
    static{
        GameResource.resources = new HashMap<>();
        try {
            GameResource.resources.put("tankOne",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png"))));
            GameResource.resources.put("tankTwo",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png"))));
            GameResource.resources.put("breakableWall",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall2.gif"))));
            GameResource.resources.put("unbreakableWall",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall1.gif"))));
            GameResource.resources.put("bullet",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("shell.gif"))));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }

    }
    public static BufferedImage get(String name){
        return resources.get(name);
    }
}
