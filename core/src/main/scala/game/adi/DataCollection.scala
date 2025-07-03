package game.adi

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.ScreenUtils
import java.io.{BufferedWriter, FileWriter}



class DataCollection(game:Swerve) extends Screen{
    var player:Player =_
    var enemy:Enemy =_
    val batch = game.batch
    var playerImg: Texture = _
    var enemyImg: Texture = _
    var t:Float = 0f
    val fileWriter: BufferedWriter = new BufferedWriter(new FileWriter("enemy_training_data.csv"))
    var model: Model = _



    override def show(): Unit = {
        fileWriter.write("distance,enemyVel\n") // CSV header row


        player = new Player
        playerImg = new Texture("Police.png")
        player.initSprite(playerImg, 0.4)
        enemy = new Enemy
        enemyImg = new Texture("Audi.png")
        enemy.initSprite(enemyImg, 0.4)
        enemy.setpos(200, 300)

        model = new Model
    }

    def updateFile():Unit = {
        val dist = player.pos().sub(enemy.pos())
        val mag = dist.len()
        val sign = if(dist.x < 0) -1f else 1f

        val enemyVel = enemy.velocity.x

        if(sign == 1 && enemyVel>0  ) {
            fileWriter.write(s"$mag,$enemyVel\n")
            fileWriter.flush()
        }

    }
    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        player.update(v)
        player.draw(batch)
        enemy.update(v, player, model)
        enemy.draw(batch)
        batch.end()


        updateFile()
//        t += v
//        if (t >= 0.4f) {
//            updateFile()
//            t -= 0.4f
//        }
    }


    override def resume(): Unit = {}

    override def resize(i: Int, i1: Int): Unit = {}

    override def pause(): Unit = {}

    override def hide(): Unit = {}

    override def dispose(): Unit ={
        playerImg.dispose()
        enemyImg.dispose()
        fileWriter.close()
    }



}
