package sk.uniza.fri.vamz

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle

/**
 * Falling object
 *
 * @constructor Create empty Falling object
 */
class FallingObject() {
    private var kebabImage: Texture = Texture(Gdx.files.internal("removedKebab.png"))
    private var kebabObj: Rectangle = Rectangle()

    init {

        kebabObj.x = MathUtils.random(0f,800-64f)
        kebabObj.y = 480 - 45f
        kebabObj.height = 45f
        kebabObj.width = 64f
    }

    /**
     * Display
     * Method for drawing of falling object to any renderable screen
     * @param batch
     */
    fun display(batch:SpriteBatch){
        batch.draw(kebabImage, kebabObj.x, kebabObj.y, kebabObj.width, kebabObj.height)
    }

    /**
     * Hranice
     * Object boundaries
     * @return
     */
    fun hranice(): Rectangle {
        return kebabObj
    }
}