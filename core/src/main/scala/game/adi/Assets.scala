package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import scala.compiletime.uninitialized

object Assets {
    private var playerSprite: Texture = uninitialized
    private var smartEnemySprite: Texture = uninitialized
    private var dumbEnemySprite: Texture = uninitialized
    private var model: Model = uninitialized
    var loaded:Boolean = false

    def load(): Unit = {
        playerSprite = new Texture("Police.png")
        smartEnemySprite = new Texture("Audi.png")
        dumbEnemySprite = new Texture("Mini_truck.png")
        model = new Model
        model.load()
        loaded = true
    }


    def loadPlayerSprite(): Texture = playerSprite

    def loadSmartEnemySprite(): Texture = smartEnemySprite

    def loadDumbEnemySprite(): Texture = dumbEnemySprite

    def loadModel(): Model = model

    def dispose(): Unit = {
        playerSprite.dispose()
        smartEnemySprite.dispose()
        dumbEnemySprite.dispose()
        model.close()
    }
}
