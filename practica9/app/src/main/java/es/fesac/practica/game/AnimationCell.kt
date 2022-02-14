package es.fesac.practica.game

import es.fesac.practica.ui.model.game.AnimationTypeEnum
import es.fesac.practica.ui.model.game.Cell
import kotlin.math.max

class AnimationCell(x: Int, y: Int, var animationType: AnimationTypeEnum, var animationTime: Long, var delayTime: Long, var extras: IntArray?): Cell(x, y) {

    var timeElapsed: Long = 0

    fun isActive(): Boolean{
        return timeElapsed >= delayTime
    }

    fun getPercentageDone(): Float{

        return max(0.0, 1.0 * (timeElapsed - delayTime) / animationTime).toFloat()
    }

    fun tick(timeElapsed: Long){
        this.timeElapsed += timeElapsed

    }

    fun animationDone(): Boolean{
        return timeElapsed > (animationTime + delayTime)
    }
}