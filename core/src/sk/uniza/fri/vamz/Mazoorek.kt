package sk.uniza.fri.vamz

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import sk.uniza.fri.vamz.screeny.GameScreen
import sk.uniza.fri.vamz.screeny.MainMenuScreen


/**
 * Mazoorek
 * Main class of game
 * @constructor Create empty Mazoorek
 */
class Mazoorek : Game() {

    public lateinit var batch: SpriteBatch
    val manager:GameManager = GameManager()

    override fun create() {
        batch = SpriteBatch()
        this.setScreen(MainMenuScreen(this))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        this.getScreen().dispose()
        batch.dispose()
    }

    /**
     * Is clicked
     * Method for checking, if object was touched
     * @param pBound
     * @param touch
     * @return
     */
    fun isClicked(pBound: Rectangle, touch: Vector3): Boolean {
        if (touch.x >= pBound.x && touch.x <= pBound.x + pBound.width) {
            if (touch.y >= pBound.y && touch.y <= pBound.y + pBound.width) {
                return true;
            }
        }
        return false
    }

}
