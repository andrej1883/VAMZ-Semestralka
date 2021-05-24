package sk.uniza.fri.vamz

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils

/**
 * Game manager
 *
 * @constructor Create empty Game manager
 */
class GameManager {
    /**
     * Game state
     *
     * @constructor Create empty Game state
     */
    enum class GameState {
        /**
         * P l a y i n g
         *
         * @constructor Create empty P l a y i n g
         */
        PLAYING,

        /**
         * G a m e_o v e r
         *
         * @constructor Create empty G a m e_o v e r
         */
        GAME_OVER,

        /**
         * P a u s e d
         *
         * @constructor Create empty P a u s e d
         */
        PAUSED,

        /**
         * R e s u m e d
         *
         * @constructor Create empty R e s u m e d
         */
        RESUMED
    }

    private var state: GameState = GameState.PLAYING
    private var fObjs: ArrayList<FallingObject> = ArrayList<FallingObject>()
    private var lastDropTime: Float = 0f
    private var kebabsDropped: Int = 0
    private var kebabsCatched: Int = 0
    private var fall: Float = 200f
    private var speed: Float = 580000000f
    private var musicLvl: Float = 0.1f
    private var soundMute: Boolean = false
    private var score:Int = 0
    private var Hscore:Int = 0
    private var difficulty: GameDifficulty = GameDifficulty.EASY
    private var dropLimit: Int = 5
    private var refreshLimit: Int = 40
    private var itemLimit: Int = 4
    private val file: String = ".mazoorek"
    private var counter: Int =0

    init {
        load()
    }

    /**
     * Get score
     *
     * @return
     */
    fun getScore(): Int{
        return score
    }

    /**
     * Get hscore
     *
     * @return
     */
    fun getHscore(): Int{
        return Hscore
    }

    /**
     * Reset
     *
     */
    fun reset() {
        state = GameState.PLAYING
        fObjs.clear()
        lastDropTime = 0f
        kebabsDropped = 0
        kebabsCatched = 0
        resetMetrics()
        score = 0
        counter = 0

    }

    /**
     * Set difficulty
     *
     * @param dif
     */
    fun setDifficulty(dif:GameDifficulty) {
        difficulty = dif
        when(dif) {
            GameDifficulty.EASY -> {
                dropLimit = 6
                refreshLimit = 40
                itemLimit = 4
            }
            GameDifficulty.MEDIUM -> {
                dropLimit = 4
                refreshLimit = 60
                itemLimit = 5
            }
            GameDifficulty.HARD -> {
                dropLimit = 2
                refreshLimit = 80
                itemLimit = 6
            }
        }
    }

    /**
     * Get m lvl
     *
     * @return
     */
    fun getMLvl(): Float {
        return musicLvl
    }

    /**
     * Get s mute
     *
     * @return
     */
    fun getSMute(): Boolean {
        return soundMute
    }

    /**
     * Mute sound
     *
     */
    fun muteSound() {
        soundMute = !soundMute
    }

    /**
     * Mute music
     *
     */
    fun muteMusic() {
        musicLvl = 0.0f
    }

    /**
     * Mute music u
     *
     */
    fun muteMusicU() {
        musicLvl = 0.05f
    }

    /**
     * Music down
     *
     */
    fun musicDown() {
        if (musicLvl > 0.05) {
            musicLvl -= 0.05f
        }
        if(musicLvl == 0f){
        }
    }

    /**
     * Music up
     *
     */
    fun musicUp() {
        if (musicLvl < 1) {
            musicLvl += 0.05f
        }
    }

    /**
     * Get msc lvl
     *
     * @return
     */
    fun getMscLvl(): Float {
        return musicLvl
    }

    /**
     * Get state
     *
     * @return
     */
    fun getState(): GameState {
        return state
    }

    /**
     * Get f objs
     *
     * @return
     */
    fun getFObjs(): ArrayList<FallingObject> {
        return fObjs
    }

    /**
     * Get kebabs dropped
     *
     * @return
     */
    fun getKebabsDropped(): Int {
        return kebabsDropped
    }

