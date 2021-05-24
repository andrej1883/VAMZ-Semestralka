package sk.uniza.fri.vamz.screeny

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import sk.uniza.fri.vamz.GameManager
import sk.uniza.fri.vamz.Mazoorek

/**
 * Options window
 * Settings Screen for adjusting sound preferences
 * @property pgame
 * @constructor Create empty Options window
 */
class OptionsWindow(val pgame: Mazoorek):Screen {
    private var game: Mazoorek = pgame
    private var manager = game.manager
    private var batch = pgame.batch
    private var camera = OrthographicCamera()

    private var muteS = Rectangle()
    private var muteM = Rectangle()
    private var mPl = Rectangle()
    private var mMin = Rectangle()
    private var back = Rectangle()
    private var touch = Vector3()

    private var muteSound = Texture(Gdx.files.internal("BTNS/checkbox-off.png"))
    private var muteMusic = Texture(Gdx.files.internal("BTNS/checkbox-off.png"))
    private var musicPl = Texture(Gdx.files.internal("BTNS/plus.png"))
    private var musicMi = Texture(Gdx.files.internal("BTNS/minus.png"))
    private var backT = Texture(Gdx.files.internal("BTNS/backReleased.png"))
    private var muteSoundP = Texture(Gdx.files.internal("BTNS/checkbox.png"))
    private var muteMusicP = Texture(Gdx.files.internal("BTNS/checkbox.png"))
    private var musicPlP = Texture(Gdx.files.internal("BTNS/plusPressed.png"))
    private var musicMiP = Texture(Gdx.files.internal("BTNS/minusPressed.png"))
    private var backTP = Texture(Gdx.files.internal("BTNS/backPressed.png"))
    private var font: BitmapFont = BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false)

    init {
        camera.setToOrtho(false, 800f, 480f)

        muteS.width = 50f
        muteS.height = 50f
        muteS.x = 375f
        muteS.y = 480-56-muteS.height

        muteM.width = 50f
        muteM.height = 50f
        muteM.x = 375f
        muteM.y = muteS.y-56-muteS.height

        mPl.width = 50f
        mPl.height = 50f
        mPl.x = 400-mPl.width
        mPl.y = muteM.y-56-mPl.height

        mMin.width = 50f
        mMin.height = 50f
        mMin.x = 400+mPl.width/2
        mMin.y = muteM.y-56-mMin.height

        back.width = 100f
        back.height = 50f
        back.x = 400 -back.width/2
        back.y = mMin.y-56-back.height

    }

    override fun show() {
    }


    override fun render(delta: Float) {
        ScreenUtils.clear(Color.OLIVE)
        camera.update()
        game.batch.projectionMatrix = camera.combined

        batch.begin()
        if(!game.manager.getSMute()) {
            batch.draw(muteSound, muteS.x, muteS.y, muteS.width, muteS.height)
        } else {
            batch.draw(muteSoundP, muteS.x, muteS.y, muteS.width, muteS.height)
        }
        if(game.manager.getMLvl()>0) {
            batch.draw(muteMusic, muteM.x, muteM.y, muteM.width, muteM.height)
        } else {
            batch.draw(muteMusicP, muteM.x, muteM.y, muteM.width, muteM.height)
        }
        batch.draw(musicPl, mPl.x, mPl.y, mPl.width, mPl.height)
        batch.draw(musicMi, mMin.x, mMin.y, mMin.width, mMin.height)
        batch.draw(backT, back.x, back.y, back.width, back.height)

        font.draw(batch, "Mute sound: ", 50f, muteS.y+muteS.height/2)
        font.draw(batch, "Mute music: ", 50f, muteM.y+muteM.height/2)
        font.draw(batch, "Adjust music: ", 50f, mPl.y+mPl.height/2)
        font.draw(batch, "Music level: ${(manager.getMLvl()*100).toInt()}", mMin.x+80, mPl.y+mPl.height/2)



        if (Gdx.input.justTouched()) {
            camera.unproject(touch.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f))

            if (game.isClicked(muteS, touch)) {
                game.manager.muteSound()
            }

            if (game.isClicked(muteM, touch)) {
                game.manager.muteMusic()
            }

            if(game.isClicked(mPl, touch)){
                batch.draw(musicPlP, mPl.x, mPl.y, mPl.width, mPl.height)
                game.manager.musicUp()
            }

            if(game.isClicked(mMin, touch)){
                batch.draw(musicMiP, mMin.x, mMin.y, mMin.width, mMin.height)
                game.manager.musicDown()
            }

            if(game.isClicked(back, touch)){
                batch.draw(backTP, back.x, back.y, back.width, back.height)
                this.game.screen = MainMenuScreen(game)
                dispose()
            }
        }
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
        muteSound.dispose()
        muteMusic.dispose()
        musicPl.dispose()
        musicMi.dispose()
        backT.dispose()
        muteSoundP.dispose()
        muteMusicP.dispose()
        musicPlP.dispose()
        musicMiP.dispose()
        backTP.dispose()
        font.dispose()
    }
}
