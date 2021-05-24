package sk.uniza.fri.vamz

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

/**
 * Catcher
 * Object for player which is catching objects
 * @constructor
 *
 * @param pbatch
 */
class Catcher(pbatch:SpriteBatch) {
    private var yummy: Sound = Gdx.audio.newSound(Gdx.files.internal("yum.mp3"))
    private var buzzer: Sound = Gdx.audio.newSound(Gdx.files.internal("buzzer.mp3"))
    private var catcherImage: Texture = Texture(Gdx.files.internal("7remo.png"))
    private var catcherObj: Rectangle = Rectangle()
    private val batch: SpriteBatch = pbatch

    init {

        catcherObj.x = (800 / 2 - 64 / 2).toFloat()
        catcherObj.y = 20f
        catcherObj.height = 56f
        catcherObj.width = 64f
    }

    /**
     * Display
     * Displays player on any renderable screen
     */
    fun display(){
        batch.draw(catcherImage, catcherObj.x, catcherObj.y, catcherObj.width, catcherObj.height)
    }

    /**
     * Play yum
     * play yum sound when object is collected
     * @param parMute
     */
    fun playYum(parMute: Boolean) {
        if(!parMute) {
            yummy.play()
        }
    }

    /**
     * Play buzz
     * play buzzer when object is dropped
     * @param parMute
     */
    fun playBuzz(parMute: Boolean) {
        if(!parMute) {
            buzzer.play()
        }
    }

    /**
     * Hranice
     * Boundaries of catcher
     * @return
     */
    fun hranice(): Rectangle {
        return catcherObj
    }

    /**
     * Dispose
     * Dispose method for catcher
     */
    fun dispose(){
        catcherImage.dispose()
        catcherObj.set(800f,0f,1f,1f)
        buzzer.dispose()
        yummy.dispose()
    }
}