    /**
     * Get kebabs catched
     *
     * @return
     */
    fun getKebabsCatched(): Int {
        return kebabsCatched
    }

    /**
     * Check
     *
     * @param catcher
     * @param camera
     */
    fun check(catcher: Catcher, camera: OrthographicCamera) {
        if (state == GameState.PLAYING) {
            checkCatcher(catcher, camera)
            checkDropped(catcher)
        }
        if(state == GameState.GAME_OVER){
            calculateScore()
            if(score > Hscore) {
                Hscore = score
                save()
            }
        }
    }
    /**
     * Check Catcher
     * checking conditions for catcher
     */
    private fun checkCatcher(catcher: Catcher, camera: OrthographicCamera) {

        if (Gdx.input.isTouched) {
            val touchPos = Vector3()
            touchPos[Gdx.input.x.toFloat(), Gdx.input.y.toFloat()] = 0f
            camera.unproject(touchPos)
            catcher.hranice().x = touchPos.x - 64 / 2
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) catcher.hranice().x -= 200 * Gdx.graphics.deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) catcher.hranice().x += 200 * Gdx.graphics.deltaTime

        if (catcher.hranice().x < 0) catcher.hranice().x = 0f
        if (catcher.hranice().x > 800 - 64) catcher.hranice().x = (800 - 64).toFloat()

    }

    /**
     * CheckDropped
     * Check condition for item spawning
     */
    private fun checkDropped(catcher: Catcher) {
        if (TimeUtils.nanoTime() - lastDropTime > speed) {
            if (fObjs.size < itemLimit) {
                spawnF()
            }
        }

        var iter = fObjs.iterator()
        while (iter.hasNext()) {
            var obj = iter.next();
            obj.hranice().y -= fall * Gdx.graphics.getDeltaTime();
            if (obj.hranice().y + 64 < 0) {
                iter.remove()
                catcher.playBuzz(soundMute)
                kebabsDropped++
                if (kebabsDropped >= dropLimit) {
                    state = GameState.GAME_OVER
                    catcher.dispose()
                }
            }
            if (obj.hranice().overlaps(catcher.hranice())) {
                kebabsCatched++
                catcher.playYum(soundMute)
                iter.remove()
                fall += 2
                speed -= 10000000
            }

            if (kebabsCatched % refreshLimit == 0) {
                resetMetrics()
                counter++
                refreshLimit +=2

            }
        }
    }
    /**
     * Spawn f
     * spawn falling object
     */
    private fun spawnF() {
        val obj = FallingObject()
        fObjs.add(obj)
        lastDropTime = TimeUtils.nanoTime().toFloat()
    }
    /**
     * Reset metrics
     * private method to reset speed falling and spawning to default
     */
    private fun resetMetrics() {
        fall = 200f
        speed = 580000000f
    }
    /**
     * Calculate score
     * method for score calculating
     */
    private fun calculateScore() {
        if(difficulty == GameDifficulty.EASY){
            score = kebabsCatched*2 - kebabsDropped*10
        }
        if(difficulty == GameDifficulty.MEDIUM){
            score = kebabsCatched*5 - kebabsDropped*10
        }
        if(difficulty == GameDifficulty.HARD){
            score = kebabsCatched*10 - kebabsDropped*10
        }
        if(score == 100000){
            score++
        }
    }

    /**
     * Set state
     * setter for game state
     * @param pstate
     */
    fun setState(pstate: GameState){
        state = pstate
    }

    /**
     * Load
     * Load saved game from local file
     */
    fun load() {
        try {
            var filehandle: FileHandle = Gdx.files.external(file)
            var string = filehandle.readString()
            Hscore = Integer.parseInt(string)
        } catch (e: Throwable) {

        }
    }

    /**
     * Save
     * Save highest score to local file
     */
    fun save() {
        try {
            var filehandle: FileHandle = Gdx.files.external(file)
            filehandle.writeString(Integer.toString(Hscore), true)
        } catch (e: Throwable) {
        }
    }

}