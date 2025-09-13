package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import scala.compiletime.uninitialized

object Assets {
    private var playerSprite: Texture = uninitialized
    private var smartEnemySprite: Texture = uninitialized
    private var dumbEnemySprite: Texture = uninitialized
    private var backgroundSprite:Texture = uninitialized
    private var model: Model = uninitialized
    var loaded: Boolean = false

    def load(): Unit = {
        playerSprite = new Texture("CarSprite.png")
        smartEnemySprite = new Texture("TruckSprite.png")
        dumbEnemySprite = new Texture("Mini_truck.png")
        backgroundSprite = new Texture("Road.png")
        model = new Model
        model.load()
        loaded = true
    }


    def loadPlayerSprite(): Texture = playerSprite

    def loadSmartEnemySprite(): Texture = smartEnemySprite

    def loadDumbEnemySprite(): Texture = dumbEnemySprite
    
    def loadBackground(): Texture = backgroundSprite

    def loadModel(): Model = model

    def dispose(): Unit = {
        playerSprite.dispose()
        smartEnemySprite.dispose()
        dumbEnemySprite.dispose()
        backgroundSprite.dispose()
        model.close()
    }
}
